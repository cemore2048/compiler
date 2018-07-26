package frontend.pascal

import frontend.EofToken
import frontend.Scanner
import frontend.Source
import frontend.Source.Companion.EOF
import frontend.Token

class PascalScanner(source: Source) : Scanner(source) {
    @Throws
    override fun extractToken(): Token? {
        val currentChar = currentChar()
        return if (currentChar == EOF) {
            EofToken(source)
        } else {
            Token(source)
        }
    }
}