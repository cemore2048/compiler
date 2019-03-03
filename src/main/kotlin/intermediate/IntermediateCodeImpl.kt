package intermediate

class IntermediateCodeImpl: IntermediateCode {

    private var root: IntermediateCodeNode? = null

    override fun setRoot(node: IntermediateCodeNode): IntermediateCodeNode {
        root = node
        return root!!
    }


    //Should this be nullable?
    override fun getRoot(): IntermediateCodeNode = root!!
}