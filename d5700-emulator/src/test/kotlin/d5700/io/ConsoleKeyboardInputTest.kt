package d5700.io

import d5700.error.InvalidHexDigitException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ConsoleKeyboardInputTest {

    @Test
    fun `readByte prompts for input`() {
        val input = ByteArrayInputStream("A\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(outputBytes))

        keyboard.readByte()

        assertTrue(outputBytes.toString().contains("Input hex byte 00-FF:"))
    }

    @Test
    fun `readByte returns zero for empty input`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        val value = keyboard.readByte()

        assertEquals(0u.toUByte(), value)
    }

    @Test
    fun `readByte parses one uppercase hex digit`() {
        val input = ByteArrayInputStream("F\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        val value = keyboard.readByte()

        assertEquals(0x0Fu.toUByte(), value)
    }

    @Test
    fun `readByte parses one lowercase hex digit`() {
        val input = ByteArrayInputStream("a\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        val value = keyboard.readByte()

        assertEquals(0x0Au.toUByte(), value)
    }

    @Test
    fun `readByte parses two hex digits as byte`() {
        val input = ByteArrayInputStream("7F\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        val value = keyboard.readByte()

        assertEquals(0x7Fu.toUByte(), value)
    }

    @Test
    fun `readByte trims whitespace`() {
        val input = ByteArrayInputStream("  2A  \n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        val value = keyboard.readByte()

        assertEquals(0x2Au.toUByte(), value)
    }

    @Test
    fun `readByte ignores characters after first two hex digits`() {
        val input = ByteArrayInputStream("ABCDEF\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        val value = keyboard.readByte()

        assertEquals(0xABu.toUByte(), value)
    }

    @Test
    fun `readByte rejects non hex input`() {
        val input = ByteArrayInputStream("G1\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        assertFailsWith<InvalidHexDigitException> {
            keyboard.readByte()
        }
    }

    @Test
    fun `readByte rejects symbols`() {
        val input = ByteArrayInputStream("@@\n".toByteArray())
        val output = ByteArrayOutputStream()
        val keyboard = ConsoleKeyboardInput(input, PrintStream(output))

        assertFailsWith<InvalidHexDigitException> {
            keyboard.readByte()
        }
    }
}