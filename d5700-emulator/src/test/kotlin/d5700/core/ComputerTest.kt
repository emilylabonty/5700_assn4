package d5700.core

import d5700.cpu.Cpu
import d5700.cpu.CpuStopReason
import d5700.cpu.Registers
import d5700.instruction.DefaultInstructionDecoder
import d5700.io.Display
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import d5700.memory.ProgramLoader
import kotlin.io.path.createTempFile
import kotlin.io.path.writeBytes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ComputerTest {

    @Test
    fun `loadProgram copies bytes into ROM and returns byte count`() {
        val program = createTempFile(suffix = ".bin")
        program.writeBytes(byteArrayOf(0x00, 0x00))

        val memoryBus = MemoryBus()
        val computer = createComputer(memoryBus = memoryBus)

        val byteCount = computer.loadProgram(program.toString())

        assertEquals(2, byteCount)
        assertEquals(0u, memoryBus.readInstructionByte(0))
        assertEquals(0u, memoryBus.readInstructionByte(1))
    }

    @Test
    fun `loadProgram resets registers and screen`() {
        val program = createTempFile(suffix = ".bin")
        program.writeBytes(byteArrayOf(0x00, 0x00))

        val registers = Registers()
        val screen = ScreenBuffer()

        registers.writeRegister(0, 0xAAu)
        registers.setAddress(0x123)
        registers.toggleMemoryMode()
        screen.draw(0, 0, 'X'.code.toUByte())

        val computer = createComputer(
            registers = registers,
            screen = screen
        )

        computer.loadProgram(program.toString())

        assertEquals(0u, registers.readRegister(0))
        assertEquals(0, registers.address)
        assertEquals(false, registers.useRom)
        assertEquals(' '.code.toUByte(), screen.read(0, 0))
    }

    @Test
    fun `run executes loaded halt program and renders display`() {
        val program = createTempFile(suffix = ".bin")
        program.writeBytes(byteArrayOf(0x00, 0x00))

        val display = FakeDisplay()
        val computer = createComputer(display = display)

        computer.loadProgram(program.toString())
        val result = computer.run()

        assertEquals(2, result.loadedByteCount)
        assertEquals(CpuStopReason.HALTED, result.stopReason)
        assertEquals(1, result.cyclesExecuted)
        assertEquals(0, result.finalProgramCounter)
        assertTrue(display.renderWasCalled)
    }

    @Test
    fun `run returns screen text`() {
        val program = createTempFile(suffix = ".bin")
        program.writeBytes(byteArrayOf(0x00, 0x00))

        val screen = ScreenBuffer()
        val computer = createComputer(screen = screen)

        computer.loadProgram(program.toString())
        screen.draw(0, 0, 'A'.code.toUByte())

        val result = computer.run()

        assertEquals("A       ", result.screenText.lines().first())
    }

    private fun createComputer(
        registers: Registers = Registers(),
        memoryBus: MemoryBus = MemoryBus(),
        screen: ScreenBuffer = ScreenBuffer(),
        keyboard: KeyboardInput = FakeKeyboardInput(),
        display: Display = FakeDisplay()
    ): Computer {
        val context = ExecutionContext(
            registers = registers,
            memoryBus = memoryBus,
            screen = screen,
            keyboard = keyboard
        )

        return Computer(
            registers = registers,
            screen = screen,
            programLoader = ProgramLoader(memoryBus),
            cpu = Cpu(context, DefaultInstructionDecoder()),
            display = display
        )
    }

    private class FakeKeyboardInput : KeyboardInput {
        override fun readByte(): UByte = 0u
    }

    private class FakeDisplay : Display {
        var renderWasCalled = false
            private set

        override fun render(screenBuffer: ScreenBuffer) {
            renderWasCalled = true
        }
    }
}