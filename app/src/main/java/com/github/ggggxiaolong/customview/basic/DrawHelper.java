package com.github.ggggxiaolong.customview.basic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.github.ggggxiaolong.customview.App;
import com.github.ggggxiaolong.customview.R;

public final class DrawHelper {

    static abstract class DrawInterface {

        protected final Paint mPaint;
        protected int mWidth, mHeight, mHalfWidth, mHalfHeight;

        public DrawInterface() {
            mPaint = new Paint();
        }

        public abstract void draw(Canvas canvas);

        public void init(int width, int height) {
            mWidth = width;
            mHeight = height;
            mHalfWidth = mWidth / 2;
            mHalfHeight = mHeight / 2;
        }
    }

    public static class DrawPoint extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            mPaint.setColor(0xff70dcdc);
            mPaint.setStrokeWidth(10);
            canvas.drawPoints(new float[]{
                    100, 100,
                    mHalfWidth, 100,
                    mHalfWidth, 200,
                    mHalfWidth, 300,
                    mHalfWidth, 400,
            }, mPaint);
        }
    }

    public static class DrawLines extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            mPaint.setColor(0xff59a0d5);
            mPaint.setStrokeWidth(4);
            canvas.drawLines(new float[]{
                    mHalfWidth - 300, 100, mHalfWidth - 100, 100,
                    mHalfWidth - 100, 100, mHalfWidth, 300,
                    mHalfWidth, 300, mHalfWidth + 100, 100,
                    mHalfWidth + 100, 100, mHalfWidth + 300, 100,
                    mHalfWidth + 300, 100, mHalfWidth, 600,
                    mHalfWidth, 600, mHalfWidth - 300, 100
            }, mPaint);
        }
    }

    public static class DrawRect extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            mPaint.setColor(0xfffab9c6);
            mPaint.setStrokeWidth(4);
            Rect rect = new Rect(100, 100, mWidth - 100, 600);
            canvas.drawRect(rect, mPaint);
        }
    }

    public static class DrawRoundRect extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            mPaint.setColor(0xffaec4ab);
            mPaint.setStrokeWidth(4);
            RectF rect = new RectF(100, 100, mWidth - 100, 600);
            canvas.drawRoundRect(rect, 30, 30, mPaint);
        }
    }

    public static class DrawOval extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            mPaint.setColor(0xffd0b3df);
            mPaint.setStrokeWidth(4);
            RectF rect = new RectF(100, 100, mWidth - 100, 600);
            canvas.drawOval(rect, mPaint);
        }
    }

    public static class DrawCircle extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            mPaint.setColor(0xff8d88a1);
            mPaint.setStrokeWidth(4);
            int mHalfWidth = mWidth / 2;
            canvas.drawCircle(mHalfWidth, 500, 200, mPaint);
        }
    }

    public static class DrawArc extends DrawInterface {

        private RectF mRectF;

        @Override
        public void draw(Canvas canvas) {
            canvas.drawArc(mRectF, 0, 90, false, mPaint);

            canvas.drawArc(mRectF, 180, 90, true, mPaint);
        }

        @Override
        public void init(int width, int height) {
            super.init(width, height);
            mRectF = new RectF(mHalfWidth - 200, 100, mHalfWidth + 200, 500);
            mPaint.setColor(0xff8d88a1);
            mPaint.setStrokeWidth(4);
        }
    }

    public static class DrawRing extends DrawInterface {

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(mHalfWidth, 400, 200, mPaint);
        }

        @Override
        public void init(int width, int height) {
            super.init(width, height);
            mPaint.setColor(0xff59a0d5);
            mPaint.setStrokeWidth(20);
            mPaint.setStyle(Paint.Style.STROKE);
        }
    }

    public static class DrawPie extends DrawInterface {

        int[] colors = {0xff70dcdc, 0xff59a0d5, 0xfffab9c6, 0xffaec4ab, 0xffd0b3df, 0xff8d88a1};
        int[] weights = {2, 6, 3, 5, 7, 1};
        private RectF mRectF;

        @Override
        public void draw(Canvas canvas) {
            int total = 0;
            for (int weight : weights) {
                total += weight;
            }
            float startAngle = 0.0f;
            for (int i = 0; i < weights.length - 1; i++) {
                float sweepAngle = ((float) weights[i] / total) * 360;
                mPaint.setColor(colors[i]);
                canvas.drawArc(mRectF, startAngle, sweepAngle, true, mPaint);
                startAngle += sweepAngle;
            }
            mPaint.setColor(colors[colors.length - 1]);
            canvas.drawArc(mRectF, startAngle, 360 - startAngle, true, mPaint);
        }

        @Override
        public void init(int width, int height) {
            super.init(width, height);
            mRectF = new RectF(mHalfWidth - 300, 100, mHalfWidth + 300, 700);
            mPaint.setStrokeWidth(10);
            mPaint.setStyle(Paint.Style.FILL);
        }
    }

    public static class DrawBitmap extends DrawInterface {

        private Bitmap mBitmap;
        private Matrix mMatrix;

        @Override
        public void draw(Canvas canvas) {

            canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        }

        @Override
        public void init(int width, int height) {
            super.init(width, height);
            mBitmap = BitmapFactory.decodeResource(App.ContextHolder.context.getResources(), R.drawable.image);
            float scale = Math.min(mWidth * 1.0f / mBitmap.getWidth(), mHeight * 1.0f / mBitmap.getHeight());
            mMatrix = new Matrix();
            mMatrix.setScale(scale, scale);
        }
    }
}
