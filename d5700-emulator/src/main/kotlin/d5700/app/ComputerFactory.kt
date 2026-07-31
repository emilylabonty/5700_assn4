package d5700.app

import d5700.core.Computer
import d5700.core.ExecutionContext
import d5700.cpu.Cpu
import d5700.cpu.CpuClock
import d5700.cpu.Registers
import d5700.instruction.DefaultInstructionDecoder
import d5700.instruction.InstructionDecoder
import d5700.io.ConsoleDisplay
import d5700.io.ConsoleKeyboardInput
import d5700.io.Display
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import d5700.memory.ProgramLoader
import java.io.InputStream
import java.io.PrintStream

interface ComputerFactory {
    fun create(): Computer
}

class ConsoleComputerFactory(
    private val input: InputStream = System.`in`,
    private val output: PrintStream = System.out,
    private val registers: Registers = Registers(),
    private val memoryBus: MemoryBus = MemoryBus(),
    private val screen: ScreenBuffer = ScreenBuffer(),
    private val keyboard: KeyboardInput = ConsoleKeyboardInput(input, output),
    private val display: Display = ConsoleDisplay(output),
    private val decoder: InstructionDecoder = DefaultInstructionDecoder(),
    private val clock: CpuClock = CpuClock()
) : ComputerFactory {

    override fun create(): Computer {
        val context = ExecutionContext(
            registers = registers,
            memoryBus = memoryBus,
            screen = screen,
            keyboard = keyboard
        )

        return Computer(
            context = context,
            screen = screen,
            programLoader = ProgramLoader(memoryBus),
            cpu = Cpu(context, decoder, clock),
            display = display
        )
    }
}
