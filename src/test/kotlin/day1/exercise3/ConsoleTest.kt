package day1.exercise3

import org.junit.jupiter.api.Test

class ConsoleTest {

    //implement pseudoPrint and pseudoReadline using Queue<String> instead than stdin and stdout

    val pseudoPrint: (String) -> Console<Unit> = TODO()
    val pseudoReadline: () -> Console<String> = TODO()

    @Test
    fun `how can we test the console?`() {

        val echo = EchoMachine(pseudoPrint, pseudoReadline)

        echo.echo()

        TODO("finish the test with assertions")

    }


    @Test
    fun `test a mini cmdline calculator`() {
        // giving + 1 1
        // should give 2 as result

    }

}