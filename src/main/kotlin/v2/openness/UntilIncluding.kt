package v2.openness

import v2.Incrementable
import v2.given

/**
 * Representation of '(-∞, y]'
 */
class UntilIncluding<T : Comparable<T>>(
    override val untilIncluding: T,
    incrementable: Incrementable<T>,
) : OpenClosed<T>, Incrementable<T> by incrementable {
    override val until: T by lazy { untilIncluding.increment() }
    override val after: T? = null
    override val from: T? = null
}

context(Incrementable<T>)
fun <T : Comparable<T>> untilIncluding(untilIncluding: T): UntilIncluding<T> =
    UntilIncluding(
        untilIncluding = untilIncluding,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> untilIncluding(untilIncluding: T?): Left.Open<T> =
    when (untilIncluding) {
        null -> `openInterval()`()
        else -> untilIncluding(untilIncluding = untilIncluding)
    }
