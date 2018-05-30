
abstract class Parser {

    protected val scanner: Scanner? = null
    protected val intermediateCode: IntermediateCodeGenerator? = null

    var currentToken: Token? = null
        get() = scanner.currentToken()

    var nextToken: Token? = null
        get() = scanner.nextToken()

    companion object {
        val symbolTable: SymbolTable? = null
    }

    @Throws
    abstract fun parse()

    abstract fun getErrorCount(): Int

}

