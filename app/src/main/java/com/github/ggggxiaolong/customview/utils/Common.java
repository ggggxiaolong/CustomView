package com.github.ggggxiaolong.customview.utils;

import android.content.IntentFilter;

/**
 * Created by tanxiaolong on 16/8/23.
 */

public final class Common {
    public static final String MSG_TITLE = "activity_change_title";

    public static IntentFilter getTitleFilter(){
        return new IntentFilter(MSG_TITLE);
    }
}
