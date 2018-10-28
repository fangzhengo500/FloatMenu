package com.loosu.floatingmenu;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class CircleMenuPanel extends FrameLayout implements IFloatingMenu.ItemPanel, View.OnClickListener {

    private final List<IFloatingMenu.Item> mItems = new ArrayList<>();

    private Point mAnchor;

    private AnimationHelper mAnimationHelper = new AnimationHelper();

    public CircleMenuPanel(@NonNull Context context) {
        super(context);
        setOnClickListener(this);
        setWillNotDraw(false);
        setBackgroundColor(0x33ff6666);
    }

    @Override
    public void showIn(ViewGroup container, Point anchor) {
        mAnchor = anchor;

        container.addView(this);
        calculateItemPosition(anchor);

        for (IFloatingMenu.Item item : mItems) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(item.getWidth(), item.getHeight());
            View view = item.getView();
            addView(view, params);
        }

        mAnimationHelper.anmateMenuOpen(anchor, mItems);
    }

    @Override
    public void hide(Point anchor) {
        ViewParent parent = getParent();
        new AnimationHelper().animatMenuClose(anchor, mItems);
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
            for (IFloatingMenu.Item item : mItems) {
                ViewGroup itemPanel = (ViewGroup) item.getView().getParent();
                if (itemPanel != null) {
                    itemPanel.removeView(item.getView());
                }
            }
        }
    }

    @Override
    public void addItem(IFloatingMenu.Item item) {
        mItems.add(item);
    }

    @Override
    public void removeItem(IFloatingMenu.Item item) {
        mItems.remove(item);
    }

    @Override
    public List<IFloatingMenu.Item> getItems() {
        return mItems;
    }

    @Override
    public void onClick(View view) {
        hide(mAnchor);
    }

    private void calculateItemPosition(Point anchor) {
        final int radius = 100;
        final float startAngle = -0;
        final float endAngle = -360;
        RectF area = new RectF(
                anchor.x - radius,
                anchor.y - radius,
                anchor.x + radius,
                anchor.y + radius);

        Path orbit = new Path();
        orbit.addArc(area, startAngle, endAngle - startAngle);

        // 为了方便计算圆弧上的坐标
        PathMeasure measure = new PathMeasure(orbit, false);
        float pathLength = measure.getLength();

        int itemCount = mItems.size();
        for (int i = 0; i < itemCount; i++) {
            float[] pos = new float[2];
            measure.getPosTan(i * pathLength / itemCount, pos, null);

            IFloatingMenu.Item item = mItems.get(i);
            item.setX((int) (anchor.x + pos[0] - item.getWidth() / 2));
            item.setY((int) (anchor.y + pos[1] - item.getHeight() / 2));
        }
    }
}
