package v2.openness

import v2.Incrementable

interface Open<T> : Left.Open<T>, Right.Open<T>
interface Closed<T> : Left.Closed<T>, Right.Closed<T>
interface OpenClosed<T>: Left.Open<T>, Right.Closed<T>
interface ClosedOpen<T>: Left.Closed<T>, Right.Open<T>

/**
 * representation of '(-∞, ∞)
 */
class All<T : Comparable<T>>(
    incrementable: Incrementable<T>
) : Open<T>, Incrementable<T> by incrementable {
    override val after: T? = null
    override val from: T? = null
    override val toIncluding: T? = null
    override val to: T? = null
}

/**
 * Representation of '(x, y)'
 */
class AfterAndUntil<T : Comparable<T>>(
    override val after: T,
    override val to: T,
    incrementable: Incrementable<T>,
) : Closed<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val toIncluding: T by lazy { to.decrement() }
}

/**
 * Representation of '[x, y)'
 */
class FromAndUntil<T : Comparable<T>>(
    override val from: T,
    override val to: T,
    incrementable: Incrementable<T>
) : Closed<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val toIncluding: T by lazy { to.decrement() }
}

/**
 * Representation of '(x, y]'
 */
class AfterAndUntilIncluding<T : Comparable<T>>(
    override val after: T,
    override val toIncluding: T,
    incrementable: Incrementable<T>
) : Closed<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val to: T by lazy { toIncluding.increment() }
}

/**
 * Representation of '[x, y]'
 */
class FromAndUntilIncluding<T: Comparable<T>>(
    override val from: T,
    override val toIncluding: T,
    incrementable: Incrementable<T>
) : Closed<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val to: T by lazy { toIncluding.increment() }
}
/**
 * Representation of '[x, ∞)'
 */
class From<T : Comparable<T>>(
    override val from: T,
    incrementable: Incrementable<T>
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val after: T by lazy { from.decrement() }
    override val to: T? = null
    override val toIncluding: T? = null
}

/**
 * Representation of '(x, ∞)'
 */
class After<T : Comparable<T>>(
    override val after: T,
    incrementable: Incrementable<T>
) : ClosedOpen<T>, Incrementable<T> by incrementable {
    override val from: T by lazy { after.increment() }
    override val to: T? = null
    override val toIncluding: T? = null
}

/**
 * Representation of '(-∞, y)'
 */
class Until<T: Comparable<T>>(
    override val to: T,
    incrementable: Incrementable<T>
) : OpenClosed<T>, Incrementable<T> by incrementable {
    override val after: T? = null
    override val from: T? = null
    override val toIncluding: T by lazy { to.decrement() }
}

/**
 * Representation of '(-∞, y]'
 */
class UntilIncluding<T: Comparable<T>>(
    override val toIncluding: T,
    incrementable: Incrementable<T>
): OpenClosed<T>, Incrementable<T> by incrementable {
    override val to: T by lazy { toIncluding.increment() }
    override val after: T? = null
    override val from: T? = null
}

context(Incrementable<T>) infix fun<T : Comparable<T>> T.until(other: T) = FromAndUntil(
    this,
    other,
    this@until as Incrementable<T>
)