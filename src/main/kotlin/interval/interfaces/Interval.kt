package interval.interfaces

sealed interface Interval<T : Comparable<T>> {
    val from: T?
    val to: T?

    val after: T?
    val toIncluding: T?

    fun intersect(other: Interval<T>) = when (other) {
        is Closed<T> -> intersect(other)
        is LeftBound<T> -> intersect(other)
        is RightBound<T> -> intersect(other)
        is Open<T> -> intersect(other)
    }

    fun intersect(other: Closed<T>): Interval<T>
    fun intersect(other: Open<T>): Interval<T>
    fun intersect(other: LeftBound<T>): Interval<T>
    fun intersect(other: RightBound<T>): Interval<T>
}