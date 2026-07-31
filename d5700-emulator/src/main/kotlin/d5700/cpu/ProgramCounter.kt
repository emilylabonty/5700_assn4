package d5700.cpu

import d5700.error.InvalidJumpException

class ProgramCounter(initialValue: Int = 0) {

    var value: Int = initialValue
        private set

    init {
        validateEven(initialValue)
        validateRange(initialValue)
    }

    fun increment() {
        value += INSTRUCTION_SIZE_BYTES
        validateRange(value)
    }

    fun skipNextInstruction() {
        value += INSTRUCTION_SIZE_BYTES * 2
        validateRange(value)
    }

    fun jumpTo(address: Int) {
        validateEven(address)
        validateRange(address)
        value = address
    }

    fun reset() {
        value = 0
    }

    private fun validateEven(address: Int) {
        if (address % 2 != 0) {
            throw InvalidJumpException(address)
        }
    }

    private fun validateRange(address: Int) {
        if (address !in 0..MAX_ADDRESS) {
            throw InvalidJumpException(address)
        }
    }

    companion object {
        const val INSTRUCTION_SIZE_BYTES = 2
        const val MAX_ADDRESS = 0x0FFF
    }
}