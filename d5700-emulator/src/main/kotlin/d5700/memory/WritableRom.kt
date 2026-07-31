package d5700.memory

class WritableRom(
    size: Int = DEFAULT_SIZE
) : Rom(size) {

    override fun write(address: Int, value: UByte) {
        setByte(address, value)
    }
}