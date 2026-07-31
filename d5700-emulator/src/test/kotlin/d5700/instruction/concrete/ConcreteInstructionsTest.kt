package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.cpu.Registers
import d5700.error.InvalidAsciiException
import d5700.error.InvalidHexDigitException
import d5700.error.InvalidJumpException
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus
import d5700.memory.Ram
import d5700.memory.Rom
import d5700.memory.WritableRom
import d5700.instruction.InstructionWord
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InstructionImplementationTest {

    @Test
    fun `halt does not change program counter`() {
        val context = context()
        context.jumpTo(2)

        HaltInstruction().execute(context, InstructionWord.fromInt(0x0000))

        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `store writes literal byte to register`() {
        val context = context()

        StoreInstruction().execute(context, InstructionWord.fromInt(0x03AB))

        assertEquals(0xABu.toUByte(), context.readRegister(3))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `add stores sum in destination register`() {
        val context = context()
        context.writeRegister(1, 10u)
        context.writeRegister(2, 15u)

        AddInstruction().execute(context, InstructionWord.fromInt(0x1123))

        assertEquals(25u.toUByte(), context.readRegister(3))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `add wraps at 8 bits`() {
        val context = context()
        context.writeRegister(1, 255u)
        context.writeRegister(2, 1u)

        AddInstruction().execute(context, InstructionWord.fromInt(0x1123))

        assertEquals(0u.toUByte(), context.readRegister(3))
    }

    @Test
    fun `sub stores difference in destination register`() {
        val context = context()
        context.writeRegister(1, 20u)
        context.writeRegister(2, 5u)

        SubInstruction().execute(context, InstructionWord.fromInt(0x2123))

        assertEquals(15u.toUByte(), context.readRegister(3))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `sub wraps at 8 bits`() {
        val context = context()
        context.writeRegister(1, 0u)
        context.writeRegister(2, 1u)

        SubInstruction().execute(context, InstructionWord.fromInt(0x2123))

        assertEquals(255u.toUByte(), context.readRegister(3))
    }

    @Test
    fun `read reads from RAM when memory mode is RAM`() {
        val ram = Ram()
        ram.write(0x100, 0x7Fu)
        val context = context(memoryBus = MemoryBus(ram = ram, rom = Rom()))
        context.setAddress(0x100)

        ReadInstruction().execute(context, InstructionWord.fromInt(0x3300))

        assertEquals(0x7Fu.toUByte(), context.readRegister(3))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `read reads from ROM when memory mode is ROM`() {
        val rom = Rom()
        rom.loadProgram(byteArrayOf(0x55))
        val context = context(memoryBus = MemoryBus(ram = Ram(), rom = rom))
        context.toggleMemoryMode()

        ReadInstruction().execute(context, InstructionWord.fromInt(0x3400))

        assertEquals(0x55u.toUByte(), context.readRegister(4))
    }

    @Test
    fun `write writes selected register to RAM`() {
        val ram = Ram()
        val context = context(memoryBus = MemoryBus(ram = ram, rom = Rom()))
        context.setAddress(0x200)
        context.writeRegister(5, 0xCCu)

        WriteInstruction().execute(context, InstructionWord.fromInt(0x4500))

        assertEquals(0xCCu.toUByte(), ram.read(0x200))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `write can write to writable ROM when memory mode is ROM`() {
        val rom = WritableRom()
        val context = context(memoryBus = MemoryBus(ram = Ram(), rom = rom))
        context.toggleMemoryMode()
        context.setAddress(0x10)
        context.writeRegister(2, 0xAAu)

        WriteInstruction().execute(context, InstructionWord.fromInt(0x4200))

        assertEquals(0xAAu.toUByte(), rom.read(0x10))
    }

    @Test
    fun `jump sets program counter and does not increment afterward`() {
        val context = context()

        JumpInstruction().execute(context, InstructionWord.fromInt(0x51F2))

        assertEquals(0x1F2, context.programCounterValue())
    }

    @Test
    fun `jump rejects odd address`() {
        val context = context()

        assertFailsWith<InvalidJumpException> {
            JumpInstruction().execute(context, InstructionWord.fromInt(0x5001))
        }
    }

    @Test
    fun `read keyboard stores input byte in register`() {
        val context = context(keyboard = FakeKeyboardInput(0xABu))

        ReadKeyboardInstruction().execute(context, InstructionWord.fromInt(0x6200))

        assertEquals(0xABu.toUByte(), context.readRegister(2))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `switch memory toggles memory mode`() {
        val registers = Registers()
        val context = context(registers = registers)

        SwitchMemoryInstruction().execute(context, InstructionWord.fromInt(0x7000))

        assertTrue(registers.useRom)
        assertEquals(2, context.programCounterValue())

        SwitchMemoryInstruction().execute(context, InstructionWord.fromInt(0x7000))

        assertFalse(registers.useRom)
        assertEquals(4, context.programCounterValue())
    }

    @Test
    fun `skip equal skips next instruction when registers are equal`() {
        val context = context()
        context.writeRegister(1, 0x22u)
        context.writeRegister(2, 0x22u)

        SkipEqualInstruction().execute(context, InstructionWord.fromInt(0x8120))

        assertEquals(4, context.programCounterValue())
    }

    @Test
    fun `skip equal does not skip when registers are different`() {
        val context = context()
        context.writeRegister(1, 0x22u)
        context.writeRegister(2, 0x33u)

        SkipEqualInstruction().execute(context, InstructionWord.fromInt(0x8120))

        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `skip not equal skips next instruction when registers are different`() {
        val context = context()
        context.writeRegister(1, 0x22u)
        context.writeRegister(2, 0x33u)

        SkipNotEqualInstruction().execute(context, InstructionWord.fromInt(0x9120))

        assertEquals(4, context.programCounterValue())
    }

    @Test
    fun `skip not equal does not skip when registers are equal`() {
        val context = context()
        context.writeRegister(1, 0x22u)
        context.writeRegister(2, 0x22u)

        SkipNotEqualInstruction().execute(context, InstructionWord.fromInt(0x9120))

        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `set address stores twelve bit address`() {
        val registers = Registers()
        val context = context(registers = registers)

        SetAddressInstruction().execute(context, InstructionWord.fromInt(0xA255))

        assertEquals(0x255, registers.address)
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `set timer stores byte from middle two nibbles`() {
        val context = context()

        SetTimerInstruction().execute(context, InstructionWord.fromInt(0xB0A0))

        assertEquals(0x0Au.toUByte(), context.readTimer())
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `read timer stores timer value in register`() {
        val context = context()
        context.setTimer(0x42u)

        ReadTimerInstruction().execute(context, InstructionWord.fromInt(0xC300))

        assertEquals(0x42u.toUByte(), context.readRegister(3))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `convert to base 10 stores hundreds tens and ones in memory`() {
        val ram = Ram()
        val context = context(memoryBus = MemoryBus(ram = ram, rom = Rom()))
        context.setAddress(0x300)
        context.writeRegister(2, 234u)

        ConvertToBase10Instruction().execute(context, InstructionWord.fromInt(0xD200))

        assertEquals(2u.toUByte(), ram.read(0x300))
        assertEquals(3u.toUByte(), ram.read(0x301))
        assertEquals(4u.toUByte(), ram.read(0x302))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `convert byte to ascii converts decimal digit`() {
        val context = context()
        context.writeRegister(1, 9u)

        ConvertByteToAsciiInstruction().execute(context, InstructionWord.fromInt(0xE120))

        assertEquals('9'.code.toUByte(), context.readRegister(2))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `convert byte to ascii converts hex digit A through F`() {
        val context = context()
        context.writeRegister(1, 0x0Fu)

        ConvertByteToAsciiInstruction().execute(context, InstructionWord.fromInt(0xE120))

        assertEquals('F'.code.toUByte(), context.readRegister(2))
    }

    @Test
    fun `convert byte to ascii rejects value greater than F`() {
        val context = context()
        context.writeRegister(1, 0x10u)

        assertFailsWith<InvalidHexDigitException> {
            ConvertByteToAsciiInstruction().execute(context, InstructionWord.fromInt(0xE120))
        }
    }

    @Test
    fun `draw writes ascii character to screen`() {
        val screen = ScreenBuffer()
        val context = context(screen = screen)
        context.writeRegister(1, 'X'.code.toUByte())
        context.writeRegister(2, 3u)
        context.writeRegister(3, 4u)

        DrawInstruction().execute(context, InstructionWord.fromInt(0xF123))

        assertEquals('X'.code.toUByte(), screen.read(3, 4))
        assertEquals(2, context.programCounterValue())
    }

    @Test
    fun `draw rejects ascii value greater than 7F`() {
        val context = context()
        context.writeRegister(1, 0x80u)
        context.writeRegister(2, 0u)
        context.writeRegister(3, 0u)

        assertFailsWith<InvalidAsciiException> {
            DrawInstruction().execute(context, InstructionWord.fromInt(0xF123))
        }
    }

    private fun context(
        registers: Registers = Registers(),
        memoryBus: MemoryBus = MemoryBus(),
        screen: ScreenBuffer = ScreenBuffer(),
        keyboard: KeyboardInput = FakeKeyboardInput(0u)
    ): ExecutionContext {
        return ExecutionContext(
            registers = registers,
            memoryBus = memoryBus,
            screen = screen,
            keyboard = keyboard
        )
    }

    private class FakeKeyboardInput(
        private val value: UByte
    ) : KeyboardInput {
        override fun readByte(): UByte {
            return value
        }
    }
}
