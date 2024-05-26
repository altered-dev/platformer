package me.altered.platformer.timeline

class NegatedFloat(
    override val original: Expression<Float>,
) : Expression.Transformed<Float> {

    override fun eval(time: Float): Float {
        return -original.eval(time)
    }

    override fun toString() = "-$original"
}

operator fun Expression<Float>.unaryMinus() = NegatedFloat(this)
