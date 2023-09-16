sealed interface NoneOpen<T : Comparable<T>>

sealed interface LeftOpen<T : Comparable<T>> : NoneOpen<T>
sealed interface RightOpen<T : Comparable<T>> : NoneOpen<T>

sealed interface BothOpen<T : Comparable<T>> : LeftOpen<T>, RightOpen<T>