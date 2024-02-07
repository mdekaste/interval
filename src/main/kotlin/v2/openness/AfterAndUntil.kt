package v2.openness

import v2.Incrementable

/**
 * Representation of '(x, y)'
 */
class AfterAndUntil<T : Comparable<T>>(
    override val after: T,
    override val until: T,
    incrementable: Incrementable<T>,
) : Closed<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val untilIncluding: T by lazy { until.decrement() }
}

fun <T : Comparable<T>> afterUntil(
    after: T,
    until: T,
    incrementable: Incrementable<T>,
): AfterAndUntil<T> =
    AfterAndUntil(
        after = after,
        until = until,
        incrementable = incrementable,
    )

fun <T : Comparable<T>> afterUntil(
    after: T?,
    until: T,
    incrementable: Incrementable<T>,
): Right.Closed<T> =
    when (after) {
        null ->
            until(
                until = until,
                incrementable = incrementable,
            )

        else ->
            afterUntil(
                after = after,
                until = until,
                incrementable = incrementable,
            )
    }

fun <T : Comparable<T>> afterUntil(
    after: T,
    until: T?,
    incrementable: Incrementable<T>,
): Left.Closed<T> =
    when (until) {
        null ->
            after(
                after = after,
                incrementable = incrementable,
            )

        else ->
            afterUntil(
                after = after,
                until = until,
                incrementable = incrementable,
            )
    }

fun <T : Comparable<T>> afterUntil(
    after: T?,
    until: T?,
    incrementable: Incrementable<T>,
): Interval<T> =
    when {
        after == null ->
            until(
                until = until,
                incrementable = incrementable,
            )

        until == null ->
            after(
                after = after,
                incrementable = incrementable,
            )

        else ->
            afterUntil(
                after = after,
                until = until,
                incrementable = incrementable,
            )
    }
