package interval.interfaces

interface Between<T : Comparable<T>> : Left.Closed<T>, Right.Closed<T> {
    override operator fun contains(value: T) = value >= from && value < to
    override operator fun contains(value: Interval<T>) = when (value) {
        is Between -> value.from >= from && value.to <= to
        else -> false
    }

    override fun isEmpty(): Boolean = from >= to
}
