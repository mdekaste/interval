package incrementable

import java.time.LocalDateTime

object IncrementableLocalDateTime : Incrementable<LocalDateTime> {
    override fun LocalDateTime.increment() = plusNanos(1)
    override fun LocalDateTime.decrement() = minusNanos(1)

    override val maxValue: LocalDateTime = LocalDateTime.MAX
    override val minValue: LocalDateTime = LocalDateTime.MIN
}