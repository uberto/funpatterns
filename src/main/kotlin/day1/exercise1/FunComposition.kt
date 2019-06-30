package day1.exercise1

typealias FUN<A,B> = (A) -> B


infix fun <A,B,C> FUN<A,B>.on(other: FUN<B,C>): FUN<A, C> = TODO()

infix fun <A,B,C> FUN<A,B>.andThen(other: FUN<B,C>): FUN<A, C> = TODO()
