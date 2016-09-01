package com.github.ggggxiaolong.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public final class PathView extends View {

    private Paint mPaint;
    private Path mPath;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mPath.moveTo(100, 200);
        mPath.cubicTo(200, 100, 300, 300, 500, 200);

        mPath.moveTo(100, 200);
        RectF rectF = new RectF(100, 200, 200, 300);
        mPath.arcTo(rectF, 0, 90);

        mPath.moveTo(100, 400);
        rectF = new RectF(100, 400, 200, 500);
        mPath.arcTo(rectF, 0, 180, true);

        mPath.moveTo(300,400);
        mPath.rLineTo(200,200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
