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
    override val until: T,
) : Between<T> {
    override val after: T = from.decrement()
    override val untilIncluding: T = until.increment()

    override fun times(other: LessThan<T>): Between<T> = BetweenImpl(from = from, until = minOf(until, other.until))

    override fun times(other: AtLeast<T>): Between<T> = BetweenImpl(from = maxOf(from, other.from), until = until)

    override fun times(other: All<T>): Between<T> = this

    override fun times(other: Between<T>): Between<T> = BetweenImpl(
        from = maxOf(from, other.from),
        until = minOf(until, other.until),
    )

    override fun toString() = "ClosedImpl(from: $from, to: $until)"

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is AtLeastImpl<*>) {
            return false
        }
        return from == other.from && until == other.until
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + until.hashCode()
        return result
    }
}

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.until(to: T): Between<T> = BetweenImpl(from = this, until = to)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.untilIncluding(toIncluding: T): Between<T> =
    BetweenImpl(from = this, until = toIncluding.decrement())

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntil(to: T): Between<T> = BetweenImpl(from = increment(), to)

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.afterUntilIncluding(toIncluding: T): Between<T> =
    BetweenImpl(from = increment(), until = toIncluding.decrement())
