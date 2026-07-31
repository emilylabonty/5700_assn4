package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class StoreInstruction : Instruction() {

    private var registerIndex: Int = 0
    private var value: UByte = 0u

    override fun decode(word: InstructionWord) {
        registerIndex = word.registerX()
        value = word.byteLiteral
    }

    override fun perform(context: ExecutionContext) {
        context.writeRegister(registerIndex, value)
    }
}