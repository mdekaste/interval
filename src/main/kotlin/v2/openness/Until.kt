package v2.openness

import v2.Incrementable

/**
 * Representation of '(-∞, y)'
 */
private class Until<T : Comparable<T>>(
    override val until: T,
    incrementable: Incrementable<T>
) : OpenClosed<T>, Incrementable<T> by incrementable {
    override val after: T? = null
    override val from: T? = null
    override val untilIncluding: T by lazy { until.decrement() }
}

fun <T : Comparable<T>> until(
    until: T,
    incrementable: Incrementable<T>
): OpenClosed<T> = Until(
    until = until,
    incrementable = incrementable
)

fun <T : Comparable<T>> until(
    until: T?,
    incrementable: Incrementable<T>
): Interval<T> = when (until) {
    null -> all()
    else -> until(
        until = until,
        incrementable = incrementable
    )
}