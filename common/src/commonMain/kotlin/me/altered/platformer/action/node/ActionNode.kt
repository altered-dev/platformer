package me.altered.platformer.action.node

import androidx.compose.ui.geometry.Offset

/**
 * A node that actions are composed of.
 */
sealed interface ActionNode {

    val position: Offset
}
