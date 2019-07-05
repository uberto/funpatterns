package day1.homeExercise

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.size
import org.junit.jupiter.api.Test

internal class BirthdayServiceKtTest {

    @Test
    fun `right line map to an employee`() {
        val service = BirthdayService
        val test: Record = listOf("Felice", "Pagano", "1985-06-07", "paganofelice@gmail.com")
        assertThat(service.lineToEmployee(test)).isNotEmpty()
    }

    @Test
    fun `malformed date will return empty optional`() {
        val service = BirthdayService
        val test: Record = listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com")
        assertThat(service.lineToEmployee(test)).isEmpty()
    }

    @Test
    fun `malformed line will return empty optional`() {
        val service = BirthdayService
        val test: Record = listOf("Felice", "1985-05-07", "paganofelice@gmail.com")
        assertThat(service.lineToEmployee(test)).isEmpty()
    }

    @Test
    fun `only well formed Record will be part of the output`() {
        val service = BirthdayService
        val test: CSV = listOf(listOf("Felice", "Pagano", "1985-05-07", "paganofelice@gmail.com"),
            listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com"),
            listOf("Felice", "1985-05-07", "paganofelice@gmail.com"))
        assertThat(service.parseCsv(test)).size().isEqualTo(1)
    }

    @Test
    fun `my program shoul works`() {
        val test: Reader = { listOf(listOf("Felice", "Pagano", "1985-05-07", "paganofelice@gmail.com"),
            listOf("Marco", "Plinio", "1985-07-05", "paganofelice@gmail.com"),
            listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com"),
            listOf("Felice", "1985-05-07", "paganofelice@gmail.com")) }

        BirthdayService.program("test", test, ::println)
    }
}