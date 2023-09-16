import customImplementation.double.IncrementableDouble.groupByInterval
import customImplementation.double.IncrementableDouble.until

fun main(){
    val list = List(1000){ it }

    val map = list.groupByInterval(
        keySelector = { null until it.toDouble() },
        valueTransform = { it.toDouble() },
        defaultValue = 0.0,
        onConflict = Double::plus
    )
}