package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class HaltInstruction : Instruction() {

    override fun decode(word: InstructionWord) {
        // HALT has no operands.
    }

    override fun perform(context: ExecutionContext) {
        // HALT is handled by the CPU loop before normal execution.
    }

    override fun updateProgramCounter(context: ExecutionContext) {
        // HALT does not advance the program counter.
    }
}