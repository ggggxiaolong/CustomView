package com.github.ggggxiaolong.customview.polyline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * 折线图
 * http://blog.csdn.net/aigestudio/article/details/41960507
 */

public final class PolyLineView extends View {

    private final float LEFT = 1 / 16f, TOP = 1 / 16f, RIGHT = 15 / 16f, BOTTOM = 7 / 8f,//网格区域
            TIME_X = 3 / 32f, TIME_Y = 1 / 16f, MONEY_X = 31 / 32f, MONEY_Y = 15 / 16f,//文字位置
            TEXT_SIGN = 1 / 32f,//文字相对大小
            THICK_LINE_WIDTH = 1 / 128f, THIN_LINE_WIDTH = 1 / 512f;//粗线和细线的相对大小
    private TextPaint mTextPaint;
    private Paint mLinePaint;
    private Paint mPointPaint;
    private Path mPath;
    private ArrayList<PointF> mPointFs;
    private int mSize;
    private float mTextTimeX;
    private float mTextTimeY;
    private float mTextMoneyX;
    private float mTextMoneyY;
    private float mTextSignSize;
    private float mTop;
    private float mLeft;
    private float mRight;
    private float mBottom;
    private float mThickLineWidth;
    private float mThinLineWidth;
    private float mMaxX;
    private float mMaxY;
    private float mSpaceX;
    private float mSpaceY;
    private float mGridRight;
    private float mGridTop;
    private Paint mColorPaint;
    private int mCount;
    private float[] mRulerX;
    private float[] mRulerY;
    private float mTextRulerSize;
    private float mRuleTextY;
    private float mRuleTextX;

    public PolyLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
    }

    private void initPaint() {
        //设置文本画笔参数
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setColor(Color.WHITE);
        //设置线条画笔参数
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.WHITE);
        //设置点画笔参数
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(Color.WHITE);

        mColorPaint = new Paint();
        mColorPaint.setStyle(Paint.Style.FILL);
        mColorPaint.setARGB(75,255,0,0);

        mPath = new Path();
    }

    private void initData() {
        Random random = new Random();
        mPointFs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PointF pointF = new PointF();
            pointF.x = random.nextInt(100) * i;
            pointF.y = random.nextInt(100) * i;
            mPointFs.add(pointF);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSize = w;

        initRes();
    }

    private void initRes() {
        //计算文本坐标
        mTextTimeX = mSize * TIME_X;
        mTextTimeY = mSize * TIME_Y;

        mTextMoneyX = mSize * MONEY_X;
        mTextMoneyY = mSize * MONEY_Y;

        //计算文本大小
        mTextSignSize = mSize * TEXT_SIGN;
        mTextPaint.setTextSize(mTextSignSize);

        //计算网格坐标s
        mTop = mSize * TOP;
        mLeft = mSize * LEFT;
        mRight = mSize * RIGHT;
        mBottom = mSize * BOTTOM;

        mThickLineWidth = mSize * THICK_LINE_WIDTH;
        mThinLineWidth = mSize * THIN_LINE_WIDTH;
        //刻度的文字大小
        mTextRulerSize = mTextSignSize / 2;
        //计算刻度值
        mCount = mPointFs.size();
        int divisor = mCount - 1;
        //计算横轴最大值
        mMaxX = 0;
        for (PointF pointF : mPointFs) {
            if (mMaxX < pointF.x) {
                mMaxX = pointF.x;
            }
        }

        int remainderX = (int) mMaxX % divisor;
        mMaxX = remainderX == 0 ? mMaxX : mMaxX + divisor - remainderX;
        mRulerX = new float[mCount];
        float space = mMaxX / divisor;
        for (int i = 0; i < mCount; i++) {
            mRulerX[i] = space * i;
        }

        //计算横轴最大值
        mMaxY = 0;
        for (PointF pointF : mPointFs) {
            if (mMaxY < pointF.y) {
                mMaxY = pointF.y;
            }
        }

        int remainderY = (int) mMaxY % divisor;
        mMaxY = remainderY == 0 ? mMaxY : mMaxY + divisor - remainderY;
        mRulerY = new float[mCount];
        space = mMaxY / divisor;
        for (int i = 0; i < mCount; i++) {
            mRulerY[i] = space * i;
        }

        mSpaceX = (RIGHT - LEFT) * mSize / mCount;
        mSpaceY = (BOTTOM - TOP) * mSize / mCount;

        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mRuleTextY = mBottom + mTextRulerSize + mThickLineWidth;
        mRuleTextX = mLeft - mThickLineWidth;
        mGridRight = mRight - mSpaceX;
        mGridTop = mTop + mSpaceY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xff9596c4);
        drawSign(canvas);
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        canvas.save();
        //画坐标轴
        mLinePaint.setStrokeWidth(mThickLineWidth);
        mPath.moveTo(mLeft, mTop);
        mPath.lineTo(mLeft, mBottom);
        mPath.lineTo(mRight, mBottom);
        canvas.drawPath(mPath, mLinePaint);
        drawLines(canvas);
        drawData(canvas);
        canvas.restore();
    }

    private void drawData(Canvas canvas) {
        RectF rectF = new RectF(mLeft, mGridTop, mGridRight, mBottom);
        canvas.drawRect(rectF,mColorPaint);

        float lastX = mLeft;
        float lastY = mBottom;
        mLinePaint.setStrokeWidth(mThickLineWidth);
        for (PointF pointF : mPointFs) {
            float x = pointF.x / mMaxX * (mGridRight - mLeft) + mLeft;
            float y = mBottom - pointF.y / mMaxY * (mBottom - mGridTop);
            canvas.drawCircle(x, y, mThickLineWidth, mPointPaint);
            canvas.drawLine(lastX, lastY, x, y, mLinePaint);
            lastX = x;
            lastY = y;
        }
    }

    private void drawLines(Canvas canvas) {

        mTextPaint.setTextSize(mTextRulerSize);
        mLinePaint.setStrokeWidth(mThinLineWidth);

        for (int i = 1; i < mCount; i++) {
            float startY = mBottom - mSpaceY * i;
            float startX = mLeft + mSpaceX * i;
            canvas.drawLine(mLeft, startY, mGridRight, startY, mLinePaint);
            canvas.drawLine(startX, mBottom, startX, mGridTop, mLinePaint);
            canvas.drawText(String.valueOf(mRulerX[i]), startX, mRuleTextY, mTextPaint);
            canvas.drawText(String.valueOf(mRulerY[i]), mRuleTextX, startY, mTextPaint);
        }
        canvas.drawText("0.0", mLeft, mRuleTextY, mTextPaint);

    }

    private void drawSign(Canvas canvas) {
        canvas.save();
        mTextPaint.setTextSize(mTextSignSize);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Time", mTextTimeX, mTextTimeY, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Money", mTextMoneyX, mTextMoneyY, mTextPaint);
        canvas.restore();
    }
}
