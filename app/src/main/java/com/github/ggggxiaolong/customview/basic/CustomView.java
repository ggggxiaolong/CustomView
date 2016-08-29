package com.github.ggggxiaolong.customview.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View
 */

public final class CustomView extends View {

    private int mWidth, mHeight;

    private DrawHelper.DrawInterface mDrawInterface = new DrawHelper.DrawPoint();

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawInterface.draw(canvas);
    }

    public void translate(DrawHelper.DrawInterface drawInterface) {
        mDrawInterface = drawInterface;
        mDrawInterface.init(mWidth, mHeight);
        invalidate();
    }
}
