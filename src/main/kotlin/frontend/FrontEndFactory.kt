package frontend

import frontend.pascal.PascalScanner
import frontend.pascal.parsers.PascalParserTD

object FrontEndFactory {
    fun createParser(language: String, type: String, source: Source): Parser {
        if (language == "pascal" && type == "top-down") {
            val scanner: Scanner = PascalScanner(source)
            //TODO am I passing in the correct thing here?
            return PascalParserTD(scanner)
        } else if (language != "pascal") {
            throw Exception("Parser Factory: invalid language $language")
        } else {
            throw Exception("Parser Factory: invalid type $type")
        }
    }
}