package d5700.instruction

import d5700.instruction.concrete.AddInstruction
import d5700.instruction.concrete.ConvertByteToAsciiInstruction
import d5700.instruction.concrete.ConvertToBase10Instruction
import d5700.instruction.concrete.DrawInstruction
import d5700.instruction.concrete.HaltInstruction
import d5700.instruction.concrete.JumpInstruction
import d5700.instruction.concrete.ReadInstruction
import d5700.instruction.concrete.ReadKeyboardInstruction
import d5700.instruction.concrete.ReadTimerInstruction
import d5700.instruction.concrete.SetAddressInstruction
import d5700.instruction.concrete.SetTimerInstruction
import d5700.instruction.concrete.SkipEqualInstruction
import d5700.instruction.concrete.SkipNotEqualInstruction
import d5700.instruction.concrete.StoreInstruction
import d5700.instruction.concrete.SubInstruction
import d5700.instruction.concrete.SwitchMemoryInstruction
import d5700.instruction.concrete.WriteInstruction

interface InstructionFactory {
    fun create(word: InstructionWord): Instruction
}

class InstructionRegistration(
    val matches: (InstructionWord) -> Boolean,
    val create: () -> Instruction
)

class DefaultInstructionFactory(
    private val patternCreators: List<InstructionRegistration> = defaultPatternCreators(),
    private val opcodeCreators: Map<Opcode, () -> Instruction> = defaultOpcodeCreators()
) : InstructionFactory {

    override fun create(word: InstructionWord): Instruction {
        val patternMatch = patternCreators.firstOrNull { it.matches(word) }

        if (patternMatch != null) {
            return patternMatch.create()
        }

        return opcodeCreators[word.opcode]?.invoke()
            ?: throw IllegalArgumentException("No instruction registered for opcode ${word.opcode}.")
    }

    companion object {
        private fun defaultPatternCreators(): List<InstructionRegistration> =
            listOf(
                InstructionRegistration(
                    matches = { word -> word.isHalt },
                    create = { HaltInstruction() }
                )
            )

        private fun defaultOpcodeCreators(): Map<Opcode, () -> Instruction> =
            mapOf(
                Opcode.STORE to { StoreInstruction() },
                Opcode.ADD to { AddInstruction() },
                Opcode.SUB to { SubInstruction() },
                Opcode.READ to { ReadInstruction() },
                Opcode.WRITE to { WriteInstruction() },
                Opcode.JUMP to { JumpInstruction() },
                Opcode.READ_KEYBOARD to { ReadKeyboardInstruction() },
                Opcode.SWITCH_MEMORY to { SwitchMemoryInstruction() },
                Opcode.SKIP_EQUAL to { SkipEqualInstruction() },
                Opcode.SKIP_NOT_EQUAL to { SkipNotEqualInstruction() },
                Opcode.SET_A to { SetAddressInstruction() },
                Opcode.SET_T to { SetTimerInstruction() },
                Opcode.READ_T to { ReadTimerInstruction() },
                Opcode.CONVERT_TO_BASE_10 to { ConvertToBase10Instruction() },
                Opcode.CONVERT_BYTE_TO_ASCII to { ConvertByteToAsciiInstruction() },
                Opcode.DRAW to { DrawInstruction() }
            )
    }
}