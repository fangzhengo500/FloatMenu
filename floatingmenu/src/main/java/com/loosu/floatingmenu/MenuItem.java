package com.loosu.floatingmenu;

import android.support.annotation.NonNull;
import android.view.View;

public class MenuItem implements IFloatingMenu.Item {

    private int mX;
    private int mY;
    private int mWidth;
    private int mHeight;

    private final View mView;

    public MenuItem(@NonNull View view, int w, int h) {
        if (view == null) {
            throw new RuntimeException("view can not null");
        }
        mView = view;
        mWidth = w;
        mHeight = h;
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public void setX(int x) {
        mX = x;
    }

    @Override
    public int getY() {
        return mY;
    }

    @Override
    public void setY(int y) {
        mY = y;
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }
}
