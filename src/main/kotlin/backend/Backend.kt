abstract class Backend {
    companion object {
        val messageHandler = MessageHandler()
    }

    protected var symbolTable: SymbolTable? = null
    protected var iCode: IntermediateCodeGenerator? = null

    abstract fun process(iCode: IntermediateCodeGenerator, symbolTable: SymbolTable)
}