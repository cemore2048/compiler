package frontend

import frontend.pascal.PascalParserTD
import frontend.pascal.PascalScanner

object FrontEndFactory {
    fun createParser(language: String, type: String, source: Source): Parser {
        if (language == "pascal" && type == "top-down") {
            val scanner: Scanner = PascalScanner(source)
           return PascalParserTD(scanner)
        } else if (language != "pascal") {
            throw Exception("Parser Factory: invalid language $language")
        } else {
            throw Exception("Parser Factory: invalid type $type")
        }
    }
}