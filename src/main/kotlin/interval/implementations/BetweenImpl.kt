package interval.implementations

import incrementable.Incrementable
import interval.interfaces.All
import interval.interfaces.AtLeast
import interval.interfaces.Between
import interval.interfaces.LessThan

context(Incrementable<T>)
private class BetweenImpl<T : Comparable<T>>(
    // TODO data class when its allowed
    override val from: T,
    override val to: T,
) : Between<T> {
    override val after: T = from.decrement()
    override val toIncluding: T = to.increment()

    override fun times(other: LessThan<T>): Between<T> = BetweenImpl(from = from, to = minOf(to, other.to))

    override fun times(other: AtLeast<T>): Between<T> = BetweenImpl(from = maxOf(from, other.from), to = to)

    override fun times(other: All<T>): Between<T> = this

    override fun times(other: Between<T>): Between<T> = BetweenImpl(
        from = maxOf(from, other.from),
        to = minOf(to, other.to),
    )

    override fun toString() = "ClosedImpl(from: $from, to: $to)"

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is AtLeastImpl<*>) {
            return false
        }
        return from == other.from && to == other.to
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.until(to: T): Between<T> = BetweenImpl(from = this, to = to)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.untilIncluding(toIncluding: T): Between<T> =
    BetweenImpl(from = this, to = toIncluding.decrement())

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntil(to: T): Between<T> = BetweenImpl(from = increment(), to)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntilIncluding(toIncluding: T): Between<T> =
    BetweenImpl(from = increment(), to = toIncluding.decrement())
