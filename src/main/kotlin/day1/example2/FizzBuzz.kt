package day1.example2

import day1.example2.Maybe.Companion.just
import day1.example2.Maybe.Companion.none


fun fizz(x: Int) = if (x % 3 == 0) just("Fizz") else none()
fun buzz(x: Int) = if (x % 5 == 0) just("Buzz") else none()

val rules = listOf(::fizz, ::buzz)

fun fizzBuzz(x: Int): String {
    val s: Semigroup<Maybe<String>> = Semigroup.ofMaybes(Semigroup.ofStrings())
    return rules.fold(None, {acc: Maybe<String>, rule -> s.combine(acc, rule(x))})
                .getOrElse(x.toString())
}