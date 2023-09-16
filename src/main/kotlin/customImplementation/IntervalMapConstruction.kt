package customImplementation

import incrementable.Incrementable
import interval.interfaces.Interval
import intervalmap.interfaces.IntervalMap
import intervalmap.interfaces.associateByInterval as associateByIntervalImpl

internal interface IntervalMapConstruction<T : Comparable<T>> : Incrementable<T> {
    fun<V> intervalMapOf(defaultValue: V, vararg pairs: Pair<Interval<T>, V>) = intervalmap.interfaces.intervalMapOf(defaultValue, *pairs)
    fun<V> mutableIntervalMapOf(defaultValue: V, vararg pairs: Pair<Interval<T>, V>) = intervalmap.interfaces.mutableIntervalMapOf(defaultValue, *pairs)
    fun <V> Iterable<V>.associateByInterval(
        keySelector: (V) -> Interval<T>
    ): IntervalMap<T, V?> = associateByIntervalImpl(keySelector)

    fun <L, V> Iterable<L>.associateByInterval(
        keySelector: (L) -> Interval<T>,
        valueTransform: (L) -> V
    ): IntervalMap<T, V?> = associateByIntervalImpl(keySelector, valueTransform)

    fun <V, L> Iterable<L>.associateByInterval(
        keySelector: (L) -> Interval<T>,
        valueTransform: (L) -> V,
        defaultValue: V
    ): IntervalMap<T, V> = associateByIntervalImpl(keySelector, valueTransform, defaultValue)
}