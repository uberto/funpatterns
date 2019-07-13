package day1.homeExercise

import day1.example3.Outcome
import java.time.LocalDate

//see http://matteo.vaccari.name/blog/archives/154 for the Kata rules

class SendGreetingsToAll(
    private val loadEmployees: (FileName) -> Outcome<ProgramError, List<Employee>>,
    private val employeeBorneToday: (Employee) -> Boolean,
    private val sendBirthDayGreetingMail: (Employee) -> Outcome<MailSendingFailure, Unit>
) : (FileName) -> Unit {

    override fun invoke(sourceFile: FileName) {
        loadEmployees(sourceFile)
            .map {employees ->
                employees
                    .filter(employeeBorneToday)
                    .map(sendBirthDayGreetingMail)
            }
    }

}

data class Employee(
    val lastName: String,
    val firstName: String,
    val birthDate: LocalDate,
    val emailAddress: EmailAddress
)

data class EmailAddress(val value: String)

sealed class ProgramError
data class FileNotFound(val path: String) : ProgramError()
data class ParseError(val source: String) : ProgramError()
data class MailSendingFailure(val emailMessage: EmailMessage) : ProgramError()