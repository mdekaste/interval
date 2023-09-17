package interval.implementations

import incrementable.Incrementable
import interval.interfaces.Left

/**
 * creates a leftbound interval
 * will be [from, to] if to is not null
 * will be [from, ∞) if to is null
 */
context(Incrementable<T>)
infix fun <T : Comparable<T>> T.until(to: T?): Left.Closed<T> = when (to) {
    null -> from(this)
    else -> this until to
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.untilIncluding(toIncluding: T?): Left.Closed<T> = when (toIncluding) {
    null -> from(this)
    else -> this untilIncluding toIncluding
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntil(to: T?): Left.Closed<T> = when (to) {
    null -> after(this)
    else -> this afterUntil to
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntilIncluding(toIncluding: T?): Left.Closed<T> = when (toIncluding) {
    null -> after(this)
    else -> this afterUntilIncluding toIncluding
}
