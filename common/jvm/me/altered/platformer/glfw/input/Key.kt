package me.altered.platformer.glfw.input

import me.altered.platformer.glfw.IntEnum
import org.lwjgl.glfw.GLFW.GLFW_KEY_0
import org.lwjgl.glfw.GLFW.GLFW_KEY_1
import org.lwjgl.glfw.GLFW.GLFW_KEY_2
import org.lwjgl.glfw.GLFW.GLFW_KEY_3
import org.lwjgl.glfw.GLFW.GLFW_KEY_4
import org.lwjgl.glfw.GLFW.GLFW_KEY_5
import org.lwjgl.glfw.GLFW.GLFW_KEY_6
import org.lwjgl.glfw.GLFW.GLFW_KEY_7
import org.lwjgl.glfw.GLFW.GLFW_KEY_8
import org.lwjgl.glfw.GLFW.GLFW_KEY_9
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE
import org.lwjgl.glfw.GLFW.GLFW_KEY_B
import org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSLASH
import org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE
import org.lwjgl.glfw.GLFW.GLFW_KEY_C
import org.lwjgl.glfw.GLFW.GLFW_KEY_CAPS_LOCK
import org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_E
import org.lwjgl.glfw.GLFW.GLFW_KEY_END
import org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER
import org.lwjgl.glfw.GLFW.GLFW_KEY_EQUAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_KEY_F
import org.lwjgl.glfw.GLFW.GLFW_KEY_F1
import org.lwjgl.glfw.GLFW.GLFW_KEY_F10
import org.lwjgl.glfw.GLFW.GLFW_KEY_F11
import org.lwjgl.glfw.GLFW.GLFW_KEY_F12
import org.lwjgl.glfw.GLFW.GLFW_KEY_F13
import org.lwjgl.glfw.GLFW.GLFW_KEY_F14
import org.lwjgl.glfw.GLFW.GLFW_KEY_F15
import org.lwjgl.glfw.GLFW.GLFW_KEY_F16
import org.lwjgl.glfw.GLFW.GLFW_KEY_F17
import org.lwjgl.glfw.GLFW.GLFW_KEY_F18
import org.lwjgl.glfw.GLFW.GLFW_KEY_F19
import org.lwjgl.glfw.GLFW.GLFW_KEY_F2
import org.lwjgl.glfw.GLFW.GLFW_KEY_F20
import org.lwjgl.glfw.GLFW.GLFW_KEY_F21
import org.lwjgl.glfw.GLFW.GLFW_KEY_F22
import org.lwjgl.glfw.GLFW.GLFW_KEY_F23
import org.lwjgl.glfw.GLFW.GLFW_KEY_F24
import org.lwjgl.glfw.GLFW.GLFW_KEY_F25
import org.lwjgl.glfw.GLFW.GLFW_KEY_F3
import org.lwjgl.glfw.GLFW.GLFW_KEY_F4
import org.lwjgl.glfw.GLFW.GLFW_KEY_F5
import org.lwjgl.glfw.GLFW.GLFW_KEY_F6
import org.lwjgl.glfw.GLFW.GLFW_KEY_F7
import org.lwjgl.glfw.GLFW.GLFW_KEY_F8
import org.lwjgl.glfw.GLFW.GLFW_KEY_F9
import org.lwjgl.glfw.GLFW.GLFW_KEY_G
import org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT
import org.lwjgl.glfw.GLFW.GLFW_KEY_H
import org.lwjgl.glfw.GLFW.GLFW_KEY_HOME
import org.lwjgl.glfw.GLFW.GLFW_KEY_I
import org.lwjgl.glfw.GLFW.GLFW_KEY_INSERT
import org.lwjgl.glfw.GLFW.GLFW_KEY_J
import org.lwjgl.glfw.GLFW.GLFW_KEY_K
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DECIMAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_DIVIDE
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_EQUAL
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_MULTIPLY
import org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT
import org.lwjgl.glfw.GLFW.GLFW_KEY_L
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_BRACKET
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SUPER
import org.lwjgl.glfw.GLFW.GLFW_KEY_M
import org.lwjgl.glfw.GLFW.GLFW_KEY_MENU
import org.lwjgl.glfw.GLFW.GLFW_KEY_MINUS
import org.lwjgl.glfw.GLFW.GLFW_KEY_N
import org.lwjgl.glfw.GLFW.GLFW_KEY_NUM_LOCK
import org.lwjgl.glfw.GLFW.GLFW_KEY_O
import org.lwjgl.glfw.GLFW.GLFW_KEY_P
import org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_PAGE_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_PAUSE
import org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD
import org.lwjgl.glfw.GLFW.GLFW_KEY_PRINT_SCREEN
import org.lwjgl.glfw.GLFW.GLFW_KEY_Q
import org.lwjgl.glfw.GLFW.GLFW_KEY_R
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_SCROLL_LOCK
import org.lwjgl.glfw.GLFW.GLFW_KEY_SEMICOLON
import org.lwjgl.glfw.GLFW.GLFW_KEY_SLASH
import org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE
import org.lwjgl.glfw.GLFW.GLFW_KEY_T
import org.lwjgl.glfw.GLFW.GLFW_KEY_TAB
import org.lwjgl.glfw.GLFW.GLFW_KEY_U
import org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_V
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_KEY_WORLD_1
import org.lwjgl.glfw.GLFW.GLFW_KEY_WORLD_2
import org.lwjgl.glfw.GLFW.GLFW_KEY_X
import org.lwjgl.glfw.GLFW.GLFW_KEY_Y
import org.lwjgl.glfw.GLFW.GLFW_KEY_Z
import org.lwjgl.glfw.GLFW.glfwGetKeyScancode

