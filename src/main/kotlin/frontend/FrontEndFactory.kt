package frontend

import frontend.pascal.PascalScanner

object FrontEndFactory {
    fun createParser(language: String, type: String, source: Source): Scanner {
        if (language.equals("Pascal") && type.equals("top-down")) {
           return PascalScanner(source)
        } else if (!language.equals("Pascal")) {
            throw Exception("Parser Factory: invalid language $language")
        } else {
            throw Exception("Parser Factory: invalid type $type")
        }
    }
}