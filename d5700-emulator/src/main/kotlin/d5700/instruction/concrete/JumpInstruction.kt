package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class JumpInstruction : Instruction() {

    private var address: Int = 0

    override fun decode(word: InstructionWord) {
        address = word.addressLiteral.toInt()
    }

    override fun perform(context: ExecutionContext) {
        context.jumpTo(address)
    }

    override fun updateProgramCounter(context: ExecutionContext) {
        // JUMP sets the program counter directly.
    }
}
