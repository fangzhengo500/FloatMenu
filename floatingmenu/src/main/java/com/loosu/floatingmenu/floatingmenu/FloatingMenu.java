package com.loosu.floatingmenu.floatingmenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.loosu.floatingmenu.IFloatingMenu;
import com.loosu.floatingmenu.R;

public class FloatingMenu extends FrameLayout implements IFloatingMenu, View.OnClickListener {

    public enum Anchor {
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM,
    }

    private View mActionView = null;
    private View mMenuPanel = null;

    public FloatingMenu(Context context) {
        this(context, null);
    }

    public FloatingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_floating_menu, this, true);
        mActionView = (View) findViewById(R.id.menu_action_view);
        mMenuPanel = (View) findViewById(R.id.menu_panel);

        mActionView.setOnClickListener(this);
    }


    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void setDraggable(boolean darggable) {

    }

    @Override
    public boolean isDraggale() {
        return false;
    }

    @Override
    public void onClick(View v) {
        expandMenuPanel();
    }

    private void expandMenuPanel() {
        mMenuPanel.setScaleX(0);
        mMenuPanel.setScaleY(0);
        ViewCompat.animate(mMenuPanel)
                .scaleX(1)
                .scaleY(1)
                .start();

    }
}
