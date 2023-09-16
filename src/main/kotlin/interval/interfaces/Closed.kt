package interval.interfaces

interface Closed<T : Comparable<T>> : LeftBound<T>, RightBound<T> {
    override operator fun contains(value: T) = super<LeftBound>.contains(value) && super<RightBound>.contains(value)
    override operator fun contains(value: Interval<T>) =
        super<LeftBound>.contains(value) && super<RightBound>.contains(value)

    override fun isEmpty(): Boolean = from >= to
}
