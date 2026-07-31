package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class ReadTimerInstruction : Instruction() {

    private var registerX: Int = 0

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
    }

    override fun perform(context: ExecutionContext) {
        context.registers.writeRegister(registerX, context.registers.timer.read())
    }
}