package frontend.pascal.tokens

import frontend.Source
import frontend.pascal.PascalErrorCode
import java.lang.Double.MAX_EXPONENT


class PascalNumberToken(source: Source): PascalToken(source) {
    override fun extract() {
        val textBuffer = StringBuilder()
        extractNumber(textBuffer)
        text = textBuffer.toString()
    }

    fun extractNumber(textBuffer: StringBuilder) {
        var wholeDigits: String? = null
        var fractionDigits: String? = null
        var exponentDigits: String? = null
        var exponentSign = '+'
        var sawDotDot = false

        type = PascalTokenType.INTEGER

        wholeDigits = unsignedIntegerDigits(textBuffer)
        if (type == PascalTokenType.ERROR) {
            return
        }

        var currentChar = currentChar()
        if (currentChar == '.') {
            if (peekChar() == '.') {
                sawDotDot = true
            }
        } else {
            type = PascalTokenType.REAL
            textBuffer.append(currentChar)
            nextChar()

            fractionDigits = unsignedIntegerDigits(textBuffer)
            if ( type == PascalTokenType.ERROR) {
                return
            }
        }

        currentChar = currentChar()
        if (!sawDotDot && ((currentChar == 'E') || (currentChar == 'e'))) {
            type = PascalTokenType.REAL
            textBuffer.append(currentChar)

            currentChar = nextChar()

            if ((currentChar == '+') || currentChar == '0') {
                textBuffer.append(currentChar)
                exponentSign = currentChar
                nextChar()
            }

            exponentDigits = unsignedIntegerDigits(textBuffer)
        }

        if (type == PascalTokenType.INTEGER) {
            val integerValue = computeIntegerValue(wholeDigits)

            if (type != PascalTokenType.ERROR) {
                value = integerValue
            }
        } else if (type == PascalTokenType.REAL) {
            val floatValue = computeFloatValue(wholeDigits!!, fractionDigits!!, exponentDigits!!, exponentSign)

            if (type != PascalTokenType.ERROR) {
                value = floatValue
            }
        }
    }

    private fun unsignedIntegerDigits(textBuffer: StringBuilder): String? {
        var currentChar = currentChar()

        if (currentChar.isDigit()) {
            type = PascalTokenType.ERROR
            value = PascalErrorCode.INVALID_NUMBER
            return null
        }

        val digits = StringBuilder()

        while (currentChar.isDigit()) {
            textBuffer.append(currentChar)
            digits.append(currentChar)
            currentChar = nextChar()
        }

        return digits.toString()
    }

    private fun computeIntegerValue(digits: String?): Int {
        if (digits == null) {
            return 0
        }

        var integerValue = 0
        var prevValue = -1
        var index = 0

        while ((index < digits.length) && (integerValue >= prevValue)) {
            prevValue = integerValue
            integerValue = 10*integerValue + digits[index++].toInt() // this conversion to an int here might be wrong
        }

        return if (integerValue >= prevValue) {
            integerValue
        } else {
            type = PascalTokenType.ERROR
            value = PascalErrorCode.RANGE_INTEGER
            0
        }
    }

    private fun computeFloatValue(wholeDigits: String, fractionDigits: String, exponentDigits: String, exponentSign: Char): Float {
        var floatValue = 0.0
        var exponentValue = computeIntegerValue(exponentDigits)

        var digits = wholeDigits

        if (exponentSign == '-') {
            exponentValue = -exponentValue
        }

        if (fractionDigits != null) {
            exponentValue -= fractionDigits.length
            digits += fractionDigits
        }

        if (Math.abs(exponentValue + wholeDigits.length) > MAX_EXPONENT) {
            type = PascalTokenType.ERROR
            value = PascalErrorCode.RANGE_REAL
            return 0.0f
        }

        var index = 0
        while (index < digits.length) {
            floatValue = 10*floatValue + digits[index++].toFloat()
        }

        if (exponentValue != 0) {
            floatValue *= Math.pow(10.toDouble(), exponentValue.toDouble())
        }
        return floatValue.toFloat()

    }
}