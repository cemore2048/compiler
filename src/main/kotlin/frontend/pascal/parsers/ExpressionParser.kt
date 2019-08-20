package frontend.pascal.parsers

import frontend.Token
import frontend.TokenType
import frontend.pascal.PascalErrorCode
import frontend.pascal.tokens.PascalTokenType
import frontend.pascal.tokens.PascalTokenType.*
import intermediate.IntermediateCodeFactory
import intermediate.IntermediateCodeKey
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeType
import java.sql.Statement
import java.util.*

class ExpressionParser(pascalParserTD: PascalParserTD) : StatementParser(pascalParserTD) {

    init {
        REL_OPS_MAP[EQUALS] = IntermediateCodeNodeType.EQ
        REL_OPS_MAP[NOT_EQUALS] = IntermediateCodeNodeType.NE
        REL_OPS_MAP[LESS_THAN] = IntermediateCodeNodeType.LT
        REL_OPS_MAP[LESS_EQUALS] = IntermediateCodeNodeType.LE
        REL_OPS_MAP[GREATER_THAN] = IntermediateCodeNodeType.GT
        REL_OPS_MAP[GREATER_EQUALS] = IntermediateCodeNodeType.GE
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

        if ((tokentype == PLUS || tokentype == MINUS)) {
            signType = tokentype
            localToken = nextToken
        }

        var rootNode: IntermediateCodeNode = parseTerm(localToken)

        if (signType == MINUS) {
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
            IDENTIFIER -> {
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

            INTEGER -> {
                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.INTEGER_CONSTANT)
                rootNode.setAttribute(IntermediateCodeKey.VALUE, localToken?.value!!)

                localToken = nextToken
            }

            REAL -> {
                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.REAL_CONSTANT)
                rootNode.setAttribute(IntermediateCodeKey.VALUE, localToken?.value!!)

                localToken = nextToken
            }

            STRING -> {
                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.STRING_CONSTANT)
                rootNode.setAttribute(IntermediateCodeKey.VALUE, localToken?.value!!)

                localToken = nextToken
            }

            NOT -> {
                localToken = nextToken

                rootNode = IntermediateCodeFactory.createIntermediateCodeNode(IntermediateCodeNodeType.NOT)

                rootNode.addChild(parseFactor(localToken))
            }

            LEFT_PAREN -> {
                localToken = nextToken

                rootNode = parseExpression(localToken!!)

                localToken = currentToken
                if (localToken?.type == RIGHT_PAREN) {
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
        val REL_OPS: EnumSet<PascalTokenType> = EnumSet.of(EQUALS,
                NOT_EQUALS,
                LESS_THAN,
                LESS_EQUALS,
                GREATER_THAN,
                GREATER_EQUALS)

        val REL_OPS_MAP: MutableMap<PascalTokenType, IntermediateCodeNodeType> = mutableMapOf()

        val ADD_OPS: EnumSet<PascalTokenType> = EnumSet.of(PLUS, MINUS, OR)

        val ADD_OPS_OPS_MAP =
                mapOf(PLUS to IntermediateCodeNodeType.ADD,
                        MINUS to IntermediateCodeNodeType.SUBTRACT,
                        OR to IntermediateCodeNodeType.OR)

        val MULT_OPS: EnumSet<PascalTokenType> = EnumSet.of(STAR, MOD, AND)

        val MULT_OPS_OPS_MAP: Map<PascalTokenType, IntermediateCodeNodeType> =
                mapOf(STAR to IntermediateCodeNodeType.MULTIPLY,
                        SLASH to IntermediateCodeNodeType.FLOAT_DIVIDE,
                        DIV to IntermediateCodeNodeType.INTEGER_DIVIDE,
                        MOD to IntermediateCodeNodeType.MOD,
                        AND to IntermediateCodeNodeType.AND)
    }
}