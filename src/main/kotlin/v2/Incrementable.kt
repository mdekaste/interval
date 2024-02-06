package v2

interface Incrementable<T : Comparable<T>> {
    val minValue: T
    val maxValue: T

    fun T.increment(): T

    fun T.decrement(): T
}
