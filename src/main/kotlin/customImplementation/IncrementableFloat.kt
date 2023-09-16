package customImplementation

import kotlin.math.nextDown
import kotlin.math.nextUp

object IncrementableFloat : Intervallable<Float> {
    override fun Float.increment() = nextUp()
    override fun Float.decrement() = nextDown()

    override val maxValue: Float = Float.MAX_VALUE
    override val minValue: Float = Float.MIN_VALUE
}
