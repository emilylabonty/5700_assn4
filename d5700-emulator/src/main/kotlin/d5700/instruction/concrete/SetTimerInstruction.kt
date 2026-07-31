package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class SetTimerInstruction : Instruction() {

    private var value: UByte = 0u

    override fun decode(word: InstructionWord) {
        value = (((word.nibble1 shl 4) or word.nibble2) and 0xFF).toUByte()
    }

    override fun perform(context: ExecutionContext) {
        context.registers.timer.set(value)
    }
}