package extra

import java.util.function.Function


interface Applicative<A> {

    val value: A


}
//
//infix fun <T, R> Applicative<T>.applyOn(other: Applicative<(T) -> R>): Applicative<R> = Applicative.map(other.value(value))
//
//fun <A, B, R> Applicative<Pair<A, B>.map2(f: (Pair<A, B>) -> R): Applicative<R> = TODO()