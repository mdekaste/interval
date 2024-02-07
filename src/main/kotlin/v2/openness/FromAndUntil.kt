package v2.openness

import v2.Incrementable

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

fun <T : Comparable<T>> fromAndUntil(
    from: T,
    until: T,
    incrementable: Incrementable<T>,
): FromAndUntil<T> =
    FromAndUntil(
        from = from,
        until = until,
        incrementable = incrementable,
    )

fun <T : Comparable<T>> fromAndUntil(
    from: T?,
    until: T,
    incrementable: Incrementable<T>,
): Right.Closed<T> =
    when (from) {
        null ->
            until(
                until = until,
                incrementable = incrementable,
            )

        else ->
            fromAndUntil(
                from = from,
                until = until,
                incrementable = incrementable,
            )
    }

fun <T : Comparable<T>> fromAndUntil(
    from: T,
    until: T?,
    incrementable: Incrementable<T>,
): Left.Closed<T> =
    when (until) {
        null ->
            from(
                from = from,
                incrementable = incrementable,
            )

        else ->
            fromAndUntil(
                from = from,
                until = until,
                incrementable = incrementable,
            )
    }

fun <T : Comparable<T>> fromAndUntil(
    from: T?,
    until: T?,
    incrementable: Incrementable<T>,
): Interval<T> =
    when {
        from == null ->
            until(
                until = until,
                incrementable = incrementable,
            )

        until == null ->
            from(
                from = from,
                incrementable = incrementable,
            )

        else ->
            fromAndUntil(
                from = from,
                until = until,
                incrementable = incrementable,
            )
    }
