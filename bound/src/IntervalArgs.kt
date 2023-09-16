interface FromToNull<T : Comparable<T>>{
    val from: T?
    val to: T?
}

interface FromNonNull<T : Comparable<T>> : FromToNull<T>{
    override val from: T
}

interface ToNonNull<T: Comparable<T>> : FromToNull<T>{
    override val to: T
}

interface FromToNonNull<T : Comparable<T>> : FromNonNull<T>, ToNonNull<T>