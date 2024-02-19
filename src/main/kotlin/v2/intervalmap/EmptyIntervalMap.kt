package v2.intervalmap

import v2.Incrementable
import v2.given
import v2.openness.*
import java.io.Serializable
import java.util.*

private object EmptyIntervalMap : IntervalMap<Comparable<Any>, Nothing?>, Serializable {
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

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(
    initialValue: V,
    vararg pairs: Pair<Interval<K>, V>,
): MutableIntervalMap<K, V> =
    TreeIntervalMap(
        initialValue = initialValue,
        incrementable = given(),
    ).apply {
        pairs.forEach { (interval, value) -> set(interval, value) }
    }

@Suppress("UNCHECKED_CAST")
fun <K : Comparable<K>, V> emptyIntervalMap(): IntervalMap<K, V> = EmptyIntervalMap as IntervalMap<K, V>

context(Incrementable<K>)
fun <K : Comparable<K>, V> intervalMapOf(vararg pairs: Pair<Interval<K>, V>): IntervalMap<K, V?> =
    TreeIntervalMap<K, V?>(null, given()).apply {
        pairs.forEach { (interval, value) -> set(interval, value) }
    }
