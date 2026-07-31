package d5700.app

import d5700.error.EmulatorException
import java.io.PrintStream

class EmulatorApp(
    private val programPathPrompt: ProgramPathPrompt,
    private val computerFactory: ComputerFactory,
    private val output: PrintStream = System.out
) {
    fun start() {
        output.println("D5700 Emulator")

        val programPath = programPathPrompt.ask()

        if (programPath.isBlank()) {
            output.println("No program path provided. Exiting.")
            return
        }

        try {
            val computer = computerFactory.create()

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