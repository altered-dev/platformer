package me.altered.platformer.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.circle
import me.altered.platformer.resources.collision

/**
 * An event that kicks off the action.
 */
@Serializable
@SerialName("trigger")
sealed interface Trigger {

    val inspectorInfo: InspectorInfo

    /**
     * Checks if the specified trigger can be used to start the action
     * this trigger belongs to.
     */
    infix fun matches(other: Trigger): Boolean = equals(other)
}

/**
 * A trigger that can be used to fire an action.
 *
 * Mostly just a marker interface to prevent unwanted triggers in some places.
 */
@Serializable
@SerialName("fire_trigger")
sealed interface FireTrigger : Trigger

/**
 * Test trigger. Unused in editor.
 */
@Serializable
@SerialName("test")
data object TestTrigger : FireTrigger {

    override val inspectorInfo = InspectorInfo("Test", InspectorInfo.Icon.Drawable(Res.drawable.circle))
}

/**
 * A trigger that fires when player starts colliding with the object.
 */
@Serializable
@SerialName("player_collided")
data object PlayerCollided : FireTrigger {

    override val inspectorInfo = InspectorInfo("Player collided", InspectorInfo.Icon.Drawable(Res.drawable.collision))
}
