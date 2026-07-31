package d5700.memory

import d5700.error.EmulatorException
import d5700.error.RomWriteException

open class Rom(
    override val size: Int = DEFAULT_SIZE
) : MemoryDevice {

    private val bytes = UByteArray(size)

    override fun read(address: Int): UByte {
        validateAddress(address)
        return bytes[address]
    }

    override fun write(address: Int, value: UByte) {
        validateAddress(address)
        throw RomWriteException(address)
    }

    fun loadProgram(programBytes: ByteArray) {
        if (programBytes.size > size) {
            throw EmulatorException(
                "Program size ${programBytes.size} bytes exceeds ROM size $size bytes."
            )
        }

        bytes.fill(0u)

        programBytes.forEachIndexed { index, byte ->
            bytes[index] = byte.toUByte()
        }
    }

    protected fun setByte(address: Int, value: UByte) {
        validateAddress(address)
        bytes[address] = value
    }

    private fun validateAddress(address: Int) {
        if (address !in 0 until size) {
            throw EmulatorException(
                "ROM address ${address.toString(16).uppercase()} is outside valid range 000-${(size - 1).toString(16).uppercase()}."
            )
        }
    }

    companion object {
        const val DEFAULT_SIZE = 4096
    }
}