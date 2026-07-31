package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.error.EmulatorException
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class StoreInstruction : Instruction() {

    private var registerIndex: Int = 0
    private var value: UByte = 0u

    override fun decode(word: InstructionWord) {
        registerIndex = word.registerX()
        value = word.byteLiteral
    }

    override fun validate(context: ExecutionContext) {
        if (registerIndex !in 0 until REGISTER_COUNT) {
            throw EmulatorException("STORE register index $registerIndex is outside valid range 0-7.")
        }
    }

    override fun perform(context: ExecutionContext) {
        context.registers.writeRegister(registerIndex, value)
    }

    companion object {
        private const val REGISTER_COUNT = 8
    }
}