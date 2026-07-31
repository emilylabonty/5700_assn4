package d5700.instruction

import d5700.core.ExecutionContext
import d5700.cpu.Registers
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals

class InstructionTest {

    @Test
    fun `execute follows template method order`() {
        val instruction = RecordingInstruction()
        val context = testContext()
        val word = InstructionWord.fromInt(0x00FF)

        instruction.execute(context, word)

        assertEquals(
            listOf("decode", "validate", "perform", "updateProgramCounter"),
            instruction.events
        )
    }

    @Test
    fun `default updateProgramCounter increments by two`() {
        val instruction = RecordingInstruction()
        val context = testContext()

        instruction.execute(context, InstructionWord.fromInt(0x00FF))

        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `subclass can override program counter update`() {
        val instruction = NoIncrementInstruction()
        val context = testContext()

        instruction.execute(context, InstructionWord.fromInt(0x00FF))

        assertEquals(0, context.programCounterValue())
    }

    private fun testContext(): ExecutionContext {
        return ExecutionContext(
            registers = Registers(),
            memoryBus = MemoryBus(),
            screen = ScreenBuffer(),
            keyboard = FakeKeyboardInput()
        )
    }

    private class RecordingInstruction : Instruction() {
        val events = mutableListOf<String>()

        override fun decode(word: InstructionWord) {
            events.add("decode")
        }

        override fun validate(context: ExecutionContext) {
            events.add("validate")
        }

        override fun perform(context: ExecutionContext) {
            events.add("perform")
        }

        override fun updateProgramCounter(context: ExecutionContext) {
            events.add("updateProgramCounter")
            super.updateProgramCounter(context)
        }
    }

    private class NoIncrementInstruction : Instruction() {
        override fun decode(word: InstructionWord) = Unit

        override fun perform(context: ExecutionContext) = Unit

        override fun updateProgramCounter(context: ExecutionContext) = Unit
    }

    private class FakeKeyboardInput : KeyboardInput {
        override fun readByte(): UByte = 0u
    }
}