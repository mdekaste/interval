package interval.interfaces

sealed interface Interval<T : Comparable<T>> {
    val from: T?
    val to: T?

    val after: T?
    val toIncluding: T?

    operator fun times(other: Interval<T>) = when (other) {
        is Closed<T> -> times(other)
        is LeftBound<T> -> times(other)
        is RightBound<T> -> times(other)
        is Open<T> -> times(other)
    }

    operator fun times(other: Closed<T>): Interval<T>
    operator fun times(other: Open<T>): Interval<T>
    operator fun times(other: LeftBound<T>): Interval<T>
    operator fun times(other: RightBound<T>): Interval<T>
    operator fun contains(value: T): Boolean
    operator fun contains(value: Interval<T>): Boolean
    fun isEmpty(): Boolean

}