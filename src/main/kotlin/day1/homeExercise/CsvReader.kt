package day1.homeExercise

import day1.example3.Outcome

class CsvReader: (FileName) -> Outcome<ProgramError, CsvFile> {
    override fun invoke(file: FileName): Outcome<ProgramError, CsvFile> {
        return Outcome.tryThis {
            this.javaClass.getResource(file.path)
                .readText()
                .split("\n")
                .drop(1)
                .map { line -> CsvLine(line) }
                .let { lines -> CsvFile(lines) }
        }.mapFailure {
            FileNotFound
        }
    }
}

data class FileName(val path: String)
data class CsvFile(val rows: List<CsvLine>)
data class CsvLine(val raw: String)