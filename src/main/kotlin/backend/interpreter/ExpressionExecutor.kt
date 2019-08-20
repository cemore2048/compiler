package backend.interpreter

import intermediate.*
import java.util.*

class ExpressionExecutor(executor: Executor) : StatementExecutor(executor) {

    companion object {
        private val ARITH_OPS: EnumSet<IntermediateCodeNodeType> = EnumSet.of(IntermediateCodeNodeType.ADD,
                IntermediateCodeNodeType.SUBTRACT, IntermediateCodeNodeType.MULTIPLY, IntermediateCodeNodeType.FLOAT_DIVIDE,
                IntermediateCodeNodeType.INTEGER_DIVIDE)
    }

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
            IntermediateCodeNodeType.INTEGER_CONSTANT -> {
                return node.getAttribute(IntermediateCodeKey.VALUE) as Int
            }
            IntermediateCodeNodeType.REAL_CONSTANT -> {
                return node.getAttribute(IntermediateCodeKey.VALUE) as Float
            }
            IntermediateCodeNodeType.STRING_CONSTANT -> {
                return node.getAttribute(IntermediateCodeKey.VALUE) as String
            }
            IntermediateCodeNodeType.NEGATE -> {
                // Get the NEGATE node's expression node child.
                val children = node.getChildren()
                val expressionNode = children[0]

                // Execute the expression and return the negative of it's value
                val value = execute(expressionNode)
                return if (value is Int) {
                    -value
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

    fun executeBinaryOperator(node: IntermediateCodeNode, nodeType: IntermediateCodeNodeType): Any {
        // Get the two operand children of the operator node
        val children = node.getChildren()
        val operandNode1 = children[0]
        val operandNode2 = children[1]

        // Operands
        val operand1 = execute(operandNode1)
        val operand2 = execute(operandNode2)

        val integerMode = (operand1 is Int) && (operand2 is Int)

        // Arithmetic Operators

        if (ARITH_OPS.contains(nodeType)) {
            if (integerMode) {
                val value1 = operand1 as Int
                val value2 = operand2 as Int

                //Int operations
                when (nodeType) {
                    IntermediateCodeNodeType.ADD -> return value1 + value2
                    IntermediateCodeNodeType.SUBTRACT -> return value1 - value2
                    IntermediateCodeNodeType.MULTIPLY -> return value1 * value2
                    IntermediateCodeNodeType.FLOAT_DIVIDE -> {
                        // Check for division by 0
                        return if (value2 != 0) {
                            //TODO why can't a  cast from int to float ever succeed in Kotlin
                            (value1.toFloat()) / (value2.toFloat())
                        } else {
                            errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this)
                            0
                        }
                    }
                    IntermediateCodeNodeType.INTEGER_DIVIDE -> {
                        return if (value2 != 0) {
                            value1 / value2
                        } else {
                            errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this)
                            0
                        }
                    }
                    IntermediateCodeNodeType.MOD -> {
                        return if (value2 != 0) {
                            value1%value2
                        } else {
                            errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this)
                            0
                        }
                    }
                }
            }
        } else {
            val value1: Float = if (operand1 is Float) operand1 else { operand1 }
            val value2: Float = if (operand2 is Float) operand2 else { operand2.toFloat() }

            when (nodeType) {

            }
        }
    }
}