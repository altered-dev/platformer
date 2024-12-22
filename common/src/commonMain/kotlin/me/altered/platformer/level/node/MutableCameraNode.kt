package me.altered.platformer.level.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import me.altered.platformer.level.data.MutableCamera

class MutableCameraNode(
    override val obj: MutableCamera,
) : CameraNode(obj) {

    override var position: Offset
        get() = Offset(obj.x.staticValue, obj.y.staticValue)
        set(_) = Unit
    override var rotation: Float
        get() = obj.rotation.staticValue
        set(_) = Unit
    override var zoom: Float
        get() = obj.zoom.staticValue
        set(_) = Unit
    override var followPlayer: Size
        get() = Size(obj.followPlayerX.staticValue, obj.followPlayerY.staticValue)
        set(_) = Unit
}
