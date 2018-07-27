package frontend.pascal

import frontend.EofToken
import frontend.Scanner
import frontend.Source
import frontend.Source.Companion.EOF
import frontend.Token
import frontend.pascal.tokens.PascalErrorToken

class PascalScanner(source: Source) : Scanner(source) {
    @Throws
    override fun extractToken(): Token? {
        skipWhiteSpace()
        val token: Token? = currentToken()
        val currentChar = currentChar()


        return if (currentChar == EOF) {
            EofToken(source, END_OF_FILE)
        } else if (currentChar.isLetter()) {
            PascalWordToken(source)
        } else if (currentChar.isDigit()) {
            PascalNumberToken(source)
        } else if (currentChar == '\'') {
            PascalStringToken(source)
        } else if (PascalTokenType.SPECIAL_SYMBOLS.containsKey(currentChar.toString())) {
            PascalSpecialSymbolToken(source)
        } else {
            PascalErrorToken(source)
        }
    }

    private fun skipWhiteSpace() {
        var currentChar = currentChar()

        while (currentChar.isWhitespace() || (currentChar == '{')) {
            if (currentChar == '{') {
                do {
                    currentChar = nextChar()
                } while (currentChar != '}' && currentChar != EOF)

                if (currentChar == '}') currentChar = nextChar()
            } else {
                currentChar = nextChar()
            }
        }
    }
}