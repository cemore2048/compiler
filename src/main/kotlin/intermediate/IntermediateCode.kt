package intermediate

interface IntermediateCode {
    fun setRoot(node: IntermediateCodeNode): IntermediateCodeNode

    fun getRoot(): IntermediateCodeNode
}