import customImplementation.localDate.IncrementableLocalDate.associateByInterval
import customImplementation.localDate.IncrementableLocalDate.until
import java.time.LocalDate

fun main(){
    val list = List(2){ it * 10 }

    val map = list.associateByInterval(
        keySelector = { LocalDate.now().let { date -> date.plusWeeks(it.toLong()) until date.plusWeeks(it.toLong() + 1L) } },
        valueTransform = { it.toString() },
        defaultValue = "nope"
    )

    println(map.firstEntry())
    println(map.lastEntry())
    map.entries.let(::println)

    val test = map.toSortedMap(compareBy(nullsLast()){ it.to })

    println(test)
}