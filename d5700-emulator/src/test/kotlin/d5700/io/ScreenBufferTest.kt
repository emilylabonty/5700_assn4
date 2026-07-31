package d5700.io

import d5700.error.EmulatorException
import d5700.error.InvalidAsciiException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ScreenBufferTest {

    @Test
    fun `screen buffer has default 8 by 8 size`() {
        val screen = ScreenBuffer()

        assertEquals(8, screen.rows)
        assertEquals(8, screen.columns)
    }

    @Test
    fun `screen buffer starts filled with spaces`() {
        val screen = ScreenBuffer()

        assertEquals(' '.code.toUByte(), screen.read(0, 0))
        assertEquals(' '.code.toUByte(), screen.read(7, 7))
    }

    @Test
    fun `draw stores ascii byte at row and column`() {
        val screen = ScreenBuffer()

        screen.draw(row = 2, column = 3, asciiValue = 'A'.code.toUByte())

        assertEquals('A'.code.toUByte(), screen.read(2, 3))
    }

    @Test
    fun `draw only changes selected cell`() {
        val screen = ScreenBuffer()

        screen.draw(row = 2, column = 3, asciiValue = 'A'.code.toUByte())

        assertEquals(' '.code.toUByte(), screen.read(2, 2))
        assertEquals('A'.code.toUByte(), screen.read(2, 3))
        assertEquals(' '.code.toUByte(), screen.read(2, 4))
    }

    @Test
    fun `clear resets all cells to spaces`() {
        val screen = ScreenBuffer()

        screen.draw(row = 0, column = 0, asciiValue = 'A'.code.toUByte())
        screen.draw(row = 7, column = 7, asciiValue = 'Z'.code.toUByte())

        screen.clear()

        assertEquals(' '.code.toUByte(), screen.read(0, 0))
        assertEquals(' '.code.toUByte(), screen.read(7, 7))
    }

    @Test
    fun `renderText returns eight rows`() {
        val screen = ScreenBuffer()

        val lines = screen.renderText().lines()

        assertEquals(8, lines.size)
    }

    @Test
    fun `renderText returns eight columns per row`() {
        val screen = ScreenBuffer()

        val lines = screen.renderText().lines()

        assertEquals(8, lines[0].length)
        assertEquals(8, lines[7].length)
    }

    @Test
    fun `renderText includes drawn characters`() {
        val screen = ScreenBuffer()

        screen.draw(row = 0, column = 0, asciiValue = 'A'.code.toUByte())
        screen.draw(row = 3, column = 4, asciiValue = 'X'.code.toUByte())
        screen.draw(row = 7, column = 7, asciiValue = 'Z'.code.toUByte())

        val lines = screen.renderText().lines()

        assertEquals("A       ", lines[0])
        assertEquals("    X   ", lines[3])
        assertEquals("       Z", lines[7])
    }

    @Test
    fun `draw rejects negative row`() {
        val screen = ScreenBuffer()

        assertFailsWith<EmulatorException> {
            screen.draw(row = -1, column = 0, asciiValue = 'A'.code.toUByte())
        }
    }

    @Test
    fun `draw rejects row equal to height`() {
        val screen = ScreenBuffer()

        assertFailsWith<EmulatorException> {
            screen.draw(row = 8, column = 0, asciiValue = 'A'.code.toUByte())
        }
    }

    @Test
    fun `draw rejects negative column`() {
        val screen = ScreenBuffer()

        assertFailsWith<EmulatorException> {
            screen.draw(row = 0, column = -1, asciiValue = 'A'.code.toUByte())
        }
    }

    @Test
    fun `draw rejects column equal to width`() {
        val screen = ScreenBuffer()

        assertFailsWith<EmulatorException> {
            screen.draw(row = 0, column = 8, asciiValue = 'A'.code.toUByte())
        }
    }

    @Test
    fun `read rejects invalid position`() {
        val screen = ScreenBuffer()

        assertFailsWith<EmulatorException> {
            screen.read(row = 8, column = 8)
        }
    }

    @Test
    fun `draw accepts highest valid ascii value`() {
        val screen = ScreenBuffer()

        screen.draw(row = 0, column = 0, asciiValue = 0x7Fu.toUByte())

        assertEquals(0x7Fu.toUByte(), screen.read(0, 0))
    }

    @Test
    fun `draw rejects ascii value above 7F`() {
        val screen = ScreenBuffer()

        assertFailsWith<InvalidAsciiException> {
            screen.draw(row = 0, column = 0, asciiValue = 0x80u.toUByte())
        }
    }

    @Test
    fun `screen buffer supports custom dimensions`() {
        val screen = ScreenBuffer(rows = 2, columns = 3)

        screen.draw(row = 1, column = 2, asciiValue = 'Q'.code.toUByte())

        assertEquals("   \n  Q", screen.renderText())
    }
}