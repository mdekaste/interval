package intervalmap.interfaces

import incrementable.Incrementable
import interval.implementations.from
import interval.implementations.until
import interval.interfaces.*
import java.util.*
import java.util.AbstractMap.SimpleEntry

context(Incrementable<K>)
private class TreeIntervalMap<K : Comparable<K>, V>(
    initialValue: V,
    pairs: Array<out Pair<Interval<K>, V>> = emptyArray(),
    onConflict: ((V, V) -> V)? = null
) : MutableIntervalMap<K, V> {

    private val _backingMap = TreeMap<K?, V>(nullsLast())

    init {
        _backingMap[null] = initialValue
        for ((interval, value) in pairs) {
            when(onConflict){
                null -> set(interval, value)
                else -> merge(interval){ onConflict(it, value) }
            }
        }
    }

    override fun set(interval: Interval<K>, value: V) = when (interval) {
        is Closed<K> -> setClosed(interval, value)
        is LeftBound<K> -> setLeftbound(interval, value)
        is RightBound<K> -> setRightbound(interval, value)
        is Open<K> -> setOpen(interval, value)
    }

    private fun setClosed(closed: Closed<K>, value: V) {
        val fromBoundValue = _backingMap.ceilingEntry(closed.from).value
        val untilBoundValue = _backingMap.higherEntry(closed.to).value

        submapSelection(closed).clear()

        if (fromBoundValue != value) {
            _backingMap[closed.from] = fromBoundValue
        }
        if (untilBoundValue != value) {
            _backingMap[closed.to] = value
        }
    }

    private fun setLeftbound(leftBound: LeftBound<K>, value: V) {
        val fromBoundValue = _backingMap.ceilingEntry(leftBound.from).value

        submapSelection(leftBound).clear()

        if (fromBoundValue != value) {
            _backingMap[leftBound.from] = fromBoundValue
        }

        _backingMap[null] = value
    }

    private fun setRightbound(rightBound: RightBound<K>, value: V) {
        val untilBoundValue = _backingMap.higherEntry(rightBound.to).value

        submapSelection(rightBound).clear()

        if (untilBoundValue != value) {
            _backingMap[rightBound.to] = value
        }
    }

    private fun setOpen(open: Open<K>, value: V) {
        submapSelection(open).clear()
        _backingMap[null] = value
    }

    private fun submapSelection(interval: Interval<K>): NavigableMap<K?, V> = when (interval) {
        is Closed -> _backingMap.subMap(interval.from, true, interval.to, true)
        is LeftBound -> _backingMap.tailMap(interval.from, true)
        is RightBound -> _backingMap.headMap(interval.to, true)
        is Open -> _backingMap
    }


    private data class Wrapper<V>(
        val value : V
    )

    override fun merge(interval: Interval<K>, merge: (V) -> V) {
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
                val higherValue = _backingMap.higherEntry(interval.to).value
                val mergedHigherValue = merge(_backingMap.ceilingEntry(interval.to).value)
                val vanValue = _backingMap.ceilingEntry(interval.from).value

                val previousValue = mergeStrategy(Wrapper(mergedHigherValue))

                if (mergedHigherValue != higherValue) {
                    _backingMap[interval.to] = mergedHigherValue
                }
                if (vanValue != previousValue) {
                    _backingMap[interval.from] = vanValue
                }
            }

            is RightBound -> {
                val totBoundValue = _backingMap.higherEntry(interval.to).value
                val mergedTotBoundValue = merge(_backingMap.ceilingEntry(interval.to).value)

                mergeStrategy(Wrapper(mergedTotBoundValue))

                if (totBoundValue != mergedTotBoundValue) {
                    _backingMap[interval.to] = mergedTotBoundValue
                }
            }

            is LeftBound -> {
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

    override fun firstEntry(): Map.Entry<Interval<K>, V> {
        val from: K? = null
        val (until, value) = _backingMap.firstEntry()
        return SimpleEntry(from until until, value)
    }

    override fun lastEntry(): Map.Entry<Interval<K>, V> {
        val from: K? = _backingMap.lowerKey(null)
        val (until, value) = _backingMap.lastEntry()
        return SimpleEntry(from until until, value)
    }

    override fun getEntry(key: K): Map.Entry<Interval<K>, V> {
        val from: K? = _backingMap.lowerKey(key)
        val (until, value) = _backingMap.higherEntry(key)
        return SimpleEntry(from until until, value)
    }


    override fun getAll(key: Interval<K>) = submapSelection(key).values


    override val entries: Set<Map.Entry<Interval<K>, V>>
        get() = buildSet {
            _backingMap.firstEntry().let { (until, value) -> add(SimpleEntry(null until until, value)) }
            _backingMap.entries.zipWithNext { (from, _), (until, value) -> add(SimpleEntry(from until until, value)) }
        }
    override val keys: Set<Interval<K>>
        get() = buildSet {
            add(null until _backingMap.firstKey())
            _backingMap.keys.zipWithNext { from, until -> add(from until until) }
        }
    override val size: Int
        get() = _backingMap.size
    override val values: Collection<V>
        get() = _backingMap.values

    override fun containsKey(key: Interval<K>) =
        _backingMap.containsKey(key.to) && _backingMap.lowerKey(key.to) == key.from

    override fun containsValue(value: V) = _backingMap.containsValue(value)

    override fun get(key: Interval<K>): V? = _backingMap[key.to].takeIf { _backingMap.lowerKey(key.to) == key.from }

    override fun isEmpty() = false

    override fun toString() = _backingMap.toString()
}

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(): IntervalMap<K, V?> = TreeIntervalMap(null)

context(Incrementable<K>)
fun <K : Comparable<K>, V> mutableIntervalMapOf(): MutableIntervalMap<K, V?> = TreeIntervalMap(null)

context(Incrementable<K>)
fun <K : Comparable<K>, V> mutableIntervalMapOf(
    defaultValue: V,
    vararg pairs: Pair<Interval<K>, V>
): MutableIntervalMap<K, V> = TreeIntervalMap(defaultValue, pairs)

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(defaultValue: V, vararg pairs: Pair<Interval<K>, V>): IntervalMap<K, V> =
    TreeIntervalMap(defaultValue, pairs)

context(Incrementable<K>)
fun <K : Comparable<K>, V> Iterable<V>.associateByInterval(
    keySelector: (V) -> Interval<K>
): IntervalMap<K, V?> = TreeIntervalMap(null as V?).apply {
    this@associateByInterval.forEach { value ->
        set(
            keySelector(value),
            value
        )
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, M : MutableIntervalMap<K, V>> Iterable<V>.associateByIntervalTo(
    destination: M,
    keySelector: (V) -> Interval<K>
): M = destination.apply {
    this@associateByIntervalTo.forEach { value ->
        set(
            keySelector(value),
            value
        )
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, T> Iterable<T>.associateByInterval(
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V
): IntervalMap<K, V?> = TreeIntervalMap(null as V?).apply {
    this@associateByInterval.forEach { value ->
        set(
            keySelector(value),
            valueTransform(value)
        )
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, T, M : MutableIntervalMap<K, V>> Iterable<T>.associateByIntervalTo(
    destination: M,
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V
): M = destination.apply {
    this@associateByIntervalTo.forEach { value ->
        set(
            keySelector(value),
            valueTransform(value)
        )
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, T> Iterable<T>.associateByInterval(
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
    defaultValue: V
): IntervalMap<K, V> = TreeIntervalMap(defaultValue).apply {
    this@associateByInterval.forEach { value ->
        set(
            keySelector(value),
            valueTransform(value)
        )
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V> Iterable<V>.groupByInterval(
    keySelector: (V) -> Interval<K>
) : IntervalMap<K, List<V>> = TreeIntervalMap<K, List<V>>(emptyList()).apply {
    this@groupByInterval.forEach { value ->
        merge(keySelector(value)) { previous ->
            previous + value
        }
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, M : MutableIntervalMap<K, List<V>>> Iterable<V>.groupByIntervalTo(
    destination: M,
    keySelector: (V) -> Interval<K>
) : M = destination.apply {
    this@groupByIntervalTo.forEach { value ->
        merge(keySelector(value)) { previous ->
            previous + value
        }
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, T> Iterable<T>.groupByInterval(
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
) : IntervalMap<K, List<V>> = TreeIntervalMap<K, List<V>>(emptyList()).apply {
    this@groupByInterval.forEach { value ->
        merge(keySelector(value)) { previous ->
            previous + valueTransform(value)
        }
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V, T, M : MutableIntervalMap<K, List<V>>> Iterable<T>.groupByInterval(
    destination: M,
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
) : M = destination.apply {
    this@groupByInterval.forEach { value ->
        merge(keySelector(value)) { previous ->
            previous + valueTransform(value)
        }
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V ,T> Iterable<T>.groupByInterval(
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
    defaultValue: V,
    onConflict: (V, V) -> V
) : IntervalMap<K, V> = TreeIntervalMap(defaultValue).apply {
    this@groupByInterval.forEach { value ->
        merge(keySelector(value)) { previous ->
            onConflict(previous, valueTransform(value))
        }
    }
}

context(Incrementable<K>)
fun <K : Comparable<K>, V ,T, M : MutableIntervalMap<K, V>> Iterable<T>.groupByInterval(
    destination: M,
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
    onConflict: (V, V) -> V
) : M = destination.apply {
    this@groupByInterval.forEach { value ->
        merge(keySelector(value)) { previous ->
            onConflict(previous, valueTransform(value))
        }
    }
}
