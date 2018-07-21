import backend.Backend
import frontend.FrontEndFactory
import frontend.Parser
import frontend.Source
import backend.BackendFactory
import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import java.io.BufferedReader
import java.io.FileReader

class Pascal(operation: String, filePath: String, flags: String) {

    lateinit var parser: Parser,
    lateinit var source: Source,
    lateinit var iCode: IntermediateCodeGenerator?,
    lateinit var symbolTable: SymbolTable?,
    lateinit var backend: Backend,
    lateinit var operation: String

    init {
        try {
            val intermediate = flags.indexOf('i') > -1
            val xref = flags.indexOf('x') > -1

            source = Source(BufferedReader(FileReader(filePath)))
            source.addMessageListener(MessageListenerImpl())

            parser = FrontEndFactory.createParser("pascal", "top-down", source)
            parser.addMessageListener(MessageListenerImpl())

            backend = BackendFactory.createBackend(operation)
            backend.addMessageListener(ParserMessageListener())

            parser.parse()
            source.close()

            iCode = parser.intermediateCode
            symbolTable = parser.symbolTable

            backend.process(iCode!!, symbolTable!!)
        } catch (ex: Exception) {
            print("***** Internal translator error. *****")
            ex.printStackTrace()
        }
    }
}

fun main(args: List<String>) {
    try {
        val operation: String = args[0]

        if (operation != "compile" || operation != "execute") {
            throw Exception()
        }

        var i = 0
        var flags = ""

        while (++i < args.size && args[i][0] == '-') {
            flags += args[i].substring(1)
        }

        if (i < args.size) {
            val path = args[i]
            Pascal(operation, path, flags)
        } else {
            throw Exception("Invalid input index")
        }
    } catch (ex: Exception) {
        throw Exception("Invalid argument")
    }
}