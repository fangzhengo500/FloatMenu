package com.loosu.floatingmenu;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;

public class Util {

    @NonNull
    public static Activity findActivity(Context ctx) {
        Activity activity = null;
        if (ctx != null) {
            if (ctx instanceof Activity) {
                activity = (Activity) ctx;
            } else if (ctx instanceof ContextWrapper) {
                activity = findActivity(((ContextWrapper) ctx).getBaseContext());
            }
        }
        if (activity == null) {
            throw new RuntimeException("ctx can not get activity");
        }
        return activity;
    }

    @NonNull
    public static View getActivityContentView(Context ctx) {
        return findActivity(ctx).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public static Point getScreenSize(Context ctx) {
        Point size = new Point();
        findActivity(ctx).getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }
}
