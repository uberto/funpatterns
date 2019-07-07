package day1.exercise3

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ConsoleTest {

    private val stdOut = ArrayDeque<String>()
    private val stdIn = ArrayDeque<String>()

    val pseudoPrint: (String) -> Console<Unit> = { msg -> Console{ stdOut.addLast(msg) } }
    val pseudoReadline: () -> Console<String> = { Console { stdIn.poll() } }

    @Test
    fun `how can we test the console?`() {

        stdIn.add("Hello")
        stdIn.add("World")

        val echo = EchoMachine(pseudoPrint, pseudoReadline)
        echo.echo()
        echo.echo()

        assertThat(stdIn).isEmpty()
        assertThat(stdOut.first).isEqualTo("Hello")
        assertThat(stdOut.last).isEqualTo("World")
    }


    @Test
    fun `test a mini cmdline calculator`() {
        // giving + 1 1
        // should give 2 as result

        stdIn.add("+ 1 3")
        stdIn.add("* 2 4")

        val echo = Calculator(pseudoPrint, pseudoReadline)

        echo.operation()
        assertThat(stdOut.poll()).equals("4")
        echo.operation()
        assertThat(stdOut.poll()).equals("8")
    }

}
