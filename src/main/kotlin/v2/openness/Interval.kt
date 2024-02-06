package v2.openness

sealed interface Interval<T> {
    val from: T?
    val until: T?

    val after: T?
    val untilIncluding: T?
}