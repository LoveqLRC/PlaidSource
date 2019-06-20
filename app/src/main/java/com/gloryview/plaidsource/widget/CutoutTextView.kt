package com.gloryview.plaidsource.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.Bitmap.createBitmap
import androidx.core.content.res.ResourcesCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
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
    lateinit var text: String

    private var cutout: Bitmap? = null
    private var foregroundColor = Color.MAGENTA
    private var textY = 0f
    private var textX = 0f


    //读取xml配置参数，有就设置
    init {
        context.withStyledAttributes(attrs, R.styleable.CutoutTextView) {
            if (hasValue(R.styleable.CutoutTextView_android_fontFamily)) {

                try {
                    val font =
                        ResourcesCompat.getFont(
                            getContext(),
                            getResourceId(R.styleable.CutoutTextView_android_fontFamily, 0)
                        )
                    if (font != null) {
                        textPaint.typeface = font
                    }
                } catch (nfe: Resources.NotFoundException) {

                }
            }

            if (hasValue(R.styleable.CutoutTextView_foregroundColor)) {
                foregroundColor = getColor(R.styleable.CutoutTextView_foregroundColor, foregroundColor)
            }

            text = if (hasValue(R.styleable.CutoutTextView_android_text)) {
                getString(R.styleable.CutoutTextView_android_text)
            } else {
                ""
            }
        }

        maxTextSize = context.resources.getDimensionPixelSize(R.dimen.display_4_text_size).toFloat()


    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateTextPosition()
        createBitmap()
    }


    /**
     * 计算字体位置，字体的大小
     */
    private fun calculateTextPosition() {
        //实际的字体宽度*PHI=控件的宽度 (这里PHI黄金分隔值)
        val targetWidth = width / PHI
        //根据传入字体宽度 动态计算合适的字体大小
        val textSize = ViewUtils.getSingleLineTextSize(
            text, textPaint, targetWidth, 0f, maxTextSize, 0.5f,
            resources.displayMetrics
        )
        textPaint.textSize = textSize
        //https://chris.banes.me/2014/03/27/measuring-text/
        //计算字体的开始X坐标
        textX = (width - textPaint.measureText(text)) / 2
        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        //计算字体的开始Y坐标
        val textHeight = textBounds.height().toFloat()
        textY = (height + textHeight) / 2

    }

    private fun createBitmap() {
        // cutout资源
        cutout?.run {
            if (!isRecycled) {
                recycle()
            }
        }

        //https://blog.csdn.net/iispring/article/details/50472485#commentBox
        //PorterDuff.Mode.CLEAR相当于透明色的效果，具体功能见上面的分析
        textPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        //创建bitmap
        cutout = createBitmap(width, height).applyCanvas {
            drawColor(foregroundColor)
            drawText(text, textX, textY, textPaint)
        }
    }


    override fun onDraw(canvas: Canvas) {
        //画图
        canvas.drawBitmap(cutout!!, 0f, 0f, null)
    }


    companion object {

        private const val PHI = 1.6182f
    }

}