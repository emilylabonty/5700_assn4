package d5700.cpu

class CpuClock(
    private val throttle: Boolean = false
) {
    private var timerAccumulator = 0

    fun afterInstruction(timer: TimerRegister) {
        updateTimer(timer)

        if (throttle) {
            Thread.sleep(CPU_CYCLE_MILLIS)
        }
    }

    fun reset() {
        timerAccumulator = 0
    }

    private fun updateTimer(timer: TimerRegister) {
        timerAccumulator += TIMER_HZ

        while (timerAccumulator >= CPU_HZ) {
            timer.tick()
            timerAccumulator -= CPU_HZ
        }
    }

    companion object {
        const val CPU_HZ = 500
        const val TIMER_HZ = 60
        const val CPU_CYCLE_MILLIS = 2L
    }
}