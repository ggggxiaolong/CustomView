package com.github.ggggxiaolong.customview.avatar;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * 张鸿洋的博客
 * http://blog.csdn.net/lmj623565791/article/details/43752383
 */
public final class AvatarImageDrawable extends Drawable {

    private int mWidth;
    private Paint mPaint;

    public AvatarImageDrawable(Bitmap bitmap) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(shader);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    //当设置的值为Wrap_content时返回bitmap的宽度
    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    //当设置的值为Wrap_content时返回bitmap的宽度
    @Override
    public int getIntrinsicHeight() {
        return mWidth;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
