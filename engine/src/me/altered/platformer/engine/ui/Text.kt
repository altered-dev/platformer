package me.altered.platformer.engine.ui

import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.Shader
import org.jetbrains.skia.paragraph.FontCollection
import org.jetbrains.skia.paragraph.Paragraph
import org.jetbrains.skia.paragraph.ParagraphBuilder
import org.jetbrains.skia.paragraph.ParagraphStyle
import org.jetbrains.skia.paragraph.TextStyle

class Text(
    name: String = "UiNode",
    parent: Node? = null,
    width: Size = wrap(),
    height: Size = wrap(),
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : UiNode(name, parent, width, height, fill, stroke, strokeWidth) {

    private val textStyle = TextStyle().apply {
        foreground = fillPaint.nativePaint
        fontSize = 12.0f
        setFontFamily("Inter")
    }

    private val style = ParagraphStyle().apply {
        textStyle = this@Text.textStyle
    }

    private val collection = FontCollection().apply {
        setDefaultFontManager(FontMgr.default)
    }

    private var paragraph: Paragraph = makeParagraph(name)

    private fun makeParagraph(text: String): Paragraph {
        return ParagraphBuilder(style, collection).addText(text).build()
    }

    override var name: String
        get() = super.name
        set(value) {
            super.name = value
            paragraph = makeParagraph(value)
        }

    var direction by style::direction
    var alignment by style::alignment
    var maxLines by style::maxLinesCount
    var ellipsis by style::ellipsis

    override fun measure(width: Float, height: Float): Pair<Float, Float> {
        val w = when (val w = this.width) {
            is Size.Fixed -> w.value
            is Size.Expand -> width
            is Size.Wrap -> paragraph.minIntrinsicWidth
        }
        val h = when (val h = this.height) {
            is Size.Fixed -> h.value
            is Size.Expand -> height
            is Size.Wrap -> paragraph.height
        }
        isMeasured = true
        return w to h
    }

    override fun draw(canvas: Canvas) {
        paragraph
            // for some reason it cuts off when wrapped so adding 1 to the width
            .layout(bounds.width + 1.0f)
            .paint(canvas, 0.0f, 0.0f)
        // temporary
        isMeasured = false
    }
}
