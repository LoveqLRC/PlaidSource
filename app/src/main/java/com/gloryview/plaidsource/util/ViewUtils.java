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

        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, metrics));

        final float maxLineWidth = paint.measureText(text);

        if ((high - low) < precision) {
            return low;
        } else if (maxLineWidth > targetWidth) {
            return getSingleLineTextSize(text, paint, targetWidth, low, mid, precision, metrics);
        } else if (maxLineWidth < targetWidth) {
            return getSingleLineTextSize(text, paint, targetWidth, mid, high, precision, metrics);
        } else {
            return mid;
        }
    }
}
