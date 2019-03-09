package util

import intermediate.SymbolTable
import intermediate.SymbolTableStack
import java.lang.StringBuilder

class CrossReferencer {

    fun print(symbolTableStack: SymbolTableStack) {
        print("\n==== CROSS-REFERENCE TABLE ====")
        printColumnHeadings()

        printSymbolTable(symbolTableStack.localSymbolTable)
    }

    private fun printColumnHeadings() {
        println()
        println(String.format(NAME_FORMAT, "Identifier") + NUMBERS_LABEL)

        println(String.format(NAME_FORMAT, "----------") + NUMBERS_UNDERLINE)
    }

    private fun printSymbolTable(symbolTable: SymbolTable?) {
        symbolTable?.let {
            val sorted = it.sortedEntries()
            sorted.forEach { entry ->
                val lineNumbers = entry.lineNumbers

                print(String.format(NAME_FORMAT, entry.name))

                lineNumbers.forEach { lineNumber ->
                    println(String.format(NUMBERS_FORMAT, lineNumber))
                }
            }

        }
    }
    companion object {
        private const val NAME_WIDTH = 16
        private const val NAME_FORMAT = "%- ${NAME_WIDTH}s"
        private const val NUMBERS_LABEL = " Line numbers    "
        private const val NUMBERS_UNDERLINE = " ------------    "
        private const val NUMBERS_FORMAT = " %03d"

        private const val LABEL_WIDTH = NUMBERS_LABEL.length
        private const val INDENT_WIDTH = NAME_WIDTH + LABEL_WIDTH

        private val INDENT = StringBuilder(INDENT_WIDTH)

        init {
            for (i in 0..INDENT_WIDTH) {
                INDENT.append(" ")
            }
        }
    }


}