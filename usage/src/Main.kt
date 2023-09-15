
import customImplementation.localDate.IntervalLocalDate.until
import interval.implementations.until
import java.time.LocalDate

fun main(){
    val date = LocalDate.now()
    val date2 = date.plusWeeks(1)

    val period = date until date2
    println(period)
}