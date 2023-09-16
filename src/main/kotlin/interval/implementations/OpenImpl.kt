package interval.implementations

import incrementable.Incrementable
import interval.interfaces.*

private object OpenImpl : Open<Nothing> {
    override val after: Nothing? = null
    override val from: Nothing? = null
    override val to: Nothing? = null
    override val toIncluding: Nothing? = null

    override fun intersect(other: RightBound<Nothing>) = other
    override fun intersect(other: LeftBound<Nothing>) = other
    override fun intersect(other: Open<Nothing>) = other
    override fun intersect(other: Closed<Nothing>) = other
}

fun <T : Comparable<T>> openInterval(): Open<T> = @Suppress("UNCHECKED_CAST") (OpenImpl as Open<T>)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.until(to: T?): Open<T> = when {
    this != null -> this until to
    to != null -> before(to)
    else -> openInterval()
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.untilIncluding(toIncluding: T?): Open<T> = when{
    this != null -> this untilIncluding toIncluding
    toIncluding != null -> beforeIncluding(toIncluding)
    else -> openInterval()
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.afterUntil(to: T?): Open<T> = when{
    this != null -> this afterUntil to
    to != null -> before(to)
    else -> openInterval()
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T?.afterUntilIncluding(toIncluding: T?): Open<T> = when{
    this != null -> this afterUntilIncluding toIncluding
    toIncluding != null -> beforeIncluding(toIncluding)
    else -> openInterval()
}