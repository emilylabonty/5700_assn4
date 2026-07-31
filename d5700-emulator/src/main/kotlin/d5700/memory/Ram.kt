package d5700.memory

import d5700.error.EmulatorException

class Ram(
    override val size: Int = DEFAULT_SIZE
) : MemoryDevice {

    private val bytes = UByteArray(size)

    override fun read(address: Int): UByte {
        validateAddress(address)
        return bytes[address]
    }

    override fun write(address: Int, value: UByte) {
        validateAddress(address)
        bytes[address] = value
    }

    fun clear() {
        bytes.fill(0u)
    }

    private fun validateAddress(address: Int) {
        if (address !in 0 until size) {
            throw EmulatorException(
                "RAM address ${address.toString(16).uppercase()} is outside valid range 000-${(size - 1).toString(16).uppercase()}."
            )
        }
    }

    companion object {
        const val DEFAULT_SIZE = 4096
    }
}