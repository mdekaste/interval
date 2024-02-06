package v2.openness

import v2.Incrementable

/**
 * Representation of '[x, y]'
 */
private class FromAndUntilIncluding<T : Comparable<T>>(
    override val from: T,
    override val untilIncluding: T,
    incrementable: Incrementable<T>
) : Closed<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val until: T by lazy { untilIncluding.increment() }
}

fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T,
    untilIncluding: T,
    incrementable: Incrementable<T>
): Closed<T> = FromAndUntilIncluding(
    from = from,
    untilIncluding = untilIncluding,
    incrementable = incrementable
)

fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T?,
    untilIncluding: T,
    incrementable: Incrementable<T>
): Right.Closed<T> = when (from) {
    null -> untilIncluding(
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )

    else -> fromAndUntilIncluding(
        from = from,
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )
}

fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T,
    untilIncluding: T?,
    incrementable: Incrementable<T>
): Left.Closed<T> = when (untilIncluding) {
    null -> from(
        from = from,
        incrementable = incrementable
    )

    else -> fromAndUntilIncluding(
        from = from,
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )
}

fun <T : Comparable<T>> fromAndUntilIncluding(
    from: T?,
    untilIncluding: T?,
    incrementable: Incrementable<T>
): Interval<T> = when {
    from == null -> untilIncluding(
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )

    untilIncluding == null -> from(
        from = from,
        incrementable = incrementable
    )

    else -> fromAndUntilIncluding(
        from = from,
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )
}