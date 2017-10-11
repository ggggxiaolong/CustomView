package com.github.ggggxiaolong.customview.trapezoid;

import android.support.annotation.ColorInt;
import java.util.Comparator;

/**
 * @author mrtan on 10/10/17.
 */

public class TrapezoidBean{
  public final String title;
  public final @ColorInt int color;
  public final int value;

  public TrapezoidBean(String title, int color, int value) {
    this.title = title;
    this.color = color;
    this.value = value;
  }

  public static Comparator<TrapezoidBean> comparator(){
    return new Comparator<TrapezoidBean>() {
      @Override public int compare(TrapezoidBean o1, TrapezoidBean o2) {
        return o2.value - o1.value;
      }
    };
  }
}