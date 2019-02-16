package intermediate

import java.util.*

class SymbolTableImpl(private var nestingLevel: Int): TreeMap<String, SymbolTableEntry>(), SymbolTable {
    override fun enter(name: String): SymbolTableEntry {
       val entry: SymbolTableEntry = SymbolTableFactory.createSymbolTableEntry(name, this)
        put(name, entry)
        return entry
    }

    override fun lookupSymbolTable(name: String): SymbolTableEntry = get(name)!!

    override fun sortedEntries(): MutableList<SymbolTableEntry> = values.toMutableList()

}