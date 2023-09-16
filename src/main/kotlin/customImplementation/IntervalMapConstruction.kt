package customImplementation

import incrementable.Incrementable
import interval.interfaces.Interval
import intervalmap.interfaces.IntervalMap
import intervalmap.interfaces.MutableIntervalMap
import intervalmap.interfaces.associateByInterval as associateByIntervalImpl
import intervalmap.interfaces.associateByIntervalTo as associateByIntervalToImpl
import intervalmap.interfaces.groupByInterval as groupByIntervalImpl
import intervalmap.interfaces.groupByIntervalTo as groupByIntervalToImpl

interface IntervalMapConstruction<K : Comparable<K>> : Incrementable<K> {
    fun <V> intervalMapOf(defaultValue: V, vararg pairs: Pair<Interval<K>, V>) =
        intervalmap.interfaces.intervalMapOf(defaultValue, *pairs)

    fun <V> mutableIntervalMapOf(defaultValue: V, vararg pairs: Pair<Interval<K>, V>) =
        intervalmap.interfaces.mutableIntervalMapOf(defaultValue, *pairs)

    fun <V> Iterable<V>.associateByInterval(
        keySelector: (V) -> Interval<K>,
    ): IntervalMap<K, V?> = associateByIntervalImpl(keySelector)

    fun <V, M : MutableIntervalMap<K, V>> Iterable<V>.associateByIntervalTo(
        destination: M,
        keySelector: (V) -> Interval<K>,
    ): M = associateByIntervalToImpl(destination, keySelector)

    fun <T, V> Iterable<T>.associateByInterval(
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
    ): IntervalMap<K, V?> = associateByIntervalImpl(keySelector, valueTransform)

    fun <T, V, M : MutableIntervalMap<K, V>> Iterable<T>.associateByIntervalTo(
        destination: M,
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
    ): M = associateByIntervalToImpl(destination, keySelector, valueTransform)

    fun <V, T> Iterable<T>.associateByInterval(
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
        defaultValue: V,
    ): IntervalMap<K, V> = associateByIntervalImpl(keySelector, valueTransform, defaultValue)

    fun <V> Iterable<V>.groupByInterval(
        keySelector: (V) -> Interval<K>,
    ): IntervalMap<K, List<V>> = groupByIntervalImpl(keySelector)

    fun <V, M : MutableIntervalMap<K, List<V>>> Iterable<V>.groupByInterval(
        destination: M,
        keySelector: (V) -> Interval<K>,
    ): M = groupByIntervalToImpl(destination, keySelector)

    fun <V, T> Iterable<T>.groupByInterval(
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
    ): IntervalMap<K, List<V>> = groupByIntervalImpl(keySelector, valueTransform)

    fun <V, T, M : MutableIntervalMap<K, List<V>>> Iterable<T>.groupByInterval(
        destination: M,
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
    ): M = groupByIntervalImpl(destination, keySelector, valueTransform)

    fun <V, T> Iterable<T>.groupByInterval(
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
        defaultValue: V,
        onConflict: (V, V) -> V,
    ): IntervalMap<K, V> = groupByIntervalImpl(keySelector, valueTransform, defaultValue, onConflict)

    fun <V, T, M : MutableIntervalMap<K, V>> Iterable<T>.groupByInterval(
        destination: M,
        keySelector: (T) -> Interval<K>,
        valueTransform: (T) -> V,
        onConflict: (V, V) -> V,
    ): M = groupByIntervalImpl(destination, keySelector, valueTransform, onConflict)
}
