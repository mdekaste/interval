package v2.openness

import incrementable.Incrementable

sealed interface Interval<T> {
    val from: T?
    val to: T?

    val after: T?
    val toIncluding: T?
}