package intermediate

class IntermediateCodeFactory {

    companion object {
        fun createIntermediateCode() = IntermediateCodeImpl()

        fun createIntermediateCodeNode(type: IntermediateCodeNodeType) = IntermediateCodeNodeImpl(type)
    }
}