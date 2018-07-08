package frontend

import frontend.pascal.PascalScanner

object FrontEndFactory {
    fun createParser(language: String, type: String, source: Source): Scanner {
        if (language == "Pascal" && type == "top-down") {
           return PascalScanner(source)
        } else if (language != "Pascal") {
            throw Exception("Parser Factory: invalid language $language")
        } else {
            throw Exception("Parser Factory: invalid type $type")
        }
    }
}