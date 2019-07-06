package backend.interpreter

import backend.Backend
import intermediate.IntermediateCodeKey
import intermediate.IntermediateCodeNode
import message.Message
import message.MessageType

class RuntimeErrorHandler {

    companion object {
        const private val MAX_ERRORS = 5
        private var errorCount = 0
    }

    fun flag(node: IntermediateCodeNode?, errorCode: RuntimeErrorCode, backend: Backend) {
        var localNode = node
        val lineNumber: String? = null
        while ((localNode != null) && (node?.getAttribute(IntermediateCodeKey.LINE) == null)) {
            localNode = node?.getParent()
        }

        backend.sendMessage(Message(MessageType.RUNTIME_ERROR, listOf(errorCode.toString(), node?.getAttribute(IntermediateCodeKey.LINE) as Int)))

        if (++errorCount > MAX_ERRORS) {
            print("*** ABORTED AFTER TOO MANY RUNTIME ERRORS.")
            System.exit(-1)
        }
    }
}

enum class RuntimeErrorCode(val message: String) {
    UNINITIALIZED_VALUE("Uninitialized Value"),
    VALUE_RANGE("Value out of range"),
    INVALID_CASE_BY_EXPRESSION_VALUE("Invalid CASE by expression value"),
    DIVISION_BY_ZERO("Division by Zero"),
    INVALID_STANDARD_FUNCTION_AGREEMENT("Invalid standard function argument"),
    INVALID_INPUT("Invalid input"),
    STACK_OVERFLOW("Runtime stack overflow"),
    UNIMPLEMENTED_FEATURE("Unimplemented runtime feature");

}