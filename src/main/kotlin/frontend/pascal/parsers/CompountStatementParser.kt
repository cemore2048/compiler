package frontend.pascal.parsers

import frontend.Token
import frontend.pascal.PascalErrorCode
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeType

class CompountStatementParser(pascalParserTD: PascalParserTD): StatementParser(pascalParserTD) {

    override fun parse(token: Token): IntermediateCodeNode {
        var localToken: Token? = token
        localToken = nextToken

        val compoundNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.COMPOUND)

        val statementParser = StatementParser(this)
        statementParser.parseStatementList(localToken!!, compoundNode, PascalTokenType.END, PascalErrorCode.MISSING_END)

        return compoundNode
    }
}