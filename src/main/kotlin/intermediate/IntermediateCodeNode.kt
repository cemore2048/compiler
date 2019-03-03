package intermediate

interface IntermediateCodeNode {

    fun getType(): IntermediateCodeNodeType

    fun getParent(): IntermediateCodeNode?


    fun addChild(node: IntermediateCodeNode): IntermediateCodeNode

    fun getChildren(): MutableList<IntermediateCodeNode>

    fun setAttribute(key: IntermediateCodeKey, value: Any)

    fun getAttribute(key: IntermediateCodeKey): Any?

    fun copy(): IntermediateCodeNode
}