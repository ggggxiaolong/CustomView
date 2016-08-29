package com.github.ggggxiaolong.customview;

import android.app.Application;
import android.content.Context;

public final class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(getApplicationContext());
    }

    public static class ContextHolder {

        public static Context context;

        public static void setContext(Context context) {
            ContextHolder.context = context;
        }
    }
}
