package d5700.core

import d5700.cpu.Cpu
import d5700.io.Display
import d5700.io.ScreenBuffer
import d5700.memory.ProgramLoader

class Computer(
    private val context: ExecutionContext,
    private val screen: ScreenBuffer,
    private val programLoader: ProgramLoader,
    private val cpu: Cpu,
    private val display: Display
) {
    private var loadedByteCount: Int = 0

    fun loadProgram(path: String): Int {
        loadedByteCount = programLoader.load(path).size
        context.resetState()
        return loadedByteCount
    }

    fun run(maxCycles: Int = Cpu.DEFAULT_MAX_CYCLES): EmulatorResult {
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
