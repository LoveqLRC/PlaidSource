package com.gloryview.plaidsource.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Bitmap.createBitmap
import androidx.core.content.res.ResourcesCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import com.gloryview.plaidsource.R
import com.gloryview.plaidsource.util.ViewUtils


/**
 *
 * @ProjectName:    PlaidSource
 * @Package:        com.gloryview.plaidsource.widget
 * @ClassName:      CutoutTextView
 * @Description:     java类作用描述
 * @Author:         Rc
 * @CreateDate:     2019/3/4 2:55 PM
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/3/4 2:55 PM
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class CutoutTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val maxTextSize: Float
    private val text: String

    private var cutout: Bitmap? = null
    private var foregroundColor = Color.MAGENTA
    private var textY = 0f
    private var textX = 0f

    init {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.CutoutTextView, 0, 0)

        if (a.hasValue(R.styleable.CutoutTextView_android_fontFamily)) {

            try {
                val font =
                    ResourcesCompat.getFont(
                        getContext(),
                        a.getResourceId(R.styleable.CutoutTextView_android_fontFamily, 0)
                    )
                if (font != null) {
                    textPaint.typeface = font
                }
            } catch (nfe: Resources.NotFoundException) {

            }
        }

        if (a.hasValue(R.styleable.CutoutTextView_foregroundColor)) {
            foregroundColor = a.getColor(R.styleable.CutoutTextView_foregroundColor, foregroundColor)
        }

        text = if (a.hasValue(R.styleable.CutoutTextView_android_text)) {
            a.getString(R.styleable.CutoutTextView_android_text)
        } else {
            ""
        }


        maxTextSize = context.resources.getDimensionPixelSize(R.dimen.display_4_text_size).toFloat()

        a.recycle()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateTextPosition()
        createBitmap()
    }


    private fun calculateTextPosition() {
        val targetWidth = width / PHI
        val textSize = ViewUtils.getSingleLineTextSize(
            text, textPaint, targetWidth, 0f, maxTextSize, 0.5f,
            resources.displayMetrics
        )
        textPaint.textSize = textSize
        //https://chris.banes.me/2014/03/27/measuring-text/
        textX = (width - textPaint.measureText(text)) / 2
        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        val textHeight = textBounds.height().toFloat()
        textY = (height + textHeight) / 2

    }

    private fun createBitmap() {
        cutout?.run {
            if (!isRecycled) {
                recycle()
            }
        }
        //https://blog.csdn.net/iispring/article/details/50472485#commentBox

        textPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        cutout = createBitmap(width, height).applyCanvas {
            drawColor(foregroundColor)
            drawText(text, textX, textY, textPaint)
        }
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(cutout, 0f, 0f, null)
    }


    companion object {

        private const val PHI = 1.6182f
    }

}