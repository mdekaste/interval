package v2.construction

import v2.Incrementable
import v2.openness.*

interface IntervalConstruction<C : Comparable<C>> : Incrementable<C> {
    // After
    fun after(value: C): After<C> = v2.openness.after(value)

    fun after(value: C?): Right.Open<C> = v2.openness.after(value)

    // AfterUntil
    fun afterUntil(
        after: C,
        until: C,
    ): AfterAndUntil<C> = v2.openness.afterUntil(after, until)

    fun afterUntil(
        after: C?,
        until: C,
    ): Right.Closed<C> = v2.openness.afterUntil(after, until)

    fun afterUntil(
        after: C,
        until: C?,
    ): Left.Closed<C> = v2.openness.afterUntil(after, until)

    fun afterUntil(
        after: C?,
        until: C?,
    ): Interval<C> = v2.openness.afterUntil(after, until)

    // AfterAndUntilIncluding
    fun afterAndUntilIncluding(
        after: C,
        untilIncluding: C,
    ): AfterAndUntilIncluding<C> =
        v2.openness.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    fun afterAndUntilIncluding(
        after: C?,
        untilIncluding: C,
    ): Right.Closed<C> =
        v2.openness.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    fun afterAndUntilIncluding(
        after: C,
        untilIncluding: C?,
    ): Left.Closed<C> =
        v2.openness.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    fun afterAndUntilIncluding(
        after: C?,
        untilIncluding: C?,
    ): Interval<C> =
        v2.openness.afterAndUntilIncluding(
            after,
            untilIncluding,
        )

    // From
    fun from(from: C): From<C> = v2.openness.from(from)

    fun from(from: C?): Right.Open<C> = v2.openness.from(from)

    // FromAndUntil
    fun fromAndUntil(
        from: C,
        until: C,
    ): FromAndUntil<C> = v2.openness.fromAndUntil(from, until)

    fun fromAndUntil(
        from: C?,
        until: C,
    ): Right.Closed<C> = v2.openness.fromAndUntil(from, until)

    fun fromAndUntil(
        from: C,
        until: C?,
    ): Left.Closed<C> = v2.openness.fromAndUntil(from, until)

    fun fromAndUntil(
        from: C?,
        until: C?,
    ): Interval<C> = v2.openness.fromAndUntil(from, until)

    infix fun C.until(until: C): FromAndUntil<C> = v2.openness.fromAndUntil(this, until)

    infix fun C?.until(until: C): Right.Closed<C> = v2.openness.fromAndUntil(this, until)

    infix fun C.until(until: C?): Left.Closed<C> = v2.openness.fromAndUntil(this, until)

    infix fun C?.until(until: C?): Interval<C> = v2.openness.fromAndUntil(this, until)

    // FromAndUntilIncluding
    fun fromAndUntilIncluding(
        from: C,
        untilIncluding: C,
    ): FromAndUntilIncluding<C> =
        v2.openness.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    fun fromAndUntilIncluding(
        from: C?,
        untilIncluding: C,
    ): Right.Closed<C> =
        v2.openness.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    fun fromAndUntilIncluding(
        from: C,
        untilIncluding: C?,
    ): Left.Closed<C> =
        v2.openness.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    fun fromAndUntilIncluding(
        from: C?,
        untilIncluding: C?,
    ): Interval<C> =
        v2.openness.fromAndUntilIncluding(
            from,
            untilIncluding,
        )

    infix fun C.untilIncluding(untilIncluding: C): FromAndUntilIncluding<C> = v2.openness.fromAndUntilIncluding(this, untilIncluding)

    infix fun C?.untilIncluding(untilIncluding: C): Right.Closed<C> = v2.openness.fromAndUntilIncluding(this, untilIncluding)

    infix fun C.untilIncluding(untilIncluding: C?): Left.Closed<C> = v2.openness.fromAndUntilIncluding(this, untilIncluding)

    infix fun C?.untilIncluding(untilIncluding: C?): Interval<C> = v2.openness.fromAndUntilIncluding(this, untilIncluding)

    // Until
    fun until(until: C): Until<C> = v2.openness.until(until)

    fun until(until: C?): Left.Open<C> = v2.openness.until(until)

    // UntilIncluding
    fun untilIncluding(untilIncluding: C): UntilIncluding<C> = v2.openness.untilIncluding(untilIncluding)

    fun untilIncluding(untilIncluding: C?): Left.Open<C> = v2.openness.untilIncluding(untilIncluding)
}
