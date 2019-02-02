package frontend

open class Token(val source: Source) {

    var type: TokenType? = null
    var text: String? = ""
    var value: Any? = null
    var lineNum: Int = source.lineNum
    var position: Int? = source.currentPosition

    init {
        extract()
    }

    @Throws
    open fun extract() {
        text = currentChar().toString()
        value = null
        nextChar()
    }

    @Throws
    protected fun currentChar(): Char = source.currentChar()

    @Throws
    protected fun nextChar(): Char = source.nextChar()

    protected fun peekChar(): Char = source.peekChar()

    override fun toString(): String {
        return currentChar().toString()
    }
}