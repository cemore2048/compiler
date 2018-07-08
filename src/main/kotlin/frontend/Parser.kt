package frontend

import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import message.Message
import message.MessageHandler
import message.MessageListener
import message.MessageProducer

abstract class Parser(private val scanner: Scanner) : MessageProducer {

    companion object {
        var symbolTable: SymbolTable? = null
        var messageHandler: MessageHandler = MessageHandler()
    }
    protected val intermediateCode: IntermediateCodeGenerator? = null

    var currentToken: Token? = scanner.currentToken()

    var nextToken: Token? = null
        get() = scanner.nextToken()

    @Throws
    abstract fun parse()

    abstract fun getErrorCount(): Int

    override fun addMessageListener(listener: MessageListener) = messageHandler.addListener(listener)

    override fun removeMessageListener(listener: MessageListener) = messageHandler.removeListener(listener)

    override fun sendMessage(message: Message) = messageHandler.sendMessage(message)
}