enum class Key(override val code: Int) : IntEnum {
    UNKNOWN(GLFW_KEY_UNKNOWN),
    SPACE(GLFW_KEY_SPACE),
    APOSTROPHE(GLFW_KEY_APOSTROPHE),
    COMMA(GLFW_KEY_COMMA),
    MINUS(GLFW_KEY_MINUS),
    PERIOD(GLFW_KEY_PERIOD),
    SLASH(GLFW_KEY_SLASH),
    N0(GLFW_KEY_0),
    N1(GLFW_KEY_1),
    N2(GLFW_KEY_2),
    N3(GLFW_KEY_3),
    N4(GLFW_KEY_4),
    N5(GLFW_KEY_5),
    N6(GLFW_KEY_6),
    N7(GLFW_KEY_7),
    N8(GLFW_KEY_8),
    N9(GLFW_KEY_9),
    SEMICOLON(GLFW_KEY_SEMICOLON),
    EQUAL(GLFW_KEY_EQUAL),
    A(GLFW_KEY_A),
    B(GLFW_KEY_B),
    C(GLFW_KEY_C),
    D(GLFW_KEY_D),
    E(GLFW_KEY_E),
    F(GLFW_KEY_F),
    G(GLFW_KEY_G),
    H(GLFW_KEY_H),
    I(GLFW_KEY_I),
    J(GLFW_KEY_J),
    K(GLFW_KEY_K),
    L(GLFW_KEY_L),
    M(GLFW_KEY_M),
    N(GLFW_KEY_N),
    O(GLFW_KEY_O),
    P(GLFW_KEY_P),
    Q(GLFW_KEY_Q),
    R(GLFW_KEY_R),
    S(GLFW_KEY_S),
    T(GLFW_KEY_T),
    U(GLFW_KEY_U),
    V(GLFW_KEY_V),
    W(GLFW_KEY_W),
    X(GLFW_KEY_X),
    Y(GLFW_KEY_Y),
    Z(GLFW_KEY_Z),
    LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET),
    BACKSLASH(GLFW_KEY_BACKSLASH),
    RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),
    GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT),
    WORLD_1(GLFW_KEY_WORLD_1),
    WORLD_2(GLFW_KEY_WORLD_2),
    ESCAPE(GLFW_KEY_ESCAPE),
    ENTER(GLFW_KEY_ENTER),
    TAB(GLFW_KEY_TAB),
    BACKSPACE(GLFW_KEY_BACKSPACE),
    INSERT(GLFW_KEY_INSERT),
    DELETE(GLFW_KEY_DELETE),
    RIGHT(GLFW_KEY_RIGHT),
    LEFT(GLFW_KEY_LEFT),
    DOWN(GLFW_KEY_DOWN),
    UP(GLFW_KEY_UP),
    PAGE_UP(GLFW_KEY_PAGE_UP),
    PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
    HOME(GLFW_KEY_HOME),
    END(GLFW_KEY_END),
    CAPS_LOCK(GLFW_KEY_CAPS_LOCK),
    SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),
    NUM_LOCK(GLFW_KEY_NUM_LOCK),
    PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),
    PAUSE(GLFW_KEY_PAUSE),
    F1(GLFW_KEY_F1),
    F2(GLFW_KEY_F2),
    F3(GLFW_KEY_F3),
    F4(GLFW_KEY_F4),
    F5(GLFW_KEY_F5),
    F6(GLFW_KEY_F6),
    F7(GLFW_KEY_F7),
    F8(GLFW_KEY_F8),
    F9(GLFW_KEY_F9),
    F10(GLFW_KEY_F10),
    F11(GLFW_KEY_F11),
    F12(GLFW_KEY_F12),
    F13(GLFW_KEY_F13),
    F14(GLFW_KEY_F14),
    F15(GLFW_KEY_F15),
    F16(GLFW_KEY_F16),
    F17(GLFW_KEY_F17),
    F18(GLFW_KEY_F18),
    F19(GLFW_KEY_F19),
    F20(GLFW_KEY_F20),
    F21(GLFW_KEY_F21),
    F22(GLFW_KEY_F22),
    F23(GLFW_KEY_F23),
    F24(GLFW_KEY_F24),
    F25(GLFW_KEY_F25),
    KP_0(GLFW_KEY_KP_0),
    KP_1(GLFW_KEY_KP_1),
    KP_2(GLFW_KEY_KP_2),
    KP_3(GLFW_KEY_KP_3),
    KP_4(GLFW_KEY_KP_4),
    KP_5(GLFW_KEY_KP_5),
    KP_6(GLFW_KEY_KP_6),
    KP_7(GLFW_KEY_KP_7),
    KP_8(GLFW_KEY_KP_8),
    KP_9(GLFW_KEY_KP_9),
    KP_DECIMAL(GLFW_KEY_KP_DECIMAL),
    KP_DIVIDE(GLFW_KEY_KP_DIVIDE),
    KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
    KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT),
    KP_ADD(GLFW_KEY_KP_ADD),
    KP_ENTER(GLFW_KEY_KP_ENTER),
    KP_EQUAL(GLFW_KEY_KP_EQUAL),
    LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
    LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL),
    LEFT_ALT(GLFW_KEY_LEFT_ALT),
    LEFT_SUPER(GLFW_KEY_LEFT_SUPER),
    RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
    RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
    RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
    RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER),
    MENU(GLFW_KEY_MENU),
    ;
    val scancode: Int
        get() = glfwGetKeyScancode(code)

    companion object {
        val LAST = MENU
    }
}
