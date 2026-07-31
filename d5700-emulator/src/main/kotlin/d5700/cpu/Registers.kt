package d5700.cpu

import d5700.error.EmulatorException

class Registers(
    val programCounter: ProgramCounter = ProgramCounter(),
    val timer: TimerRegister = TimerRegister()
) {

    private val generalPurpose = UByteArray(REGISTER_COUNT)

    var address: Int = 0
        private set

    var useRom: Boolean = false
        private set

    fun readRegister(index: Int): UByte {
        validateRegisterIndex(index)
        return generalPurpose[index]
    }

    fun writeRegister(index: Int, value: UByte) {
        validateRegisterIndex(index)
        generalPurpose[index] = value
    }

    fun setAddress(address: Int) {
        if (address !in 0..MAX_ADDRESS) {
            throw EmulatorException(
                "Address ${address.toString(16).uppercase()} is outside valid range 000-FFF."
            )
        }

        this.address = address
    }

    fun toggleMemoryMode() {
        useRom = !useRom
    }

    fun reset() {
        generalPurpose.fill(0u)
        address = 0
        useRom = false
        programCounter.reset()
        timer.reset()
    }

    private fun validateRegisterIndex(index: Int) {
        if (index !in 0 until REGISTER_COUNT) {
            throw EmulatorException("Register index $index is outside valid range 0-7.")
        }
    }

    companion object {
        const val REGISTER_COUNT = 8
        const val MAX_ADDRESS = 0x0FFF
    }
}