package intermediate

interface SymbolTable {

    val nestingLevel: Int

    fun enter(name: String): SymbolTableEntry

    fun lookupSymbolTable(name: String): SymbolTableEntry?

    fun sortedEntries(): MutableList<SymbolTableEntry>
}