package d5700.core

import d5700.cpu.Registers
import d5700.io.KeyboardInput
import d5700.io.ScreenBuffer
import d5700.memory.MemoryBus

class ExecutionContext(
    val registers: Registers,
    val memoryBus: MemoryBus,
    val screen: ScreenBuffer,
    val keyboard: KeyboardInput
)