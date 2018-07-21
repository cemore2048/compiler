package message

interface MessageProducer {

    fun addMessageListener(listener: MessageListener)

    fun removeMessageListener(listener: MessageListener)

    fun sendMessage(message: Message)
}