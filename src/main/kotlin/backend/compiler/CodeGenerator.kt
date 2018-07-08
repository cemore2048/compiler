package backend.compiler

import backend.Backend
import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import message.Message
import message.MessageType

class CodeGenerator: Backend() {
    override fun process(iCode: IntermediateCodeGenerator, symtab: SymbolTable) {
        val startTime: Long = System.currentTimeMillis()
        val elapsedTime: Float = (System.currentTimeMillis() - startTime) / 1000f

        val instructionCount = 0

        sendMessage(Message(MessageType.COMPILER_SUMMARY, arrayOf<Number>(instructionCount, elapsedTime)))
    }
}