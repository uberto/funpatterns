package day1.homeExercise

import assertk.assertThat
import assertk.assertions.isEqualTo
import day1.example3.Failure
import day1.example3.Success
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ParseEmployeeTest {

    private val parseEmployee = ParseEmployee()

    @Test
    fun `read correct employee from CsvLine`() {
        assertThat(parseEmployee(CsvLine("Doe, John, 1982/10/08, john.doe@foobar.com")))
            .isEqualTo(
                Success(
                    Employee(
                        "Doe",
                        "John",
                        LocalDate.of(1982, 10, 8),
                        Email("john.doe@foobar.com")
                    )
                )
            )
    }

    @Test
    internal fun `csv missing a field`() {
        assertThat(parseEmployee(CsvLine("#comment")))
            .isEqualTo(Failure(ParseError("#comment")))
    }
}