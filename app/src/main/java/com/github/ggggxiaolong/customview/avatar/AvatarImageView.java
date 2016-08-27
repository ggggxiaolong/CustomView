package com.github.ggggxiaolong.customview.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 自定义头像
 * http://blog.csdn.net/lmj623565791/article/details/41967509
 * https://github.com/hdodenhof/CircleImageView/blob/master/circleimageview/src/main/java/de/hdodenhof/circleimageview/CircleImageView.java
 */

public final class AvatarImageView extends ImageView {

    private int mBorder = 5;
    private int mPoint, mLastPoint;
    private int mRadius;
    private Paint mBitmapPaint;
    private Paint mBorderPaint;
    private final String TAG = "AvatarImageView";
    private Matrix mMatrix;

    public AvatarImageView(Context context) {
        this(context, null);
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmapPaint = new Paint();
        mBorderPaint = new Paint();

        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorder);
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
//        setLayerType(LAYER_TYPE_SOFTWARE, mBorderPaint);
//        mBorderPaint.setShadowLayer(12, 3, 3, Color.BLACK);

        mBitmapPaint.setAntiAlias(true);//抗锯齿
        mBorderPaint.setAntiAlias(true);//抗锯齿
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置测量的结果的一个最大的正方形
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        width = width < height ? width : height;
        mPoint = width / 2;
        mRadius = mPoint - 2 * mBorder;
        setMeasuredDimension(width, width);
        Drawable drawable = getDrawable();
        if (drawable == null || mLastPoint == mPoint) return;
        mLastPoint = mPoint;
        Bitmap bitmap = getBitmap(drawable);
        //计算缩放
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float scale = width * 1.0f / min;
        mMatrix.setScale(scale, scale);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(bitmapShader);
        Log.i(TAG, "onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (getDrawable() == null) return;

        canvas.drawCircle(mPoint, mPoint, mRadius, mBitmapPaint);
        canvas.drawCircle(mPoint, mPoint, mRadius, mBorderPaint);
        Log.i(TAG, "onDraw");
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
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("parent", super.onSaveInstanceState());
        bundle.putInt("border", mBorder);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("parent"));
            mBorder = bundle.getInt("border");
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
