package day1.homeExercise

import day1.example3.Outcome
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ParseEmployee : (CsvLine) -> Outcome<ProgramError, Employee> {
    override fun invoke(line: CsvLine): Outcome<ProgramError, Employee> =
        line.raw.split(",")
            .map { it.trim() }
            .let { csvLineCols ->
                Outcome.tryThis {
                    Employee(
                        lastName = csvLineCols[0],
                        firstName = csvLineCols[1],
                        birthDate = LocalDate.parse(csvLineCols[2], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        emailAddress = EmailAddress(csvLineCols[3])
                    )
                }.mapFailure { ParseError(line.raw) }
            }
}

