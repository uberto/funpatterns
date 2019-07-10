package day1.example2

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class FizzBuzzTest {

    @Test
    fun `fizzBuzz returns list of string according to the rules`() {

        val res = (1..15)
            .map { fizzBuzz(it) }
            .joinToString()

        val expected = "1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz"

        assertThat(res).isEqualTo(expected)

    }
}