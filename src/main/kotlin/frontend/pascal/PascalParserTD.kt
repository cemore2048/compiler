package frontend.pascal

import frontend.EofToken
import frontend.Parser
import frontend.Scanner
import message.Message
import message.MessageType.PARSER_SUMMARY

class PascalParserTD(scanner: Scanner): Parser(scanner) {

    @Throws
    override fun parse() {
        val startTime: Long = System.currentTimeMillis()

        currentToken = nextToken
        while (currentToken !is EofToken) {
            currentToken = nextToken
        }

        val elapsedTime = (System.currentTimeMillis() - startTime) / 1000f
        sendMessage(Message(PARSER_SUMMARY, arrayListOf<Number>(currentToken!!.lineNum!!, getErrorCount(), elapsedTime)))
    }

    override fun getErrorCount() = 0
}