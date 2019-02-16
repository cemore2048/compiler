package intermediate

interface SymbolTable {

    fun enter(name: String): SymbolTableEntry

    fun lookupSymbolTable(name: String): SymbolTableEntry

    fun sortedEntries(): MutableList<SymbolTableEntry>
}