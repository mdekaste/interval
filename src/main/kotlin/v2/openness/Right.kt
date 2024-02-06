package v2.openness

sealed interface Right<T> {
    sealed interface Open<T> : Right<T>, Interval<T>
    sealed interface Closed<T> : Right<T>, Interval<T> {
        override val until: T
        override val untilIncluding: T
    }
}