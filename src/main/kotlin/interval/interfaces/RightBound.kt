package interval.interfaces

interface RightBound<T : Comparable<T>> : Open<T> {
    override val to: T
    override val toIncluding: T
}

