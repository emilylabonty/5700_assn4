package d5700.cpu

import kotlin.test.Test
import kotlin.test.assertEquals

class TimerRegisterTest {

    @Test
    fun `timer starts at zero by default`() {
        val timer = TimerRegister()

        assertEquals(0u.toUByte(), timer.read())
    }

    @Test
    fun `timer can start with initial value`() {
        val timer = TimerRegister(10)

        assertEquals(10u.toUByte(), timer.read())
    }

    @Test
    fun `set updates timer value`() {
        val timer = TimerRegister()

        timer.set(0xABu.toUByte())

        assertEquals(0xABu.toUByte(), timer.read())
    }

    @Test
    fun `tick decrements nonzero timer`() {
        val timer = TimerRegister(3)

        timer.tick()

        assertEquals(2u.toUByte(), timer.read())
    }

    @Test
    fun `tick does not decrement below zero`() {
        val timer = TimerRegister(0)

        timer.tick()

        assertEquals(0u.toUByte(), timer.read())
    }

    @Test
    fun `reset sets timer to zero`() {
        val timer = TimerRegister(20)

        timer.reset()

        assertEquals(0u.toUByte(), timer.read())
    }
}