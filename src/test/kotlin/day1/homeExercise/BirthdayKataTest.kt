package day1.homeExercise

import assertk.assertThat
import assertk.assertions.isEqualTo
import day1.example3.Failure
import day1.example3.Outcome
import day1.example3.Success
import org.junit.jupiter.api.Test
import java.time.LocalDate


class BirthdayKataTest {


    @Test
    fun `happy path`() {
        val app = sendGreetings(RealReadCsv(), SendEmailSpy())
        val result = app.invoke(
            FileName("fixtures/goodFile.csv"),
            SmtpEndpoint("localhost", 25),
            Sender("test@from.com"),
            LocalDate.parse("2019-10-08")
        )
        assertThat(result).isEqualTo(Success(1))
    }


    @Test
    fun `csv file with errors`() {
        val path = "fixtures/wrongFile.csv"

        val app = sendGreetings(RealReadCsv(), SendEmailSpy())
        val result = app.invoke(
            FileName(path),
            SmtpEndpoint("localhost", 25),
            Sender("test@from.com"),
            LocalDate.parse("2019-10-08")
        )

        assertThat(result).isEqualTo(Failure(AppError("Sorry mate", LoadError("bad file content"))))
    }

}

class SendEmailSpy : SendEmails {

    override fun invoke(endpoint: SmtpEndpoint, es: Emails): Outcome<SendError, MailSent> {
        return Success(es.size)
    }
}
