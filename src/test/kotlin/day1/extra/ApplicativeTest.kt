package day1.extra


import day1.exercise3.Console
import day1.exercise3.printIO
import day1.exercise3.readlineIO


fun qaConsole(showQuestion: Console<Unit>, readAnswer: Console<String>) =
    Console { showQuestion.andThen(readAnswer).exec() }

fun question(msg: String): Console<String> = qaConsole(printIO(msg), readlineIO())

data class Person(val name: String, val surname: String, val country: String)


fun main() {


    val conPerson = question("your name") applyOn
            question("your surname") applyOn
            question("your country") map3 ::Person


    val p = conPerson.exec()

    println(p)
}

infix fun <A, B> Console<A>.applyOn(other: Console<B>): Console<Pair<A, B>> {
    return Console({ Pair(this.exec(), other.exec()) })
}


infix fun <A, B, R> Console<Pair<A, B>>.map2(f: (A, B) -> R): Console<R> {
    val (a, b) = this.exec()

    return Console({ f(a, b) })
}

infix fun <A, B, C, R> Console<Pair<Pair<A, B>, C>>.map3(f: (A, B, C) -> R): Console<R> {
    val (pair, c) = this.exec()
    val (a, b) = pair
    return Console({ f(a, b, c) })
}
