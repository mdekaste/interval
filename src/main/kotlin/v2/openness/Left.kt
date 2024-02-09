package v2.openness

sealed interface Left<T> {
    sealed interface Open<T> : Left<T>, Interval<T>

    sealed interface Closed<T> : Left<T>, Interval<T> {
        override val after: T
        override val from: T
    }
}
