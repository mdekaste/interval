package v2.intervalmap

import v2.openness.Interval

interface MutableIntervalMap<K : Comparable<K>, V>: IntervalMap<K, V> {
    operator fun set(interval: Interval<K>, value: V)
    fun merge(interval: Interval<K>, merge: (V) -> V)
}