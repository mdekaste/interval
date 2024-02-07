package v2.openness

import v2.Incrementable

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

fun <T : Comparable<T>> untilIncluding(
    untilIncluding: T,
    incrementable: Incrementable<T>,
): UntilIncluding<T> =
    UntilIncluding(
        untilIncluding = untilIncluding,
        incrementable = incrementable,
    )

fun <T : Comparable<T>> untilIncluding(
    untilIncluding: T?,
    incrementable: Incrementable<T>,
): Left.Open<T> =
    when (untilIncluding) {
        null -> all()
        else ->
            untilIncluding(
                untilIncluding,
                incrementable,
            )
    }
