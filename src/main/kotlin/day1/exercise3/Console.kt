package day1.exercise3

import java.io.BufferedReader
import java.io.InputStreamReader


data class Console<T> (val run: () -> T) {

    fun andThen(other: Console<T>): Console<T> = Console {
        this.run()
        other.run()
    }
}


fun printIO(msg: String) = Console { println(msg)}

val reader =  BufferedReader( InputStreamReader(System.`in`))

fun readlineIO(msg: String) = Console<String> { reader.readLine() }