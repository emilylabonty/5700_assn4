package d5700.core

import d5700.cpu.CpuStopReason

data class EmulatorResult(
    val loadedByteCount: Int,
    val stopReason: CpuStopReason,
    val cyclesExecuted: Int,
    val finalProgramCounter: Int,
    val screenText: String
)