package frontend.pascal.tokens

import frontend.TokenType

private val FIRST_RESERVED_INDEX: Int = PascalTokenType.AND.ordinal

enum class PascalTokenType(identifier: String = ""): TokenType {
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

    IDENTIFIER, INTEGER, REAL, STRING, ERROR, END_OF_FILE
}