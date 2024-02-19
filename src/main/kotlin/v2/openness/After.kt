package v2.openness

import v2.Incrementable
import v2.given

/**
 * Representation of '(x, ∞)'
 */
class After<T : Comparable<T>> internal constructor(
    override val after: T,
    incrementable: Incrementable<T>,
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val until: T? = null
    override val untilIncluding: T? = null
}

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T): After<T> =
    After(
        after = after,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> after(after: T?): Right.Open<T> =
    when (after) {
        null -> `openInterval()`()
        else -> after(after = after)
    }
