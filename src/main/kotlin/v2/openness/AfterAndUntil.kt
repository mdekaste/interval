package v2.openness

import v2.Incrementable
import v2.given

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

context(Incrementable<T>)
fun <T : Comparable<T>> afterUntil(
    after: T,
    until: T,
): AfterAndUntil<T> =
    AfterAndUntil(
        after = after,
        until = until,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> afterUntil(
    after: T?,
    until: T,
): Right.Closed<T> =
    when (after) {
        null ->
            until(
                until = until,
            )

        else ->
            afterUntil(
                after = after,
                until = until,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> afterUntil(
    after: T,
    until: T?,
): Left.Closed<T> =
    when (until) {
        null ->
            after(
                after = after,
            )

        else ->
            afterUntil(
                after = after,
                until = until,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> afterUntil(
    after: T?,
    until: T?,
): Interval<T> =
    when {
        after == null -> until(until = until)
        until == null -> after(after = after)
        else ->
            afterUntil(
                after = after,
                until = until,
            )
    }
