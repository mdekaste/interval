import customImplementation.IncrementableInt.after
import customImplementation.IncrementableInt.associateByInterval
import customImplementation.IncrementableInt.associateByIntervalTo
import customImplementation.IncrementableInt.before
import customImplementation.IncrementableInt.beforeIncluding
import customImplementation.IncrementableInt.from
import customImplementation.IncrementableLocalDate.from
import customImplementation.IncrementableInt.increment
import customImplementation.IncrementableInt.mutableIntervalMapOf
import customImplementation.IncrementableInt.toMutableIntervalMap
import customImplementation.IncrementableInt.until
import interval.interfaces.*
import intervalmap.asNonNullValueIntervalMap
import intervalmap.interfaces.IntervalMap
import intervalmap.interfaces.MutableIntervalMap
import java.time.LocalDate


fun main() {
    intervalMapsAreNeat()
}

fun someRangeConstructions() {
    val before: LessThan<Int> = before(5)
    val beforeMaybe: Left.Open<Int> = before(5 as Int?)

    val beforeIncluding: LessThan<Int> = beforeIncluding(10)
    val beforeIncludingMaybe: Left.Open<Int> = beforeIncluding(10 as Int?)

    val from: AtLeast<Int> = from(15)
    val fromMaybe: Right.Open<Int> = from(15 as Int?)

    val after: AtLeast<Int> = after(20)
    val afterMaybe: Right.Open<Int> = after(20 as Int?)

    val between: Between<Int> = 0 until 10
    val maybeBetween: Right.Closed<Int> = 0 as Int? until 10
    val betweenMaybe: Left.Closed<Int> = 0 until 10 as Int?
    val maybeBetweenMaybe: Interval<Int> = 0 as Int? until 10 as Int?

    // And all the other Between options with including and after ...
}

fun intervalMapsAreNeat() {
    // [0, 1, ..., 8, 9]
    val list = List(10) { it }

    // constructed with default value null
    val intervalMapA: IntervalMap<Int, Int?> = list.associateByInterval { it until it.increment() }
    // first entry is guaranteed open on the left
    val firstEntryA: Map.Entry<Left.Open<Int>, Int?> = intervalMapA.firstEntry()

    // make mutable
    val intervalMapB: MutableIntervalMap<Int, Int?> = intervalMapA.toMutableIntervalMap()
    // can also be done with by to
    list.associateByIntervalTo(mutableIntervalMapOf()) { it until it.increment() }

    //replace null with another number
    intervalMapB.merge { previous ->
        when (previous) {
            null -> 0
            else -> previous
        }
    }

    //now we can check the value to be non null
    val intervalMapC: MutableIntervalMap<Int, Int> =
        intervalMapB.asNonNullValueIntervalMap() ?: error("this is not the case")

    // last entry is guaranteed open on the right
    val lastEntryC: Map.Entry<Right.Open<Int>, Int> = intervalMapC.lastEntry()

    // double the values between [3, 5)
    intervalMapC.merge(3 until 5) { it * 2 }

    val date = LocalDate.now()

    from(date)
}

fun exhaustiveCheckOnAllOptions(interval: Interval<LocalDate>): String {
    return when (interval) {
        is All -> "this is an 'all' interval"
        is AtLeast -> "this is an 'atLeast' interval with from: ${interval.from}"
        is LessThan -> "this is a 'lessThan' interval with to: ${interval.until}"
        is Between -> "this is a 'between' interval with from: ${interval.from} and to: ${interval.until}"
    }
}

fun exhaustiveCheckLeft(interval: Interval<LocalDate>): String {
    return when (interval) {
        is Left.Open -> "this interval doesn't have a 'from' value"
        is Left.Closed -> "but this one does: ${interval.from}"
    }
}

fun exhaustiveCheckRight(interval: Interval<LocalDate>): String {
    return when (interval) {
        is Right.Open -> "this interval doesn't doesn't have a 'to' value"
        is Right.Closed -> "but this one does: ${interval.until}"
    }
}


fun mixedCheck(interval: Interval<LocalDate>): String {
    return when (interval) {
        is Right.Closed -> "this interval has a to: ${interval.until} but no info about from"
        is AtLeast -> "this interval has a from: ${interval.from} but to is unknown"
        is All -> "this is the only option left"
    }
}

fun mixedCheck2(interval: Interval<LocalDate>): String {
    return when (interval) {
        is Right.Closed -> "this interval has a to: ${interval.until} but no info about from"
        is AtLeast -> "this interval has a from: ${interval.from} but to is unknown"
        is Left.Open -> "also covered by Left.Open"
    }
}

fun exhaustiveCheckOnSubtype(leftIsOpen: Left.Open<Int>) : String {
    return when(leftIsOpen){
        is LessThan -> "Now right is closed"
        is All -> "Now both left and right are open"
    }
}

fun exhaustiveCheckOnSubtype(rightIsOpen: Right.Open<Int>): String {
    return when (rightIsOpen) {
        is AtLeast -> "Now Left is closed"
        is All -> "Now both Left and Right are open"
    }
}

fun rangeChecks(value: Int): String {
    return when(value){
        in before(30) -> "less than 30"
        in 30 until 60 -> "between 30 and 60"
        in after(60) -> "hoi"
        else -> "compiler plugin needed"
    }
}


