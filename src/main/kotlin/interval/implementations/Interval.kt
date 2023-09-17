package interval.implementations

import incrementable.Incrementable
import interval.interfaces.Interval

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.until(to: T?): Interval<T> = when {
    this != null -> this until to
    to != null -> before(to)
    else -> openInterval()
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.untilIncluding(toIncluding: T?): Interval<T> = when {
    this != null -> this untilIncluding toIncluding
    toIncluding != null -> beforeIncluding(toIncluding)
    else -> openInterval()
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.afterUntil(to: T?): Interval<T> = when {
    this != null -> this afterUntil to
    to != null -> before(to)
    else -> openInterval()
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.afterUntilIncluding(toIncluding: T?): Interval<T> = when {
    this != null -> this afterUntilIncluding toIncluding
    toIncluding != null -> beforeIncluding(toIncluding)
    else -> openInterval()
}
