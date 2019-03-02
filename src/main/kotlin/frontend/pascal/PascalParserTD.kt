package frontend.pascal

import frontend.EofToken
import frontend.Parser
import frontend.Scanner
import frontend.TokenType
import frontend.pascal.tokens.PascalTokenType
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
                val tokenType: TokenType = token?.type!!

                if (tokenType == PascalTokenType.IDENTIFIER) {
                    val name = token.text?.toLowerCase()

                    // If it's not already in the symbol table,
                    // create and enter a new entry for the identifier

                    val entry = symbolTableStack.lookup(name!!) ?: symbolTableStack.enterLocal(name)
                    entry.appendLineNumber(token.lineNum)

                } else if (tokenType == PascalTokenType.ERROR) {
                    errorHandler.flag(token, token.value as PascalErrorCode, this)
                }
            }

            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000f
            sendMessage(Message(PARSER_SUMMARY, listOf<Number>(currentToken!!.lineNum, getErrorCount(), elapsedTime)))
        } catch (ex: IOException) {
            errorHandler.abortTranslation(PascalErrorCode.IO_ERROR, this)
        }

    }

    override fun getErrorCount() = PascalErrorHandler.errorCount
}