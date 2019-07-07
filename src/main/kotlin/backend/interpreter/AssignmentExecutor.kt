package backend.interpreter

import intermediate.IntermediateCode
import intermediate.IntermediateCodeGenerator
import intermediate.IntermediateCodeNode
import intermediate.SymbolTableStack
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
        val value: Any = expressionExecutor.execute(expressionNode)

        //Set the value as an attribute of the variable's symbol table entry.

        return null
    }
}