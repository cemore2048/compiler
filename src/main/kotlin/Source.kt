import java.io.BufferedReader
import java.io.EOFException
import java.io.IOException

class Source(private val reader: BufferedReader) {
    private var line: String? = null
    private var lineNum: Int = 0
    private var currentPosition: Int = -2

    @Throws
    fun currentChar(): Char {
        val currentLine = line!!
        return if (currentPosition == -2) {
            readLine()
            nextChar()
        } else if (line == null ){
            throw EOFException()
        } else if ((currentPosition == -1) || (currentPosition == line!!.length)) {
            throw EOFException()
        } else if (currentPosition > line!!.length) {
            readLine()
            nextChar()
        } else {
            currentLine[currentPosition]
        }
    }

    @Throws
    fun nextChar(): Char {
        currentPosition++
        return currentChar()
    }

    @Throws
    fun peekChar(): Char {
        val currentLine = line!!
        currentChar()
        if (line == null) {
            throw EOFException()
        }

        val nextPos = currentPosition + 1
        return if (nextPos < line!!.length) currentLine[nextPos] else throw EOFException()
    }

    @Throws
    fun readLine() {
        line = reader!!.readLine()
        currentPosition--
        if (line != null) {
            lineNum++
        }
    }

    @Throws
    fun close() {
        if (reader != null) {
            try {
                reader!!.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
                throw ex
            }
        }
    }
}
