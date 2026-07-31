package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class SkipNotEqualInstruction : Instruction() {

    private var registerX: Int = 0
    private var registerY: Int = 0
    private var shouldSkip: Boolean = false

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
        registerY = word.registerY()
    }

    override fun perform(context: ExecutionContext) {
        shouldSkip = context.readRegister(registerX) != context.readRegister(registerY)
    }

    override fun updateProgramCounter(context: ExecutionContext) {
        if (shouldSkip) {
            context.skipNextInstruction()
        } else {
            context.incrementProgramCounter()
        }
    }
}
