package day1.exercise3

import java.io.BufferedReader
import java.io.InputStreamReader


data class Console<A> (val exec: () -> A) {

    fun andThen(other: Console<A>): Console<A> = Console {
        this.exec()
        other.exec()
    }

    fun <B> map(f: (A) -> B): Console<B> = Console {
        f(exec())
    }

    fun <B> flatMap(f: (A) -> Console<B>): Console<B> = Console {
        f(exec()).exec()
    }
}


fun printIO(msg: String): Console<Unit> = Console { println(msg)}

val reader =  BufferedReader( InputStreamReader(System.`in`))

fun readlineIO() = Console<String> { reader.readLine() }