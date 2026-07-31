package d5700.instruction

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OpcodeTest {

    @Test
    fun `fromNibble decodes STORE`() {
        assertEquals(Opcode.STORE, Opcode.fromNibble(0x0))
    }

    @Test
    fun `fromNibble decodes ADD`() {
        assertEquals(Opcode.ADD, Opcode.fromNibble(0x1))
    }

    @Test
    fun `fromNibble decodes SUB`() {
        assertEquals(Opcode.SUB, Opcode.fromNibble(0x2))
    }

    @Test
    fun `fromNibble decodes READ`() {
        assertEquals(Opcode.READ, Opcode.fromNibble(0x3))
    }

    @Test
    fun `fromNibble decodes WRITE`() {
        assertEquals(Opcode.WRITE, Opcode.fromNibble(0x4))
    }

    @Test
    fun `fromNibble decodes JUMP`() {
        assertEquals(Opcode.JUMP, Opcode.fromNibble(0x5))
    }

    @Test
    fun `fromNibble decodes READ_KEYBOARD`() {
        assertEquals(Opcode.READ_KEYBOARD, Opcode.fromNibble(0x6))
    }

    @Test
    fun `fromNibble decodes SWITCH_MEMORY`() {
        assertEquals(Opcode.SWITCH_MEMORY, Opcode.fromNibble(0x7))
    }

    @Test
    fun `fromNibble decodes SKIP_EQUAL`() {
        assertEquals(Opcode.SKIP_EQUAL, Opcode.fromNibble(0x8))
    }

    @Test
    fun `fromNibble decodes SKIP_NOT_EQUAL`() {
        assertEquals(Opcode.SKIP_NOT_EQUAL, Opcode.fromNibble(0x9))
    }

    @Test
    fun `fromNibble decodes SET_A`() {
        assertEquals(Opcode.SET_A, Opcode.fromNibble(0xA))
    }

    @Test
    fun `fromNibble decodes SET_T`() {
        assertEquals(Opcode.SET_T, Opcode.fromNibble(0xB))
    }

    @Test
    fun `fromNibble decodes READ_T`() {
        assertEquals(Opcode.READ_T, Opcode.fromNibble(0xC))
    }

    @Test
    fun `fromNibble decodes CONVERT_TO_BASE_10`() {
        assertEquals(Opcode.CONVERT_TO_BASE_10, Opcode.fromNibble(0xD))
    }

    @Test
    fun `fromNibble decodes CONVERT_BYTE_TO_ASCII`() {
        assertEquals(Opcode.CONVERT_BYTE_TO_ASCII, Opcode.fromNibble(0xE))
    }

    @Test
    fun `fromNibble decodes DRAW`() {
        assertEquals(Opcode.DRAW, Opcode.fromNibble(0xF))
    }

    @Test
    fun `fromNibble rejects negative nibble`() {
        assertFailsWith<IllegalArgumentException> {
            Opcode.fromNibble(-1)
        }
    }

    @Test
    fun `fromNibble rejects value greater than F`() {
        assertFailsWith<IllegalArgumentException> {
            Opcode.fromNibble(0x10)
        }
    }

    @Test
    fun `opcode nibble values match data sheet`() {
        assertEquals(0x0, Opcode.STORE.nibble)
        assertEquals(0x1, Opcode.ADD.nibble)
        assertEquals(0x2, Opcode.SUB.nibble)
        assertEquals(0x3, Opcode.READ.nibble)
        assertEquals(0x4, Opcode.WRITE.nibble)
        assertEquals(0x5, Opcode.JUMP.nibble)
        assertEquals(0x6, Opcode.READ_KEYBOARD.nibble)
        assertEquals(0x7, Opcode.SWITCH_MEMORY.nibble)
        assertEquals(0x8, Opcode.SKIP_EQUAL.nibble)
        assertEquals(0x9, Opcode.SKIP_NOT_EQUAL.nibble)
        assertEquals(0xA, Opcode.SET_A.nibble)
        assertEquals(0xB, Opcode.SET_T.nibble)
        assertEquals(0xC, Opcode.READ_T.nibble)
        assertEquals(0xD, Opcode.CONVERT_TO_BASE_10.nibble)
        assertEquals(0xE, Opcode.CONVERT_BYTE_TO_ASCII.nibble)
        assertEquals(0xF, Opcode.DRAW.nibble)
    }
}