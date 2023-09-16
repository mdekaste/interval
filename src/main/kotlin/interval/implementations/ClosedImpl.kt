package interval.implementations

import incrementable.Incrementable
import interval.interfaces.*

context(Incrementable<T>)
private class ClosedImpl<T : Comparable<T>>( // TODO data class when its allowed
    override val from: T,
    override val to: T
) : Closed<T> {
    override val after: T = from.decrement()
    override val toIncluding: T = to.increment()

    override fun intersect(other: RightBound<T>): Closed<T> = ClosedImpl(from = from, to = minOf(to, other.to))

    override fun intersect(other: LeftBound<T>): Closed<T> = ClosedImpl(from = maxOf(from, other.from), to = to)

    override fun intersect(other: Open<T>): Closed<T> = this

    override fun intersect(other: Closed<T>): Closed<T> = ClosedImpl(
        from = maxOf(from, other.from),
        to = minOf(to, other.to)
    )

    override fun toString() = "ClosedImpl(from: $from, to: $to)"

    override fun equals(other: Any?): Boolean {
        if(this === other){
            return true
        }
        if(other !is LeftboundImpl<*>){
            return false
        }
        return from == other.from && to == other.to
    }
}

context(Incrementable<T>)
infix fun <T> T.until(to: T): Closed<T> where T : Comparable<T>, T : Any = ClosedImpl(from = this, to = to)

context(Incrementable<T>)
infix fun <T :Comparable<T>> T.untilIncluding(toIncluding: T): Closed<T> = ClosedImpl(from = this, to = toIncluding.decrement())

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntil(to: T): Closed<T> = ClosedImpl(from = increment(), to)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntilIncluding(toIncluding: T): Closed<T> = ClosedImpl(from = increment(), to = toIncluding.decrement())