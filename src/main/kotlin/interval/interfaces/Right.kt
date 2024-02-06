package interval.interfaces

interface Right<T : Comparable<T>> {
    sealed interface Open<T : Comparable<T>> : Right<T>, Interval<T>
    sealed interface Closed<T : Comparable<T>> : Right<T>, Interval<T> {
        override val until: T
        override val untilIncluding: T
    }
}