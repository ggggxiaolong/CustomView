package com.github.ggggxiaolong.customview.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.github.ggggxiaolong.customview.R;
import com.github.ggggxiaolong.customview.utils.MeasureUtil;

/**
 * 阴影层效果
 */

public final class ShadowView extends ImageView {
    private final int RECT_SIZE = 100; //一般矩形的大小
    private Paint mPaint;
    private int left, right, top, bottom;//绘制时坐标
    private Bitmap mBitmap;
    private Paint mBitmapPaint;

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();
        initRes(context);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(50);
        mPaint.setShadowLayer(10, 3, 3, Color.DKGRAY);
    }


    private void initRes(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize(context);
        left = screenSize[0] / 2;
        right = screenSize[1] / 2;
        top = screenSize[1] / 2 - RECT_SIZE;
        bottom = screenSize[1] / 2 + RECT_SIZE;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.nav_header);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float scale = Math.max(150f / mBitmap.getWidth(), 150f / mBitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        mBitmapPaint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(left, top, right, bottom, mPaint);
        canvas.drawCircle(left, right, 150, mBitmapPaint);
//        canvas.drawCircle(left, right, 150, mPaint);
    }
}
