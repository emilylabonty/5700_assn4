package d5700.app

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProgramPathPromptTest {

    @Test
    fun `ask prints prompt`() {
        val input = ByteArrayInputStream("program.bin\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val prompt = ProgramPathPrompt(input, PrintStream(outputBytes))

        prompt.ask()

        assertTrue(outputBytes.toString().contains("Enter the path to the program to load:"))
    }

    @Test
    fun `ask returns trimmed program path`() {
        val input = ByteArrayInputStream("  programs/test.bin  \n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val prompt = ProgramPathPrompt(input, PrintStream(outputBytes))

        val path = prompt.ask()

        assertEquals("programs/test.bin", path)
    }

    @Test
    fun `ask returns empty string for blank input`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val prompt = ProgramPathPrompt(input, PrintStream(outputBytes))

        val path = prompt.ask()

        assertEquals("", path)
    }
}