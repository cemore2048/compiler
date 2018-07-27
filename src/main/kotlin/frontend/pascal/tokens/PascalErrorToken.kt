package frontend.pascal.tokens

import frontend.Source
import frontend.pascal.PascalErrorCode
import frontend.pascal.PascalScanner

data class PascalErrorToken(val source: Source, val errorCode: PascalErrorCode, val tokenText: String, private val scanner: PascalScanner) : PascalToken(scanner) {

}