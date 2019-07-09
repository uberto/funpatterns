package day1.homeExercise

import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.isEmpty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.subethamail.smtp.server.SMTPServer
import java.time.LocalDate

const val MAIL_SERVER_PORT = 1025

class BirthdayKataTest {

    private val sentMessageListener = TestMessageListener()
    private val today = LocalDate.of(2019, 9, 11)
    private val server = SMTPServer.port(MAIL_SERVER_PORT).messageHandler(sentMessageListener).build()

    private val sendGreetingsToAll = SendGreetingsToAll(
        ReadCsv(),
        ParseEmployee(),
        EmployeeBirthdayFilter(today),
        ComposeBirthdayEmailMessage("Happy birthday %s!"),
        SendEmail(
            MailServerConfiguration(
            host = "localhost",
            port = MAIL_SERVER_PORT
        ))
    )

    @BeforeEach
    fun setUp() {
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.stop()
    }

    @Test
    fun `happy path`(){
        sendGreetingsToAll(FileName("/fixtures/bigFile.csv"))
        assertThat(sentMessageListener.recipients).containsAll(
            "mary.ann@foobar.com",
            "caty.ann@foobar.com",
            "maggie.ann@foobar.com",
            "sue.ann@foobar.com",
            "daisy.ann@foobar.com",
            "lucy.ann@foobar.com"
        )
    }


    @Test
    fun `csv file with errors`(){
        sendGreetingsToAll(FileName("/fixtures/wrongFile.csv"))
        assertThat(sentMessageListener.recipients).isEmpty()
    }


}
