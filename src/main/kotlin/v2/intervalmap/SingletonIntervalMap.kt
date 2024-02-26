package v2.intervalmap

import v2.Incrementable
import v2.given
import v2.openness.*
import java.io.Serializable
import java.util.*

private object SingletonIntervalMap : IntervalMap<Comparable<Any>, Nothing?>, Serializable {
    private fun readResolve(): Any = SingletonIntervalMap

    private val ENTRY: Map.Entry<Open<Comparable<Any>>, Nothing?> =
        AbstractMap.SimpleEntry(
            openInterval(),
            null,
        )

    override fun get(key: Interval<Comparable<Any>>): Nothing? = null

    override fun firstEntry(): Map.Entry<Left.Open<Comparable<Any>>, Nothing?> = ENTRY

    override fun lastEntry(): Map.Entry<Right.Open<Comparable<Any>>, Nothing?> = ENTRY

    override fun getEntry(key: Comparable<Any>): Map.Entry<Interval<Comparable<Any>>, Nothing?> = ENTRY

    override fun get(key: Comparable<Any>): Nothing? = null

    override val entries: Set<Map.Entry<Interval<Comparable<Any>>, Nothing?>> = setOf(ENTRY)
    override val keys: Set<Interval<Comparable<Any>>> = setOf(openInterval())
    override val size: Int = 1
    override val values: Collection<Nothing?> = setOf(null)

    override fun containsValue(value: Nothing?) =
        when (value) {
            null -> true
            else -> false
        }

    override fun containsKey(key: Interval<Comparable<Any>>) =
        when (key) {
            openInterval<Comparable<Any>>() -> true
            else -> false
        }
}

fun <K : Comparable<K>, V> MutableIntervalMap<in K, in V>.putAll(pairs: Array<out Pair<Interval<K>, V>>) {
    for ((key, value) in pairs) {
        set(key, value)
    }
}

fun <K : Comparable<K>, V> MutableIntervalMap<in K, in V>.putAll(pairs: Sequence<Pair<Interval<K>, V>>) {
    for ((key, value) in pairs) {
        set(key, value)
    }
}

fun <K : Comparable<K>, V> MutableIntervalMap<in K, in V>.putAll(pairs: Iterable<Pair<Interval<K>, V>>) {
    for ((key, value) in pairs) {
        set(key, value)
    }
}

fun <K : Comparable<K>, V> MutableIntervalMap<in K, V>.putAll(
    pairs: Iterable<Pair<Interval<K>, V>>,
    onConflict: (V, V) -> V,
) {
    for ((key, value) in pairs) {
        merge(key) {
            onConflict(it, value)
        }
    }
}

fun <K : Comparable<K>, V> MutableIntervalMap<in K, V>.putAll(
    pairs: Sequence<Pair<Interval<K>, V>>,
    onConflict: (V, V) -> V,
) {
    for ((key, value) in pairs) {
        merge(key) {
            onConflict(it, value)
        }
    }
}

fun <K : Comparable<K>, V> MutableIntervalMap<in K, V>.putAll(
    pairs: Array<out Pair<Interval<K>, V>>,
    onConflict: (V, V) -> V,
) {
    for ((key, value) in pairs) {
        merge(key) {
            onConflict(it, value)
        }
    }
}

fun <K : Comparable<K>, V, M : MutableIntervalMap<in K, in V>> Sequence<Pair<Interval<K>, V>>.toIntervalMap(destination: M): M =
    destination.apply {
        putAll(this@toIntervalMap)
    }

fun <K : Comparable<K>, V, M : MutableIntervalMap<in K, in V>> Iterable<Pair<Interval<K>, V>>.toIntervalMap(destination: M): M =
    destination.apply {
        putAll(this@toIntervalMap)
    }

fun <K : Comparable<K>, V, M : MutableIntervalMap<in K, in V>> Array<out Pair<Interval<K>, V>>.toIntervalMap(destination: M): M =
    destination.apply {
        putAll(this@toIntervalMap)
    }

fun <K : Comparable<K>, V, M : MutableIntervalMap<in K, V>> Sequence<Pair<Interval<K>, V>>.toIntervalMap(
    destination: M,
    onConflict: (V, V) -> V,
): M =
    destination.apply {
        putAll(this@toIntervalMap, onConflict)
    }

