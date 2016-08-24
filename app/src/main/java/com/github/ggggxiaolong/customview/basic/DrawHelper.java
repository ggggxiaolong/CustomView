package com.github.ggggxiaolong.customview.basic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public final class DrawHelper {
    interface DrawInterface {
        void draw(Canvas canvas, int width);
    }

    public static class DrawPoint implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xff70dcdc);
            paint.setStrokeWidth(10);
            int half = width / 2;
            canvas.drawPoints(new float[]{
                    100, 100,
                    half, 100,
                    half, 200,
                    half, 300,
                    half, 400,
            }, paint);
        }
    }

    public static class DrawLines implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xff59a0d5);
            paint.setStrokeWidth(4);
            int half = width / 2;
            canvas.drawLines(new float[]{
                    half - 300, 100, half - 100, 100,
                    half - 100, 100, half, 300,
                    half, 300, half + 100, 100,
                    half + 100, 100, half + 300, 100,
                    half + 300, 100, half, 600,
                    half, 600, half - 300, 100
            }, paint);
        }
    }

    public static class DrawRect implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xfffab9c6);
            paint.setStrokeWidth(4);
            Rect rect = new Rect(100, 100, width - 100, 600);
            canvas.drawRect(rect, paint);
        }
    }

    public static class DrawRoundRect implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xffaec4ab);
            paint.setStrokeWidth(4);
            RectF rect = new RectF(100, 100, width - 100, 600);
            canvas.drawRoundRect(rect, 30, 30, paint);
        }
    }

    public static class DrawOval implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xffd0b3df);
            paint.setStrokeWidth(4);
            RectF rect = new RectF(100, 100, width - 100, 600);
            canvas.drawOval(rect, paint);
        }
    }

    public static class DrawCircle implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xff8d88a1);
            paint.setStrokeWidth(4);
            int half = width / 2;
            canvas.drawCircle(half, 500, 200, paint);
        }
    }

    public static class DrawArc implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xff8d88a1);
            paint.setStrokeWidth(4);
            int half = width / 2;
            RectF rectF = new RectF(half - 200, 100, half + 200, 500);
            RectF rectF2 = new RectF(half - 200, 520, half + 200, 920);
            canvas.drawArc(rectF,0,90,false,paint);

            canvas.drawArc(rectF,180,90,true,paint);
        }
    }

    public static class DrawRing implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            paint.setColor(0xff59a0d5);
            paint.setStrokeWidth(20);
            paint.setStyle(Paint.Style.STROKE);
            int half = width / 2;
            canvas.drawCircle(half, 400, 200, paint);
        }
    }

    public static class DrawPie implements DrawInterface {

        @Override
        public void draw(Canvas canvas, int width) {
            Paint paint = new Paint();
            int[] colors = {0xff70dcdc,0xff59a0d5,0xfffab9c6,0xffaec4ab,0xffd0b3df,0xff8d88a1};
            int[] weights = {2,6,3,5,7,1};
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.FILL);
            int half = width / 2;
            RectF rectF = new RectF(half - 300, 100, half + 300, 700);
            int total = 0;
            for (int weight : weights){
                total += weight;
            }
            float startAngle = 0.0f;
            for (int i= 0 ;i < weights.length - 1; i++){
               float sweepAngle = ((float)weights[i] / total) * 360;
                paint.setColor(colors[i]);
                canvas.drawArc(rectF,startAngle,sweepAngle,true,paint);
                startAngle += sweepAngle;
            }
            paint.setColor(colors[colors.length -1]);
            canvas.drawArc(rectF,startAngle,360 - startAngle,true,paint);
        }
    }
}
