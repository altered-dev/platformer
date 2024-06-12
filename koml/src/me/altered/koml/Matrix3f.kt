package me.altered.koml

open class Matrix3f(
    override var m00: Float = 0.0f,
    override var m01: Float = 0.0f,
    override var m02: Float = 0.0f,
    override var m10: Float = 0.0f,
    override var m11: Float = 0.0f,
    override var m12: Float = 0.0f,
    override var m20: Float = 0.0f,
    override var m21: Float = 0.0f,
    override var m22: Float = 0.0f,
) : Matrix3fc {
    override val minComponent: Float
        get() = TODO("Not yet implemented")
    override val maxComponent: Float
        get() = TODO("Not yet implemented")
    override val determinant: Float
        get() = TODO("Not yet implemented")

    override fun isFinite(): Boolean {
        TODO("Not yet implemented")
    }

    override fun copy(dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun negate(dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun get(component: Int): Float {
        TODO("Not yet implemented")
    }

    override fun invert(dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun transpose(dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun add(other: Matrix3fc, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun sub(other: Matrix3fc, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun mul(other: Matrix3fc, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun mul(scalar: Float, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun frc(other: Matrix3fc, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun frc(scalar: Float, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun mulLocal(other: Matrix3fc, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }

    override fun mulLocal(scalar: Float, dest: Matrix3f): Matrix3f {
        TODO("Not yet implemented")
    }


}
