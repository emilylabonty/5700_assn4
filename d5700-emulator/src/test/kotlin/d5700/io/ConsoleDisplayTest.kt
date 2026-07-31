package d5700.io

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConsoleDisplayTest {

    @Test
    fun `render prints top and bottom borders`() {
        val outputBytes = ByteArrayOutputStream()
        val display = ConsoleDisplay(PrintStream(outputBytes))
        val screenBuffer = ScreenBuffer()

        display.render(screenBuffer)

        val output = outputBytes.toString()

        assertTrue(output.startsWith("--------"))
        assertTrue(output.trimEnd().endsWith("--------"))
    }

    @Test
    fun `render prints screen buffer contents`() {
        val outputBytes = ByteArrayOutputStream()
        val display = ConsoleDisplay(PrintStream(outputBytes))
        val screenBuffer = ScreenBuffer()

        screenBuffer.draw(row = 0, column = 0, asciiValue = 'A'.code.toUByte())
        screenBuffer.draw(row = 7, column = 7, asciiValue = 'Z'.code.toUByte())

        display.render(screenBuffer)

        val output = outputBytes.toString()

        assertTrue(output.contains("A       "))
        assertTrue(output.contains("       Z"))
    }

    @Test
    fun `render prints ten lines total`() {
        val outputBytes = ByteArrayOutputStream()
        val display = ConsoleDisplay(PrintStream(outputBytes))
        val screenBuffer = ScreenBuffer()

        display.render(screenBuffer)

        val lines = outputBytes.toString().trimEnd().lines()

        assertEquals(10, lines.size)
        assertEquals("--------", lines.first())
        assertEquals("--------", lines.last())
    }

    @Test
    fun `render preserves eight character screen rows`() {
        val outputBytes = ByteArrayOutputStream()
        val display = ConsoleDisplay(PrintStream(outputBytes))
        val screenBuffer = ScreenBuffer()

        screenBuffer.draw(row = 3, column = 4, asciiValue = 'X'.code.toUByte())

        display.render(screenBuffer)

        val lines = outputBytes.toString().trimEnd().lines()
        val screenRows = lines.drop(1).dropLast(1)

        assertEquals(8, screenRows.size)
        assertTrue(screenRows.all { it.length == 8 })
        assertEquals("    X   ", screenRows[3])
    }
}