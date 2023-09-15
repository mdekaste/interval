package customImplementation.double

import incrementable.Incrementable
import kotlin.math.nextDown
import kotlin.math.nextUp

object IncrementableDouble : Incrementable<Double> {
    override fun Double.increment() = nextUp()
    override fun Double.decrement() = nextDown()

    override val maxValue: Double = Double.MAX_VALUE
    override val minValue: Double = Double.MIN_VALUE
}