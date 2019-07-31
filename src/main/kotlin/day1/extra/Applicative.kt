package day1.extra

data class Applicative<T>(val value: T) {

    fun <R> applyOn(other: Applicative<(T) -> R>): Applicative<R> = Applicative(other.value(this.value))
}

fun <A, B, R> Applicative<Pair<A, B>>.map2(f: (A, B) -> R): Applicative<R> = Applicative(f(this.value.first, this.value.second))

fun <A, B, C, R> Applicative<Triple<A, B, C>>.map3(f: (A, B, C) -> R): Applicative<R> = Applicative(f(this.value.first, this.value.second, this.value.third))