fun <K : Comparable<K>, V, M : MutableIntervalMap<in K, V>> Iterable<Pair<Interval<K>, V>>.toIntervalMap(
    destination: M,
    onConflict: (V, V) -> V,
): M =
    destination.apply {
        putAll(this@toIntervalMap, onConflict)
    }

fun <K : Comparable<K>, V, M : MutableIntervalMap<in K, V>> Array<out Pair<Interval<K>, V>>.toIntervalMap(
    destination: M,
    onConflict: (V, V) -> V,
): M =
    destination.apply {
        putAll(this@toIntervalMap, onConflict)
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> mutableIntervalMapOf(): MutableIntervalMap<K, V?> =
    TreeIntervalMap(
        initialValue = null,
        incrementable = given(),
    )

context(Incrementable<K>)
fun <K : Comparable<K>, V> mutableIntervalMapOf(initialValue: V): MutableIntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = initialValue,
        incrementable = given(),
    )

context(Incrementable<K>)
fun <K : Comparable<K>, V> mutableIntervalMapOf(
    initialValue: V,
    vararg pairs: Pair<Interval<K>, V>,
): MutableIntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = initialValue,
        incrementable = given(),
    ).apply {
        putAll(pairs)
    }

@Suppress("UNCHECKED_CAST")
fun <K : Comparable<K>, V> emptyIntervalMap(): IntervalMap<K, V> = SingletonIntervalMap as IntervalMap<K, V>

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(vararg pairs: Pair<Interval<K>, V>): IntervalMap<K, V?> =
    TreeIntervalMap<K, V?>(
        initialValue = null,
        incrementable = given(),
    ).apply {
        putAll(pairs)
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(
    vararg pairs: Pair<Interval<K>, V>,
    onConflict: (V?, V?) -> V,
): IntervalMap<K, V?> =
    TreeIntervalMap<K, V?>(
        initialValue = null,
        incrementable = given(),
    ).apply {
        putAll(pairs, onConflict)
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(
    initialValue: V,
    vararg pairs: Pair<Interval<K>, V>,
    onConflict: (V, V) -> V,
): IntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = initialValue,
        incrementable = given(),
    ).apply {
        pairs.forEach { (interval, value) ->
            merge(interval) {
                onConflict(it, value)
            }
        }
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> Sequence<Pair<K, V>>.toIntervalMap(
    initialValue: V,
    onConflict: (V, V) -> V,
): IntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = initialValue,
        incrementable = given<Incrementable<K>>(),
    ).apply {
        forEach { (key, value) ->
            merge(key) {
                onConflict(it, value)
            }
        }
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> Sequence<Pair<K, V>>.toIntervalMap(initialValue: V): IntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = initialValue,
        incrementable = given<Incrementable<K>>(),
    ).apply {
        forEach { (key, value) -> set(key, value) }
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> IntervalMap<K, V>.toMutableIntervalMap(
    onConflict: (V, V) -> V,
): MutableIntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = firstEntry().value,
        incrementable = given<Incrementable<K>>(),
    ).apply {
        putAll(entries.map { it.key to it.value }, onConflict)
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V> Iterable<V>.associateByInterval(keySelector: (V) -> Interval<K>): IntervalMap<K, V?> =
    TreeIntervalMap<K, V?>(
        initialValue = null,
        incrementable = given<Incrementable<K>>(),
    ).apply {
        this@associateByInterval.forEach {
            set(keySelector(it), it)
        }
    }

context(Incrementable<K>)
fun <K : Comparable<K>, V, T> Iterable<T>.groupByInterval(
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
    defaultValue: V,
    onConflict: (V, V) -> V,
): IntervalMap<K, V> =
    TreeIntervalMap(defaultValue, given<Incrementable<K>>()).apply {
        this@groupByInterval.forEach { value ->
            merge(keySelector(value)) { previous ->
                onConflict(previous, valueTransform(value))
            }
        }
    }

fun <K : Comparable<K>, V, T, M : MutableIntervalMap<in K, V>> Iterable<T>.groupByIntervalTo(
    destination: M,
    keySelector: (T) -> Interval<K>,
    valueTransform: (T) -> V,
    onConflict: (V, V) -> V,
): M =
    destination.apply {
        this@groupByIntervalTo.forEach { value ->
            merge(keySelector(value)) { previous ->
                onConflict(previous, valueTransform(value))
            }
        }
    }
