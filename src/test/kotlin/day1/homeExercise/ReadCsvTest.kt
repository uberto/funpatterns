package day1.homeExercise

import assertk.assertThat
import assertk.assertions.isEqualTo
import day1.example3.Failure
import day1.example3.Success
import org.junit.jupiter.api.Test

class ReadCsvTest {

    private val readCsv: ReadCsv  = ReadCsv()

    @Test
    fun `non existing file`() {
        assertThat(readCsv(FileName("/fixtures/a_non_existing_file")))
            .isEqualTo(Failure(FileNotFound("/fixtures/a_non_existing_file")))
    }

    @Test
    fun `empty file so empty csv`() {
        assertThat(readCsv(FileName("/fixtures/emptyFile.csv")))
            .isEqualTo(Success(CsvFile(listOf())))
    }

    @Test
    internal fun `filled csv`() {
        assertThat(readCsv(FileName("/fixtures/goodFile.csv")))
            .isEqualTo(Success(CsvFile(listOf(
                CsvLine("Doe, John, 1982/10/08, john.doe@foobar.com"),
                CsvLine("Ann, Mary, 1975/09/11, mary.ann@foobar.com")
            ))))
    }
}


