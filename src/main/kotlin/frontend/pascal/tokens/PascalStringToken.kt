package frontend.pascal.tokens

import frontend.Source
import frontend.Source.Companion.EOF
import frontend.pascal.PascalErrorCode


class PascalStringToken(source: Source): PascalToken(source) {
    private fun extract() {
        val textBuffer = StringBuilder()
        val valueBuffer = StringBuilder()

        var currentChar = nextChar()
        textBuffer.append('\'')

        do {
            if (currentChar.isWhitespace()) {
                currentChar = ' '
            }

            if (currentChar == '\'') {
                while ((currentChar == '\'') && peekChar() == '\'') {
                    textBuffer.append("''")
                    nextChar()
                    currentChar = nextChar()
                }
            }
        } while ((currentChar != '\'') && (currentChar != EOF))

        if (currentChar == '\'') {
            nextChar()
            textBuffer.append('\'')

            type = PascalTokenType.STRING
            value = valueBuffer.toString()
        } else {
            type = PascalTokenType.ERROR
            value = PascalErrorCode.UNEXPECTED_EOF
        }

        text = textBuffer.toString()
    }
}