package backend.interpreter

import intermediate.IntermediateCodeNode

class CompoundExecutor(val parent: StatementExecutor): StatementExecutor(parent) {

    /**
     * Execute a compound statement
     * @param node the root node of the compound statement
     * @return null
     */

    override fun execute(node: IntermediateCodeNode): Executor? {
        node.getChildren().forEach {
            parent.execute(it)
        }

        return null
    }
}