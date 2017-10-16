package com.github.ggggxiaolong.customview.trapezoid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.util.Preconditions;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.github.ggggxiaolong.customview.R;
import java.util.Collections;
import java.util.List;

/**
 * @author mrtan on 10/10/17.
 */

public class TrapezoidView extends View {
  private TextPaint mTextPaint;
  private Paint mPaint;
  private int textColor, textSize;//字体大小颜色
  final private float DEFAULT_MIN_WIDTH = 1.0f / 4;//默认最小宽度
  final private int divider;  //每个Item之间的间隔
  private int minWidth; //最小Item的宽度
  private int perItemWidth;//每个item的value的值对应的宽度
  private int itemHeight;//Item的高度
  private int mWidth, mHeight;  //View测量之后的宽高
  private List<TrapezoidBean> mItems;
  private Path mPath;
  private int mTextX, mTextHeight;
  private String TAG = "TrapView";

  public TrapezoidView(Context context) {
    this(context, null);
  }

  public TrapezoidView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TrapezoidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TrapezoidView);
    textColor = a.getColor(R.styleable.TrapezoidView_android_textColor, Color.WHITE);
    divider = a.getDimensionPixelSize(R.styleable.TrapezoidView_android_dividerHeight, 0);
    minWidth = a.getDimensionPixelSize(R.styleable.TrapezoidView_android_minWidth, 0);
    textSize = a.getDimensionPixelSize(R.styleable.TrapezoidView_android_textSize, 0);
    a.recycle();
    init();
  }

  private void init() {
    mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
    mTextPaint.setColor(textColor);
    if (textSize != 0) mTextPaint.setTextSize(textSize);

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    mPaint.setStyle(Paint.Style.FILL);

    mPath = new Path();
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = w;
    mHeight = h;
    mTextX = mWidth / 2;
    if (minWidth == 0) minWidth = (int) (mWidth * DEFAULT_MIN_WIDTH);//最小宽度
    initData();
  }

  private void initData() {
    //如果设置了字体大小，最小宽度要大于等于最小值字的长度
    if (mItems == null || mItems.isEmpty()) return;
    int size = mItems.size();
    if (textSize != 0) {
      String title = mItems.get(size - 1).title;
      int textWidth = (int) mTextPaint.measureText(title) + dp2px(getContext(), 2);
      if (minWidth < textWidth) minWidth = textWidth;
    }
    //计算每个Item的高度
    itemHeight = (mHeight - divider * (size - 1)) / size;
    //如果没有设置字体的大小按照item的大小
    if (textSize == 0) {
      textSize = itemHeight - dp2px(getContext(), 2);
      mTextPaint.setTextSize(textSize);
    }
    //计算每份item的value的宽度
    if (size == 1) {
      perItemWidth = 0;
    } else {
      perItemWidth = (mWidth - minWidth) / (mItems.get(size - 1).value - mItems.get(0).value) / 2;
    }
    //计算字体想对于梯形的高度
    final Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    mTextHeight = (int) (itemHeight - fontMetrics.descent - fontMetrics.ascent) / 2;
    //Log.i(TAG, "minWidth:" + minWidth);
    //Log.i(TAG, "textSize:" + textSize);
    //Log.i(TAG, "perItemWidth:" + perItemWidth);
    //Log.i(TAG, "itemHeight:" + itemHeight);
    //Log.i(TAG, "mTextX:" + mTextX);
    //Log.i(TAG, "divider:" + divider);
    //Log.i(TAG, "mTextHeight:" + mTextHeight);
  }

  public void setItems(List<TrapezoidBean> items) {
    if (items == null) throw new IllegalArgumentException("data can't be null");
    mItems = items;
    Collections.sort(mItems, TrapezoidBean.comparator());
    initData();
  }

  public static int dp2px(Context context, float dpValue) {
    DisplayMetrics metric = context.getResources().getDisplayMetrics();
    return (int) (dpValue * metric.density + 0.5f);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mItems == null || mItems.isEmpty()) return;
    int lastLeft = 0, lastRight = mWidth, lastTop = 0;
    int left;
    int right;
    int top;
    int bottom;
    int gap = 0;

    if (mItems.size() == 1) {
      TrapezoidBean bean = mItems.get(0);
      left = 0;
      right = mWidth;
      top = 0;
      bottom = mHeight;
      drawTrapezoid(canvas, left, right, top, bottom, gap, bean);
    } else {
      TrapezoidBean bean = mItems.get(0);
      TrapezoidBean next;
      int index = 1;
      for (; index < mItems.size(); index++) {
        next = mItems.get(index);
        gap = (next.value - bean.value) * perItemWidth;
        bottom = lastTop + itemHeight;
        drawTrapezoid(canvas, lastLeft, lastRight, lastTop, bottom, gap, bean);
        lastLeft += gap;
        lastRight -= gap;
        lastTop = bottom + divider;
        bean = next;
      }
      //画最后一个
      drawTrapezoid(canvas, lastLeft, lastRight, lastTop, lastTop + itemHeight, 0, bean);
    }
  }

  private void drawTrapezoid(Canvas canvas, int left, int right, int top, int bottom, int gap,
      TrapezoidBean bean) {
    //文字的位置
    int textY = top + mTextHeight;

    //Log.i(TAG, "left:" + left);
    //Log.i(TAG, "right:" + right);
    //Log.i(TAG, "top:" + top);
    //Log.i(TAG, "bottom:" + bottom);
    //Log.i(TAG, "gap:" + gap);
    //Log.i(TAG, "textY:" + textY);
    //画图形
    mPath.reset();
    mPath.moveTo(left, top);
    mPath.lineTo(right, top);
    mPath.lineTo(right - gap, bottom);
    mPath.lineTo(left + gap, bottom);
    mPath.close();
    mPaint.setColor(bean.color);
    canvas.drawPath(mPath, mPaint);
    mTextPaint.setTextAlign(Paint.Align.CENTER);
    canvas.drawText(bean.title, mTextX, textY, mTextPaint);
  }
}