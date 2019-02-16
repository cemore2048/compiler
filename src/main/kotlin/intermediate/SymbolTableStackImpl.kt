package intermediate

class SymbolTableStackImpl: ArrayList<SymbolTable>(), SymbolTableStack {
    override var currentNestingLevel: Int = 0

    override var localSymbolTable: SymbolTable? = null
        get() = get(currentNestingLevel)

    init {
        currentNestingLevel = 0
        add(SymbolTableFactory.createSymbolTable(currentNestingLevel))
    }
    override fun enterLocal(name: String): SymbolTableEntry = get(currentNestingLevel).enter(name)

    override fun lookupLocal(name: String): SymbolTableEntry = get(currentNestingLevel).lookupSymbolTable(name)

    override fun lookup(name: String): SymbolTableEntry = lookupLocal(name)
}