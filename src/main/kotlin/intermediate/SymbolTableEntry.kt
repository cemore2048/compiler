package intermediate

interface SymbolTableEntry {

    var name: String

    var symbolTable: SymbolTable

    var lineNumbers: MutableList<Int>

    fun setAttribute(key: SymbolTableKey, value: Any)

    fun getAttribute(key: SymbolTableKey): Any

    fun appendLineNumber(lineNumber: Int)
}