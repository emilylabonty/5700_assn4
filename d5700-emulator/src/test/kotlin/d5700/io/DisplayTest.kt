package d5700.io

import kotlin.test.Test
import kotlin.test.assertSame

class DisplayTest {

    @Test
    fun `display receives screen buffer`() {
        val screenBuffer = ScreenBuffer()
        val display = FakeDisplay()

        display.render(screenBuffer)

        assertSame(screenBuffer, display.lastRenderedScreenBuffer)
    }

    private class FakeDisplay : Display {
        var lastRenderedScreenBuffer: ScreenBuffer? = null
            private set

        override fun render(screenBuffer: ScreenBuffer) {
            lastRenderedScreenBuffer = screenBuffer
        }
    }
}