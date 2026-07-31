package d5700.core

import d5700.cpu.Registers
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertEquals

class ExecutionContextTest {

    @Test
    fun `execution context exposes focused operations`() {
        val context = ExecutionContext(
            registers = Registers(),
            memoryBus = MemoryBus(),
            screen = ScreenBuffer(),
            keyboard = FakeKeyboardInput()
        )

        context.writeRegister(1, 0x2Au.toUByte())

        assertEquals(0x2Au.toUByte(), context.readRegister(1))
    }

    private class FakeKeyboardInput : KeyboardInput {
        override fun readByte(): UByte = 0u
    }
}
