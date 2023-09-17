package interval.interfaces

sealed interface Left<T : Comparable<T>> {
    sealed interface Open<T : Comparable<T>> : Left<T>, Interval<T>
    sealed interface Closed<T : Comparable<T>> : Left<T>, Interval<T> {
        override val from: T
        override val after: T
    }
}