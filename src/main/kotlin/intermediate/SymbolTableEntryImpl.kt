package intermediate

class SymbolTableEntryImpl(override var name: String,
                           override var symbolTable: SymbolTable,
                           override var lineNumbers: MutableList<Int>) : HashMap<SymbolTableKey, Any>(), SymbolTableEntry {

    override fun setAttribute(key: SymbolTableKey, value: Any) {
        put(key, value)
    }

    override fun getAttribute(key: SymbolTableKey) = get(key)!!

    override fun appendLineNumber(lineNumber: Int) {
        lineNumbers.add(lineNumber)
    }
}