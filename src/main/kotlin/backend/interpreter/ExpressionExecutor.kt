package backend.interpreter

import intermediate.*

class ExpressionExecutor(executor: Executor): StatementExecutor(executor) {
    /**
     * Execute an expression.
     * @param node the root intermediate code node of the compound statement.
     * @return the computed value of the expression
     */
    fun execute(node: IntermediateCodeNode): Any {
        val nodeType: IntermediateCodeNodeType = node.getType() as IntermediateCodeNodeType

        when (nodeType) {
            IntermediateCodeNodeType.VARIABLE -> {
                // Get the variable's symbol table entry and return its value.
                val symTableEntry: SymbolTableEntry = node.getAttribute(IntermediateCodeKey.ID) as SymbolTableEntry
                return symTableEntry.getAttribute(SymbolTableKeyImpl.DataValue)
            }
            IntermediateCodeNodeType.INTEGER_CONSTANT -> { return node.getAttribute(IntermediateCodeKey.VALUE) as Int }
            IntermediateCodeNodeType.REAL_CONSTANT -> { return node.getAttribute(IntermediateCodeKey.VALUE) as Float }
            IntermediateCodeNodeType.STRING_CONSTANT -> { return node.getAttribute(IntermediateCodeKey.VALUE) as String }
            IntermediateCodeNodeType.NEGATE -> {
                // Get the NEGATE node's expression node child.
                val children = node.getChildren()
                val expressionNode = children[0]

                // Execute the expression and return the negative of it's value
                val value = execute(expressionNode)
                return if (value is Int) {
                    - value
                } else {
                    -(value as Float)
                }
            }

            IntermediateCodeNodeType.NOT -> {
                // Get the NOT node's expression node child.
                val children = node.getChildren()
                val expressionNode = children[0]

                // Execute the expression and return the "not" of it's value.
                val value = execute(expressionNode) as Boolean
                return !value
            }

            else -> return executeBinaryOperator(node, nodeType)
        }
    }
}