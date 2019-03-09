package frontend.pascal.parsers

import frontend.Token
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeNode

class StatementParser(pascalParserTD: PascalParserTD) : PascalParserTD(pascalParserTD) {

    fun parse(token: Token): IntermediateCodeNode {
        val node: IntermediateCodeNode? = null

        when (token.type as PascalTokenType) {
            PascalTokenType.BEGIN -> {
            }
        }


        return node!!
    }
}