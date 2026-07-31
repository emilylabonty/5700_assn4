package d5700.instruction.concrete

import d5700.core.ExecutionContext
import d5700.instruction.Instruction
import d5700.instruction.InstructionWord

class ConvertToBase10Instruction : Instruction() {

    private var registerX: Int = 0

    override fun decode(word: InstructionWord) {
        registerX = word.registerX()
    }

    override fun perform(context: ExecutionContext) {
        val value = context.registers.readRegister(registerX).toInt()
        val hundreds = value / 100
        val tens = (value / 10) % 10
        val ones = value % 10
        val address = context.registers.address

        context.memoryBus.write(address, hundreds.toUByte(), context.registers.useRom)
        context.memoryBus.write(address + 1, tens.toUByte(), context.registers.useRom)
        context.memoryBus.write(address + 2, ones.toUByte(), context.registers.useRom)
    }
}