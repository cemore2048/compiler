open class Token(val source: Source) {

    protected var type: TokenType? = null
    protected var text: String? = null
    protected var value: Any? = null
    protected var lineNum: Int? = source.lineNum
    protected var position: Int? = source.currentPosition

    init {
        extract()
    }

    @Throws
    fun extract() {
        text = currentChar().toString()
        value = null
        nextChar()
    }

    @Throws
    protected fun currentChar(): Char = source.currentChar()

    @Throws
    protected fun nextChar(): Char = source.nextChar()

    protected fun peekChar(): Char = source.peekChar()


}