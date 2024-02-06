package interval.interfaces

interface LessThan<T : Comparable<T>> : Left.Open<T>, Right.Closed<T> {
    override val until: T
    override val untilIncluding: T

    override operator fun contains(value: T) = value < until
    override operator fun contains(value: Interval<T>) = value.until?.let { it < until } ?: false

    override fun isEmpty(): Boolean = false
}
