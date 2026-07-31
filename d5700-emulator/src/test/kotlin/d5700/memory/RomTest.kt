package d5700.memory

import d5700.error.EmulatorException
import d5700.error.RomWriteException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RomTest {

    @Test
    fun `ROM has default size of 4096 bytes`() {
        val rom = Rom()

        assertEquals(4096, rom.size)
    }

    @Test
    fun `ROM starts filled with zero bytes`() {
        val rom = Rom()

        assertEquals(0u, rom.read(0))
        assertEquals(0u, rom.read(4095))
    }

    @Test
    fun `ROM loads program bytes starting at address zero`() {
        val rom = Rom()

        rom.loadProgram(byteArrayOf(0x12, 0x34, 0x56))

        assertEquals(0x12u, rom.read(0))
        assertEquals(0x34u, rom.read(1))
        assertEquals(0x56u, rom.read(2))
    }

    @Test
    fun `ROM load clears previous program bytes`() {
        val rom = Rom()

        rom.loadProgram(byteArrayOf(0x12, 0x34, 0x56))
        rom.loadProgram(byteArrayOf(0xAB.toByte()))

        assertEquals(0xABu, rom.read(0))
        assertEquals(0u, rom.read(1))
        assertEquals(0u, rom.read(2))
    }

    @Test
    fun `ROM rejects program larger than ROM size`() {
        val rom = Rom(size = 2)

        assertFailsWith<EmulatorException> {
            rom.loadProgram(byteArrayOf(0x01, 0x02, 0x03))
        }
    }

    @Test
    fun `ROM rejects write`() {
        val rom = Rom()

        assertFailsWith<RomWriteException> {
            rom.write(0, 0xFFu)
        }
    }

    @Test
    fun `ROM rejects negative read address`() {
        val rom = Rom()

        assertFailsWith<EmulatorException> {
            rom.read(-1)
        }
    }

    @Test
    fun `ROM rejects read address equal to size`() {
        val rom = Rom(size = 8)

        assertFailsWith<EmulatorException> {
            rom.read(8)
        }
    }

    @Test
    fun `ROM validates address before rejecting write`() {
        val rom = Rom(size = 8)

        assertFailsWith<EmulatorException> {
            rom.write(8, 0x01u)
        }
    }
}