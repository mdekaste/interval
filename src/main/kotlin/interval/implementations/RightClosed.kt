package interval.implementations

import incrementable.Incrementable
import interval.interfaces.Right

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.until(other: T): Right.Closed<T> = when (this) {
    null -> before(other)
    else -> this until other
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.untilIncluding(other: T): Right.Closed<T> = when (this) {
    null -> beforeIncluding(other)
    else -> this untilIncluding other
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.afterUntil(other: T): Right.Closed<T> = when (this) {
    null -> before(other)
    else -> this afterUntil other
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.afterUntilIncluding(other: T): Right.Closed<T> = when (this) {
    null -> beforeIncluding(other)
    else -> this afterUntilIncluding other
}
