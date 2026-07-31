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

interface InstructionDecoder {
    fun decode(word: InstructionWord): Instruction
}

class DefaultInstructionDecoder : InstructionDecoder {

    override fun decode(word: InstructionWord): Instruction {
        if (word.isHalt) {
            return HaltInstruction()
        }

        return when (word.opcode) {
            Opcode.STORE -> StoreInstruction()
            Opcode.ADD -> AddInstruction()
            Opcode.SUB -> SubInstruction()
            Opcode.READ -> ReadInstruction()
            Opcode.WRITE -> WriteInstruction()
            Opcode.JUMP -> JumpInstruction()
            Opcode.READ_KEYBOARD -> ReadKeyboardInstruction()
            Opcode.SWITCH_MEMORY -> SwitchMemoryInstruction()
            Opcode.SKIP_EQUAL -> SkipEqualInstruction()
            Opcode.SKIP_NOT_EQUAL -> SkipNotEqualInstruction()
            Opcode.SET_A -> SetAddressInstruction()
            Opcode.SET_T -> SetTimerInstruction()
            Opcode.READ_T -> ReadTimerInstruction()
            Opcode.CONVERT_TO_BASE_10 -> ConvertToBase10Instruction()
            Opcode.CONVERT_BYTE_TO_ASCII -> ConvertByteToAsciiInstruction()
            Opcode.DRAW -> DrawInstruction()
        }
    }
}