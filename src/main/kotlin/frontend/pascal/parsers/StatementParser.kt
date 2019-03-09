package frontend.pascal.parsers

import frontend.EofToken
import frontend.Token
import frontend.pascal.PascalErrorCode
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeKey
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeType

open class StatementParser(pascalParserTD: PascalParserTD) : PascalParserTD(pascalParserTD) {

    open fun parse(token: Token): IntermediateCodeNode {
        var statementNode: IntermediateCodeNode? = null

        when (token.type as PascalTokenType) {
            PascalTokenType.BEGIN -> {
                val compounStatementParser = CompountStatementParser(this)

                statementNode = compounStatementParser.parse(token)
            }

            PascalTokenType.IDENTIFIER -> {
                val assignmentStatementParser = AssignmentStatementParser(this)
                statementNode = assignmentStatementParser.parse(token)
            }

            else -> statementNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.NO_OP)
        }

        setLineNumber(statementNode, token)
        return statementNode
    }

    protected fun setLineNumber(node: IntermediateCodeNode, token: Token) {
        node.setAttribute(IntermediateCodeKey.LINE, token.lineNum)
    }

    // referred to as parseList() in book
    fun parseStatementList(token: Token, parentNode: IntermediateCodeNode, terminator: PascalTokenType, errorCode: PascalErrorCode) {
        var localToken: Token? = token
        while ((token.type !is EofToken) && (token.type != terminator)) {

            // Parse statement. The parent node adopts the statement node
            val statementNode: IntermediateCodeNode = parse(token)
            parentNode.addChild(statementNode)

            localToken = currentToken
            val tokenType = token.type

            when {
                tokenType == PascalTokenType.SEMICOLON -> localToken = nextToken
                tokenType == PascalTokenType.IDENTIFIER -> errorHandler.flag(localToken!!, PascalErrorCode.MISSING_SEMICOLON, this)
                tokenType != terminator -> {
                    errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this)
                    localToken = nextToken
                }
            }

            if (token.type == terminator) {
                localToken = nextToken
            } else {
                errorHandler.flag(localToken!!, errorCode, this)
            }
        }
    }
}