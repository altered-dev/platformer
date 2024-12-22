package me.altered.platformer.level.data

import me.altered.platformer.expression.AnimatedFloatState
import me.altered.platformer.expression.InspectorInfo

class MutableCamera(
    override val x: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.X),
    override val y: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.Y),
    override val rotation: AnimatedFloatState = AnimatedFloatState(0.0f, InspectorInfo.Rotation),
    override val zoom: AnimatedFloatState = AnimatedFloatState(1.0f),
    // TODO: move this functionality to the x and y expressions
    override val followPlayerX: AnimatedFloatState = AnimatedFloatState(1.0f),
    override val followPlayerY: AnimatedFloatState = AnimatedFloatState(1.0f),
) : Camera(x, y, rotation, zoom, followPlayerX, followPlayerY) {

    fun toObject() = Camera(
        x = x.toExpression(),
        y = y.toExpression(),
        rotation = rotation.toExpression(),
        zoom = zoom.toExpression(),
        followPlayerX = followPlayerX.toExpression(),
        followPlayerY = followPlayerY.toExpression(),
    )
}
