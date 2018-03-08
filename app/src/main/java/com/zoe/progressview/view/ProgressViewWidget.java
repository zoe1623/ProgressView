package com.zoe.progressview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.zoe.progressview.R;

/**
 * 自定义圆弧进度条
 * @author seven
 */
public class ProgressViewWidget extends View {

    private static final int[] SECTION_COLORS = {Color.rgb(255, 120, 110), Color.rgb(255, 190, 80), Color.rgb(90, 210, 110)};
    private Paint mPaint;
    private int mWidth, mHeight, mWidthCenter, mHeightCenter;
    private Bitmap drawBitmap;
    private int currentCount;
    private int currentDegree;


    private ProgressScrollCallback callback;

    public ProgressViewWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ProgressViewWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressViewWidget(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        setBackgroundColor(Color.rgb(217, 217, 217));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dip2px(20));
        mPaint.setTextAlign(Paint.Align.CENTER);
        drawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.score_bg);
        textBigSize = dip2px(50);
        textSize = dip2px(14);
    }

    private RectF rectF, rectBlackBg;
    private LinearGradient shader;
    private int swit = 1;
    private int textBigSize = 150;
    private int textSize = 42;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShader(shader);
        mPaint.setStyle(Style.STROKE);
        canvas.drawArc(rectBlackBg, 150, swit, false, mPaint);
        canvas.drawBitmap(drawBitmap, null, rectF, mPaint);
        mPaint.setShader(null);
        mPaint.setStyle(Style.FILL);

        mPaint.setTextSize(textSize);
        mPaint.setColor(Color.GRAY);
        canvas.drawText("当前评分", mWidthCenter, mHeightCenter - textBigSize, mPaint);

        mPaint.setTextSize(textBigSize);
        mPaint.setColor(Color.rgb(125,210,100));


        if (swit < currentDegree) {
            canvas.drawText(((int)(swit/2.4)) + "", mWidthCenter, mHeightCenter, mPaint);
            swit = swit + 3;
            postInvalidate();
        } else if (swit > currentDegree) {
            canvas.drawText(((int)(swit/2.4)) + "", mWidthCenter, mHeightCenter, mPaint);
            swit = swit - 1;
            postInvalidate();
        } else {
            canvas.drawText(currentCount + "", mWidthCenter, mHeightCenter, mPaint);
            mPaint.setTextSize(textSize);
            mPaint.setColor(Color.rgb(255,120,120));
            if (this.callback != null)
                this.callback.actionFinish();
        }
    }

    /***
     * 设置当前的进度值
     *
     * @param currentCount
     */
    public void setCurrentCount(int currentCount, int percent) {
        this.currentCount = currentCount;
        currentDegree = (int) (240 * percent / 100);
        swit = 1;
        postInvalidate();
    }

    public void setWidthAndHeight(int width, int height) {
        mWidth = width;
        mHeight = height;
        mWidthCenter = width / 2;
        mHeightCenter = height / 2;
        rectF = new RectF(0, 0, mWidth, mHeight);
        rectBlackBg = new RectF(dip2px(12), dip2px(12), mWidth - dip2px(12), mHeight - dip2px(12));
        shader = new LinearGradient(0, mHeight, mWidth, mHeight, SECTION_COLORS, null, Shader.TileMode.MIRROR);
    }

    public void setCallback(ProgressScrollCallback callback) {
        this.callback = callback;
    }

    public interface ProgressScrollCallback {
        public void actionFinish();
    }

    private int dip2px(int dp){
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

}
