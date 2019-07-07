package day1.exercise3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.util.*

class ConsoleTest {

    //implement pseudoPrint and pseudoReadline using Queue<String> instead than stdin and stdout
    private val inputQueue: Queue<String> = LinkedList()
    private val outputQueue: Queue<String> = LinkedList()

    //implement pseudoPrint and pseudoReadline using Queue<String> instead than stdin and stdout

    private val pseudoPrint: (String) -> Console<Unit> = { msg ->
        Console {
            outputQueue.offer(msg)
            Unit
        }
    }

    private val pseudoReadline: () -> Console<String> = {
        Console {
            inputQueue.poll()
        }
    }

    @Test
    fun `how can we test the console?`() {

        val echo = EchoMachine(pseudoPrint, pseudoReadline)

        inputQueue.offer("A message")
        inputQueue.offer("A second message")

        echo.echo()
        echo.echo()

        assertThat(outputQueue.poll()).isEqualTo("A message")
        assertThat(outputQueue.poll()).isEqualTo("A second message")

    }

    @Test
    fun `test a mini cmdline calculator`() {
        val calculator = CmdLineCalculator(pseudoPrint, pseudoReadline)

        inputQueue.offer("+ 1 1")
        inputQueue.offer("+ A 1")
        inputQueue.offer("/ 1 1")

        calculator.enter()
        calculator.enter()
        calculator.enter()

        assertThat(outputQueue.poll()).isEqualTo("2")
        assertThat(outputQueue.poll()).isEqualTo("Error: cannot process + A 1")
        assertThat(outputQueue.poll()).isEqualTo("Error: cannot process / 1 1")

    }
}