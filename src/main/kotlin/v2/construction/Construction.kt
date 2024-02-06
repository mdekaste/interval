package v2.construction

import v2.Incrementable
import v2.openness.*

interface IntervalConstruction<T : Comparable<T>> : Incrementable<T> {
    fun after(after: T): ClosedOpen<T> = after(after, this@IntervalConstruction)

    fun after(after: T?): Interval<T> = after(after, this@IntervalConstruction)

    infix fun T.afterUntil(until: T): Closed<T> = afterUntil(this, until, this@IntervalConstruction)

    infix fun T.afterUntil(until: T?): Left.Closed<T> = afterUntil(this, until, this@IntervalConstruction)

    infix fun T?.afterUntil(until: T): Right.Closed<T> = afterUntil(this, until, this@IntervalConstruction)

    infix fun T?.afterUntil(until: T?): Interval<T> = afterUntil(this, until, this@IntervalConstruction)
}
