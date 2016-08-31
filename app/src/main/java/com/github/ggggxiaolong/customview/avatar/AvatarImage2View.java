package com.github.ggggxiaolong.customview.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public final class AvatarImage2View extends ImageView {

    private Paint mPaint;
    private int mWidth;
    private PorterDuffXfermode mXfermode;
    private Matrix mMatrix;
    private Bitmap mBitmap;

    public AvatarImage2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        initRes();
    }

    private void initRes() {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        mBitmap = getBitmap(drawable);
        float scale = Math.min(mWidth * 1f / mBitmap.getWidth(), mWidth * 1.0f / mBitmap.getHeight());
        mMatrix = new Matrix();
        mMatrix.setScale(scale, scale);
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }

        Bitmap bitmap;

        if (drawable instanceof ColorDrawable) {
            bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);//最终会通过缩放实现覆盖
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) return;
        int sc = canvas.saveLayer(0, 0, mWidth, mWidth, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
