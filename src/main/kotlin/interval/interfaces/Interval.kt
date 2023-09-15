package interval.interfaces

sealed interface Interval<T : Comparable<T>> {
    val from: T?
    val to: T?

    val after: T?
    val toIncluding: T?

    fun overlap(other: Interval<T>) = when (other) {
        is Closed<T> -> overlap(other)
        is LeftBound<T> -> overlap(other)
        is RightBound<T> -> overlap(other)
        is Open<T> -> overlap(other)
    }

    fun overlap(other: Closed<T>): Interval<T>
    fun overlap(other: Open<T>): Interval<T>
    fun overlap(other: LeftBound<T>): Interval<T>
    fun overlap(other: RightBound<T>): Interval<T>
}