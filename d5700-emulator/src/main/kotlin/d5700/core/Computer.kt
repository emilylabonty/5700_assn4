package d5700.core

import d5700.cpu.Cpu
import d5700.cpu.CpuClock
import d5700.cpu.Registers
import d5700.instruction.DefaultInstructionDecoder
import d5700.instruction.InstructionDecoder
import d5700.io.Display
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import d5700.memory.ProgramLoader

class Computer(
    private val registers: Registers = Registers(),
    private val memoryBus: MemoryBus = MemoryBus(),
    private val screen: ScreenBuffer = ScreenBuffer(),
    private val keyboard: KeyboardInput,
    private val display: Display,
    private val decoder: InstructionDecoder = DefaultInstructionDecoder(),
    private val clock: CpuClock = CpuClock()
) {
    private var loadedByteCount: Int = 0

    fun loadProgram(path: String): Int {
        val loader = ProgramLoader(memoryBus)
        loadedByteCount = loader.load(path).size
        registers.reset()
        screen.clear()
        return loadedByteCount
    }

    fun run(maxCycles: Int = Cpu.DEFAULT_MAX_CYCLES): EmulatorResult {
        val context = ExecutionContext(
            registers = registers,
            memoryBus = memoryBus,
            screen = screen,
            keyboard = keyboard
        )

        val cpu = Cpu(
            context = context,
            decoder = decoder,
            clock = clock
        )

        val cpuResult = cpu.run(maxCycles)
        display.render(screen)

        return EmulatorResult(
            loadedByteCount = loadedByteCount,
            stopReason = cpuResult.stopReason,
            cyclesExecuted = cpuResult.cyclesExecuted,
            finalProgramCounter = cpuResult.finalProgramCounter,
            screenText = screen.renderText()
        )
    }
}