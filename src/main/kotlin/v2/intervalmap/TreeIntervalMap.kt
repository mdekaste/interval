package v2.intervalmap

import v2.Incrementable
import v2.openness.Interval
import v2.openness.Left
import v2.openness.Right
import java.util.*

class TreeIntervalMap<K : Comparable<K>, V>(
    initialValue: V,
    incrementable: Incrementable<K>
): MutableIntervalMap<K, V>, Incrementable<K> by incrementable {
    private val _backingMap = TreeMap<K?, V>(nullsLast()).apply {
        set(null, initialValue)
    }

    override fun get(key: Interval<K>): V? {
        TODO("Not yet implemented")
    }

    override fun set(interval: Interval<K>, value: V) {
        TODO("Not yet implemented")
    }

    override fun merge(interval: Interval<K>, merge: (V) -> V) {
        TODO("Not yet implemented")
    }

    override fun firstEntry(): Map.Entry<Left.Open<K>, V> {
        TODO("Not yet implemented")
    }

    override fun lastEntry(): Map.Entry<Right.Open<K>, V> {
        TODO("Not yet implemented")
    }

    override fun getEntry(key: K): Map.Entry<Interval<K>, V> {
        TODO("Not yet implemented")
    }

    override fun get(key: K): V {
        TODO("Not yet implemented")
    }

    override val entries: Set<Map.Entry<Interval<K>, V>>
        get() = TODO("Not yet implemented")
    override val keys: Set<Interval<K>>
        get() = TODO("Not yet implemented")
    override val size: Int
        get() = TODO("Not yet implemented")
    override val values: Collection<V>
        get() = TODO("Not yet implemented")

    /**
     * An IntervalMap by definition cannot be empty,
     */
    override fun isEmpty() = false

    override fun containsValue(value: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsKey(key: Interval<K>): Boolean {
        TODO("Not yet implemented")
    }
}