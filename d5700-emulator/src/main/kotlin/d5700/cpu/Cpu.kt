package d5700.cpu

import d5700.core.ExecutionContext
import d5700.error.EmulatorException
import d5700.instruction.InstructionDecoder
import d5700.instruction.InstructionWord

class Cpu(
    private val context: ExecutionContext,
    private val decoder: InstructionDecoder,
    private val clock: CpuClock = CpuClock()
) {

    fun run(maxCycles: Int = DEFAULT_MAX_CYCLES): CpuRunResult {
        var cycles = 0

        while (cycles < maxCycles) {
            val result = step()
            cycles++

            if (result.stopReason != CpuStopReason.RUNNING) {
                return result.copy(cyclesExecuted = cycles)
            }
        }

        throw EmulatorException("CPU exceeded maximum cycle count of $maxCycles.")
    }

    fun step(): CpuRunResult {
        val word = fetchInstruction()

        if (word.isHalt) {
            return CpuRunResult(
                stopReason = CpuStopReason.HALTED,
                cyclesExecuted = 0,
                finalProgramCounter = context.registers.programCounter.value
            )
        }

        val instruction = decoder.decode(word)
        instruction.execute(context, word)

        clock.afterInstruction(context.registers.timer)

        return CpuRunResult(
            stopReason = CpuStopReason.RUNNING,
            cyclesExecuted = 1,
            finalProgramCounter = context.registers.programCounter.value
        )
    }

    private fun fetchInstruction(): InstructionWord {
        val address = context.registers.programCounter.value
        val highByte = context.memoryBus.readInstructionByte(address)
        val lowByte = context.memoryBus.readInstructionByte(address + 1)

        return InstructionWord(highByte, lowByte)
    }

    companion object {
        const val DEFAULT_MAX_CYCLES = 100_000
    }
}

data class CpuRunResult(
    val stopReason: CpuStopReason,
    val cyclesExecuted: Int,
    val finalProgramCounter: Int
)

enum class CpuStopReason {
    RUNNING,
    HALTED
}