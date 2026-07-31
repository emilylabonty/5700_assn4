package d5700.memory

import d5700.error.EmulatorException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RamTest {

    @Test
    fun `RAM has default size of 4096 bytes`() {
        val ram = Ram()

        assertEquals(4096, ram.size)
    }

    @Test
    fun `RAM starts filled with zero bytes`() {
        val ram = Ram()

        assertEquals(0u, ram.read(0))
        assertEquals(0u, ram.read(4095))
    }

    @Test
    fun `RAM writes and reads byte`() {
        val ram = Ram()

        ram.write(0x255, 0xABu)

        assertEquals(0xABu, ram.read(0x255))
    }

    @Test
    fun `RAM supports custom size`() {
        val ram = Ram(size = 8)

        ram.write(7, 0xFFu)

        assertEquals(8, ram.size)
        assertEquals(0xFFu, ram.read(7))
    }

    @Test
    fun `RAM clear resets all bytes to zero`() {
        val ram = Ram(size = 4)

        ram.write(0, 0xAAu)
        ram.write(1, 0xBBu)
        ram.write(2, 0xCCu)
        ram.write(3, 0xDDu)

        ram.clear()

        assertEquals(0u, ram.read(0))
        assertEquals(0u, ram.read(1))
        assertEquals(0u, ram.read(2))
        assertEquals(0u, ram.read(3))
    }

    @Test
    fun `RAM rejects negative read address`() {
        val ram = Ram()

        assertFailsWith<EmulatorException> {
            ram.read(-1)
        }
    }

    @Test
    fun `RAM rejects read address equal to size`() {
        val ram = Ram(size = 8)

        assertFailsWith<EmulatorException> {
            ram.read(8)
        }
    }

    @Test
    fun `RAM rejects negative write address`() {
        val ram = Ram()

        assertFailsWith<EmulatorException> {
            ram.write(-1, 0x01u)
        }
    }

    @Test
    fun `RAM rejects write address equal to size`() {
        val ram = Ram(size = 8)

        assertFailsWith<EmulatorException> {
            ram.write(8, 0x01u)
        }
    }
}