package d5700.io

import kotlin.test.Test
import kotlin.test.assertEquals

class KeyboardInputTest {

    @Test
    fun `keyboard input interface can be implemented by fake input`() {
        val keyboard = FakeKeyboardInput(0xABu.toUByte())

        assertEquals(0xABu.toUByte(), keyboard.readByte())
    }

    private class FakeKeyboardInput(
        private val value: UByte
    ) : KeyboardInput {
        override fun readByte(): UByte {
            return value
        }
    }
}