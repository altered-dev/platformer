package me.altered.platformer.engine.math

import org.jetbrains.skia.Matrix33

inline var Matrix33.translateX: Float
    get() = mat[2]
    set(value) { mat[2] = value }

inline var Matrix33.translateY: Float
    get() = mat[5]
    set(value) { mat[5] = value }

inline var Matrix33.scaleX: Float
    get() = mat[0]
    set(value) { mat[0] = value }

inline var Matrix33.scaleY: Float
    get() = mat[4]
    set(value) { mat[4] = value }

inline var Matrix33.skewX: Float
    get() = mat[1]
    set(value) { mat[1] = value }

inline var Matrix33.skewY: Float
    get() = mat[3]
    set(value) { mat[3] = value }
