package day1.exercise3

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class ConsoleTest {

    //implement pseudoPrint and pseudoReadline using Queue<String> instead than stdin and stdout

    val prints = mutableListOf<String>()
    val reads = mutableListOf<String>()

    val pseudoPrint: (String) -> Console<Unit> = { s -> Console { prints.add(s); Unit } }
    val pseudoReadline: () -> Console<String> = { Console { reads.removeAt(0); } }

    @Test
    fun `how can we test the console?`() {
        val echo = EchoMachine(pseudoPrint, pseudoReadline)

        reads.add("foo")
        echo.echo()

        assertThat(prints).isEqualTo(listOf("foo"))
    }


    @Test
    fun `test a mini cmdline calculator`() {
        // giving + 1 1
        // should give 2 as result
        val calc = Calculator(pseudoPrint, pseudoReadline)

        reads.add("+ 1 1")
        calc.run()

        assertThat(prints).isEqualTo(listOf("2"))
    }

}