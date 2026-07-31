package d5700.memory

class MemoryBus(
    val ram: Ram = Ram(),
    val rom: Rom = Rom()
) {

    fun read(address: Int, useRom: Boolean): UByte {
        return selectedMemory(useRom).read(address)
    }

    fun write(address: Int, value: UByte, useRom: Boolean) {
        selectedMemory(useRom).write(address, value)
    }

    fun readInstructionByte(address: Int): UByte {
        return rom.read(address)
    }

    fun loadRom(programBytes: ByteArray) {
        rom.loadProgram(programBytes)
    }

    private fun selectedMemory(useRom: Boolean): MemoryDevice {
        return if (useRom) rom else ram
    }
}