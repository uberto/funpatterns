package day1.homeExercise

import day1.example3.Outcome
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Employee(
    val lastName: String,
    val firstName: String,
    val birthDate: LocalDate,
    val email: Email
)

data class Email(val value: String)

class ParseEmployee : (CsvLine) -> Outcome<ProgramError, Employee> {
    override fun invoke(line: CsvLine): Outcome<ProgramError, Employee> =
        line.raw.split(",")
            .map { it.trim() }
            .let { parts ->
                Outcome.tryThis {
                    Employee(
                        parts[0],
                        parts[1],
                        LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        Email(parts[3])
                    )
                }.mapFailure { ParseError(line.raw) }
            }
}

