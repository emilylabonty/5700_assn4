package d5700.error

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EmulatorExceptionTest {

    @Test
    fun `EmulatorException stores message`() {
        val exception = EmulatorException("Something went wrong.")

        assertEquals("Something went wrong.", exception.message)
    }

    @Test
    fun `InvalidJumpException is an EmulatorException`() {
        val exception = InvalidJumpException(0x1F1)

        assertIs<EmulatorException>(exception)
    }

    @Test
    fun `InvalidJumpException message includes uppercase hex address`() {
        val exception = InvalidJumpException(0x1F1)

        assertTrue(exception.message!!.contains("1F1"))
        assertTrue(exception.message!!.contains("divisible by 2"))
    }

    @Test
    fun `RomWriteException is an EmulatorException`() {
        val exception = RomWriteException(0x255)

        assertIs<EmulatorException>(exception)
    }

    @Test
    fun `RomWriteException message includes uppercase hex address`() {
        val exception = RomWriteException(0x255)

        assertTrue(exception.message!!.contains("255"))
        assertTrue(exception.message!!.contains("Cannot write to ROM"))
    }

    @Test
    fun `InvalidAsciiException is an EmulatorException`() {
        val exception = InvalidAsciiException(0x80)

        assertIs<EmulatorException>(exception)
    }

    @Test
    fun `InvalidAsciiException message includes valid ASCII range`() {
        val exception = InvalidAsciiException(0x80)

        assertTrue(exception.message!!.contains("80"))
        assertTrue(exception.message!!.contains("00 and 7F"))
    }

    @Test
    fun `InvalidHexDigitException is an EmulatorException`() {
        val exception = InvalidHexDigitException(0x10)

        assertIs<EmulatorException>(exception)
    }

    @Test
    fun `InvalidHexDigitException message includes valid hex digit range`() {
        val exception = InvalidHexDigitException(0x10)

        assertTrue(exception.message!!.contains("10"))
        assertTrue(exception.message!!.contains("0 and F"))
    }

    @Test
    fun `ProgramLoadException is an EmulatorException`() {
        val exception = ProgramLoadException("Program is too large.")

        assertIs<EmulatorException>(exception)
    }

    @Test
    fun `ProgramLoadException stores custom message`() {
        val exception = ProgramLoadException("Program is too large.")

        assertEquals("Program is too large.", exception.message)
    }
}