package me.altered.platformer.expression


class FloatSum(
    val a: Expression<Float>,
    val b: Expression<Float>,
) : Expression<Float> {

    override fun eval(time: Float): Float {
        return a.eval(time) + b.eval(time)
    }
}
