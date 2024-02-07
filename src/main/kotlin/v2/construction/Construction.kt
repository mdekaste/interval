package v2.construction

import v2.Incrementable
import v2.openness.*

interface IntervalConstruction<T : Comparable<T>> : Incrementable<T> {
    fun after(after: T) = after(after, this@IntervalConstruction)

    fun after(after: T?) = after(after, this@IntervalConstruction)

    fun until(until: T) = until(until, this@IntervalConstruction)

    fun until(until: T?) = until(until, this@IntervalConstruction)

    infix fun from(from: T) = from(from, this@IntervalConstruction)

    infix fun from(from: T?) = from(from, this@IntervalConstruction)

    infix fun untilIncluding(untilIncluding: T) = untilIncluding(untilIncluding, this@IntervalConstruction)

    infix fun untilIncluding(untilIncluding: T?) = untilIncluding(untilIncluding, this@IntervalConstruction)

    infix fun T.afterUntil(until: T) = afterUntil(this, until, this@IntervalConstruction)

    infix fun T.afterUntil(until: T?) = afterUntil(this, until, this@IntervalConstruction)

    infix fun T?.afterUntil(until: T) = afterUntil(this, until, this@IntervalConstruction)

    infix fun T?.afterUntil(until: T?) = afterUntil(this, until, this@IntervalConstruction)

    infix fun After<T>.until(until: T) = afterUntil(after, until, this@IntervalConstruction)

    infix fun After<T>.until(until: T?) = afterUntil(after, until, this@IntervalConstruction)

    infix fun Until<T>.after(after: T) = afterUntil(after, until, this@IntervalConstruction)

    infix fun Until<T>.after(after: T?) = afterUntil(after, until, this@IntervalConstruction)

    infix fun T.until(until: T) = fromAndUntil(this, until, this@IntervalConstruction)
}
