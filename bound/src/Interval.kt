// // sealed interface Range<T : Comparable<T>> {
// //    interface All<T : Comparable<T>> : Range<T>, IntervalBounds.None<T>, IntervalOpen.Both<T>
// //    interface LessThan<T : Comparable<T>> : Range<T>, IntervalBounds.Right<T>, IntervalOpen.Left<T>
// //    interface AtLeast<T : Comparable<T>> : Range<T>, IntervalBounds.Left<T>, IntervalOpen.Right<T>
// //    interface Between<T : Comparable<T>>: Range<T>, IntervalBounds.Both<T>, IntervalOpen.None<T>
// // }
// //
// // data class AllImpl<T : Comparable<T>>(override val from: T? = null, override val to: T? = null): Range.All<T>
// // data class LessThanImpl<T :Comparable<T>>(override val to: T): Range.LessThan<T> {
// //    override val from: T? = null
// // }
// //
// // data class AtLeastImpl<T : Comparable<T>>(override val from: T) : Range.AtLeast<T> {
// //    override val to: T? = null
// // }
// //
// // data class BetweenImpl<T : Comparable<T>>(override val from: T, override val to: T): Range.Between<T>
// // sealed interface IntervalBounds<T : Comparable<T>> {
// //    val from: T?
// //    val to: T?
// //
// //    sealed interface None<T : Comparable<T>> : IntervalBounds<T>
// //    sealed interface Left<T : Comparable<T>> : None<T> {
// //        override val from: T
// //    }
// //
// //    sealed interface Right<T : Comparable<T>> : None<T> {
// //        override val to: T
// //    }
// //
// //    sealed interface Both<T: Comparable<T>> : Left<T>, Right<T>
// // }
// //
// // sealed interface IntervalOpen<T: Comparable<T>> {
// //    sealed interface None<T : Comparable<T>> : IntervalOpen<T>
// //    sealed interface Left<T : Comparable<T>> : None<T>
// //    sealed interface Right<T : Comparable<T>> : None<T>
// //    sealed interface Both<T: Comparable<T>> : Left<T>, Right<T>
// // }
//
// sealed interface Range<T : Comparable<T>> {
//    interface All<T : Comparable<T>> : Range<T>, FromToNull<T>
//    interface LessThan<T : Comparable<T>> : Range<T>, ToNonNull<T>
//    interface AtLeast<T : Comparable<T>> : Range<T>, FromNonNull<T>
//    interface Between<T : Comparable<T>> : Range<T>, FromToNonNull<T>
// }
//
// data class AllImpl<T : Comparable<T>>(override val from: T? = null, override val to: T? = null) :
//    Range.All<T>,
//    BothOpen<T>,
//    NoneClosed<T>
//
// data class LessThanImpl<T : Comparable<T>>(override val from: T? = null, override val to: T) :
//    Range.LessThan<T>,
//    LeftOpen<T>,
//    RightClosed<T>
//
// data class AtLeastImpl<T : Comparable<T>>(override val to: T? = null, override val from: T) :
//    Range.AtLeast<T>,
//    RightOpen<T>,
//    LeftClosed<T>
//
// data class BetweenImpl<T : Comparable<T>>(override val to: T, override val from: T) :
//    Range.Between<T>,
//    NoneOpen<T>,
//    BothClosed<T>
//
// fun main() {
//    val range: Range<Int> = LessThanImpl(to = 4)
//
//    when (range) {
//        is Range.All -> TODO()
//        is Range.AtLeast -> TODO()
//        is Range.Between -> TODO()
//        is Range.LessThan -> TODO()
//    }
// }
//
// typealias BothOpen<T> = Range.All<T>
