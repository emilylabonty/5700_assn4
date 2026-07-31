package d5700.cpu

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionDecoder
import d5700.instruction.InstructionWord
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CpuTest {

    @Test
    fun `step halts when instruction word is 0000`() {
        val bus = MemoryBus()
        bus.loadRom(byteArrayOf(0x00, 0x00))

        val cpu = Cpu(
            context = testContext(bus),
            decoder = FakeDecoder(FakeIncrementInstruction())
        )

        val result = cpu.step()

        assertEquals(CpuStopReason.HALTED, result.stopReason)
        assertEquals(0, result.finalProgramCounter)
    }

    @Test
    fun `step fetches instruction from ROM and executes decoded instruction`() {
        val bus = MemoryBus()
        bus.loadRom(byteArrayOf(0x00, 0xFF.toByte(), 0x00, 0x00))

        val registers = Registers()
        val instruction = FakeIncrementInstruction()
        val cpu = Cpu(
            context = testContext(bus, registers),
            decoder = FakeDecoder(instruction)
        )

        val result = cpu.step()

        assertEquals(CpuStopReason.RUNNING, result.stopReason)
        assertEquals(1, instruction.executionCount)
        assertEquals(2, registers.programCounter.value)
        assertEquals(2, result.finalProgramCounter)
    }

    @Test
    fun `run stops when halt instruction is encountered`() {
        val bus = MemoryBus()
        bus.loadRom(byteArrayOf(0x00, 0xFF.toByte(), 0x00, 0x00))

        val registers = Registers()
        val cpu = Cpu(
            context = testContext(bus, registers),
            decoder = FakeDecoder(FakeIncrementInstruction())
        )

        val result = cpu.run()

        assertEquals(CpuStopReason.HALTED, result.stopReason)
        assertEquals(2, result.cyclesExecuted)
        assertEquals(2, result.finalProgramCounter)
    }

    @Test
    fun `run throws when maximum cycle count is exceeded`() {
        val bus = MemoryBus()
        bus.loadRom(byteArrayOf(0x00, 0xFF.toByte(), 0x00, 0xFF.toByte()))

        val cpu = Cpu(
            context = testContext(bus),
            decoder = FakeDecoder(FakeIncrementInstruction())
        )

        assertFailsWith<RuntimeException> {
            cpu.run(maxCycles = 1)
        }
    }

    private fun testContext(
        memoryBus: MemoryBus = MemoryBus(),
        registers: Registers = Registers()
    ): ExecutionContext {
        return ExecutionContext(
            registers = registers,
            memoryBus = memoryBus,
            screen = ScreenBuffer(),
            keyboard = FakeKeyboardInput()
        )
    }

    private class FakeKeyboardInput : KeyboardInput {
        override fun readByte(): UByte = 0u.toUByte()
    }

    private class FakeDecoder(
        private val instruction: Instruction
    ) : InstructionDecoder {
        override fun decode(word: InstructionWord): Instruction {
            return instruction
        }
    }

    private class FakeIncrementInstruction : Instruction() {
        var executionCount = 0
            private set

        override fun decode(word: InstructionWord) {
            // Nothing to decode for this test instruction.
        }

        override fun perform(context: ExecutionContext) {
            executionCount++
        }
    }
}