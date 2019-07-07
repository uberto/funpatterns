package day1.homeExercise

import day1.example3.Outcome
import java.util.*
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendEmail(private val mailServerConfiguration: MailServerConfiguration) :
        (EmailMessage) -> Outcome<MailSendingFailure, Unit> {
    override fun invoke(msg: EmailMessage): Outcome<MailSendingFailure, Unit> =
        Outcome.tryThis {
            val properties = Properties()
            properties.setProperty("mail.smtp.host", mailServerConfiguration.host)
            properties.setProperty("mail.smtp.port", mailServerConfiguration.port.toString())

            val session = Session.getDefaultInstance(properties)

            val message = MimeMessage(session)

            message.setFrom(InternetAddress("no-replay@myservice.com"))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(msg.to.value))
            message.subject = msg.subject
            message.setText(msg.body)

            Transport.send(message)
        }.mapFailure { MailSendingFailure(msg) }

}

data class MailServerConfiguration(val host: String, val port: Int)
