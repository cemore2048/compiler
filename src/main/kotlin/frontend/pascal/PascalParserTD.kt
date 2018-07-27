package frontend.pascal

import frontend.*
import message.Message
import message.MessageType.PARSER_SUMMARY
import message.MessageType.TOKEN
import java.io.IOException

class PascalParserTD(scanner: Scanner) : Parser(scanner) {

    companion object {
        val errorHandler: PascalErrorHandler = PascalErrorHandler()
    }

    @Throws
    override fun parse() {
        val startTime: Long = System.currentTimeMillis()

        try {
            while (nextToken !is EofToken) {
                val token = currentToken
                val tokenType: TokenType = token.tokenType

                if (tokenType != ERROR) {
                    sendMessage(Message(TOKEN,
                            listOf(token!!.lineNum,
                                    token.position!!,
                                    tokenType,
                                    token.text!!,
                                    token.value!!)))
                } else {
                    errorHandler.flag(token!!.value as PascalErrorCode, this)
                }
            }
            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000f
            sendMessage(Message(PARSER_SUMMARY, listOf<Number>(currentToken!!.lineNum, getErrorCount(), elapsedTime)))
        } catch (ex: IOException) {
            errorHandler.abortTranslation(IO_ERROR, this)
        }

    }

    override fun getErrorCount() = errorHandler.errorCount()
}