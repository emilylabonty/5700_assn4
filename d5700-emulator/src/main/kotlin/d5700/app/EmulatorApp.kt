package d5700.app

import d5700.core.Computer
import d5700.error.EmulatorException
import d5700.io.ConsoleDisplay
import d5700.io.ConsoleKeyboardInput
import java.io.InputStream
import java.io.PrintStream

class EmulatorApp(
    private val input: InputStream = System.`in`,
    private val output: PrintStream = System.out
) {
    fun start() {
        output.println("D5700 Emulator")

        val programPath = ProgramPathPrompt(input, output).ask()

        if (programPath.isBlank()) {
            output.println("No program path provided. Exiting.")
            return
        }

        try {
            val computer = Computer(
                keyboard = ConsoleKeyboardInput(input, output),
                display = ConsoleDisplay(output)
            )

            val byteCount = computer.loadProgram(programPath)
            output.println("Loaded $byteCount bytes into ROM.")

            val result = computer.run()
            output.println("Program stopped: ${result.stopReason}")
            output.println("Cycles executed: ${result.cyclesExecuted}")
            output.println("Final program counter: ${result.finalProgramCounter.toString(16).uppercase()}")
        } catch (exception: EmulatorException) {
            output.println("Emulator error: ${exception.message}")
        }
    }
}