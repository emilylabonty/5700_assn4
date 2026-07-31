package d5700.instruction

enum class Opcode(val nibble: Int) {
    STORE(0x0),
    ADD(0x1),
    SUB(0x2),
    READ(0x3),
    WRITE(0x4),
    JUMP(0x5),
    READ_KEYBOARD(0x6),
    SWITCH_MEMORY(0x7),
    SKIP_EQUAL(0x8),
    SKIP_NOT_EQUAL(0x9),
    SET_A(0xA),
    SET_T(0xB),
    READ_T(0xC),
    CONVERT_TO_BASE_10(0xD),
    CONVERT_BYTE_TO_ASCII(0xE),
    DRAW(0xF);

    companion object {
        fun fromNibble(nibble: Int): Opcode {
            return entries.firstOrNull { it.nibble == nibble }
                ?: throw IllegalArgumentException("Unknown opcode nibble: ${nibble.toString(16).uppercase()}")
        }
    }
}