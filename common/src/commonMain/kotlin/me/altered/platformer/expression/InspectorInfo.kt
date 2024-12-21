package me.altered.platformer.expression

import me.altered.platformer.resources.Res
import me.altered.platformer.resources.angle
import me.altered.platformer.resources.circle
import me.altered.platformer.resources.corner
import me.altered.platformer.resources.group
import me.altered.platformer.resources.rectangle
import org.jetbrains.compose.resources.DrawableResource

/**
 * Info about a property that is only used in inspector.
 */
data class InspectorInfo(
    val name: String,
    val icon: Icon,
) {

    sealed interface Icon {

        data class Drawable(val resource: DrawableResource) : Icon

        data class Text(val text: String) : Icon
    }

    companion object {

        // Nodes

        val Rectangle = InspectorInfo("Rectangle", Icon.Drawable(Res.drawable.rectangle))

        val Ellipse = InspectorInfo("Ellipse", Icon.Drawable(Res.drawable.circle))

        val Group = InspectorInfo("Group", Icon.Drawable(Res.drawable.group))

        // Properties

        val X = InspectorInfo("X position", Icon.Text("X"))

        val Y = InspectorInfo("Y position", Icon.Text("Y"))

        val Width = InspectorInfo("Width", Icon.Text("W"))

        val Height = InspectorInfo("Height", Icon.Text("H"))

        val Rotation = InspectorInfo("Rotation", Icon.Drawable(Res.drawable.angle))

        val CornerRadius = InspectorInfo("Corner radius", Icon.Drawable(Res.drawable.corner))

        val Unspecified = InspectorInfo("", Icon.Text(""))
    }
}
