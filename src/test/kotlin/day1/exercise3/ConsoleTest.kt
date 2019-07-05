package day1.exercise3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.util.*

class ConsoleTest {

    val output = ArrayDeque<String>()
    val input = ArrayDeque<String>()

    val pseudoPrint: (String) -> Console<Unit> = { msg -> Console { output.add(msg); Unit } }
    val pseudoReadline: () -> Console<String> = { Console { input.remove() } }

    @Test
    fun `how can we test the console?`() {

        val echo = EchoMachine(pseudoPrint, pseudoReadline)

        input.add("Hello")
        input.add("Functional")
        input.add("World")

        echo()
        echo()
        echo()

        assertThat(output.remove()).isEqualTo("Hello")
        assertThat(output.remove()).isEqualTo("Functional")
        assertThat(output.remove()).isEqualTo("World")
    }


    @Test
    fun `test a mini cmdline calculator`() {
        // giving + 1 1
        // should give 2 as result
        val calc = Calculator(pseudoPrint, pseudoReadline)

        input.add("+ 3 4")
        input.add("* 5 6")
        input.add("@ 7 8")

        calc()
        calc()
        calc()

        assertThat(output.remove()).isEqualTo("7.0")
        assertThat(output.remove()).isEqualTo("30.0")
        assertThat(output.remove()).isEqualTo("Unknown operation @")
    }

}