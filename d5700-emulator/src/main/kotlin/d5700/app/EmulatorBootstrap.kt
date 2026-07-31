package d5700.app

import java.io.InputStream
import java.io.PrintStream

class EmulatorBootstrap(
    private val input: InputStream = System.`in`,
    private val output: PrintStream = System.out
) {
    fun start() {
        createApp().start()
    }

    fun createApp(): EmulatorApp {
        return EmulatorApp(
            programPathPrompt = ProgramPathPrompt(input, output),
            computerFactory = ConsoleComputerFactory(input, output),
            output = output
        )
    }
}