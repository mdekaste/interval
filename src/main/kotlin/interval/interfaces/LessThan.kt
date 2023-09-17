package interval.interfaces

interface LessThan<T : Comparable<T>> : Left.Open<T>, Right.Closed<T> {
    override val to: T
    override val toIncluding: T

    override operator fun contains(value: T) = value < to
    override operator fun contains(value: Interval<T>) = value.to?.let { it < to } ?: false

    override fun isEmpty(): Boolean = false
}
