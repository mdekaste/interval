package interval.interfaces

interface Closed<T : Comparable<T>> : LeftBound<T>, RightBound<T>