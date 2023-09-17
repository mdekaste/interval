package interval.implementations

import incrementable.Incrementable
import interval.interfaces.Right

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T?): Right.Open<T> = when (after) {
    null -> openInterval()
    else -> after(after)
}

context(Incrementable<T>)
fun <T : Comparable<T>> from(from: T?): Right.Open<T> = when (from) {
    null -> openInterval()
    else -> from(from)
}