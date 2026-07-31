package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.error.InvalidHexDigitException
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class ConvertByteToAsciiInstruction : Instruction() {

    private var registerX: Int = 0
    private var registerY: Int = 0

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
        registerY = word.registerY()
    }

    override fun perform(context: ExecutionContext) {
        val value = context.readRegister(registerX).toInt()

        if (value > 0xF) {
            throw InvalidHexDigitException(value)
        }

        val ascii = if (value <= 9) {
            '0'.code + value
        } else {
            'A'.code + (value - 10)
        }

        context.writeRegister(registerY, ascii.toUByte())
    }
}
