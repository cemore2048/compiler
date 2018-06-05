abstract class Scanner(val source: Source) {

    var currentToken: Token? = null

    fun currentToken() = currentToken

    @Throws
    fun nextToken(): Token {
        currentToken = extractToken()
        return currentToken!!
    }

    @Throws
    abstract fun extractToken(): Token?

    @Throws
    fun currentChar() = source.currentChar()

    fun nextChar() = source.nextChar()
}