package v2.openness

import v2.Incrementable
import v2.given

/**
 * Representation of '(-∞, y)'
 */
class Until<T : Comparable<T>>(
    override val until: T,
    incrementable: Incrementable<T>,
) : OpenClosed<T>, Incrementable<T> by incrementable {
    override val after: T? = null
    override val from: T? = null
    override val untilIncluding: T by lazy { until.decrement() }
}

context(Incrementable<T>)
fun <T : Comparable<T>> until(until: T): Until<T> =
    Until(
        until = until,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> until(until: T?): Left.Open<T> =
    when (until) {
        null -> openInterval()
        else -> until(until = until)
    }
