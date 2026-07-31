package d5700

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertTrue

class MainTest {

    @Test
    fun `runMain asks user for program path`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        runMain(input, PrintStream(outputBytes))

        val text = outputBytes.toString()

        assertTrue(text.contains("D5700 Emulator"))
        assertTrue(text.contains("Enter the path to the program to load:"))
    }

    @Test
    fun `runMain exits when program path is blank`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        runMain(input, PrintStream(outputBytes))

        assertTrue(outputBytes.toString().contains("No program path provided. Exiting."))
    }

    @Test
    fun `runMain starts emulator application flow`() {
        val input = ByteArrayInputStream("missing-program.bin\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        runMain(input, PrintStream(outputBytes))

        val text = outputBytes.toString()

        assertTrue(text.contains("Emulator error:"))
        assertTrue(text.contains("Program file does not exist"))
    }
}