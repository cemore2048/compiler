package intermediate

interface SymbolTableStack {

    var currentNestingLevel: Int

    var localSymbolTable: SymbolTable?

    fun enterLocal(name: String): SymbolTableEntry

    fun lookupLocal(name: String): SymbolTableEntry

    fun lookup(name: String): SymbolTableEntry


}