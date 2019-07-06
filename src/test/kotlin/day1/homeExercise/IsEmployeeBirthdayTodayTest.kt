package day1.homeExercise

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class IsEmployeeBirthdayTodayTest {

    @Test
    fun `simple positive case`() {
        val today = LocalDate.of(2019, 7, 6)
        val employee = Employee(
            "Doe",
            "John",
            LocalDate.of(1970, 7, 6),
            Email("john@email.com")
        )

        assertTrue(IsEmployeeBirthdayToday(today)(employee))
    }

    @Test
    fun `simple negative case`() {
        val today = LocalDate.of(2019, 7, 6)
        val employee = Employee(
            "Doe",
            "John",
            LocalDate.of(1970, 7, 7),
            Email("john@email.com")
        )

        assertFalse(IsEmployeeBirthdayToday(today)(employee))
    }


}