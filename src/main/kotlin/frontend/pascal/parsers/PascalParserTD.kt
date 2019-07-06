package frontend.pascal.parsers

import frontend.Parser
import frontend.Scanner
import frontend.pascal.PascalErrorCode
import frontend.pascal.PascalErrorHandler
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeNode
import message.Message
import message.MessageType
import java.io.IOException

open class PascalParserTD(parser: PascalParserTD) : Parser(parser.scanner) {

    companion object {
        val errorHandler: PascalErrorHandler = PascalErrorHandler()
    }

    @Throws
    override fun parse() {
        val startTime: Long = System.currentTimeMillis()
        val intermediateCode = IntermediateCodeFactory.createIntermediateCode()

        try {
            var token = nextToken
            var rootNode: IntermediateCodeNode? = null

            if (token?.type == PascalTokenType.BEGIN) {
                val statementParser = StatementParser(this)
                rootNode = statementParser.parse(token)
                token = currentToken
            } else {
                errorHandler.flag(token!!, PascalErrorCode.UNEXPECTED_TOKEN, this)
            }

            if (token?.type != PascalTokenType.DOT) {
                errorHandler.flag(token!!, PascalErrorCode.MISSING_PERIOD, this)
            }
            token = currentToken

            rootNode?.let {
                intermediateCode.setRoot(it)
            }

            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000f
            sendMessage(Message(MessageType.PARSER_SUMMARY, listOf<Number>(token?.lineNum!!, getErrorCount(), elapsedTime)))
        } catch (ex: IOException) {
            errorHandler.abortTranslation(PascalErrorCode.IO_ERROR, this)
        }

    }

    override fun getErrorCount() = PascalErrorHandler.errorCount
}