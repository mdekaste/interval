package intervalmap.interfaces

import interval.interfaces.Interval
import interval.interfaces.Left
import interval.interfaces.Right

interface IntervalMap<K : Comparable<K>, V> : Map<Interval<K>, V> {

    fun firstEntry(): Map.Entry<Left.Open<K>, V>
    fun lastEntry(): Map.Entry<Right.Open<K>, V>
    fun getEntry(key: K): Map.Entry<Interval<K>, V>
    fun getAll(key: Interval<K>): Collection<V>
}
