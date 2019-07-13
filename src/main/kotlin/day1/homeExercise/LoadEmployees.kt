package day1.homeExercise

import day1.example3.Outcome
import day1.example3.flatMap
import day1.example3.sequence
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LoadEmployees(
    private val readCsv: (FileName) -> Outcome<ProgramError, CsvFile>,
    private val parseEmployee: (CsvLine) -> Outcome<ProgramError, Employee>
) : (FileName) -> Outcome<ProgramError, List<Employee>> {
    override fun invoke(sourceFile: FileName): Outcome<ProgramError, List<Employee>> =
        readCsv(sourceFile)
            .flatMap { file ->
                file.rows
                    .map(parseEmployee)
                    .sequence()
            }
}

class ReadCsv : (FileName) -> Outcome<ProgramError, CsvFile> {
    override fun invoke(file: FileName): Outcome<ProgramError, CsvFile> =
        Outcome.tryThis {
            this.javaClass.getResource(file.path)
                .readText()
                .split("\n")
                .drop(1)
                .map { line -> CsvLine(line) }
                .let { lines -> CsvFile(lines) }
        }.mapFailure {
            FileNotFound(file.path)
        }
}

data class FileName(val path: String)
data class CsvFile(val rows: List<CsvLine>)
data class CsvLine(val raw: String)

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