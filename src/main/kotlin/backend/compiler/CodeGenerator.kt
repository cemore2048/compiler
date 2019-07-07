package backend.compiler

import backend.Backend
import intermediate.IntermediateCode
import intermediate.IntermediateCodeGenerator
import intermediate.IntermediateCodeNode
import intermediate.SymbolTableStack
import message.Message
import message.MessageListener
import message.MessageType

class CodeGenerator : Backend() {
    override fun process(iCode: IntermediateCode, symbolTableStack: SymbolTableStack) {
        val startTime: Long = System.currentTimeMillis()
        val elapsedTime: Float = (System.currentTimeMillis() - startTime) / 1000f

        val instructionCount = 0


        sendMessage(Message(MessageType.COMPILER_SUMMARY, listOf<Number>(instructionCount, elapsedTime)))
    }

    override fun addMessageListener(listener: MessageListener) = messageHandler.addListener(listener)

    override fun removeMessageListener(listener: MessageListener) = messageHandler.removeListener(listener)

    override fun sendMessage(message: Message) = messageHandler.sendMessage(message)

}