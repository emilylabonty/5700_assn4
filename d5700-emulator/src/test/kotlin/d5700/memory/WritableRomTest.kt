package d5700.memory

import kotlin.test.Test
import kotlin.test.assertEquals

class WritableRomTest {

    @Test
    fun `WritableRom writes and reads byte`() {
        val rom = WritableRom()

        rom.write(0x100, 0xABu)

        assertEquals(0xABu, rom.read(0x100))
    }

    @Test
    fun `WritableRom can overwrite loaded program byte`() {
        val rom = WritableRom()

        rom.loadProgram(byteArrayOf(0x12, 0x34))
        rom.write(1, 0xFFu)

        assertEquals(0x12u, rom.read(0))
        assertEquals(0xFFu, rom.read(1))
    }
}