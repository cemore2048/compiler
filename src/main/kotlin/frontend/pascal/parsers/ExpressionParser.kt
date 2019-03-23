package frontend.pascal.parsers

import frontend.Token
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeImpl
import intermediate.IntermediateCodeNodeType
import java.util.*
import kotlin.collections.HashMap

class ExpressionParser(pascalParserTD: PascalParserTD): PascalParserTD(pascalParserTD) {

    init {
        REL_OPS_MAP.put(PascalTokenType.EQUALS, IntermediateCodeNodeType.EQ)
        REL_OPS_MAP.put(PascalTokenType.NOT_EQUALS, IntermediateCodeNodeType.NE)
        REL_OPS_MAP.put(PascalTokenType.LESS_THAN, IntermediateCodeNodeType.LT)
        REL_OPS_MAP.put(PascalTokenType.LESS_EQUALS, IntermediateCodeNodeType.LE)
        REL_OPS_MAP.put(PascalTokenType.GREATER_THAN, IntermediateCodeNodeType.GT)
        REL_OPS_MAP.put(PascalTokenType.GREATER_EQUALS, IntermediateCodeNodeType.GE)
    }

    fun parse(token: Token): IntermediateCodeNode = parseExpression(token)

    private fun parseExpression(token: Token): IntermediateCodeNode {
        var rootNode: IntermediateCodeNode = parseSimpleExpression(token)
        var localToken: Token? = token
        localToken = currentToken

        val tokenType = localToken?.type

        if (REL_OPS.contains(tokenType)) {
            val nodeType = REL_OPS_MAP[tokenType]!!
            val opNode = IntermediateCodeFactory.createIntermediateCodeNode(nodeType)

            opNode.addChild(rootNode)

            localToken = nextToken

            opNode.addChild(parseSimpleExpression(localToken!!))

            rootNode = opNode
        }
        return rootNode
    }

    private fun parseSimpleExpression(token: Token): IntermediateCodeNode {
        val localToken: Token? = token

        // this isn't actually the value that's going to be returned
        return IntermediateCodeNodeImpl(IntermediateCodeNodeType.PROGRAM)
    }


    companion object {
        val REL_OPS = EnumSet.of(PascalTokenType.EQUALS,
                PascalTokenType.NOT_EQUALS,
                PascalTokenType.LESS_THAN,
                PascalTokenType.LESS_EQUALS,
                PascalTokenType.GREATER_THAN,
                PascalTokenType.GREATER_EQUALS)

        val REL_OPS_MAP: MutableMap<PascalTokenType, IntermediateCodeNodeType> = mutableMapOf()


    }
}