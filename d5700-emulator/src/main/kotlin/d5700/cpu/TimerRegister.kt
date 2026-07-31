package d5700.cpu

class TimerRegister(initialValue: Int = 0) {

    var value: UByte = initialValue.toUByte()
        private set

    fun set(value: UByte) {
        this.value = value
    }

    fun read(): UByte {
        return value
    }

    fun tick() {
        if (value > 0u) {
            value = (value - 1u).toUByte()
        }
    }

    fun reset() {
        value = 0u
    }
}