package d5700.instruction

import d5700.instruction.concrete.HaltInstruction
import d5700.instruction.concrete.StoreInstruction
import kotlin.test.Test
import kotlin.test.assertIs

class InstructionFactoryTest {

    private val factory = DefaultInstructionFactory()

    @Test
    fun `creates pattern matched halt instruction`() {
        assertIs<HaltInstruction>(factory.create(InstructionWord.fromInt(0x0000)))
    }

    @Test
    fun `creates opcode matched instruction`() {
        assertIs<StoreInstruction>(factory.create(InstructionWord.fromInt(0x00FF)))
    }

    @Test
    fun `decoder delegates instruction creation to factory`() {
        val decoder = DefaultInstructionDecoder(
            instructionFactory = object : InstructionFactory {
                override fun create(word: InstructionWord): Instruction {
                    return StoreInstruction()
                }
            }
        )

        assertIs<StoreInstruction>(decoder.decode(InstructionWord.fromInt(0x1010)))
    }
}