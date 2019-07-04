package day1.exercise3

import org.junit.jupiter.api.Test

class ConsoleTest {


    val pseudoPrint: (String) -> Console<Unit> = TODO()
    val pseudoReadline: () -> Console<String> = TODO()

    @Test
    fun `how can we test the console?`(){

        val echo = EchoMachine(pseudoPrint, pseudoReadline)

        TODO("finish the test with assertions")

    }

    fun main(){

    }
}