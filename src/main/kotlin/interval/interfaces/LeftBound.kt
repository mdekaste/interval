package interval.interfaces

interface LeftBound<T : Comparable<T>> : Open<T> {
    override val after: T
    override val from: T
}
