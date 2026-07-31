package d5700.core

import d5700.cpu.Registers
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus

class ExecutionContext(
    private val registers: Registers,
    private val memoryBus: MemoryBus,
    private val screen: ScreenBuffer,
    private val keyboard: KeyboardInput
) {
    fun readRegister(index: Int): UByte {
        return registers.readRegister(index)
    }

    fun writeRegister(index: Int, value: UByte) {
        registers.writeRegister(index, value)
    }

    fun setAddress(address: Int) {
        registers.setAddress(address)
    }

    fun toggleMemoryMode() {
        registers.toggleMemoryMode()
    }

    fun readMemoryAtAddress(): UByte {
        return memoryBus.read(registers.address, registers.useRom)
    }

    fun writeMemoryAtAddress(value: UByte, offset: Int = 0) {
        memoryBus.write(registers.address + offset, value, registers.useRom)
    }

    fun readKeyboard(): UByte {
        return keyboard.readByte()
    }

    fun draw(row: Int, column: Int, asciiValue: UByte) {
        screen.draw(row, column, asciiValue)
    }

    fun readTimer(): UByte {
        return registers.timer.read()
    }

    fun setTimer(value: UByte) {
        registers.timer.set(value)
    }

    fun tickTimer() {
        registers.timer.tick()
    }

    fun programCounterValue(): Int {
        return registers.programCounter.value
    }

    fun incrementProgramCounter() {
        registers.programCounter.increment()
    }

    fun skipNextInstruction() {
        registers.programCounter.skipNextInstruction()
    }

    fun jumpTo(address: Int) {
        registers.programCounter.jumpTo(address)
    }

    fun readInstructionByte(address: Int): UByte {
        return memoryBus.readInstructionByte(address)
    }

    fun resetState() {
        registers.reset()
        screen.clear()
    }
}
