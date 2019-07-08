package day1.exercise3

import java.io.BufferedReader
import java.io.InputStreamReader


data class Console<T> (val exec: () -> T) {

    fun andThen(other: Console<T>): Console<T> = Console {
        this.exec()
        other.exec()
    }

    fun <U> map(f: (T) ->U): Console<U> = Console { f(exec()) }
    fun <U> flatMap(f: (T) ->Console<U>): Console<U> = f(exec())
}


fun printIO(msg: String): Console<Unit> = Console { println(msg)}

val reader =  BufferedReader( InputStreamReader(System.`in`))

fun readlineIO() = Console<String> { reader.readLine() }