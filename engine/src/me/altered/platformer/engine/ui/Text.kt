package me.altered.platformer.engine.ui

import me.altered.platformer.engine.node.Node
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.Shader
import org.jetbrains.skia.paragraph.FontCollection
import org.jetbrains.skia.paragraph.Paragraph
import org.jetbrains.skia.paragraph.ParagraphBuilder
import org.jetbrains.skia.paragraph.ParagraphStyle

class Text(
    name: String = "UiNode",
    parent: Node? = null,
    width: Size = wrap(),
    height: Size = wrap(),
    fill: Shader = Shader.makeEmpty(),
    stroke: Shader = Shader.makeEmpty(),
    strokeWidth: Float = 0.0f,
) : UiNode(name, parent, width, height, fill, stroke, strokeWidth) {

    private val style = ParagraphStyle().apply {
        textStyle.foreground = fillPaint.nativePaint
    }

    private val builder = ParagraphBuilder(style, FontCollection().setDefaultFontManager(FontMgr.default))

    private var paragraph: Paragraph = builder.addText(name).build()

    override var name: String
        get() = super.name
        set(value) {
            super.name = value
            paragraph = builder.addText(value).build()
        }

    var direction by style::direction
    var alignment by style::alignment
    var maxLines by style::maxLinesCount
    var ellipsis by style::ellipsis

    override fun measure(width: Float, height: Float): Pair<Float, Float> {

        val w = when (val w = this.width) {
            is Size.Fixed -> w.value
            is Size.Expand -> width
            is Size.Wrap -> paragraph.maxWidth
        }
        val h = when (val h = this.height) {
            is Size.Fixed -> h.value
            is Size.Expand -> height
            is Size.Wrap -> paragraph.height
        }
        return w to h
    }

    override fun draw(canvas: Canvas) {
        paragraph.paint(canvas, 0.0f, 0.0f)
    }
}