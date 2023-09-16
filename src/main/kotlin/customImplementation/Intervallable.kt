package customImplementation

import incrementable.Incrementable
import interval.interfaces.Interval

internal interface Intervallable<T : Comparable<T>> : Incrementable<T>, IntervalConstruction<T>, IntervalMapConstruction<T>