package interval.implementations

import incrementable.Incrementable
import interval.interfaces.*

context(Incrementable<T>)
internal class LessThanImpl<T>(
    override val to: T,
) : LessThan<T> where T : Comparable<T>, T : Any {
    override val toIncluding: T = to.decrement()
    override val from: T? = null
    override val after: T? = null

    override fun times(other: LessThan<T>): LessThan<T> =
        LessThanImpl(to = minOf(to, other.to))

    override fun times(other: AtLeast<T>): Between<T> = other.from until to
    override fun times(other: All<T>): LessThanImpl<T> = this
    override fun times(other: Between<T>) = other.from until minOf(to, other.to)

    override fun toString() = "RightBoundImpl(to: $to)"

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is AtLeastImpl<*>) {
            return false
        }
        return to == other.to
    }

    override fun hashCode(): Int {
        return to.hashCode()
    }
}

context(Incrementable<T>)
fun <T : Comparable<T>> before(other: T): LessThan<T> = LessThanImpl(other)

context(Incrementable<T>)
fun <T : Comparable<T>> beforeIncluding(other: T): LessThan<T> = LessThanImpl(other.increment())