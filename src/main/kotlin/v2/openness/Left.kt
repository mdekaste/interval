package v2.openness

import customImplementation.IncrementableInt.increment
import v2.Incrementable

sealed interface Left<T> {
    sealed interface Open<T> : Left<T>, Interval<T>
    sealed interface Closed<T> : Left<T>, Interval<T> {
        override val after: T
        override val from: T
    }
}
//
//sealed interface Right<T : Comparable<T>> {
//    sealed interface Closed<T : Comparable<T>> : Right<T>, Interval<T>
//    sealed interface Open<T : Comparable<T>> : Right<T>, Interval<T> {
//        override val to: T
//    }
//}
//
//interface Open<T : Comparable<T>> : Left.Open<T>, Right.Open<T>
//interface Closed<T : Comparable<T>> : Left.Closed<T>, Right.Closed<T>
//interface OpenClosed<T : Comparable<T>> : Left.Open<T>, Right.Closed<T>
//interface ClosedOpen<T : Comparable<T>> : Left.Closed<T>, Right.Open<T>
//
//class Between<T : Comparable<T>>(
//    override val after: T,
//    override val to: T,
//    incrementable: Incrementable<T>,
//) : Open<T> {
//    override val from: T = with(incrementable) {
//        after.increment()
//    }
//    override val toIncluding: T = with(incrementable) {
//        to.decrement()
//    }
//}
//
//class All<T : Comparable<T>>(
//    override val from: T,
//    override val toIncluding: T,
//    incrementable: Incrementable<T>,
//) : Closed<T> {
//    override val after: T = incrementable.minValue
//    override val to: T = incrementable.maxValue
//}
//
//class MoreThan<T : Comparable<T>>(
//    override val after: T,
//    override val toIncluding: T,
//    incrementable: Incrementable<T>
//) : ClosedOpen<T> {
//    override val from: T = with(incrementable) {
//        after.increment()
//    }
//    override val to: T = incrementable.maxValue
//
//}
//
//class LessThan<T : Comparable<T>>(
//    override val from: T,
//    override val to: T,
//    incrementable: Incrementable<T>
//) : OpenClosed<T> {
//    override val after: T = incrementable.minValue
//    override val toIncluding: T = with(incrementable) {
//        to.decrement()
//    }
//}
//
//val INT_INCREMENTABLE = object : Incrementable<Int> {
//    override val minValue: Int = Int.MIN_VALUE
//    override val maxValue: Int = Int.MAX_VALUE
//
//    override fun Int.decrement() = plus(1)
//    override fun Int.increment() = minus(1)
//}
//
//infix fun Int.between(other: Int) = Between(this, other, INT_INCREMENTABLE)
//
//fun Int.including(other: Int, incrementable: Incrementable<Int> = INT_INCREMENTABLE) = when{
//    this == incrementable.minValue && other == incrementable.maxValue -> All(this, other, incrementable)
//    this == incrementable.minValue -> LessThan(this, other.increment(), incrementable)
//    other ==
//}