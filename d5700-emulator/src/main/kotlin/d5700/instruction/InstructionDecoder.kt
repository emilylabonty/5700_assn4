package d5700.instruction

interface InstructionDecoder {
    fun decode(word: InstructionWord): Instruction
}

class DefaultInstructionDecoder(
    private val instructionFactory: InstructionFactory = DefaultInstructionFactory()
) : InstructionDecoder {

    override fun decode(word: InstructionWord): Instruction {
        return instructionFactory.create(word)
    }
}