package d5700

import d5700.app.EmulatorBootstrap
import java.io.InputStream
import java.io.PrintStream

fun main() {
    runMain(System.`in`, System.out)
}

fun runMain(input: InputStream, output: PrintStream) {
    EmulatorBootstrap(input, output).start()
}