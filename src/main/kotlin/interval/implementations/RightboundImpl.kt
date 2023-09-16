package interval.implementations

import incrementable.Incrementable
import interval.interfaces.*

context(Incrementable<T>)
internal class RightboundImpl<T>(
    override val to: T
): RightBound<T> where T : Comparable<T>, T : Any {
    override val toIncluding: T = to.decrement()
    override val from: T? = null
    override val after: T? = null

    override fun overlap(other: RightBound<T>): RightBound<T> = RightboundImpl(to = minOf(to, other.to))
    override fun overlap(other: LeftBound<T>): Closed<T> = other.from until to
    override fun overlap(other: Open<T>): RightBound<T> = this
    override fun overlap(other: Closed<T>) = other.from until minOf(to, other.to)

    override fun toString() = "RightBoundImpl(to: $to)"

    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if(other !is LeftboundImpl<*>){
            return false
        }
        return to == other.to
    }
}

context(Incrementable<T>)
fun<T : Comparable<T>> before(other: T): RightBound<T> = RightboundImpl(other)

context(Incrementable<T>)
fun<T : Comparable<T>> beforeIncluding(other: T): RightBound<T> = RightboundImpl(other.increment())

context(Incrementable<T>)
infix fun<T : Comparable<T>> T?.until(other: T): RightBound<T> = when(this){
    null -> before(other)
    else -> this until other
}

context(Incrementable<T>)
infix fun<T : Comparable<T>> T?.untilIncluding(other: T): RightBound<T> = when(this){
    null -> beforeIncluding(other)
    else -> this untilIncluding other
}

context(Incrementable<T>)
infix fun<T : Comparable<T>> T?.afterUntil(other: T): RightBound<T> = when(this){
    null -> before(other)
    else -> this afterUntil other
}

context(Incrementable<T>)
infix fun<T : Comparable<T>> T?.afterUntilIncluding(other: T): RightBound<T> = when(this){
    null -> beforeIncluding(other)
    else -> this afterUntilIncluding other
}