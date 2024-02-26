package v2.standard

import v2.Incrementable
import v2.construction.IntervalConstruction
import v2.construction.IntervalMapConstruction

interface Intervallable<T : Comparable<T>> : Incrementable<T>, IntervalConstruction<T>, IntervalMapConstruction<T>
