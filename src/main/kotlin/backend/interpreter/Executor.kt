package backend.interpreter

import backend.Backend
import intermediate.*
import message.Message
import message.MessageListener
import message.MessageType

open class Executor() : Backend() {


    constructor(parent: Executor): this()

    var symbolTableStack: SymbolTableStack? = null

    //TODO no sure if this should be using the parent's implementation
    override var iCode: IntermediateCode? = null

    companion object {
        private val executionCount = 0
        val errorHandler = RuntimeErrorHandler()
    }

    override fun process(iCode: IntermediateCode, symbolTableStack: SymbolTableStack) {
        this.symbolTableStack = symbolTableStack
        this.iCode = iCode

        val startTime: Long = System.currentTimeMillis()


        // Get the root node of the intermediate code and execute
        val rootNode: IntermediateCodeNode = iCode.getRoot()
        val statementExecutor = StatementExecutor(this)
        statementExecutor.execute(rootNode)

        val elapsedTime: Float = (System.currentTimeMillis() - startTime) / 1000f
        val executionCount = 0
        val runtimeErrors = RuntimeErrorHandler.errorCount

        sendMessage(Message(MessageType.INTERPRETER_SUMMARY, listOf<Number>(executionCount, runtimeErrors, elapsedTime)))
    }

    override fun addMessageListener(listener: MessageListener) = messageHandler.addListener(listener)

    override fun removeMessageListener(listener: MessageListener) = messageHandler.removeListener(listener)

    override fun sendMessage(message: Message) = messageHandler.sendMessage(message)
}