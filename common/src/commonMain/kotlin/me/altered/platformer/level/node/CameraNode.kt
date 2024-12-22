package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import me.altered.platformer.level.data.Camera

open class CameraNode(
    open val obj: Camera,
) {

    open var position = Offset.Zero
        protected set
    open var rotation = 0.0f
        protected set
    open var zoom = 1.0f
        protected set
    open var followPlayer = Size.Zero
        protected set

    fun eval(time: Float) {
        position = Offset(obj.x.eval(time), obj.y.eval(time))
        rotation = obj.rotation.eval(time)
        zoom = obj.zoom.eval(time)
        followPlayer = Size(obj.followPlayerX.eval(time), obj.followPlayerY.eval(time))
    }

    fun toMutableObjectNode() = MutableCameraNode(obj.toMutableObject())
}
