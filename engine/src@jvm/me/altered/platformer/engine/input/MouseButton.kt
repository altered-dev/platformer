package me.altered.platformer.engine.input

import me.altered.platformer.engine.util.IntEnum
import java.awt.event.MouseEvent.*

actual enum class MouseButton(override val code: Int) : IntEnum {
    N1(BUTTON1),
    N2(BUTTON2),
    N3(BUTTON3),
    N4(4),
    N5(5),
    ;
    actual companion object
}
