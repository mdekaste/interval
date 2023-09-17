package interval.interfaces

interface AtLeast<T : Comparable<T>> : Left.Closed<T>, Right.Open<T> {
    override val after: T
    override val from: T

    override operator fun contains(value: T) = value >= from
    override operator fun contains(value: Interval<T>) = value.from?.let { it >= from } ?: false

    override fun isEmpty(): Boolean = false
}
