package v2.openness

import v2.Incrementable
import v2.given

/**
 * Representation of '[x, ∞)'
 */
class From<T : Comparable<T>>(
    override val from: T,
    incrementable: Incrementable<T>,
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val until: T? = null
    override val untilIncluding: T? = null

    override fun toString() = "[$from, ∞)"
}

context(Incrementable<T>)
fun <T : Comparable<T>> from(from: T): From<T> = From(from = from, incrementable = given())

context(Incrementable<T>)
fun <T : Comparable<T>> from(from: T?): Right.Open<T> =
    when (from) {
        null -> openInterval()
        else -> from(from = from)
    }

context(Incrementable<T>)
infix fun <T : Comparable<T>> T.until(none: Nothing?): From<T> = from(this)
