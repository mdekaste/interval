package v2.openness

import v2.Incrementable

/**
 * Representation of '(x, ∞)'
 */
class After<T : Comparable<T>>(
    override val after: T,
    incrementable: Incrementable<T>,
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val until: T? = null
    override val untilIncluding: T? = null
}

fun <T : Comparable<T>> after(
    after: T,
    incrementable: Incrementable<T>,
): After<T> =
    After(
        after = after,
        incrementable = incrementable,
    )

fun <T : Comparable<T>> after(
    after: T?,
    incrementable: Incrementable<T>,
): Right.Open<T> =
    when (after) {
        null -> all()
        else ->
            after(
                after = after,
                incrementable = incrementable,
            )
    }
