package d5700.cpu

import d5700.error.EmulatorException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RegistersTest {

    @Test
    fun `general purpose registers start at zero`() {
        val registers = Registers()

        for (index in 0 until Registers.REGISTER_COUNT) {
            assertEquals(0u.toUByte(), registers.readRegister(index))
        }
    }

    @Test
    fun `writeRegister stores value in selected register`() {
        val registers = Registers()

        registers.writeRegister(3, 0x7Fu.toUByte())

        assertEquals(0x7Fu.toUByte(), registers.readRegister(3))
    }

    @Test
    fun `writeRegister only changes selected register`() {
        val registers = Registers()

        registers.writeRegister(3, 0x7Fu.toUByte())

        assertEquals(0u.toUByte(), registers.readRegister(2))
        assertEquals(0x7Fu.toUByte(), registers.readRegister(3))
        assertEquals(0u.toUByte(), registers.readRegister(4))
    }

    @Test
    fun `readRegister rejects negative index`() {
        val registers = Registers()

        assertFailsWith<EmulatorException> {
            registers.readRegister(-1)
        }
    }

    @Test
    fun `readRegister rejects index greater than seven`() {
        val registers = Registers()

        assertFailsWith<EmulatorException> {
            registers.readRegister(8)
        }
    }

    @Test
    fun `writeRegister rejects negative index`() {
        val registers = Registers()

        assertFailsWith<EmulatorException> {
            registers.writeRegister(-1, 0x01u.toUByte())
        }
    }

    @Test
    fun `writeRegister rejects index greater than seven`() {
        val registers = Registers()

        assertFailsWith<EmulatorException> {
            registers.writeRegister(8, 0x01u.toUByte())
        }
    }

    @Test
    fun `address starts at zero`() {
        val registers = Registers()

        assertEquals(0, registers.address)
    }

    @Test
    fun `setAddress stores twelve bit address`() {
        val registers = Registers()

        registers.setAddress(0xFFF)

        assertEquals(0xFFF, registers.address)
    }

    @Test
    fun `setAddress rejects negative address`() {
        val registers = Registers()

        assertFailsWith<EmulatorException> {
            registers.setAddress(-1)
        }
    }

    @Test
    fun `setAddress rejects address larger than twelve bits`() {
        val registers = Registers()

        assertFailsWith<EmulatorException> {
            registers.setAddress(0x1000)
        }
    }

    @Test
    fun `memory mode starts as RAM`() {
        val registers = Registers()

        assertFalse(registers.useRom)
    }

    @Test
    fun `toggleMemoryMode switches between RAM and ROM`() {
        val registers = Registers()

        registers.toggleMemoryMode()
        assertTrue(registers.useRom)

        registers.toggleMemoryMode()
        assertFalse(registers.useRom)
    }

    @Test
    fun `reset clears all register state`() {
        val registers = Registers()

        registers.writeRegister(0, 0xAAu.toUByte())
        registers.setAddress(0x123)
        registers.toggleMemoryMode()
        registers.programCounter.jumpTo(0x200)
        registers.timer.set(10u.toUByte())

        registers.reset()

        assertEquals(0u.toUByte(), registers.readRegister(0))
        assertEquals(0, registers.address)
        assertFalse(registers.useRom)
        assertEquals(0, registers.programCounter.value)
        assertEquals(0u.toUByte(), registers.timer.read())
    }
}