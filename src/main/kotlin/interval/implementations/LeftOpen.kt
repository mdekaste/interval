package interval.implementations

import incrementable.Incrementable
import interval.interfaces.Left

context(Incrementable<T>)
fun <T : Comparable<T>> before(other: T?): Left.Open<T> = when (other) {
    null -> openInterval()
    else -> before(other)
}

context(Incrementable<T>)
fun <T : Comparable<T>> beforeIncluding(other: T?): Left.Open<T> = when (other) {
    null -> openInterval()
    else -> beforeIncluding(other)
}
