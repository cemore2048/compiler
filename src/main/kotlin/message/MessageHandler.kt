package message

class MessageHandler {

    private var message: Message? = null
    private val listeners: ArrayList<MessageListener> = arrayListOf()

    fun addListener(listener: MessageListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: MessageListener) {
        listeners.remove(listener)
    }

    fun sendMessage(message: Message) {
        this.message = message
        notifyListeners()
    }

    private fun notifyListeners() {
        listeners.forEach {
            it.messageReceived(message!!)
        }
    }
}