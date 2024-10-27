package me.altered.platformer.scene.editor

import me.altered.platformer.level.World
import me.altered.platformer.node.CanvasNode

class EditorScene : CanvasNode("editor") {

    private val world = +World()

    private val grid = world + Grid()
}
