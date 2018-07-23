package frontend

open class Token(val source: Source) {

    protected var type: TokenType? = null
    private var text: String? = null
    private var value: Any? = null
    var lineNum: Int? = source.lineNum
    protected var position: Int? = source.currentPosition

    init {
        extract()
    }

    @Throws
    private fun extract() {
        text = currentChar().toString()
        value = null
        nextChar()
    }

    @Throws
    private fun currentChar(): Char = source.currentChar

    @Throws
    private fun nextChar(): Char = source.nextChar()

    protected fun peekChar(): Char = source.peekChar()
}