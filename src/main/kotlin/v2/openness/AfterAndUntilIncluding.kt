package v2.openness

import v2.Incrementable

/**
 * Representation of '(x, y]'
 */
private class AfterAndUntilIncluding<T : Comparable<T>>(
    override val after: T,
    override val untilIncluding: T,
    incrementable: Incrementable<T>
) : Closed<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val until: T by lazy { untilIncluding.increment() }
}

fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T,
    untilIncluding: T,
    incrementable: Incrementable<T>
): Closed<T> = AfterAndUntilIncluding(
    after = after,
    untilIncluding = untilIncluding,
    incrementable = incrementable
)

fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T?,
    untilIncluding: T,
    incrementable: Incrementable<T>
): Right.Closed<T> = when (after) {
    null -> untilIncluding(
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )

    else -> afterAndUntilIncluding(
        after = after,
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )
}

fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T,
    untilIncluding: T?,
    incrementable: Incrementable<T>
): Left.Closed<T> = when (untilIncluding) {
    null -> after(
        after = after,
        incrementable = incrementable
    )

    else -> afterAndUntilIncluding(
        after = after,
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )
}

fun <T : Comparable<T>> afterAndUntilIncluding(
    after: T?,
    untilIncluding: T?,
    incrementable: Incrementable<T>
): Interval<T> = when {
    after == null -> untilIncluding(
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )

    untilIncluding == null -> after(
        after = after,
        incrementable = incrementable
    )

    else -> afterAndUntilIncluding(
        after = after,
        untilIncluding = untilIncluding,
        incrementable = incrementable
    )
}