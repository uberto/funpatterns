package day1.homeExercise

import day1.example3.Outcome
import day1.example3.toOptional
import java.time.LocalDate

data class Employee(val lastName: String, val firstName: String, val dateOfBirth: LocalDate, val email: String) {
    fun isBirthday(xDate: LocalDate): Boolean = this.dateOfBirth.withYear(xDate.year).equals(xDate)
}

typealias Record = List<String>
typealias CSV = List<List<String>>
typealias Notificator = (Employee) -> Unit
typealias Reader = (String) -> CSV

fun sendEmail(employee: Employee) {
    println(employee)
}

fun readFile(fileName: String): CSV = TODO()

object BirthdayService {

    fun lineToEmployee(line: Record): List<Employee> {
        if(line.size != 4) {
            return emptyList()
        }

        return Outcome.tryThis { LocalDate.parse(line[2]) }
            .toOptional()
            .map { listOf(Employee(line[0], line[1], it, line[3])) }
            .orElse(emptyList())
    }

    fun parseCsv(csv: CSV) : List<Employee> =
        csv.flatMap(::lineToEmployee)

    fun program(fileName: String, reader: Reader, action: Notificator) {
        parseCsv(reader(fileName))
            .filter { it.isBirthday(LocalDate.now()) }
            .forEach { action(it) }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val test: Reader = { listOf(listOf("Felice", "Pagano", "1985-05-07", "paganofelice@gmail.com"),
            listOf("Marco", "Plinio", "1985-07-05", "paganofelice@gmail.com"),
            listOf("Felice", "Pagano", "1985-00-07", "paganofelice@gmail.com"),
            listOf("Felice", "1985-05-07", "paganofelice@gmail.com")) }


        program("test", test, ::println)
    }

}