package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class SwitchMemoryInstruction : Instruction() {

    override fun decode(word: InstructionWord) {
        // SWITCH_MEMORY has no operands.
    }

    override fun perform(context: ExecutionContext) {
        context.toggleMemoryMode()
    }
}
