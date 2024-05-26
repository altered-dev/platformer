package me.altered.platformer.editor.action

data class CommittedAction<P>(
    val action: Action<P>,
    val product: P,
)
