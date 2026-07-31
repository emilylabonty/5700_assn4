package d5700.io

import d5700.error.EmulatorException
import d5700.error.InvalidAsciiException

class ScreenBuffer(
    val rows: Int = DEFAULT_ROWS,
    val columns: Int = DEFAULT_COLUMNS
) {
    private val cells = UByteArray(rows * columns) { SPACE }

    fun draw(row: Int, column: Int, asciiValue: UByte) {
        validatePosition(row, column)
        validateAscii(asciiValue)

        cells[index(row, column)] = asciiValue
    }

    fun read(row: Int, column: Int): UByte {
        validatePosition(row, column)
        return cells[index(row, column)]
    }

    fun clear() {
        cells.fill(SPACE)
    }

    fun renderText(): String {
        return buildString {
            for (row in 0 until rows) {
                for (column in 0 until columns) {
                    append(read(row, column).toInt().toChar())
                }

                if (row != rows - 1) {
                    appendLine()
                }
            }
        }
    }

    private fun index(row: Int, column: Int): Int {
        return row * columns + column
    }

    private fun validatePosition(row: Int, column: Int) {
        if (row !in 0 until rows) {
            throw EmulatorException("Screen row $row is outside valid range 0-${rows - 1}.")
        }

        if (column !in 0 until columns) {
            throw EmulatorException("Screen column $column is outside valid range 0-${columns - 1}.")
        }
    }

    private fun validateAscii(value: UByte) {
        if (value.toInt() > MAX_ASCII) {
            throw InvalidAsciiException(value.toInt())
        }
    }

    companion object {
        const val DEFAULT_ROWS = 8
        const val DEFAULT_COLUMNS = 8
        const val MAX_ASCII = 0x7F
        val SPACE: UByte = 0x20u
    }
}