package d5700.app

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.io.path.createTempFile
import kotlin.io.path.writeBytes
import kotlin.test.Test
import kotlin.test.assertTrue

class EmulatorAppTest {

    @Test
    fun `start prints title`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        createApp(input, outputBytes).start()

        assertTrue(outputBytes.toString().contains("D5700 Emulator"))
    }

    @Test
    fun `start exits when path is blank`() {
        val input = ByteArrayInputStream("\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        createApp(input, outputBytes).start()

        val output = outputBytes.toString()

        assertTrue(output.contains("No program path provided. Exiting."))
    }

    @Test
    fun `start reports emulator error for missing program`() {
        val input = ByteArrayInputStream("missing-program.bin\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        createApp(input, outputBytes).start()

        val output = outputBytes.toString()

        assertTrue(output.contains("Emulator error:"))
        assertTrue(output.contains("Program file does not exist"))
    }

    @Test
    fun `start loads and runs halt program`() {
        val program = createTempFile(suffix = ".bin")
        program.writeBytes(byteArrayOf(0x00, 0x00))

        val input = ByteArrayInputStream("${program}\n".toByteArray())
        val outputBytes = ByteArrayOutputStream()

        createApp(input, outputBytes).start()

        val output = outputBytes.toString()

        assertTrue(output.contains("Loaded 2 bytes into ROM."))
        assertTrue(output.contains("Program stopped: HALTED"))
        assertTrue(output.contains("Cycles executed: 1"))
        assertTrue(output.contains("Final program counter: 0"))
    }

    private fun createApp(
        input: ByteArrayInputStream,
        outputBytes: ByteArrayOutputStream
    ): EmulatorApp {
        val output = PrintStream(outputBytes)

        return EmulatorApp(
            programPathPrompt = ProgramPathPrompt(input, output),
            computerFactory = ConsoleComputerFactory(input, output),
            output = output
        )
    }
}