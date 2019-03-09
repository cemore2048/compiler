package util

import intermediate.*
import java.io.PrintStream
import java.lang.StringBuilder

class ParseTreePrinter(val printStream: PrintStream,
                       var lineLength: Int = 0,
                       var indentation: String = "",
                       var indent: String = "",
                       val line: StringBuilder = StringBuilder()) {

    init {
        for (i in 0..INDENT_WIDTH) {
            indent += " "
        }
    }

    fun print(intermediateCode: IntermediateCode) {
        printStream.print("\n===== INTERMEDIATE CODE =====\n")

        printNode((intermediateCode.getRoot() as IntermediateCodeNodeImpl))
        printLine()
    }

    fun printNode(node: IntermediateCodeNodeImpl) {
        append(indentation)
        append("<$node")

        printAttributes(node)
        printTypeSpec(node)

        val childNodes = node.getChildren()

        if (childNodes.size > 0) {
            append(">")
            printLine()

            printChildNodes(childNodes)
            append(indentation)
            append("</$node>")
        } else {
            append(" ")
            append("\\>")
        }

        printLine()

    }

    private fun printAttributes(node: IntermediateCodeNodeImpl) {
        val saveIndentation = indentation
        indentation += indent

        node.entries.forEach {
            printAttribute(it.key.toString(), it.value)
        }

        indentation = saveIndentation

    }

    private fun printAttribute(keyString: String, value: Any) {
        val isSymbolTableEntry = value is SymbolTableEntry
        val valueString = if (isSymbolTableEntry) (value as SymbolTableEntry).name else value.toString()

        val text = "${keyString.toLowerCase()} $=\" $valueString\""
        append(" ")
        append(text)

        if (isSymbolTableEntry) {
            val level = (value as SymbolTableEntry).symbolTable.nestingLevel
            printAttribute("LEVEL", level)
        }
    }

    private fun printChildNodes(childNodes: MutableList<IntermediateCodeNode>) {
        val saveIndentation = indentation
        indentation += indent

        childNodes.forEach {
            printNode(it as IntermediateCodeNodeImpl)
        }

        indentation = saveIndentation
    }

    private fun printTypeSpec(node: IntermediateCodeNodeImpl) {}

    private fun append(text: String) {
        val textLength = text.length
        var lineBreak = false

        if (lineLength + textLength > LINE_WIDTH) {
            printLine()
            line.append(indentation)
            lineLength = indentation.length
            lineBreak = true

        }

        if (!(lineBreak && text == " ")) {
            line.append(text)
            lineLength += textLength
        }
    }

    private fun printLine() {
        if (lineLength > 0) {
            printStream.println(line)
            line.setLength(0)
            lineLength = 0
        }
    }
    companion object {
        private const val INDENT_WIDTH = 4
        private const val LINE_WIDTH = 80
    }
}