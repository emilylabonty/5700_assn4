package d5700.core

import d5700.cpu.Registers
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import kotlin.test.Test
import kotlin.test.assertSame

class ExecutionContextTest {

    @Test
    fun `execution context stores emulator dependencies`() {
        val registers = Registers()
        val memoryBus = MemoryBus()
        val screen = ScreenBuffer()
        val keyboard = FakeKeyboardInput()

        val context = ExecutionContext(
            registers = registers,
            memoryBus = memoryBus,
            screen = screen,
            keyboard = keyboard
        )

        assertSame(registers, context.registers)
        assertSame(memoryBus, context.memoryBus)
        assertSame(screen, context.screen)
        assertSame(keyboard, context.keyboard)
    }

    private class FakeKeyboardInput : KeyboardInput {
        override fun readByte(): UByte = 0u
    }
}