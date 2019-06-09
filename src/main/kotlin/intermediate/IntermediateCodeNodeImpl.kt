package intermediate

class IntermediateCodeNodeImpl(var nodeType: IntermediateCodeNodeType) : HashMap<IntermediateCodeKey, Any>(), IntermediateCodeNode {

    private var parent: IntermediateCodeNode? = null
    private var children: MutableList<IntermediateCodeNode> = mutableListOf()

    override fun getType(): IntermediateCodeNodeType = nodeType

    override fun getParent(): IntermediateCodeNode? = parent

    override fun addChild(node: IntermediateCodeNode): IntermediateCodeNode {
        children.add(node)
        (node as IntermediateCodeNodeImpl).parent = this
        return node
    }

    override fun getChildren(): MutableList<IntermediateCodeNode> = children

    override fun setAttribute(key: IntermediateCodeKey, value: Any) {
        put(key, value)
    }

    override fun getAttribute(key: IntermediateCodeKey): Any? = get(key)

    override fun copy(): IntermediateCodeNode {
        val copy = IntermediateCodeFactory.createIntermediateCodeNode(nodeType)

        val attributes: Set<Map.Entry<IntermediateCodeKey, Any>> = entries

        attributes.forEach {
            copy[it.key] = it.value
        }

        return copy
    }

    override fun toString(): String = nodeType.toString()
}