package d5700.memory

import d5700.error.ProgramLoadException
import kotlin.io.path.createDirectory
import kotlin.io.path.createTempDirectory
import kotlin.io.path.writeBytes
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProgramLoaderTest {

    @Test
    fun `load reads program bytes from file`() {
        val tempDir = createTempDirectory()
        val programFile = tempDir.resolve("program.bin")
        programFile.writeBytes(byteArrayOf(0x12, 0x34, 0x56))

        val bus = MemoryBus()
        val loader = ProgramLoader(bus)

        val result = loader.load(programFile.toString())

        assertContentEquals(byteArrayOf(0x12, 0x34, 0x56), result)
    }

    @Test
    fun `load copies program bytes into ROM`() {
        val tempDir = createTempDirectory()
        val programFile = tempDir.resolve("program.bin")
        programFile.writeBytes(byteArrayOf(0x12, 0x34, 0x56))

        val rom = Rom()
        val bus = MemoryBus(rom = rom)
        val loader = ProgramLoader(bus)

        loader.load(programFile.toString())

        assertEquals(0x12u, rom.read(0))
        assertEquals(0x34u, rom.read(1))
        assertEquals(0x56u, rom.read(2))
    }

    @Test
    fun `load trims path text before reading file`() {
        val tempDir = createTempDirectory()
        val programFile = tempDir.resolve("program.bin")
        programFile.writeBytes(byteArrayOf(0x7F))

        val bus = MemoryBus()
        val loader = ProgramLoader(bus)

        val result = loader.load("  $programFile  ")

        assertContentEquals(byteArrayOf(0x7F), result)
    }

    @Test
    fun `load rejects missing file`() {
        val bus = MemoryBus()
        val loader = ProgramLoader(bus)

        assertFailsWith<ProgramLoadException> {
            loader.load("missing-program.bin")
        }
    }

    @Test
    fun `load rejects directory path`() {
        val tempDir = createTempDirectory()
        val programDirectory = tempDir.resolve("program-directory")
        programDirectory.createDirectory()

        val bus = MemoryBus()
        val loader = ProgramLoader(bus)

        assertFailsWith<ProgramLoadException> {
            loader.load(programDirectory.toString())
        }
    }

    @Test
    fun `load rejects program larger than ROM`() {
        val tempDir = createTempDirectory()
        val programFile = tempDir.resolve("too-large.bin")
        programFile.writeBytes(ByteArray(Rom.DEFAULT_SIZE + 1))

        val bus = MemoryBus()
        val loader = ProgramLoader(bus)

        assertFailsWith<ProgramLoadException> {
            loader.load(programFile.toString())
        }
    }
}