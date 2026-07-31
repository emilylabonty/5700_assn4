package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class AddInstruction : Instruction() {

    private var registerX: Int = 0
    private var registerY: Int = 0
    private var registerZ: Int = 0

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
        registerY = word.registerY()
        registerZ = word.registerZ()
    }

    override fun perform(context: ExecutionContext) {
        val left = context.readRegister(registerX).toInt()
        val right = context.readRegister(registerY).toInt()
        val result = ((left + right) and 0xFF).toUByte()

        context.writeRegister(registerZ, result)
    }
}
