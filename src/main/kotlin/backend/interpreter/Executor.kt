package backend.interpreter

import backend.Backend
import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import message.Message
import message.MessageListener
import message.MessageType

class Executor: Backend() {

    override fun process(iCode: IntermediateCodeGenerator, symbolTable: SymbolTable) {
        val startTime: Long = System.currentTimeMillis()
        val elapsedTime: Float= (System.currentTimeMillis() - startTime)/1000f
        var executionCount = 0
        var runtimeErrors = 0

        sendMessage(Message(MessageType.INTERPRETER_SUMMARY, listOf<Number>(executionCount, runtimeErrors, elapsedTime)))
    }

    override fun addMessageListener(listener: MessageListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeMessageListener(listener: MessageListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendMessage(message: Message) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}