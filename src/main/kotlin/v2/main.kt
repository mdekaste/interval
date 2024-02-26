package v2

import v2.construction.IntIncrementable.until
import v2.intervalmap.groupByIntervalTo
import v2.standard.IntIntervallable.mutableIntervalMapOf

fun main() {
    val interval = 1 until 5
    List(10) { it }.groupByIntervalTo(
        destination = mutableIntervalMapOf(0),
        keySelector = { it until 10 },
        valueTransform = { it * 2 },
        onConflict = Int::plus,
    ).let(::println)

    //    destination: M,
    //    keySelector: (T) -> Interval<K>,
    //    valueTransform: (T) -> V,
    //    onConflict: (V, V) -> V,
}
