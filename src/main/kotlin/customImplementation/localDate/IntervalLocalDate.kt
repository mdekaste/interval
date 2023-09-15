package customImplementation.localDate

import customImplementation.IntervalConstruction
import incrementable.Incrementable
import java.time.LocalDate

object IntervalLocalDate : IntervalConstruction<LocalDate>, Incrementable<LocalDate> by IncrementableLocalDate