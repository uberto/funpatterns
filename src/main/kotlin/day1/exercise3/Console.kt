package day1.exercise3

import java.io.BufferedReader
import java.io.InputStreamReader


data class Console<T> (val exec: () -> T) {

    fun <B> andThen(other: Console<B>): Console<B> = Console {
        this.exec()
        other.exec()
    }
}


fun printIO(msg: String) = Console { println(msg)}

val reader =  BufferedReader( InputStreamReader(System.`in`))

fun readlineIO() = Console<String> { reader.readLine() }