package d5700.instruction

class InstructionWord(
    val highByte: UByte,
    val lowByte: UByte
) {
    val raw: UShort =
        (((highByte.toInt() and 0xFF) shl 8) or (lowByte.toInt() and 0xFF)).toUShort()

    val opcode: Opcode =
        Opcode.fromNibble(nibble0)

    val nibble0: Int
        get() = (raw.toInt() shr 12) and 0xF

    val nibble1: Int
        get() = (raw.toInt() shr 8) and 0xF

    val nibble2: Int
        get() = (raw.toInt() shr 4) and 0xF

    val nibble3: Int
        get() = raw.toInt() and 0xF

    val byteLiteral: UByte
        get() = (raw.toInt() and 0xFF).toUByte()

    val addressLiteral: UShort
        get() = (raw.toInt() and 0x0FFF).toUShort()

    val isHalt: Boolean
        get() = raw.toInt() == 0x0000

    fun registerX(): Int = nibble1

    fun registerY(): Int = nibble2

    fun registerZ(): Int = nibble3

    override fun toString(): String {
        return raw.toInt().toString(16).uppercase().padStart(4, '0')
    }

    companion object {
        fun fromBytes(highByte: Byte, lowByte: Byte): InstructionWord {
            return InstructionWord(highByte.toUByte(), lowByte.toUByte())
        }

        fun fromInt(value: Int): InstructionWord {
            require(value in 0x0000..0xFFFF) {
                "Instruction word must fit in 16 bits."
            }

            val high = ((value shr 8) and 0xFF).toUByte()
            val low = (value and 0xFF).toUByte()

            return InstructionWord(high, low)
        }
    }
}