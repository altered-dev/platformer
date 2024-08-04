package me.altered.platformer.objects

import me.altered.platformer.timeline.Expression
import me.altered.platformer.timeline.named

class Reference(
    private val obj: ObjectNode,
) : ObjectContext {

    override val x: Expression<Float> = named("${obj.name}.x") { obj.xExpr.eval(it) }
    override val y: Expression<Float> = named("${obj.name}.y") { obj.yExpr.eval(it) }
    override val rotation: Expression<Float> = named("${obj.name}.rotation") { obj.rotationExpr.eval(it) }
}
