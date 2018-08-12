package frontend.pascal

import frontend.Parser
import frontend.Token
import message.Message
import message.MessageType

open class PascalErrorHandler {
    companion object {
        var MAX_ERRORS = 25
        var errorCount = 0
    }

    fun flag(token: Token, errorCode: PascalErrorCode, parser: Parser) {
        parser.sendMessage(Message(MessageType.SYNTAX_ERROR, listOf(token.lineNum, token.position!!, token.text!!, errorCode.toString())))

        if (++errorCount > MAX_ERRORS) {
            abortTranslation(PascalErrorCode.TOO_MANY_ERRORS, parser)
        }
    }

    fun abortTranslation(errorCode: PascalErrorCode, parser: Parser) {
        val fatalText = "FATAL ERROR $errorCode"
        parser.sendMessage(Message(MessageType.SYNTAX_ERROR, listOf(0, 0, "", fatalText)))

        //TODO pull status code out of error code
        System.exit(0)
    }


}