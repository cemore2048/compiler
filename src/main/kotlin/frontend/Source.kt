package frontend

import message.*
import java.io.BufferedReader
import java.io.IOException

class Source(private val reader: BufferedReader): MessageProducer {

    companion object {
        const val EOL: Char = '\n'
        const val EOF: Char = 0.toChar()
        var messageHandler = MessageHandler()
    }

    private var line: String? = null
    var lineNum: Int = 0
    var currentPosition: Int = -2

    @Throws
    fun currentChar(): Char {
        val currentLine = line
        return if (currentPosition == -2) {
            readLine()
            nextChar()
        } else if (line == null) {
            return EOF

        } else if ((currentPosition == -1) || (currentPosition == line!!.length)) {
            return EOF
        } else if (currentPosition > line!!.length) {
            readLine()
            nextChar()
        } else {
            currentLine!![currentPosition]
        }
    }

    @Throws
    fun nextChar(): Char {
        currentPosition++
        return currentChar()
    }

    @Throws
    fun peekChar(): Char {
        if (line == null) {
           return EOF
        }

        val currentLine = line!!
        currentChar()

        val nextPos = currentPosition + 1
        return if (nextPos < line!!.length) currentLine[nextPos] else EOL
    }

    @Throws
    private fun readLine() {
        line = reader.readLine()
        currentPosition = 0

        if (line != null) {
            lineNum++
            sendMessage(Message(MessageType.SOURCE_LINE, listOf(lineNum, line!!)))
        }
    }

    @Throws
    fun close() {
        try {
            reader.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw ex
        }

    }

    override fun addMessageListener(listener: MessageListener) = messageHandler.addListener(listener)

    override fun removeMessageListener(listener: MessageListener) = messageHandler.removeListener(listener)

    override fun sendMessage(message: Message) = messageHandler.sendMessage(message)
}
