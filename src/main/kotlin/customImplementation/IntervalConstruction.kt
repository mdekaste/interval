package customImplementation

import incrementable.Incrementable
import interval.implementations.*
import interval.implementations.afterUntil as afterUntilImpl
import interval.implementations.afterUntilIncluding as afterUntilIncludingImpl
import interval.implementations.until as untilImpl
import interval.implementations.untilIncluding as untilIncludingImpl
import interval.interfaces.Closed
import interval.interfaces.LeftBound
import interval.interfaces.Open
import interval.interfaces.RightBound


/**
 * connects the functions that normally require a context to work in, to make a user able to define their own contexts as a static object
 *
 */
internal interface IntervalConstruction<T>: Incrementable<T> where T : Comparable<T>  {
    infix fun T.until(to: T): Closed<T> = this untilImpl to
    infix fun T.untilIncluding(toIncluding: T): Closed<T> = this untilIncludingImpl toIncluding
    infix fun T.afterUntil(to: T): Closed<T> = this afterUntilImpl to
    infix fun T.afterUntilIncluding(toIncluding: T): Closed<T> = this afterUntilIncludingImpl toIncluding

    infix fun T.until(to: T?): LeftBound<T> = this untilImpl to
    infix fun T.untilIncluding(toIncluding: T?): LeftBound<T> = this untilIncludingImpl toIncluding
    infix fun T.afterUntil(to: T?): LeftBound<T> = this afterUntilImpl to
    infix fun T.afterUntilIncluding(toIncluding: T?): LeftBound<T> = this afterUntilIncludingImpl toIncluding

    infix fun T?.until(other: T): RightBound<T> = this untilImpl other
    infix fun T?.untilIncluding(other: T): RightBound<T> = this untilIncludingImpl other
    infix fun T?.afterUntil(other: T): RightBound<T> = this afterUntilImpl other
    infix fun T?.afterUntilIncluding(other: T): RightBound<T> = this afterUntilIncludingImpl other

    infix fun T?.until(to: T?): Open<T> = this untilImpl to
    infix fun T?.untilIncluding(toIncluding: T?) = this untilIncludingImpl toIncluding
    infix fun T?.afterUntil(to: T?): Open<T> = this afterUntilImpl to
    infix fun T?.afterUntilIncluding(toIncluding: T?) = this afterUntilIncludingImpl toIncluding
}