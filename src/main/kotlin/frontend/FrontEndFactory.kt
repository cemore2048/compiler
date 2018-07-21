package frontend

import frontend.pascal.PascalParserTD
import frontend.pascal.PascalScanner

object FrontEndFactory {
    fun createParser(language: String, type: String, source: Source): Parser {
        if (language == "Pascal" && type == "top-down") {
            val scanner: Scanner = PascalScanner(source)
           return PascalParserTD(scanner)
        } else if (language != "Pascal") {
            throw Exception("Parser Factory: invalid language $language")
        } else {
            throw Exception("Parser Factory: invalid type $type")
        }
    }
}