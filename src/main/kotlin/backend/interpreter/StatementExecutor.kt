package backend.interpreter

import intermediate.IntermediateCodeKey
import intermediate.IntermediateCodeNode
import intermediate.IntermediateCodeNodeType
import message.Message
import message.MessageType

open class StatementExecutor(parent: Executor) : Executor(parent) {

    /**
     * Execute a statement
     * To be overridden by the specialized statement executor subclasses.
     * @param node the root node of the statement
     * @return null
     */

    open fun execute(node: IntermediateCodeNode): Executor? {
        val nodeType: IntermediateCodeNodeType = node.getType()

        //send message about current source line
        sendSourceLineMessage(node)

        when (nodeType) {
            IntermediateCodeNodeType.COMPOUND -> {
                return CompoundExecutor(this).execute(node)
            }

            IntermediateCodeNodeType.ASSIGNMENT -> {
                return AssignmentExecutor(this).execute(node)
            }

            IntermediateCodeNodeType.NO_OP -> {
                return null
            }

            else -> {
                errorHandler.flag(node, RuntimeErrorCode.UNIMPLEMENTED_FEATURE, this)
                return null
            }
        }
    }

    private fun sendSourceLineMessage(node: IntermediateCodeNode) {
        val lineNumber = node.getAttribute(IntermediateCodeKey.LINE)

        if (lineNumber != null) {
            sendMessage(Message(MessageType.SOURCE_LINE, listOf(lineNumber)))
        }
    }
}