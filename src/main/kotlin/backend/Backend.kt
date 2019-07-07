package backend

import intermediate.IntermediateCode
import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import intermediate.SymbolTableStack
import message.MessageHandler
import message.MessageProducer

abstract class Backend : MessageProducer {
    companion object {
        val messageHandler = MessageHandler()
    }

    protected var symbolTable: SymbolTable? = null
    open protected var iCode: IntermediateCode? = null

    abstract fun process(iCode: IntermediateCode, symbolTableStack: SymbolTableStack)
}