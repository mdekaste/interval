package interval.interfaces

interface RightBound<T : Comparable<T>> : Open<T> {
    override val to: T
    override val toIncluding: T

    override operator fun contains(value: T) = value < to
    override operator fun contains(value: Interval<T>) = value.to?.let { it < to } ?: false

    override fun isEmpty(): Boolean = false
}

