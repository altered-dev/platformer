package me.altered.platformer.expression

import androidx.compose.runtime.Immutable
import me.altered.platformer.resources.Res
import me.altered.platformer.resources.actions
import me.altered.platformer.resources.angle
import me.altered.platformer.resources.circle
import me.altered.platformer.resources.corner
import me.altered.platformer.resources.fill
import me.altered.platformer.resources.group
import me.altered.platformer.resources.move
import me.altered.platformer.resources.outline
import me.altered.platformer.resources.rectangle
import me.altered.platformer.resources.rotate
import me.altered.platformer.resources.scale
import me.altered.platformer.resources.time
import me.altered.platformer.ui.Icon
import org.jetbrains.compose.resources.DrawableResource

/**
 * Info about a property that is only used in inspector.
 */
@Immutable
data class InspectorInfo(
    val name: String,
    val icon: Icon,
) {

    @Immutable
    sealed interface Icon {

        @Immutable
        data class Drawable(val resource: DrawableResource) : Icon

        @Immutable
        data class Text(val text: String) : Icon
    }

    companion object {

        // Nodes

        val Rectangle = InspectorInfo("Rectangle", Icon.Drawable(Res.drawable.rectangle))

        val Ellipse = InspectorInfo("Ellipse", Icon.Drawable(Res.drawable.circle))

        val Group = InspectorInfo("Group", Icon.Drawable(Res.drawable.group))

        val Move = InspectorInfo("Move", Icon.Drawable(Res.drawable.move))

        val Rotate = InspectorInfo("Rotate", Icon.Drawable(Res.drawable.rotate))

        val Scale = InspectorInfo("Scale", Icon.Drawable(Res.drawable.scale))

        // Properties

        val X = InspectorInfo("X position", Icon.Text("X"))

        val Y = InspectorInfo("Y position", Icon.Text("Y"))

        val Width = InspectorInfo("Width", Icon.Text("W"))

        val Height = InspectorInfo("Height", Icon.Text("H"))

        val Rotation = InspectorInfo("Rotation", Icon.Drawable(Res.drawable.angle))

        val CornerRadius = InspectorInfo("Corner radius", Icon.Drawable(Res.drawable.corner))

        val Fill = InspectorInfo("Fill", Icon.Drawable(Res.drawable.fill))

        val Outline = InspectorInfo("Outline", Icon.Drawable(Res.drawable.outline))

        val OutlineWidth = InspectorInfo("Outline width", Icon.Drawable(Res.drawable.outline))

        val Actions = InspectorInfo("Actions", Icon.Drawable(Res.drawable.actions))

        val Duration = InspectorInfo("Duration", Icon.Drawable(Res.drawable.time))

        val Background = InspectorInfo("Background", Icon.Drawable(Res.drawable.fill))

        val Unspecified = InspectorInfo("", Icon.Text(""))
    }
}
