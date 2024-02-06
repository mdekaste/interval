package v2.openness

import v2.Incrementable

/**
 * Representation of '[x, ∞)'
 */
private class From<T : Comparable<T>>(
    override val from: T,
    incrementable: Incrementable<T>
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val until: T? = null
    override val untilIncluding: T? = null
}

fun <T : Comparable<T>> from(
    from: T,
    incrementable: Incrementable<T>
): ClosedOpen<T> = From(
    from = from,
    incrementable = incrementable
)

fun <T : Comparable<T>> from(
    from: T?,
    incrementable: Incrementable<T>
): Interval<T> = when (from) {
    null -> all()
    else -> from(
        from = from,
        incrementable = incrementable
    )
}