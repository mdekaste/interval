package intervalmap.interfaces

import interval.interfaces.Interval

interface IntervalMap<K : Comparable<K>, V> : Map<Interval<K>, V> {

    fun firstEntry(): Map.Entry<Interval<K>, V>
    fun lastEntry(): Map.Entry<Interval<K>, V>
    fun getEntry(key: K): Map.Entry<Interval<K>, V>
    fun getAll(key: Interval<K>): Collection<V>
}