package d5700

import kotlin.test.Test
import kotlin.test.assertTrue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MainTest {

    @Test
    fun `runMain asks user for program path`() {
        val input = ByteArrayInputStream("programs/test.bin\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val output = PrintStream(outputBytes)

        runMain(input, output)

        val text = outputBytes.toString()

        assertTrue(text.contains("D5700 Emulator"))
        assertTrue(text.contains("Enter the path to the program to load:"))
    }

    @Test
    fun `runMain prints selected program path`() {
        val input = ByteArrayInputStream("programs/test.bin\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val output = PrintStream(outputBytes)

        runMain(input, output)

        val text = outputBytes.toString()

        assertTrue(text.contains("Loading program: programs/test.bin"))
    }

    @Test
    fun `runMain exits when program path is blank`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()
        val output = PrintStream(outputBytes)

        runMain(input, output)

        val text = outputBytes.toString()

        assertTrue(text.contains("No program path provided. Exiting."))
    }
}