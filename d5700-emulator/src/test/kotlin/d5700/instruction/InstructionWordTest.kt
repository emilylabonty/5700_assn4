package d5700.instruction

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class InstructionWordTest {

    @Test
    fun `fromInt creates instruction word from 16 bit value`() {
        val word = InstructionWord.fromInt(0x12AB)

        assertEquals(0x12u, word.highByte)
        assertEquals(0xABu, word.lowByte)
        assertEquals(0x12ABu, word.raw)
    }

    @Test
    fun `fromBytes creates instruction word from signed bytes as unsigned bytes`() {
        val word = InstructionWord.fromBytes(0xAB.toByte(), 0xCD.toByte())

        assertEquals(0xABu, word.highByte)
        assertEquals(0xCDu, word.lowByte)
        assertEquals(0xABCDu, word.raw)
    }

    @Test
    fun `fromInt rejects negative value`() {
        assertFailsWith<IllegalArgumentException> {
            InstructionWord.fromInt(-1)
        }
    }

    @Test
    fun `fromInt rejects value larger than 16 bits`() {
        assertFailsWith<IllegalArgumentException> {
            InstructionWord.fromInt(0x10000)
        }
    }

    @Test
    fun `nibbles are extracted correctly`() {
        val word = InstructionWord.fromInt(0x1A2F)

        assertEquals(0x1, word.nibble0)
        assertEquals(0xA, word.nibble1)
        assertEquals(0x2, word.nibble2)
        assertEquals(0xF, word.nibble3)
    }

    @Test
    fun `opcode is decoded from first nibble`() {
        val word = InstructionWord.fromInt(0xA255)

        assertEquals(Opcode.SET_A, word.opcode)
    }

    @Test
    fun `byte literal returns low byte`() {
        val word = InstructionWord.fromInt(0x00FF)

        assertEquals(0xFFu, word.byteLiteral)
    }

    @Test
    fun `address literal returns lower twelve bits`() {
        val word = InstructionWord.fromInt(0x51F2)

        assertEquals(0x1F2u, word.addressLiteral)
    }

    @Test
    fun `register helpers return instruction register nibbles`() {
        val word = InstructionWord.fromInt(0xF123)

        assertEquals(1, word.registerX())
        assertEquals(2, word.registerY())
        assertEquals(3, word.registerZ())
    }

    @Test
    fun `zero instruction is halt`() {
        val word = InstructionWord.fromInt(0x0000)

        assertTrue(word.isHalt)
    }

    @Test
    fun `nonzero store instruction is not halt`() {
        val word = InstructionWord.fromInt(0x00FF)

        assertFalse(word.isHalt)
    }

    @Test
    fun `toString returns uppercase four digit hex`() {
        assertEquals("0000", InstructionWord.fromInt(0x0000).toString())
        assertEquals("00FF", InstructionWord.fromInt(0x00FF).toString())
        assertEquals("A255", InstructionWord.fromInt(0xA255).toString())
        assertEquals("FFFF", InstructionWord.fromInt(0xFFFF).toString())
    }
}