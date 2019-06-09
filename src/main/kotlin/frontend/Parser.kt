package frontend

import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import intermediate.SymbolTableFactory
import message.Message
import message.MessageHandler
import message.MessageListener
import message.MessageProducer

abstract class Parser(val scanner: Scanner) : MessageProducer {

    var symbolTable: SymbolTable? = null
    var messageHandler: MessageHandler = MessageHandler()

    val intermediateCode: IntermediateCodeGenerator? = null

    val symbolTableStack = SymbolTableFactory.createSymbolTableStack()

    val currentToken: Token?
        get() = scanner.currentToken()

    val nextToken: Token?
        get() = scanner.nextToken()

    @Throws
    abstract fun parse()

    abstract fun getErrorCount(): Int

    override fun addMessageListener(listener: MessageListener) = messageHandler.addListener(listener)

    override fun removeMessageListener(listener: MessageListener) = messageHandler.removeListener(listener)

    override fun sendMessage(message: Message) = messageHandler.sendMessage(message)

    companion object {
        val messageHandler = MessageHandler()
    }
}
