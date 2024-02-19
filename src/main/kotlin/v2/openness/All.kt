package v2.openness

/**
 * representation of '(-∞, ∞)
 */
object All : Open<Nothing> {
    override val after: Nothing? = null
    override val from: Nothing? = null
    override val untilIncluding: Nothing? = null
    override val until: Nothing? = null
}

@Suppress("UNCHECKED_CAST")
fun <T : Comparable<T>> openInterval(): Open<T> = All as Open<T>
