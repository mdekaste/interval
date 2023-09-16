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
    pairs: Array<out Pair<Interval<K>, V>> = emptyArray()
) : MutableIntervalMap<K, V> {

    private val _backingMap = TreeMap<K?, V>(nullsLast())

    init {
        _backingMap[null] = initialValue
        for ((interval, value) in pairs) {
            set(interval, value)
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
        is RightBound -> _backingMap.headMap(interval.from, true)
        is Open -> _backingMap
    }


    override fun merge(interval: Interval<K>, merge: (V) -> V) {
        TODO("Not yet implemented")
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
fun <K : Comparable<K>, V> nullIntervalMap(): IntervalMap<K, V?> = TreeIntervalMap(null)

context(Incrementable<K>)
fun <K : Comparable<K>, V> mutableIntervalMapOf(
    defaultValue: V,
    vararg pairs: Pair<Interval<K>, V>
): MutableIntervalMap<K, V> = TreeIntervalMap(defaultValue, pairs)

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(defaultValue: V, vararg pairs: Pair<Interval<K>, V>): IntervalMap<K, V> =
    TreeIntervalMap(defaultValue, pairs)

fun <K : Comparable<K>, V> IntervalMap<K, V?>.asNonNullValueIntervalMap(): IntervalMap<K, V & Any>? {
    for (value in values) {
        if (value == null) {
            return null
        }
    }
    @Suppress("UNCHECKED_CAST")
    return this as IntervalMap<K, V & Any>
}

fun <K : Comparable<K>, V> MutableIntervalMap<K, V?>.asNonNullValueIntervalMap(): MutableIntervalMap<K, V & Any>? {
    for (value in values) {
        if (value == null) {
            return null
        }
    }
    @Suppress("UNCHECKED_CAST")
    return this as MutableIntervalMap<K, V & Any>
}

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