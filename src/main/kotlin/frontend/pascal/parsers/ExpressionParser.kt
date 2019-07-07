package frontend.pascal.parsers

import frontend.Token
import frontend.TokenType
import frontend.pascal.PascalErrorCode
import frontend.pascal.tokens.PascalTokenType
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeKey
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeType
import java.sql.Statement
import java.util.*

class ExpressionParser(pascalParserTD: PascalParserTD) : StatementParser(pascalParserTD) {

    init {
        REL_OPS_MAP.put(PascalTokenType.EQUALS, IntermediateCodeNodeType.EQ)
        REL_OPS_MAP.put(PascalTokenType.NOT_EQUALS, IntermediateCodeNodeType.NE)
        REL_OPS_MAP.put(PascalTokenType.LESS_THAN, IntermediateCodeNodeType.LT)
        REL_OPS_MAP.put(PascalTokenType.LESS_EQUALS, IntermediateCodeNodeType.LE)
        REL_OPS_MAP.put(PascalTokenType.GREATER_THAN, IntermediateCodeNodeType.GT)
        REL_OPS_MAP.put(PascalTokenType.GREATER_EQUALS, IntermediateCodeNodeType.GE)
    }

    override fun parse(token: Token): IntermediateCodeNode = parseExpression(token)

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
        var localToken: Token? = token
        var signType: TokenType? = null

        var tokentype: TokenType? = localToken?.type

        if ((tokentype == PascalTokenType.PLUS || tokentype == PascalTokenType.MINUS)) {
            signType = tokentype
            localToken = nextToken
        }

        var rootNode: IntermediateCodeNode = parseTerm(localToken)

        if (signType == PascalTokenType.MINUS) {
            val negateNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.NEGATE)
            rootNode = negateNode
        }

        localToken = currentToken
        tokentype = localToken?.type

        while (ADD_OPS.contains(tokentype)) {
            val nodeType = ADD_OPS_OPS_MAP[tokentype]

            //TODO come back to this and make this !! more safe
            val opNode = IntermediateCodeFactory.createIntermediateCodeNode(nodeType!!)

            opNode.addChild(rootNode)

            localToken = nextToken

            opNode.addChild(parseTerm(token))

            rootNode = opNode
            localToken = currentToken
            tokentype = localToken?.type
        }

        // this isn't actually the value that's going to be returned
        return rootNode
    }

    private fun parseTerm(token: Token?): IntermediateCodeNode {
        var localToken = token
        var rootNode = parseFactor(localToken)

        var tokenType = localToken?.type

        while (MULT_OPS.contains(tokenType)) {
            val nodeType = MULT_OPS_OPS_MAP[tokenType]
            val opNode = IntermediateCodeFactory.createIntermediateCodeNode(nodeType!!)

            opNode.addChild(rootNode)

            localToken = nextToken

            opNode.addChild(parseFactor(localToken))

            rootNode = opNode

            localToken = currentToken
            tokenType = localToken?.type
        }

        return rootNode
    }

    private fun parseFactor(token: Token?): IntermediateCodeNode {
        var localToken = token
        val tokenType: TokenType? = localToken?.type

        var rootNode: IntermediateCodeNode? = null

        when (tokenType as PascalTokenType) {
            PascalTokenType.IDENTIFIER -> {
                val name = localToken?.text?.toLowerCase()
                var id = symbolTableStack.lookup(name!!)

                if (id == null) {
                    errorHandler.flag(localToken!!, PascalErrorCode.IDENTIFIER_UNDEFINED, this)
                    id = symbolTableStack.enterLocal(name)
                }

                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.VARIABLE)
                rootNode.setAttribute(IntermediateCodeKey.ID, id)
                id.appendLineNumber(localToken!!.lineNum)

                localToken = nextToken
            }

            PascalTokenType.INTEGER -> {
                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.INTEGER_CONSTANT)
                rootNode.setAttribute(IntermediateCodeKey.VALUE, localToken?.value!!)

                localToken = nextToken
            }

            PascalTokenType.REAL -> {
                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.REAL_CONSTANT)
                rootNode.setAttribute(IntermediateCodeKey.VALUE, localToken?.value!!)

                localToken = nextToken
            }

            PascalTokenType.STRING -> {
                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.STRING_CONSTANT)
                rootNode.setAttribute(IntermediateCodeKey.VALUE, localToken?.value!!)

                localToken = nextToken
            }

            PascalTokenType.NOT -> {
                localToken = nextToken

                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.NOT)

                rootNode.addChild(parseFactor(localToken))
            }

            PascalTokenType.LEFT_PAREN -> {
                localToken = nextToken

                rootNode = parseExpression(localToken!!)

                localToken = currentToken
                if (localToken?.type == PascalTokenType.RIGHT_PAREN) {
                    localToken = nextToken
                } else {
                    errorHandler.flag(localToken!!, PascalErrorCode.MISSING_RIGHT_PAREN, this)
                }
            }

            else -> {
                errorHandler.flag(localToken!!, PascalErrorCode.UNEXPECTED_TOKEN, this)
            }

        }
        return rootNode!!
    }

    companion object {
        val REL_OPS: EnumSet<PascalTokenType> = EnumSet.of(PascalTokenType.EQUALS,
                PascalTokenType.NOT_EQUALS,
                PascalTokenType.LESS_THAN,
                PascalTokenType.LESS_EQUALS,
                PascalTokenType.GREATER_THAN,
                PascalTokenType.GREATER_EQUALS)

        val REL_OPS_MAP: MutableMap<PascalTokenType, IntermediateCodeNodeType> = mutableMapOf()

        val ADD_OPS: EnumSet<PascalTokenType> = EnumSet.of(PascalTokenType.PLUS, PascalTokenType.MINUS, PascalTokenType.OR)

        val ADD_OPS_OPS_MAP =
                mapOf(PascalTokenType.PLUS to IntermediateCodeNodeType.ADD,
                        PascalTokenType.MINUS to IntermediateCodeNodeType.SUBTRACT,
                        PascalTokenType.OR to IntermediateCodeNodeType.OR)

        val MULT_OPS: EnumSet<PascalTokenType> = EnumSet.of(PascalTokenType.STAR, PascalTokenType.MOD, PascalTokenType.AND)

        val MULT_OPS_OPS_MAP: Map<PascalTokenType, IntermediateCodeNodeType> =
                mapOf(PascalTokenType.STAR to IntermediateCodeNodeType.MULTIPLY,
                        PascalTokenType.SLASH to IntermediateCodeNodeType.FLOAT_DIVIDE,
                        PascalTokenType.DIV to IntermediateCodeNodeType.INTEGER_DIVIDE,
                        PascalTokenType.MOD to IntermediateCodeNodeType.MOD,
                        PascalTokenType.AND to IntermediateCodeNodeType.AND)
    }
}