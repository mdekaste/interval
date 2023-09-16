
import customImplementation.IncrementableDouble.groupByInterval
import customImplementation.IncrementableDouble.until
import customImplementation.IncrementableInt.before
import customImplementation.IncrementableInt.from

fun main(){
    val list = List(1000){ it }

    when(4){
        in before(5) -> println("smaller than 5")
        in from(5) -> println("at least 5")
    }

    val interval = before(3)
    val map = list.groupByInterval(
        keySelector = { null until it.toDouble() },
        valueTransform = { it.toDouble() },
        defaultValue = 0.0,
        onConflict = Double::plus
    )
}