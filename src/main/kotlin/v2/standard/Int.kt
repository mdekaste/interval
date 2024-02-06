package v2.standard

import v2.Incrementable

object IntIncrementable : Incrementable<Int> {
    override val minValue: Int = Int.MIN_VALUE
    override val maxValue: Int = Int.MAX_VALUE

    override fun Int.decrement() = minus(1)
    override fun Int.increment() = plus(1)
}