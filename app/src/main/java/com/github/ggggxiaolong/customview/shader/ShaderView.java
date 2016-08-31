package com.github.ggggxiaolong.customview.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.github.ggggxiaolong.customview.R;
import com.github.ggggxiaolong.customview.utils.MeasureUtil;

public class ShaderView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private PorterDuffXfermode mXfermode;
    private int y;
    private int x;
    private Bitmap mDarkBitmap;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes(context);
        initPaint();
    }

    private void initRes(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize(context);
        int screenW  = screenSize[0];
        int screenH = screenSize[1];

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gril);

        Paint darkPaint = new Paint();
        mDarkBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDarkBitmap);
        float radius = canvas.getHeight() * 2f / 3;
        RadialGradient radialGradient = new RadialGradient(canvas.getWidth() / 2f, canvas.getHeight() / 2f, radius, new int[]{0, 0, 0xaa000000}, new float[]{0f, .7f, 1f}, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setScale(canvas.getWidth() / (radius * 2f), 1f);
        matrix.preTranslate(radius - canvas.getWidth() / 2, 0);
        radialGradient.setLocalMatrix(matrix);
        darkPaint.setShader(radialGradient);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), darkPaint);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        x = screenW / 2 - mBitmap.getWidth() / 2;
        y = screenH / 2 - mBitmap.getHeight() / 2 - 100;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{0.8587F, 0.2940F, -0.0927F, 0,
                6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0,
                0, 0, 1, 0}));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //新建图层
        int sc = canvas.saveLayer(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(0xcc1c093e);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mBitmap, x, y, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
        canvas.drawBitmap(mDarkBitmap, x, y, null);
    }
}
