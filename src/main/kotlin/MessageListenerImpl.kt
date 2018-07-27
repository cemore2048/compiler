import frontend.TokenType
import frontend.pascal.tokens.PascalTokenType
import message.Message
import message.MessageListener
import message.MessageType


class MessageListenerImpl : MessageListener {

    companion object {
        const val SOURCE_LINE_FORMAT = "%03d %s"
        const val PARSER_SUMMARY_FORMAT = "\n%,20d source lines, \n%,20d syntax, \n%,20.2f seconds total parsing time\n"
        const val INTERPRETER_SUMMARY_FORMAT = "\n%,20d statements executed. \n%,20d runtime errors, \n%,20.2f seconds total execution time.\n"
        const val COMPILER_SUMMARY_FORMAT = "\n%,20d instructions generated, \n%,20f seconds total code generation time.\n"
        const val TOKEN_FORMAT = ">>> %-15s line=%03d, pos=%2d, text=\"%s\""
        const val VALUE_FORMAT = ">>>  value =%s"
        const val PREFIX_WIDTH = 5
    }

    override fun messageReceived(inputMessage: Message) {
        val type = inputMessage.type

        //each one of these types used to have it's own listener. Aggregated all of them in one message listener impl
        when (type) {
            MessageType.SOURCE_LINE -> {

                val body: List<*> = inputMessage.body
                val linenumber = body[0] as Int
                val lineText = body[1] as String

                println(String.format(SOURCE_LINE_FORMAT, linenumber, lineText))
            }
            MessageType.SYNTAX_ERROR -> {
                val body = inputMessage.body
                val lineNumber = body[0] as Int
                val position = body[1] as Int
                val tokenText = body[2] as String
                val errorMessage = body[3] as String

                val spaceCount = PREFIX_WIDTH + position
                val flagBuffer = StringBuffer()

                for (i in 0..spaceCount) {
                    flagBuffer.append(' ')
                }

                flagBuffer.append("^\n *** ")

                tokenText.let {
                    flagBuffer.append(" [at \"").append(it).append("\"]")
                }

                println(flagBuffer.toString())
            }
            MessageType.PARSER_SUMMARY -> {
                val body: List<*> = inputMessage.body
                val statementCount = body[0] as Int
                val syntaxErrors = body[1] as Int
                val elapsedTime = body[2] as Float

                println(String.format(PARSER_SUMMARY_FORMAT, statementCount, syntaxErrors, elapsedTime))
            }
            MessageType.INTERPRETER_SUMMARY -> {
                val body: List<*> = inputMessage.body
                val statementCount = body[0] as Int
                val syntaxErrors = body[1] as Int
                val elapsedTime = body[2] as Long

                println(String.format(INTERPRETER_SUMMARY_FORMAT, statementCount, syntaxErrors, elapsedTime))
            }
            MessageType.COMPILER_SUMMARY -> {
                val body: List<*> = inputMessage.body
                val instructionCount: Int = body[0] as Int
                val elapsedTime = body[1] as Float

                println(String.format(COMPILER_SUMMARY_FORMAT, instructionCount, elapsedTime))
            }
            MessageType.MISCELLANEOUS -> TODO()
            MessageType.TOKEN -> {
                val body: List<*> = inputMessage.body
                val line = body[0] as Int
                val position = body[1] as Int
                val tokenType = body[2] as TokenType
                val tokenText = body[3] as String
                var tokenValue = body[4]

                print(String.format(TOKEN_FORMAT, tokenType, line, position, tokenText))

                tokenValue.let {
                    if (tokenType == PascalTokenType.STRING) {
                        tokenValue = "\" $tokenValue \""
                    }

                    println(String.format(VALUE_FORMAT, tokenValue))
                }
            }
            MessageType.ASSIGN -> TODO()
            MessageType.FETCH -> TODO()
            MessageType.BREAKPOINT -> TODO()
            MessageType.RUNTIME_ERROR -> TODO()
            MessageType.CALL -> TODO()
            MessageType.RETURN -> TODO()
        }
    }
}