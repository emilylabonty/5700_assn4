package d5700.memory

interface MemoryDevice {
    val size: Int

    fun read(address: Int): UByte

    fun write(address: Int, value: UByte)
}