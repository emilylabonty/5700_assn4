package d5700.cpu

class CpuClock(
    private val throttle: Boolean = false
) {
    private var timerAccumulator = 0

    fun afterInstruction(tickTimer: () -> Unit) {
        updateTimer(tickTimer)

        if (throttle) {
            Thread.sleep(CPU_CYCLE_MILLIS)
        }
    }

    private fun updateTimer(tickTimer: () -> Unit) {
        timerAccumulator += TIMER_HZ

        while (timerAccumulator >= CPU_HZ) {
            tickTimer()
            timerAccumulator -= CPU_HZ
        }
    }

    fun reset() {
        timerAccumulator = 0
    }


    companion object {
        const val CPU_HZ = 500
        const val TIMER_HZ = 60
        const val CPU_CYCLE_MILLIS = 2L
    }
}