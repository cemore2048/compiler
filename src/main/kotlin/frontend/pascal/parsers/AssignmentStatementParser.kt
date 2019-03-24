package frontend.pascal.parsers

import frontend.Token
import frontend.pascal.PascalErrorCode
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeKey
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeType

class AssignmentStatementParser(pascalParserTD: PascalParserTD) : StatementParser(pascalParserTD) {

    override fun parse(token: Token): IntermediateCodeNode {
        val assignmentNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.ASSIGNMENT)
        var localToken: Token? = token
        val targetName = token.text?.toLowerCase()

        var targetID = symbolTableStack.lookup(targetName!!)

        targetID?.let {
            targetID = symbolTableStack.enterLocal(targetName)
        }

        targetID?.appendLineNumber(token.lineNum)

        localToken = nextToken

        val variableNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.VARIABLE)
        variableNode.setAttribute(IntermediateCodeKey.ID, targetID!!)

        assignmentNode.addChild(variableNode)

        if (token.type == PascalTokenType.COLON_EQUALS) {
            localToken = nextToken
        } else {
            errorHandler.flag(localToken!!, PascalErrorCode.MISSING_COLON_EQUALS, this)
        }

        //uncomment when ExpressionParse is implemented
//        val expressionParser = ExpressionParser(this)
//        assignmentNode.addChild(expressionParser.parse(token))

        return assignmentNode
    }
}