package day1.homeExercise

import org.subethamail.smtp.MessageContext
import org.subethamail.smtp.helper.BasicMessageListener

class TestMessageListener : BasicMessageListener {
    
    val recipients = mutableListOf<String>()
    
    override fun messageArrived(context: MessageContext?, from: String?, to: String?, data: ByteArray?) {
        to?.let { recipient -> recipients.add(recipient) }
    }
}
