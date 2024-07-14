package me.altered.platformer.engine.node2d

import me.altered.koml.Vector2f
import me.altered.platformer.engine.node.Node

class Camera(
    name: String = "Camera",
    parent: Node? = null,
    position: Vector2f = defaultPosition,
    rotation: Float = defaultRotation,
    scale: Vector2f = defaultScale,
) : Node2D(name, parent, position, rotation, scale) {

}
