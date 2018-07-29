package frontend.pascal.tokens

import frontend.Source

class PascalWordToken(source: Source) : PascalToken(source) {
    fun extract() {
        val textBuffer = StringBuilder()
        var currentChar = currentChar()

        while (currentChar.isLetterOrDigit()) {
            textBuffer.append(currentChar)
            currentChar = nextChar()
        }

        type =
                if (PascalTokenType.RESERVED_WORDS.contains(textBuffer.toString()))
                    PascalTokenType.valueOf(text!!.toUpperCase())
                else
                    PascalTokenType.IDENTIFIER
    }
}