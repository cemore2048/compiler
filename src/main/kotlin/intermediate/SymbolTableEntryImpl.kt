package intermediate

class SymbolTableEntryImpl(override var name: String,
                           override var symbolTable: SymbolTable) : HashMap<SymbolTableKey, Any>(), SymbolTableEntry {

    override var lineNumbers: MutableList<Int> = ArrayList()

    override fun setAttribute(key: SymbolTableKey, value: Any) {
        put(key, value)
    }

    override fun getAttribute(key: SymbolTableKey) = get(key)!!

    override fun appendLineNumber(lineNumber: Int) {
        lineNumbers.add(lineNumber)
    }
}