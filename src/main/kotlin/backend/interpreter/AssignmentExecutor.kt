package backend.interpreter

import intermediate.*
import message.Message
import message.MessageType
import java.beans.Expression

class AssignmentExecutor(val parent: StatementExecutor): StatementExecutor(parent) {
    override fun process(iCode: IntermediateCode, symbolTableStack: SymbolTableStack) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Execute a compound statement
     * @param node the root node of the compound statement
     * @return null
     */

    override fun execute(node: IntermediateCodeNode): Executor? {
        val nodeChildren = node.getChildren()

        val variableNode = nodeChildren[0]
        val expressionNode = nodeChildren[1]

        val expressionExecutor = ExpressionExecutor(this)
        val value: Any? = expressionExecutor.execute(expressionNode)

        //Set the value as an attribute of the variable's symbol table entry.
        val variableId = variableNode.getAttribute(IntermediateCodeKey.ID) as SymbolTableEntry
        value?.let { variableId.setAttribute(SymbolTableKeyImpl.DataValue, it) }

        sendMessage(node, variableId.name, value)
        ++executionCount
        return null
    }

    /**
     * Send a message about the assignment operation.
     * @param node the ASSIGN node.
     * @param variableName the name of the target variable.
     * @param value the value of the expression.
     */

    private fun sendMessage(node: IntermediateCodeNode, variableName: String, value: Any?) {
        val lineNum = node.getAttribute(IntermediateCodeKey.LINE)

        if (lineNum != null && value != null) {
            sendMessage(Message(MessageType.ASSIGN, arrayListOf(lineNum, variableName, value)))
        }
    }
}