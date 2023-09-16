package intervalmap.interfaces

import interval.interfaces.Interval

interface MutableIntervalMap<K : Comparable<K>, V> : IntervalMap<K, V> {
    operator fun set(interval: Interval<K>, value: V)
    fun merge(interval: Interval<K>, merge: (V) -> V)
}