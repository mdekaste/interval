sealed interface NoneClosed<T : Comparable<T>>
sealed interface LeftClosed<T : Comparable<T>> : NoneClosed<T>
sealed interface RightClosed<T : Comparable<T>> : NoneClosed<T>
sealed interface BothClosed<T : Comparable<T>> : LeftClosed<T>, RightClosed<T>