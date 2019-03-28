package com.gloryview.plaidsource.util;

import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @ProjectName: PlaidSource
 * @Package: com.gloryview.plaidsource.util
 * @ClassName: ViewUtils
 * @Description: java类作用描述
 * @Author: Rc
 * @CreateDate: 2019/3/4 3:42 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/3/4 3:42 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ViewUtils {
    private ViewUtils() {
    }


    /**
     * 通过递归查找返回最佳的单行文本字体大小
     *
     * @param text        待绘制的文本
     * @param paint       画笔
     * @param targetWidth 目标宽度
     * @param low         最小字体
     * @param high        最大字体
     * @param precision   精度（最大字体和最小字体相差大小）
     * @param metrics     屏幕相关信息
     * @return 最佳的单行文本字体大小
     */
    public static float getSingleLineTextSize(String text,
                                              TextPaint paint,
                                              float targetWidth,
                                              float low,
                                              float high,
                                              float precision,
                                              DisplayMetrics metrics) {
        final float mid = (low + high) / 2.0f;
        //给画笔设置字体大小
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, metrics));
        //计算在该字体下，画这些字需要多宽
        final float maxLineWidth = paint.measureText(text);

        if ((high - low) < precision) {
            //high为最大允许的字体，low为最小允许的字体，我们的目标是计算字体大小最合适当前给定的宽度targetWidth
            return low;
        } else if (maxLineWidth > targetWidth) {//计算出的字体太大了，应该再小一点
            //如果计算出当前的宽度超出目标的宽度，那么重新计算合适字体的大小，下一次计算的最大字体值为mid
            return getSingleLineTextSize(text, paint, targetWidth, low, mid, precision, metrics);
        } else if (maxLineWidth < targetWidth) {//计算出的字体大小了，应该再大一点
            //如果计算出当前的宽度小于目标的宽度，那么重新计算合适字体的大小，下一次计算的最小字体值为mid
            return getSingleLineTextSize(text, paint, targetWidth, mid, high, precision, metrics);
        } else {
            //计算出来的字体刚刚好
            return mid;
        }
    }
}
