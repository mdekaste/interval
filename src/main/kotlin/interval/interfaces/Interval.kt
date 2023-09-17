package interval.interfaces

sealed interface Interval<T : Comparable<T>> {
    val from: T?
    val to: T?

    val after: T?
    val toIncluding: T?

    operator fun times(other: Interval<T>) = when (other) {
        is Between<T> -> times(other)
        is AtLeast<T> -> times(other)
        is LessThan<T> -> times(other)
        is All<T> -> times(other)
    }

    operator fun times(other: Between<T>): Interval<T>
    operator fun times(other: All<T>): Interval<T>
    operator fun times(other: AtLeast<T>): Interval<T>
    operator fun times(other: LessThan<T>): Interval<T>
    operator fun contains(value: T): Boolean
    operator fun contains(value: Interval<T>): Boolean
    fun isEmpty(): Boolean
}
