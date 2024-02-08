package v2.intervalmap

import interval.implementations.until
import v2.Incrementable
import v2.openness.Closed
import v2.openness.ClosedOpen
import v2.openness.Interval
import v2.openness.Left
import v2.openness.Open
import v2.openness.OpenClosed
import v2.openness.Right
import v2.openness.from
import v2.openness.fromAndUntil
import v2.openness.until
import java.util.*

class TreeIntervalMap<K : Comparable<K>, V>(
    initialValue: V,
    incrementable: Incrementable<K>,
) : MutableIntervalMap<K, V>, Incrementable<K> by incrementable {
    private val _backingMap =
        TreeMap<K?, V>(nullsLast()).apply {
            set(null, initialValue)
        }

    override fun set(
        interval: Interval<K>,
        value: V,
    ) = when (interval) {
        is Closed<K> -> setClosed(interval, value)
        is ClosedOpen<K> -> setLeftbound(interval, value)
        is OpenClosed<K> -> setRightbound(interval, value)
        is Open<K> -> setOpen(interval, value)
    }

    private fun setClosed(
        between: Closed<K>,
        value: V,
    ) {
        val fromBoundValue = _backingMap.ceilingEntry(between.from).value
        val untilBoundValue = _backingMap.higherEntry(between.until).value

        submapSelection(between).clear()

        if (fromBoundValue != value) {
            _backingMap[between.from] = fromBoundValue
        }
        if (untilBoundValue != value) {
            _backingMap[between.until] = value
        }
    }

    private fun setLeftbound(
        closedOpen: ClosedOpen<K>,
        value: V,
    ) {
        val fromBoundValue = _backingMap.ceilingEntry(closedOpen.from).value

        submapSelection(closedOpen).clear()

        if (fromBoundValue != value) {
            _backingMap[closedOpen.from] = fromBoundValue
        }

        _backingMap[null] = value
    }

    private fun setRightbound(
        openClosed: OpenClosed<K>,
        value: V,
    ) {
        val untilBoundValue = _backingMap.higherEntry(openClosed.until).value

        submapSelection(openClosed).clear()

        if (untilBoundValue != value) {
            _backingMap[openClosed.until] = value
        }
    }

    private fun setOpen(
        open: Open<K>,
        value: V,
    ) {
        submapSelection(open).clear()
        _backingMap[null] = value
    }

    private fun submapSelection(interval: Interval<K>): NavigableMap<K?, V> =
        when (interval) {
            is Closed -> _backingMap.subMap(interval.from, true, interval.until, true)
            is ClosedOpen -> _backingMap.tailMap(interval.from, true)
            is OpenClosed -> _backingMap.headMap(interval.until, true)
            is Open -> _backingMap
        }

    private data class Wrapper<V>(
        val value: V,
    )

    override fun merge(
        interval: Interval<K>,
        merge: (V) -> V,
    ) {
        fun mergeStrategy(previous: Wrapper<V>? = null): V {
            val iterator = submapSelection(interval).descendingMap().iterator()
            var previousValue = previous
            while (iterator.hasNext()) {
                val currentEntry = iterator.next()
                val currentValue = Wrapper(merge(currentEntry.value))
                if (currentValue != previousValue) {
                    currentEntry.setValue(currentValue.value)
                } else {
                    iterator.remove()
                }
                previousValue = currentValue
            }
            return previousValue!!.value
        }
        when (interval) {
            is Closed -> {
                val higherValue = _backingMap.higherEntry(interval.until).value
                val mergedHigherValue = merge(_backingMap.ceilingEntry(interval.until).value)
                val vanValue = _backingMap.ceilingEntry(interval.from).value

                val previousValue = mergeStrategy(Wrapper(mergedHigherValue))

                if (mergedHigherValue != higherValue) {
                    _backingMap[interval.until] = mergedHigherValue
                }
                if (vanValue != previousValue) {
                    _backingMap[interval.from] = vanValue
                }
            }

            is OpenClosed -> {
                val totBoundValue = _backingMap.higherEntry(interval.until).value
                val mergedTotBoundValue = merge(_backingMap.ceilingEntry(interval.until).value)

                mergeStrategy(Wrapper(mergedTotBoundValue))

                if (totBoundValue != mergedTotBoundValue) {
                    _backingMap[interval.until] = mergedTotBoundValue
                }
            }

            is ClosedOpen -> {
                val vanBoundValue = _backingMap.ceilingEntry(interval.from).value

                val previousValue = mergeStrategy()

                if (vanBoundValue != previousValue) {
                    _backingMap[interval.from] = vanBoundValue
                }
            }

            is Open -> {
                mergeStrategy()
            }
        }
    }

    override fun firstEntry(): Map.Entry<Left.Open<K>, V> {
        val (until, value) = _backingMap.firstEntry()
        val interval = until(until, this)
        return AbstractMap.SimpleEntry(interval, value)
    }

    override fun lastEntry(): Map.Entry<Right.Open<K>, V> {
        val from: K? = _backingMap.lowerKey(null)
        val (_, value) = _backingMap.lastEntry()
        return AbstractMap.SimpleEntry(from(from, this), value)
    }

    override fun getEntry(key: K): Map.Entry<Interval<K>, V> {
        val from: K? = _backingMap.lowerKey(key)
        val (until, value) = _backingMap.higherEntry(key)
        return AbstractMap.SimpleEntry(fromAndUntil(from, until, this), value)
    }

    override fun getAll(key: interval.interfaces.Interval<K>) = submapSelection(key).values

    override val entries: Set<Map.Entry<Interval<K>, V>>
        get() =
            buildSet {
                _backingMap.firstEntry().let { (until, value) -> add(AbstractMap.SimpleEntry(null until until, value)) }
                _backingMap.entries.zipWithNext { (from, _), (until, value) -> add(AbstractMap.SimpleEntry(from until until, value)) }
            }
    override val keys: Set<interval.interfaces.Interval<K>>
        get() =
            buildSet {
                add(null until _backingMap.firstKey())
                _backingMap.keys.zipWithNext { from, until -> add(from until until) }
            }
    override val size: Int
        get() = _backingMap.size
    override val values: Collection<V>
        get() = _backingMap.values

    override fun containsKey(key: interval.interfaces.Interval<K>) =
        _backingMap.containsKey(key.until) && _backingMap.lowerKey(key.until) == key.from

    override fun containsValue(value: V) = _backingMap.containsValue(value)

    override fun get(key: interval.interfaces.Interval<K>): V? =
        _backingMap[key.until].takeIf {
            _backingMap.lowerKey(
                key.until,
            ) == key.from
        }

    override fun isEmpty() = false

    override fun toString() = _backingMap.toString()
}
