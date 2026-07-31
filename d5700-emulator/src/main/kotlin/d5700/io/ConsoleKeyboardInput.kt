package d5700.io

import d5700.error.InvalidHexDigitException
import java.io.InputStream
import java.io.PrintStream

class ConsoleKeyboardInput(
    private val input: InputStream = System.`in`,
    private val output: PrintStream = System.out
) : KeyboardInput {

    override fun readByte(): UByte {
        output.print("Input hex byte 00-FF: ")

        val text = input.bufferedReader()
            .readLine()
            ?.trim()
            .orEmpty()

        if (text.isEmpty()) {
            return 0u
        }

        val limitedText = text.take(MAX_INPUT_DIGITS)

        if (!limitedText.all { it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F' }) {
            throw InvalidHexDigitException(-1)
        }

        return limitedText.toInt(radix = 16).toUByte()
    }

    companion object {
        private const val MAX_INPUT_DIGITS = 2
    }
}