package com.github.ggggxiaolong.customview.Circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public final class CircleView extends View {

    private Paint mPaint;
    private final float STROKE_WIDTH = 1F / 256F,//描边宽度占比
            SPACE = 1f / 64f,
            LINE_LENGTH = 3f / 32f,//线段长度
            CIRCLE_LARGER_RADIUS = 3F / 32F,//大圆半径
            CIRCLE_SMALL_RADIUS = 5f / 64F,//小圆半径
            ARC_RADIUS = 1F / 8F,//圆弧半径
            ARC_TEXT_RADIUS = 5F / 32F;//弧围绕文字半径
    private int mSize;
    private float mLargeCircleRadius;
    private float x;
    private float y;
    private float mLineLength;
    private TextPaint mTextPaint;
    private float mTextOffsetY;
    private Paint mArcPaint;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextOffsetY = (mTextPaint.descent() + mTextPaint.ascent()) / 2;

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);//强制宽高相等
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSize = w;
        calculation();
    }

    private void calculation() {
        mPaint.setStrokeWidth(mSize * STROKE_WIDTH);
        mLargeCircleRadius = mSize * CIRCLE_LARGER_RADIUS;
        mLineLength = mSize * LINE_LENGTH;
        x = mSize / 2;
        y = mSize / 2 + mLargeCircleRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFF29B76);
        canvas.drawCircle(x, y, mLargeCircleRadius, mPaint);
        canvas.drawText("AigeStudio", x, y - mTextOffsetY, mTextPaint);
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottom(canvas, -100, mLargeCircleRadius, "Banana");
        drawBottom(canvas, 100, mLargeCircleRadius, "Cucumber");
        drawBottom(canvas, 180, mSize * CIRCLE_SMALL_RADIUS, "Vibrators");
    }

    private void drawBottom(Canvas canvas, float degree, float radius, String text) {
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(degree);
        float space = mSize * SPACE;
        float nextY = -mLargeCircleRadius - space;
        canvas.drawLine(0, nextY, 0, nextY - mLineLength, mPaint);
        nextY = nextY - mLargeCircleRadius - radius - space;
        canvas.drawCircle(0, nextY, radius, mPaint);
        canvas.drawText(text, 0, nextY - mTextOffsetY, mTextPaint);
        canvas.restore();
    }

    private void drawTopRight(Canvas canvas) {
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(30);
        float nextY = -mLargeCircleRadius;
        canvas.drawLine(0, nextY, 0, nextY - mLineLength, mPaint);
        nextY = nextY - mLineLength - mLargeCircleRadius;
        canvas.drawCircle(0, nextY, mLargeCircleRadius, mPaint);
        canvas.drawText("Tropical", 0, nextY - mTextOffsetY, mTextPaint);
        drawTopRightArc(canvas, nextY);
        canvas.restore();
    }

    private void drawTopRightArc(Canvas canvas, float y) {
        canvas.save();
        canvas.translate(0, y);
        canvas.rotate(-30);
        float arcRadius = mSize * ARC_RADIUS;
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        mArcPaint.setStyle(Paint.Style.FILL);
        mArcPaint.setColor(0x55EC6941);
        canvas.drawArc(rectF, -22.5f, -135f, true, mArcPaint);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mSize * STROKE_WIDTH);
        mArcPaint.setColor(Color.WHITE);
        canvas.drawArc(rectF, -22.5f, -135f, false, mArcPaint);
        //画文字
        canvas.save();
        float textRadius = mSize * ARC_TEXT_RADIUS;
        canvas.rotate(-67.5f);
        canvas.drawText("Aige", 0, -textRadius, mTextPaint);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.rotate(33.75f * i);
            canvas.drawText("Aige", 0, -textRadius, mTextPaint);
            canvas.restore();
        }
        canvas.restore();

        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas) {
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(-30);
        float nextY = -mLargeCircleRadius;
        canvas.drawLine(0, nextY, 0, nextY - mLineLength, mPaint);
        nextY = nextY - mLineLength;
        canvas.drawCircle(0, nextY - mLargeCircleRadius, mLargeCircleRadius, mPaint);
        canvas.drawText("Apple", 0, nextY - mLargeCircleRadius - mTextOffsetY, mTextPaint);
        nextY = nextY - mLargeCircleRadius * 2;
        canvas.drawLine(0, nextY, 0, nextY - mLineLength, mPaint);
        nextY = nextY - mLineLength;
        canvas.drawCircle(0, nextY - mLargeCircleRadius, mLargeCircleRadius, mPaint);
        canvas.drawText("Orange", 0, nextY - mLargeCircleRadius - mTextOffsetY, mTextPaint);
        canvas.restore();
    }
}
