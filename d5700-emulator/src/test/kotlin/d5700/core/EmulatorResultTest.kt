package d5700.core

import d5700.cpu.CpuStopReason
import kotlin.test.Test
import kotlin.test.assertEquals

class EmulatorResultTest {

    @Test
    fun `emulator result stores run summary`() {
        val result = EmulatorResult(
            loadedByteCount = 4,
            stopReason = CpuStopReason.HALTED,
            cyclesExecuted = 2,
            finalProgramCounter = 6,
            screenText = "screen"
        )

        assertEquals(4, result.loadedByteCount)
        assertEquals(CpuStopReason.HALTED, result.stopReason)
        assertEquals(2, result.cyclesExecuted)
        assertEquals(6, result.finalProgramCounter)
        assertEquals("screen", result.screenText)
    }

    @Test
    fun `emulator result supports data class copy`() {
        val result = EmulatorResult(
            loadedByteCount = 4,
            stopReason = CpuStopReason.HALTED,
            cyclesExecuted = 2,
            finalProgramCounter = 6,
            screenText = "screen"
        )

        val updated = result.copy(cyclesExecuted = 3)

        assertEquals(3, updated.cyclesExecuted)
        assertEquals(4, updated.loadedByteCount)
    }
}