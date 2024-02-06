package v2.openness

import com.sun.jdi.IntegerValue

sealed interface Right<T> {
    sealed interface Open<T> : Right<T>, Interval<T>
    sealed interface Closed<T> : Right<T>, Interval<T> {
        override val to: T
        override val toIncluding: T
    }
}