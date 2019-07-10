package day1.homeExercise

import day1.example1.`@`
import day1.example1.curry
import day1.example3.Error
import day1.example3.Outcome
import day1.example3.flatMap
import day1.exercise1.andThen
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors


//see http://matteo.vaccari.name/blog/archives/154 for the Kata rules

fun sendGreetingsToAll(filename: String): Outcome<AppError, MailSent> {
    val app = sendGreetings(RealReadCsv(), RealSendEmail())
    return app.invoke(FileName(filename), SmtpEndpoint("localhost", 25), Sender("a@b.com"), LocalDate.now())
}


fun sendGreetings(load: LoadEmployee, send: SendEmails): App =
    { fileName: FileName, smtpEndpoint: SmtpEndpoint, from: Sender, today: LocalDate ->
        load(fileName)
            .map(IsBirthday(today).andThen(ToEmails(from)))
            .flatMap(send.curry().`@`(smtpEndpoint))
            .mapFailure { e -> AppError("Sorry mate", e) }
    }

data class Employee(
    val lastName: String,
    val firstName: String,
    val birthDate: LocalDate,
    val email: String
) {

    fun isBirthday(today: LocalDate): Boolean =
        sameMonth(today) && sameDay(today)

    private fun sameMonth(today: LocalDate): Boolean =
        today.month == birthDate.month

    private fun sameDay(today: LocalDate): Boolean =
        today.dayOfMonth == birthDate.dayOfMonth

    fun toEmail(from: Sender): Email =
        Email(from.value, email, "Happy birthday!", "Happy birthday, dear $firstName!")
}

data class Email(
    val from: String,
    val to: String,
    val subject: String,
    val body: String
)

data class SmtpEndpoint(
    val host: String,
    val port: Int
)

data class Sender(val value: String)

data class FileName(val path: String)
data class CsvFile(val rows: List<CsvLine>)
data class CsvLine(val raw: String)

data class AppError(override val msg: String, val inner: Error) : Error
data class LoadError(override val msg: String) : Error
data class ParseEmployeeError(override val msg: String) : Error
data class SendError(override val msg: String) : Error

typealias LoadEmployee = (FileName) -> Outcome<LoadError, Employees>
typealias MailSent = Int
typealias SendEmails = (SmtpEndpoint, Emails) -> Outcome<SendError, MailSent>
typealias App = (FileName, SmtpEndpoint, Sender, LocalDate) -> Outcome<AppError, MailSent>
typealias Employees = List<Employee>
typealias Emails = List<Email>

class RealReadCsv : LoadEmployee {
    override fun invoke(fn: FileName): Outcome<LoadError, Employees> {
        return Outcome.tryThis {
            CsvFile(Files.readAllLines(Paths.get(fn.path)).stream()
                .skip(1)
                .map { line -> CsvLine(line) }
                .collect(Collectors.toList()))
        }
            .flatMap(ToEmployees())
            .mapFailure { LoadError("bad file content") }
    }
}

class ToEmployees : (CsvFile) -> Outcome<ParseEmployeeError, Employees> {
    override fun invoke(csv: CsvFile): Outcome<ParseEmployeeError, Employees> {
        return Outcome.tryThis {
            csv.rows.map { line ->
                val parts: List<String> = line.raw.split(",").map { it.trim() }
                Employee(
                    parts[0],
                    parts[1],
                    parseDate(parts[2]),
                    parts[3]
                )
            }
        }
            .mapFailure { ParseEmployeeError("not an employee") }
    }

    private fun parseDate(raw: String): LocalDate =
        LocalDate.parse(raw, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}

class IsBirthday(val today: LocalDate) : (Employees) -> Employees {
    override fun invoke(es: Employees): Employees {
        return es.filter { it.isBirthday(today) }
    }
}

class ToEmails(val from: Sender) : (Employees) -> Emails {
    override fun invoke(es: Employees): Emails {
        return es.map { it.toEmail(from) }
    }
}

class RealSendEmail : SendEmails {
    override fun invoke(endpoint: SmtpEndpoint, es: Emails): Outcome<SendError, MailSent> {
        TODO("not implemented")
    }
}
