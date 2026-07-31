package d5700.cpu

import d5700.error.InvalidJumpException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProgramCounterTest {

    @Test
    fun `program counter starts at zero by default`() {
        val programCounter = ProgramCounter()

        assertEquals(0, programCounter.value)
    }

    @Test
    fun `program counter can start at even address`() {
        val programCounter = ProgramCounter(0x200)

        assertEquals(0x200, programCounter.value)
    }

    @Test
    fun `program counter rejects odd initial address`() {
        assertFailsWith<InvalidJumpException> {
            ProgramCounter(0x201)
        }
    }

    @Test
    fun `increment moves program counter by two bytes`() {
        val programCounter = ProgramCounter()

        programCounter.increment()

        assertEquals(2, programCounter.value)
    }

    @Test
    fun `skipNextInstruction moves program counter by four bytes`() {
        val programCounter = ProgramCounter()

        programCounter.skipNextInstruction()

        assertEquals(4, programCounter.value)
    }

    @Test
    fun `jumpTo sets even address`() {
        val programCounter = ProgramCounter()

        programCounter.jumpTo(0x1F2)

        assertEquals(0x1F2, programCounter.value)
    }

    @Test
    fun `jumpTo rejects odd address`() {
        val programCounter = ProgramCounter()

        assertFailsWith<InvalidJumpException> {
            programCounter.jumpTo(0x1F1)
        }
    }

    @Test
    fun `jumpTo rejects address outside ROM range`() {
        val programCounter = ProgramCounter()

        assertFailsWith<InvalidJumpException> {
            programCounter.jumpTo(0x1000)
        }
    }

    @Test
    fun `reset returns program counter to zero`() {
        val programCounter = ProgramCounter(0x200)

        programCounter.reset()

        assertEquals(0, programCounter.value)
    }
}