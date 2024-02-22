package v2.intervalmap

import v2.Incrementable
import v2.openness.Interval
import v2.openness.Left
import v2.openness.Right

interface IntervalMap<K : Comparable<K>, V> : Map<Interval<K>, V>, Incrementable<K> {
    fun firstEntry(): Map.Entry<Left.Open<K>, V>

    fun lastEntry(): Map.Entry<Right.Open<K>, V>

    operator fun get(key: K): V

    fun getEntry(key: K): Map.Entry<Interval<K>, V>

    override fun isEmpty(): Boolean = false
}
