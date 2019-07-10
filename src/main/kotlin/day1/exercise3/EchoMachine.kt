package day1.exercise3

import day1.example3.*

data class EchoMachine(
    val writer: (String) -> Console<Unit>,
    val reader: () -> Console<String>
) {
    fun echo() =
        reader()
            .flatMap { writer(it) }
            .exec()
}

data class Calculator(
    val writer: (String) -> Console<Unit>,
    val reader: () -> Console<String>
) {
    fun run() =
        reader()
            .map {
                parseOperation(it)
                    .map(this::evalOperation)
                    .map { it.toString() }
                    .recoverWith { it.msg }
            }
            .flatMap { writer(it) }
            .exec()

    private fun evalOperation(op: Operation): Int {
        return when (op) {
            is Sum -> op.x + op.y
        }
    }

    private fun parseOperation(input: String): Outcome<ParsingError, Operation> {
        val parts = input.split(' ')
        return if (parts.size != 3)
            Failure(WrongParamsCount("Invalid: $input"))
        else if (parts[0] != "+")
            Failure(UnsupportedOperation("Invalid: $input"))
        else if (parts[1].toIntOrNull() == null ||
            parts[2].toIntOrNull() == null
        )
            Failure(InvalidNumbers("Invalid: $input"))
        else
            Success(Sum(parts[1].toInt(), parts[2].toInt()))
    }


}

sealed class Operation
data class Sum(val x: Int, val y: Int) : Operation()

sealed class ParsingError : Error
data class WrongParamsCount(override val msg: String) : ParsingError()
data class UnsupportedOperation(override val msg: String) : ParsingError()
data class InvalidNumbers(override val msg: String) : ParsingError()
