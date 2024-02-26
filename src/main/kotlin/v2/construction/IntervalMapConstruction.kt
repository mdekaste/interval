package v2.construction

import v2.Incrementable
import v2.intervalmap.IntervalMap
import v2.intervalmap.MutableIntervalMap
import v2.openness.Interval
import v2.intervalmap.associateByInterval as associateByIntervalImpl
import v2.intervalmap.groupByInterval as groupByIntervalImpl
import v2.intervalmap.toIntervalMap as toIntervalMapImpl

interface IntervalMapConstruction<K : Comparable<K>> : Incrementable<K> {
    fun <V> mutableIntervalMapOf(): MutableIntervalMap<K, V?> = v2.intervalmap.mutableIntervalMapOf()

    fun <V> mutableIntervalMapOf(initialValue: V): MutableIntervalMap<K, V> = v2.intervalmap.mutableIntervalMapOf(initialValue)

    fun <V> Iterable<V>.associateByInterval(keySelector: (V) -> Interval<K>): IntervalMap<K, V?> = associateByIntervalImpl(keySelector)

    fun <V, M : MutableIntervalMap<K, V>> Sequence<Pair<Interval<K>, V>>.toIntervalMap(destination: M): M = toIntervalMapImpl(destination)

    fun <V, M : MutableIntervalMap<K, V>> Iterable<Pair<Interval<K>, V>>.toIntervalMap(destination: M): M = toIntervalMapImpl(destination)

    fun <V, M : MutableIntervalMap<K, V>> Array<out Pair<Interval<K>, V>>.toIntervalMap(destination: M): M = toIntervalMapImpl(destination)

    fun <V, T> Iterable<T>.groupByInterval(
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
        defaultValue: V,
        onConflict: (V, V) -> V,
    ): IntervalMap<K, V> = groupByIntervalImpl(keySelector, valueTransform, defaultValue, onConflict)
}
