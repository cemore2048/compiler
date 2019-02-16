package frontend.pascal.tokens

import frontend.Source
import frontend.pascal.PascalErrorCode

class PascalSpecialSymbolToken(source: Source): PascalToken(source) {

    override fun extract() {
        var currentChar = currentChar()

        text = currentChar.toString()

        when(currentChar) {
            '+', '-', '*', '/', ',',';', '\'', '=', '(', ')', '[', ']', '{', '}', '^' -> nextChar()
            ':' -> {
                currentChar = nextChar()

                if (currentChar == '=') {
                    text += currentChar
                    nextChar()
                }
            }
            '<' -> {
                currentChar = nextChar()

                if (currentChar == '=') {
                    text += currentChar
                    nextChar()
                } else if (currentChar == '>') {
                    text += currentChar
                    nextChar()
                }
            }
            '>' -> {
                currentChar = nextChar()

                if (currentChar == '=') {
                    text += currentChar
                    nextChar()
                }
            }
            '.' -> {
                currentChar = nextChar()

                if (currentChar == '.') {
                    text += currentChar
                    nextChar()
                }
            }
            else -> {
                nextChar()
                type = PascalTokenType.ERROR
                value = PascalErrorCode.INVALID_CHARACTER
            }
        }
    }
}