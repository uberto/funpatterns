package day1.homeExercise

import day1.example3.sequence
import day1.exercise1.andThen

//see http://matteo.vaccari.name/blog/archives/154 for the Kata rules

sealed class ProgramError
data class FileNotFound(val path: String): ProgramError()
data class ParseError(val source: String): ProgramError()
data class MailSendingFailure(val emailMessage: EmailMessage): ProgramError()

class SendGreetingsToAll(
    private val readCsv: ReadCsv,
    private val parseEmployee: ParseEmployee,
    private val employeeBirthdayFilter: EmployeeBirthdayFilter,
    private val mailCompose: ComposeBirthdayEmailMessage,
    private val sendEmail: SendEmail
): (FileName) -> Unit{
    override fun invoke(sourceFile: FileName) {
        readCsv(sourceFile)
            .map { file: CsvFile ->
                file.rows
                    .map { row -> parseEmployee(row) }
                    .sequence()
                    .map { employees ->
                        employees
                            .filter(employeeBirthdayFilter)
                            .map(mailCompose.andThen(sendEmail))
                    }
            }
    }

}