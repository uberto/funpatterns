package day1.exercise3

import day1.example3.Outcome
import java.lang.UnsupportedOperationException

class CmdLineCalculator(
    val cout: (String) -> Console<Unit>,
    val cin: () -> Console<String>
) {
    fun enter() {
        val input = cin().exec()
        input.split(" ")
            .let { (operator, operand1, operand2) ->
                Outcome.tryThis{
                    when (operator){
                        "+" -> operand1.toInt() + operand2.toInt()
                        "*" -> operand1.toInt() * operand2.toInt()
                        else -> throw UnsupportedOperationException(operator)
                    }
                }
            }.fold(
                { cout("Error: cannot process $input").exec()},
                { v -> cout(v.toString()).exec()}
            )
    }

}

fun main() {
    val calculator = CmdLineCalculator(::printIO, ::readlineIO)

    calculator.enter()
}