package d5700.app

import java.io.InputStream
import java.io.PrintStream

class ProgramPathPrompt(
    private val input: InputStream = System.`in`,
    private val output: PrintStream = System.out
) {
    fun ask(): String {
        output.print("Enter the path to the program to load: ")
        return input.bufferedReader().readLine()?.trim().orEmpty()
    }
}