package d5700.memory

import d5700.error.RomWriteException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MemoryBusTest {

    @Test
    fun `read with useRom false reads from RAM`() {
        val ram = Ram()
        val rom = Rom()
        val bus = MemoryBus(ram = ram, rom = rom)

        ram.write(0x10, 0xAAu)
        rom.loadProgram(byteArrayOf(0xBB.toByte()))

        assertEquals(0xAAu, bus.read(0x10, useRom = false))
    }

    @Test
    fun `read with useRom true reads from ROM`() {
        val ram = Ram()
        val rom = Rom()
        val bus = MemoryBus(ram = ram, rom = rom)

        ram.write(0, 0xAAu)
        rom.loadProgram(byteArrayOf(0xBB.toByte()))

        assertEquals(0xBBu, bus.read(0, useRom = true))
    }

    @Test
    fun `write with useRom false writes to RAM`() {
        val ram = Ram()
        val bus = MemoryBus(ram = ram, rom = Rom())

        bus.write(0x20, 0xCCu, useRom = false)

        assertEquals(0xCCu, ram.read(0x20))
    }

    @Test
    fun `write with useRom true writes to ROM device`() {
        val writableRom = WritableRom()
        val bus = MemoryBus(ram = Ram(), rom = writableRom)

        bus.write(0x20, 0xCCu, useRom = true)

        assertEquals(0xCCu, writableRom.read(0x20))
    }

    @Test
    fun `write with useRom true fails for normal ROM`() {
        val bus = MemoryBus(ram = Ram(), rom = Rom())

        assertFailsWith<RomWriteException> {
            bus.write(0x20, 0xCCu, useRom = true)
        }
    }

    @Test
    fun `readInstructionByte always reads from ROM`() {
        val ram = Ram()
        val rom = Rom()
        val bus = MemoryBus(ram = ram, rom = rom)

        ram.write(0, 0xAAu)
        rom.loadProgram(byteArrayOf(0xBB.toByte()))

        assertEquals(0xBBu, bus.readInstructionByte(0))
    }

    @Test
    fun `loadRom copies program into ROM`() {
        val rom = Rom()
        val bus = MemoryBus(ram = Ram(), rom = rom)

        bus.loadRom(byteArrayOf(0x12, 0x34))

        assertEquals(0x12u, rom.read(0))
        assertEquals(0x34u, rom.read(1))
    }
}