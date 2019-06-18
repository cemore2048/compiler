package frontend.pascal.tokens

import frontend.Source
import frontend.pascal.PascalErrorCode

class PascalErrorToken(source: Source, val errorCode: PascalErrorCode, val tokenText: String) : PascalToken(source)