package frontend.pascal

import frontend.EofToken
import frontend.Scanner
import frontend.Source
import frontend.Source.Companion.EOF
import frontend.Token
import frontend.pascal.tokens.*

class PascalScanner(source: Source) : Scanner(source) {
    @Throws
    override fun extractToken(): Token? {
        skipWhiteSpace()
        val currentChar = currentChar()

        return when {
            currentChar == EOF -> {
                //EofToken(source, PascalTokenType.END_OF_FILE)
                EofToken(source)
            }
            currentChar.isLetter() -> PascalWordToken(source)
            currentChar.isDigit() -> PascalNumberToken(source)
            currentChar == '\'' -> PascalStringToken(source)
            PascalTokenType.SPECIAL_SYMBOLS.contains(currentChar.toString()) -> PascalSpecialSymbolToken(source)
            else -> PascalErrorToken(source, PascalErrorCode.INVALID_CHARACTER, currentChar.toString())
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