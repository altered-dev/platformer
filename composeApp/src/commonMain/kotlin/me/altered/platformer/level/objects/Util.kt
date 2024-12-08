package me.altered.platformer.level.objects

import androidx.compose.ui.graphics.drawscope.DrawScope
import me.altered.platformer.level.node.ObjectNode

fun ObjectNode.Drawable.draw(scope: DrawScope) = scope.draw()

fun ObjectNode.EditorDrawable.drawInEditor(scope: DrawScope) = scope.drawInEditor()
