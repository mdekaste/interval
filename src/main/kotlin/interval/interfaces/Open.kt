package interval.interfaces

interface Open<T : Comparable<T>> : Interval<T> {
    override operator fun contains(value: T) = true
    override operator fun contains(value: Interval<T>) = true

    override fun isEmpty(): Boolean = false
}
