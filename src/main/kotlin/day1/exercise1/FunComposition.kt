package day1.exercise1

typealias FUN<A, B> = (A) -> B


infix fun <A, B, C> FUN<B, C>.on(other: FUN<A, B>): FUN<A, C> = { a ->
    this(other(a))
}

infix fun <A, B, C> FUN<A, B>.andThen(other: FUN<B, C>): FUN<A, C> = { a ->
    other(this(a))
}
