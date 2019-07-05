package day1.exercise3

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConsoleTest {

    val fakeSideEffect: Console<Unit> = Console ({  })
    val pseudoPrint: (String) -> Console<Unit> = { x -> fakeSideEffect }
    val pseudoReadline: () -> Console<String> = { Console( {"Ciao"} ) }

    @Test
    fun `how can we test the console?`(){

        val echo = EchoMachine(pseudoPrint, pseudoReadline)

        val readedLine = pseudoPrint("Come ti chiami?").andThen(pseudoReadline()).exec()

        Assertions.assertEquals("Ciao", readedLine)
    }

    fun main(){

    }
}
