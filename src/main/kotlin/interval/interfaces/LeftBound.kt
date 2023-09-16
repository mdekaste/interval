package interval.interfaces

interface LeftBound<T : Comparable<T>> : Open<T> {
    override val after: T
    override val from: T

    override operator fun contains(value: T) = value >= from
    override operator fun contains(value: Interval<T>) = value.from?.let { it >= from } ?: false

    override fun isEmpty(): Boolean = false
}
