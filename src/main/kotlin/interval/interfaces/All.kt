package interval.interfaces

interface All<T : Comparable<T>> : Left.Open<T>, Right.Open<T> {
    override operator fun contains(value: T) = true
    override operator fun contains(value: Interval<T>) = true

    override fun isEmpty(): Boolean = false
}
