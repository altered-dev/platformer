package me.altered.platformer.level.node

import me.altered.platformer.level.data.MutableObject

sealed interface MutableObjectNode : ObjectNode {

    override val obj: MutableObject

    @set:Deprecated("Use MutableGroupNode.addChild instead", ReplaceWith("value.addChild(this)"))
    override var parent: MutableGroupNode?

    override fun toMutableObjectNode() = this
}