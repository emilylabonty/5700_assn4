package d5700.instruction

import d5700.core.ExecutionContext

abstract class Instruction {

    fun execute(context: ExecutionContext, word: InstructionWord) {
        decode(word)
        validate(context)
        perform(context)
        updateProgramCounter(context)
    }

    protected abstract fun decode(word: InstructionWord)

    protected open fun validate(context: ExecutionContext) {
        // Most instructions do not need extra validation.
    }

    protected abstract fun perform(context: ExecutionContext)

    protected open fun updateProgramCounter(context: ExecutionContext) {
        context.incrementProgramCounter()
    }
}