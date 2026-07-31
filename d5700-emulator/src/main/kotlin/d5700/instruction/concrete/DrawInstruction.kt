package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.error.InvalidAsciiException
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class DrawInstruction : Instruction() {

    private var registerX: Int = 0
    private var registerY: Int = 0
    private var registerZ: Int = 0

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
        registerY = word.registerY()
        registerZ = word.registerZ()
    }

    override fun perform(context: ExecutionContext) {
        val asciiValue = context.registers.readRegister(registerX)
        val row = context.registers.readRegister(registerY).toInt()
        val column = context.registers.readRegister(registerZ).toInt()

        if (asciiValue.toInt() > 0x7F) {
            throw InvalidAsciiException(asciiValue.toInt())
        }

        context.screen.draw(row, column, asciiValue)
    }
}