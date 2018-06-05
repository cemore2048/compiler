package message

import message.Message
import message.MessageListener

interface MessageProducer {

    fun addMessageListener(listener: MessageListener)

    fun removeMessageListener(listener: MessageListener)

    fun sendMessage(message: Message)
}