package interval.implementations

import interval.interfaces.All
import interval.interfaces.AtLeast
import interval.interfaces.Between
import interval.interfaces.LessThan

private object AllImpl : All<Nothing> {
    override val after: Nothing? = null
    override val from: Nothing? = null
    override val to: Nothing? = null
    override val toIncluding: Nothing? = null

    override fun times(other: LessThan<Nothing>) = other
    override fun times(other: AtLeast<Nothing>) = other
    override fun times(other: All<Nothing>) = other
    override fun times(other: Between<Nothing>) = other
}

fun <T : Comparable<T>> openInterval(): All<T> = @Suppress("UNCHECKED_CAST")
(AllImpl as All<T>)