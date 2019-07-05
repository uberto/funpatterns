package day1.homeExercise


//see http://matteo.vaccari.name/blog/archives/154 for the Kata rules

sealed class ProgramError
data class FileNotFound(val path: String): ProgramError()
data class ParseError(val source: String): ProgramError()

fun sendGreetingsToAll(filename: String) {
    TODO()
}