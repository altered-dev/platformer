package me.altered.platformer.level.objects

import androidx.compose.ui.graphics.drawscope.DrawScope

fun Object.Drawable.draw(scope: DrawScope) = scope.draw()

fun Object.EditorDrawable.drawInEditor(scope: DrawScope) = scope.drawInEditor()
