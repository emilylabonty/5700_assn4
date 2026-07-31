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
import kotlin.test.Test
import kotlin.test.assertIs

class InstructionDecoderTest {

    private val decoder = DefaultInstructionDecoder()

    @Test
    fun `decodes halt instruction`() {
        assertIs<HaltInstruction>(decoder.decode(InstructionWord.fromInt(0x0000)))
    }

    @Test
    fun `decodes store instruction`() {
        assertIs<StoreInstruction>(decoder.decode(InstructionWord.fromInt(0x00FF)))
    }

    @Test
    fun `decodes add instruction`() {
        assertIs<AddInstruction>(decoder.decode(InstructionWord.fromInt(0x1010)))
    }

    @Test
    fun `decodes sub instruction`() {
        assertIs<SubInstruction>(decoder.decode(InstructionWord.fromInt(0x2010)))
    }

    @Test
    fun `decodes read instruction`() {
        assertIs<ReadInstruction>(decoder.decode(InstructionWord.fromInt(0x3700)))
    }

    @Test
    fun `decodes write instruction`() {
        assertIs<WriteInstruction>(decoder.decode(InstructionWord.fromInt(0x4300)))
    }

    @Test
    fun `decodes jump instruction`() {
        assertIs<JumpInstruction>(decoder.decode(InstructionWord.fromInt(0x51F2)))
    }

    @Test
    fun `decodes read keyboard instruction`() {
        assertIs<ReadKeyboardInstruction>(decoder.decode(InstructionWord.fromInt(0x6200)))
    }

    @Test
    fun `decodes switch memory instruction`() {
        assertIs<SwitchMemoryInstruction>(decoder.decode(InstructionWord.fromInt(0x7000)))
    }

    @Test
    fun `decodes skip equal instruction`() {
        assertIs<SkipEqualInstruction>(decoder.decode(InstructionWord.fromInt(0x8120)))
    }

    @Test
    fun `decodes skip not equal instruction`() {
        assertIs<SkipNotEqualInstruction>(decoder.decode(InstructionWord.fromInt(0x9120)))
    }

    @Test
    fun `decodes set address instruction`() {
        assertIs<SetAddressInstruction>(decoder.decode(InstructionWord.fromInt(0xA255)))
    }

    @Test
    fun `decodes set timer instruction`() {
        assertIs<SetTimerInstruction>(decoder.decode(InstructionWord.fromInt(0xB0A0)))
    }

    @Test
    fun `decodes read timer instruction`() {
        assertIs<ReadTimerInstruction>(decoder.decode(InstructionWord.fromInt(0xC000)))
    }

    @Test
    fun `decodes convert to base 10 instruction`() {
        assertIs<ConvertToBase10Instruction>(decoder.decode(InstructionWord.fromInt(0xD200)))
    }

    @Test
    fun `decodes convert byte to ascii instruction`() {
        assertIs<ConvertByteToAsciiInstruction>(decoder.decode(InstructionWord.fromInt(0xE010)))
    }

    @Test
    fun `decodes draw instruction`() {
        assertIs<DrawInstruction>(decoder.decode(InstructionWord.fromInt(0xF123)))
    }
}