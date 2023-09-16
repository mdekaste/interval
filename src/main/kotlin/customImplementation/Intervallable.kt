package customImplementation

import incrementable.Incrementable

internal interface Intervallable<T : Comparable<T>> :
    Incrementable<T>,
    IntervalConstruction<T>,
    IntervalMapConstruction<T>
