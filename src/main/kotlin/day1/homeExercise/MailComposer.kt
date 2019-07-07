package day1.homeExercise

data class EmailMessage(val to: EmailAddress, val subject: String, val body: String)

class ComposeBirthdayEmailMessage(private val template: String) : (Employee) -> EmailMessage {

    override fun invoke(e: Employee): EmailMessage =
        EmailMessage(
            to = e.emailAddress,
            subject = "Birthday greetings",
            body = template.format(e.firstName)
        )
}
