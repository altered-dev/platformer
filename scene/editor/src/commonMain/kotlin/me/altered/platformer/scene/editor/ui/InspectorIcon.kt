package me.altered.platformer.scene.editor.ui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import me.altered.platformer.expression.InspectorInfo
import me.altered.platformer.ui.Icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun InspectorIcon(
    info: InspectorInfo,
) {
    when (val icon = info.icon) {
        is InspectorInfo.Icon.Drawable -> Icon(painterResource(icon.resource), tint = Color(0xFFCCCCCC))
        is InspectorInfo.Icon.Text -> IconText(icon.text)
    }
}

@Composable
private fun IconText(
    text: String,
    modifier: Modifier = Modifier,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = Color(0xFFCCCCCC),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
        ),
    )
}
