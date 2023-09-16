package interval.implementations

import incrementable.Incrementable
import interval.interfaces.*


context(Incrementable<T>)
internal class LeftboundImpl<T>(
    override val from: T
) : LeftBound<T> where T : Comparable<T>, T : Any {
    override val after: T = from.decrement()
    override val to: T? = null
    override val toIncluding: T? = null

    override fun overlap(other: RightBound<T>): Closed<T> = from until other.to

    override fun overlap(other: LeftBound<T>): LeftBound<T> = LeftboundImpl(from = maxOf(from, other.from))

    override fun overlap(other: Open<T>): LeftBound<T> = this

    override fun overlap(other: Closed<T>): Closed<T> = maxOf(from, other.from) until other.to

    override fun toString() = "LeftBoundImpl(from: $from)"

    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if(other !is LeftboundImpl<*>){
            return false
        }
        return from == other.from
    }
}

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T): LeftBound<T> =
    LeftboundImpl(from = after.increment())

context(Incrementable<T>)
fun <T : Comparable<T>> from(from: T): LeftBound<T> =
    LeftboundImpl(from = from)

/**
 * creates a leftbound interval
 * will be [from, to] if to is not null
 * will be [from, ∞) if to is null
 */
context(Incrementable<T>)
infix fun<T : Comparable<T>> T.until(to: T?): LeftBound<T> = when(to){
    null -> from(this)
    else -> this until to
}

context(Incrementable<T>)
infix fun<T : Comparable<T>> T.untilIncluding(toIncluding: T?): LeftBound<T> = when(toIncluding){
    null -> from(this)
    else -> this untilIncluding toIncluding
}

context(Incrementable<T>)
infix fun<T : Comparable<T>> T.afterUntil(to: T?): LeftBound<T> = when(to){
    null -> after(this)
    else -> this afterUntil to
}

context(Incrementable<T>)
infix fun<T : Comparable<T>> T.afterUntilIncluding(toIncluding: T?): LeftBound<T> = when(toIncluding){
    null -> after(this)
    else -> this afterUntilIncluding toIncluding
}