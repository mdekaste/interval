package intervalmap.interfaces

import interval.implementations.openInterval
import interval.interfaces.Interval

interface MutableIntervalMap<K : Comparable<K>, V> : IntervalMap<K, V> {
    operator fun set(interval: Interval<K>, value: V)
    fun merge(interval: Interval<K> = openInterval(), merge: (V) -> V)
}
