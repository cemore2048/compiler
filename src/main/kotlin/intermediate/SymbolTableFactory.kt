package intermediate

class SymbolTableFactory {

    companion object {
        fun createSymbolTableStack() = SymbolTableStackImpl()

        fun createSymbolTable(nestingLevel: Int): SymbolTable = SymbolTableImpl(nestingLevel)

        fun createSymbolTableEntry(name: String, symbolTable: SymbolTable) = SymbolTableEntryImpl(name, symbolTable)
    }
}