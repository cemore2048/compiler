package backend

import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import message.MessageHandler
import message.MessageProducer

abstract class Backend: MessageProducer {
    companion object {
        val messageHandler = MessageHandler()
    }

    protected var symbolTable: SymbolTable? = null
    protected var iCode: IntermediateCodeGenerator? = null

    abstract fun process(iCode: IntermediateCodeGenerator, symbolTable: SymbolTable)
}