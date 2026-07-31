package d5700.cpu

import kotlin.test.Test
import kotlin.test.assertEquals

class CpuClockTest {

    @Test
    fun `afterInstruction does not tick timer immediately`() {
        val clock = CpuClock()
        val timer = TimerRegister(10)

        clock.afterInstruction(timer)

        assertEquals(10u.toUByte(), timer.read())
    }

    @Test
    fun `afterInstruction ticks timer according to 60hz timer and 500hz cpu`() {
        val clock = CpuClock()
        val timer = TimerRegister(10)

        repeat(9) {
            clock.afterInstruction(timer)
        }

        assertEquals(9u.toUByte(), timer.read())
    }

    @Test
    fun `afterInstruction can tick timer multiple times over many cpu cycles`() {
        val clock = CpuClock()
        val timer = TimerRegister(10)

        repeat(17) {
            clock.afterInstruction(timer)
        }

        assertEquals(8u.toUByte(), timer.read())
    }

    @Test
    fun `timer never decrements below zero through clock`() {
        val clock = CpuClock()
        val timer = TimerRegister(0)

        repeat(100) {
            clock.afterInstruction(timer)
        }

        assertEquals(0u.toUByte(), timer.read())
    }

    @Test
    fun `reset clears clock accumulator`() {
        val clock = CpuClock()
        val timer = TimerRegister(10)

        repeat(8) {
            clock.afterInstruction(timer)
        }

        clock.reset()
        clock.afterInstruction(timer)

        assertEquals(10u.toUByte(), timer.read())
    }
}