package day1.exercise1

typealias FUN<A,B> = (A) -> B


infix fun <A,B,C> FUN<B,C>.on(other: FUN<A,B>): FUN<A, C> = {x -> this(other(x))}

infix fun <A,B,C> FUN<A,B>.andThen(other: FUN<B,C>): FUN<A, C> = {x -> other(this(x))}
