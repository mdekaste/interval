package v2.openness

import v2.Incrementable
import v2.given

/**
 * Representation of '[x, y)'
 */
class FromAndUntil<T : Comparable<T>>(
    override val from: T,
    override val until: T,
    incrementable: Incrementable<T>,
) : Closed<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val untilIncluding: T by lazy { until.decrement() }
}

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T,
    until: T,
): FromAndUntil<T> =
    FromAndUntil(
        from = from,
        until = until,
        incrementable = given(),
    )

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T?,
    until: T,
): Right.Closed<T> =
    when (from) {
        null -> until(until = until)

        else ->
            fromAndUntil(
                from = from,
                until = until,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T,
    until: T?,
): Left.Closed<T> =
    when (until) {
        null -> from(from = from)
        else ->
            fromAndUntil(
                from = from,
                until = until,
            )
    }

context(Incrementable<T>)
fun <T : Comparable<T>> fromAndUntil(
    from: T?,
    until: T?,
): Interval<T> =
    when {
        from == null -> until(until = until)
        until == null -> from(from = from)
        else ->
            fromAndUntil(
                from = from,
                until = until,
            )
    }
