package interval.interfaces

interface Between<T : Comparable<T>> : Left.Closed<T>, Right.Closed<T> {
    override operator fun contains(value: T) = value >= from && value < until
    override operator fun contains(value: Interval<T>) = when (value) {
        is Between -> value.from >= from && value.until <= until
        else -> false
    }

    override fun isEmpty(): Boolean = from >= until
}
