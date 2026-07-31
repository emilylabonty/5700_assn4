package d5700.io

import java.io.PrintStream

class ConsoleDisplay(
    private val output: PrintStream = System.out
) : Display {

    override fun render(screenBuffer: ScreenBuffer) {
        output.println(BORDER)
        output.println(screenBuffer.renderText())
        output.println(BORDER)
    }

    companion object {
        private const val BORDER = "--------"
    }
}