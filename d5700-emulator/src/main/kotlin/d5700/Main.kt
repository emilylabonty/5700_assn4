package d5700

import java.io.InputStream
import java.io.PrintStream

fun main() {
    runMain(System.`in`, System.out)
}

fun runMain(input: InputStream, output: PrintStream) {
    output.println("D5700 Emulator")
    output.print("Enter the path to the program to load: ")

    val programPath = input.bufferedReader().readLine()?.trim().orEmpty()

    if (programPath.isBlank()) {
        output.println("No program path provided. Exiting.")
        return
    }

    output.println("Loading program: $programPath")

    output.println("Program loading is not implemented yet.")
}