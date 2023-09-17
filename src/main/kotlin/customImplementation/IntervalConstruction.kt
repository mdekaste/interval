package customImplementation

import incrementable.Incrementable
import interval.interfaces.*
import interval.implementations.after as afterImpl
import interval.implementations.afterUntil as afterUntilImpl
import interval.implementations.afterUntilIncluding as afterUntilIncludingImpl
import interval.implementations.before as beforeImpl
import interval.implementations.beforeIncluding as beforeIncludingImpl
import interval.implementations.from as fromImpl
import interval.implementations.until as untilImpl
import interval.implementations.untilIncluding as untilIncludingImpl

/**
 * connects the functions that normally require a context to work in, to make a user able to define their own contexts as a static object
 */
internal interface IntervalConstruction<T> : Incrementable<T> where T : Comparable<T> {
    infix fun T.until(to: T): Between<T> = this untilImpl to
    infix fun T.untilIncluding(toIncluding: T): Between<T> = this untilIncludingImpl toIncluding
    infix fun T.afterUntil(to: T): Between<T> = this afterUntilImpl to
    infix fun T.afterUntilIncluding(toIncluding: T): Between<T> = this afterUntilIncludingImpl toIncluding

    infix fun T.until(to: T?): Left.Closed<T> = this untilImpl to
    infix fun T.untilIncluding(toIncluding: T?): Left.Closed<T> = this untilIncludingImpl toIncluding
    infix fun T.afterUntil(to: T?): Left.Closed<T> = this afterUntilImpl to
    infix fun T.afterUntilIncluding(toIncluding: T?): Left.Closed<T> = this afterUntilIncludingImpl toIncluding

    infix fun T?.until(other: T): Right.Closed<T> = this untilImpl other
    infix fun T?.untilIncluding(other: T): Right.Closed<T> = this untilIncludingImpl other
    infix fun T?.afterUntil(other: T): Right.Closed<T> = this afterUntilImpl other
    infix fun T?.afterUntilIncluding(other: T): Right.Closed<T> = this afterUntilIncludingImpl other

    infix fun T?.until(to: T?): Interval<T> = this untilImpl to
    infix fun T?.untilIncluding(toIncluding: T?): Interval<T> = this untilIncludingImpl toIncluding
    infix fun T?.afterUntil(to: T?): Interval<T> = this afterUntilImpl to
    infix fun T?.afterUntilIncluding(toIncluding: T?): Interval<T> = this afterUntilIncludingImpl toIncluding

    fun after(after: T): AtLeast<T> = afterImpl(after)
    fun from(from: T): AtLeast<T> = fromImpl(from)
    fun before(before: T): LessThan<T> = beforeImpl(before)
    fun beforeIncluding(beforeIncluding: T): LessThan<T> = beforeIncludingImpl(beforeIncluding)

    fun after(after: T?): Right.Open<T> = afterImpl(after)
    fun from(from: T?): Right.Open<T> = fromImpl(from)

    fun before(before: T?): Left.Open<T> = beforeImpl(before)
    fun beforeIncluding(beforeIncluding: T?): Left.Open<T> = beforeIncludingImpl(beforeIncluding)
}
