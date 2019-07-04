package day1.example2

import day1.example2.Maybe.Companion.just
import day1.example2.Maybe.Companion.none


fun fizzBuzz(x: Int): String {
    val s: Semigroup<Maybe<String>> = Semigroup.ofMaybes(Semigroup.ofStrings())
    return s.combine(fizz(x), buzz(x)).orElse(x.toString())
}

fun fizz(x: Int) = if (x % 3 == 0) just("Fizz") else none()
fun buzz(x: Int) = if (x % 5 == 0) just("Buzz") else none()

fun <T> identity(t: T): T = t

sealed class Maybe<A> {

    fun <B> map(f: (A) -> B): Maybe<B> =
        when (this) {
            is Just -> just(f(this.value))
            is None -> none()
        }

    fun <B> flatMap(f: (A) -> Maybe<B>): Maybe<B> =
        when (this) {
            is Just -> f(this.value)
            is None -> none()
        }

    fun <B> fold(ifEmpty: B, ifNotEmpty: (A) -> B): B =
        when (this) {
            is Just -> ifNotEmpty(this.value)
            is None -> ifEmpty
        }

    fun orElse(a: A): A = fold(a, ::identity)

    companion object {
        fun <A> just(value: A): Maybe<A> = Just(value)
        fun <A> none(): Maybe<A> = None()
    }
}

data class Just<A>(val value: A) : Maybe<A>()
class None<A>: Maybe<A>()

interface Semigroup<A> {
    fun combine(a: A, b: A): A

    companion object {
        fun ofStrings(): Semigroup<String> = object : Semigroup<String> {
            override fun combine(a: String, b: String): String = a + b
        }

        fun <A> ofMaybes(s: Semigroup<A>): Semigroup<Maybe<A>> = object : Semigroup<Maybe<A>> {
            override fun combine(a: Maybe<A>, b: Maybe<A>): Maybe<A> =
                a.fold(
                    b,
                    { valA ->
                        b.fold(
                            just(valA),
                            { valB -> just(s.combine(valA, valB)) }
                        )
                    }
                )
        }
    }
}