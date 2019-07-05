package day1.homeExercise

import day1.example3.Outcome
import day1.example3.ThrowableError
import day1.example3.toOptional
import day1.exercise1.FUN
import day1.exercise1.andThen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun <T>Optional<T>.toList(): List<T> = if(this.isEmpty) emptyList() else listOf(this.get())

private val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

data class Employee(val lastName: String, val firstName: String, val dateOfBirth: LocalDate, val email: String) {
    fun isBirthday(xDate: LocalDate): Boolean = this.dateOfBirth.withYear(xDate.year) == xDate
}

typealias Record = List<String>
typealias CSV = List<Record>
typealias Sender = (Employee) -> Outcome<ThrowableError, Unit>
typealias Reader = (String) -> Outcome<ThrowableError, CSV>

object BirthdayService {

    fun recordToEmployee(line: Record): Optional<Employee> {
        if(line.size != 4) {
            return Optional.empty()
        }

        return Outcome.tryThis { LocalDate.parse(line[2], formatter) }
            .toOptional()
            .map { Employee(line[0], line[1], it, line[3]) }
    }

    fun parseCsv(csv: CSV) = csv.flatMap { recordToEmployee(it).toList() }

    private fun filterCelebrated(employees: List<Employee>, xDate: LocalDate): List<Employee> =
        employees.filter { it.isBirthday(xDate) }

    private fun sendEmail(xs: List<Employee>, sender: Sender): List<Outcome<ThrowableError, Unit>> = xs.map { sender(it) }

    fun program(read: Reader, action: Sender, xDate: LocalDate): FUN<String, Outcome<ThrowableError, List<Outcome<ThrowableError, Unit>>>> =
        read.andThen { outcome ->  outcome.map { parseCsv(it) }}
            .andThen { outcome -> outcome.map { filterCelebrated(it, xDate) } }
            .andThen { outcome -> outcome.map { sendEmail(it, action) } }

}