package interval.implementations

import incrementable.Incrementable
import interval.interfaces.All
import interval.interfaces.AtLeast
import interval.interfaces.Between
import interval.interfaces.LessThan

context(Incrementable<T>)
internal class AtLeastImpl<T>(
    override val from: T,
) : AtLeast<T> where T : Comparable<T>, T : Any {
    override val after: T = from.decrement()
    override val to: T? = null
    override val toIncluding: T? = null

    override fun times(other: LessThan<T>): Between<T> = from until other.to

    override fun times(other: AtLeast<T>): AtLeast<T> = AtLeastImpl(from = maxOf(from, other.from))

    override fun times(other: All<T>): AtLeast<T> = this

    override fun times(other: Between<T>): Between<T> = maxOf(from, other.from) until other.to

    override fun toString() = "LeftBoundImpl(from: $from)"

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is AtLeastImpl<*>) {
            return false
        }
        return from == other.from
    }

    override fun hashCode(): Int {
        return from.hashCode()
    }
}

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T): AtLeast<T> = AtLeastImpl(from = after.increment())

context(Incrementable<T>)
fun <T : Comparable<T>> from(from: T): AtLeast<T> = AtLeastImpl(from = from)

