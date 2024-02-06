package v2.construction

import v2.Incrementable

interface Intervallable<T : Comparable<T>> : Incrementable<T>, IntervalConstruction<T>
