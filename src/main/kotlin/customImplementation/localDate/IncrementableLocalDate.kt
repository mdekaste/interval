package customImplementation.localDate

import incrementable.Incrementable
import java.time.LocalDate


object IncrementableLocalDate : Incrementable<LocalDate> {
    override fun LocalDate.increment(): LocalDate = plusDays(1)
    override fun LocalDate.decrement(): LocalDate = minusDays(1)

    override val maxValue: LocalDate = LocalDate.MAX
    override val minValue: LocalDate = LocalDate.MIN
}