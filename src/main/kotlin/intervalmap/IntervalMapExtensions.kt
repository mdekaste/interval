package intervalmap

import interval.interfaces.Interval
import intervalmap.interfaces.IntervalMap
import intervalmap.interfaces.MutableIntervalMap

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

fun <K : Comparable<K>, V> IntervalMap<K, V?>.asSequenceNotNull(): Sequence<Map.Entry<Interval<K>, V & Any>> =
    @Suppress("UNCHECKED_CAST")
    (asSequence().filter { it.value != null } as Sequence<Map.Entry<Interval<K>, V & Any>>)
