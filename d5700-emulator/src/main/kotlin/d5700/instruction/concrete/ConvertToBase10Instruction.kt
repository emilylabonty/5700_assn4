package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class ConvertToBase10Instruction : Instruction() {

    private var registerX: Int = 0

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
    }

    override fun perform(context: ExecutionContext) {
        val value = context.readRegister(registerX).toInt()
        val hundreds = value / 100
        val tens = (value / 10) % 10
        val ones = value % 10

        context.writeMemoryAtAddress(hundreds.toUByte())
        context.writeMemoryAtAddress(tens.toUByte(), offset = 1)
        context.writeMemoryAtAddress(ones.toUByte(), offset = 2)
    }
}
