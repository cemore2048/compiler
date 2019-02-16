package message

interface MessageListener {
    fun messageReceived(inputMessage: Message)
}