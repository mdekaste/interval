package customImplementation

object IncrementableInt : Intervallable<Int> {
    override fun Int.increment() = plus(1)

    override fun Int.decrement() = minus(1)

    override val maxValue: Int = Int.MAX_VALUE
    override val minValue: Int = Int.MIN_VALUE
}