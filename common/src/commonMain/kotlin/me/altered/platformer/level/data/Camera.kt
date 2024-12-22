package me.altered.platformer.level.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.Expression
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.expression.const
import me.altered.platformer.expression.toAnimatedFloatState

/**
 * The camera object that controls the viewport.
 *
 * TODO: move to object hierarchy
 */
@Serializable
@SerialName("camera")
open class Camera(
    open val x: Expression<Float> = const(0.0f),
    open val y: Expression<Float> = const(-2.0f),
    open val rotation: Expression<Float> = const(0.0f),
    open val zoom: Expression<Float> = const(1.0f),
    // TODO: move this functionality to the x and y expressions
    open val followPlayerX: Expression<Float> = const(1.0f),
    open val followPlayerY: Expression<Float> = const(1.0f),
) {

    fun toMutableObject() = MutableCamera(
        x = x.toAnimatedFloatState(InspectorInfo.X),
        y = y.toAnimatedFloatState(InspectorInfo.Y),
        rotation = rotation.toAnimatedFloatState(InspectorInfo.Rotation),
        zoom = zoom.toAnimatedFloatState(),
        followPlayerX = followPlayerX.toAnimatedFloatState(),
        followPlayerY = followPlayerY.toAnimatedFloatState(),
    )
}
