package incrementable

interface Incrementable<T : Comparable<T>> {
    fun T.increment(): T
    fun T.decrement(): T

    val maxValue: T?
    val minValue: T?
}
