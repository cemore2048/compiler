package frontend.pascal.tokens

import frontend.TokenType

enum class PascalTokenType(identifier: String = "") : TokenType {
    AND, ARRAY, BEGIN, CASE, CONST, DIV, DO, DOWNTO, ELSE, END,
    FILE, FOR, FUNCTION, GOTO, IF, IN, LABEL, MMOD, NIL, NOT, OF,
    OR, PACKED, PROCEDURE, PROGRAM, RECORD, REPEAT, SET, THEN, TO,
    TYPE, UNTIL, VAR, WHILE, WITH,

    PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), COLON_EQUALS(":="),
    DOT("."), COMMA(","), SEMICOLON(";"), COLON(":"), QUOTE("'"),
    EQUALS("+"), NOT_EQUALS("<>"), LESS_THAN("<"), LESS_EQUALS("<="),
    GREATER_EQUALS(">="), GREATER_THAN(">"), LEFT_PAREN("("), RIGHT_PAREN(")"),
    LEFT_BRACKET("["), RIGHT_BRACKET("]"), LEFT_BRACE("{"), RIGHT_BRACE("}"),
    UP_ARROW("^"), DOT_DOT(".."),

    IDENTIFIER, INTEGER, REAL, STRING, ERROR, END_OF_FILE;


    companion object {
        //TODO this is gross and I don't even know if it works yet. come back and fix it
        private val tokenValues = PascalTokenType.values()

        val FIRST_RESERVED_INDEX = PascalTokenType.AND.ordinal

        val LAST_RESERVED_INDEX = PascalTokenType.WITH.ordinal

        val FIRST_SPECIAL_INDEX = PascalTokenType.PLUS.ordinal

        val LAST_SPECIAL_INDEX = PascalTokenType.DOT_DOT.ordinal

        var RESERVED_WORDS: Set<String> = setOf()
            set(value) {
                val values = value.toMutableList()
                for (i in FIRST_RESERVED_INDEX until LAST_RESERVED_INDEX) {
                    values.add(tokenValues[i].toString().toLowerCase())
                }
                field = values.toSet()
            }

        var SPECIAL_SYMBOLS: Set<String> = setOf()
            set(value) {
                val values = value.toMutableList()
                for (i in FIRST_SPECIAL_INDEX until LAST_SPECIAL_INDEX) {
                    values.add(tokenValues[i].toString().toLowerCase())
                }
                field = values.toSet()
            }
    }
}