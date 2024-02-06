package v2.openness

interface Open<T> : Left.Open<T>, Right.Open<T>
interface OpenClosed<T> : Left.Open<T>, Right.Closed<T>
interface ClosedOpen<T> : Left.Closed<T>, Right.Open<T>
interface Closed<T> : Left.Closed<T>, Right.Closed<T>