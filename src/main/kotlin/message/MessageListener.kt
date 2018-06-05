package message

import message.Message

interface MessageListener {

    fun messageReceived(message: Message)
}