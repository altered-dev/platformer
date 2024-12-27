package me.altered.platformer.level

import me.altered.platformer.action.Action
import me.altered.platformer.action.Effect
import me.altered.platformer.action.MoveBy
import me.altered.platformer.action.PlayerCollided
import me.altered.platformer.action.RotateBy
import me.altered.platformer.expression.Easing
import me.altered.platformer.expression.const
import me.altered.platformer.level.data.Level
import me.altered.platformer.level.data.Rectangle
import me.altered.platformer.level.data.solid

val TestLevel = Level(
    name = "test",
    objects = listOf(
        Rectangle(
            id = 1,
            y = const(2.0f),
            width = const(20.0f),
            fill = listOf(
                const(solid(0xFFFFFFFF)),
            ),
            actions = listOf(
                Action(
                    trigger = PlayerCollided,
                    effects = listOf(
                        MoveBy(
                            x = 1.0f,
                            y = 0.0f,
                            easing = Easing.SineOut,
                            duration = 0.5f,
                            target = Effect.Target.Self,
                        ),
                        RotateBy(
                            angle = 15.0f,
                            easing = Easing.ExpoInOut,
                            duration = 1.0f,
                            target = Effect.Target.Self,
                        )
                    ),
                )
            ),
        )
    ),
)
