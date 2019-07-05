package day1.homeExercise

import assertk.assertThat
import assertk.assertions.*
import day1.example3.Success
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class BirthdayServiceKtTest {

    @Test
    fun `right line map to an employee`() {
        val service = BirthdayService
        val test: Record = listOf("Felice", "Pagano", "1985-06-07", "paganofelice@gmail.com")
        assertThat(service.recordToEmployee(test).isPresent).isTrue()
    }

    @Test
    fun `malformed date will return empty optional`() {
        val service = BirthdayService
        val test: Record = listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com")
        assertThat(service.recordToEmployee(test).isEmpty).isTrue()
    }

    @Test
    fun `malformed line will return empty optional`() {
        val service = BirthdayService
        val test: Record = listOf("Felice", "1985-05-07", "paganofelice@gmail.com")
        assertThat(service.recordToEmployee(test).isEmpty).isTrue()
    }

    @Test
    fun `only well formed Record will be part of the output`() {
        val test: CSV = listOf(
            listOf("Felice", "Pagano", "1985-05-07", "paganofelice@gmail.com"),
            listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com"),
            listOf("Felice", "1985-05-07", "paganofelice@gmail.com")
        )
        assertThat(BirthdayService.parseCsv(test)).size().isEqualTo(1)
    }

    @Test
    fun `my program should works`() {

        val birthday = LocalDate.now().withYear(1980).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val reader: Reader = {
            Success(
                listOf(
                    listOf("Felice", "Pagano", "1985-05-07", "paganofelice@gmail.com"),
                    listOf("Marco", "Plinio", birthday, "paganofelice@gmail.com"),
                    listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com"),
                    listOf("Felice", "1985-05-07", "paganofelice@gmail.com")
                )
            )
        }

        val sender: Sender = { employee -> Success(println(employee)) }

        BirthdayService.program(reader,  sender, LocalDate.now())("FileName")

    }
}