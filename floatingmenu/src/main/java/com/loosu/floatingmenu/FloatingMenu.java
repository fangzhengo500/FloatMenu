package com.loosu.floatingmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;


public class FloatingMenu implements IFloatingMenu {
    private static final String TAG = "FloatingMenu";

    private Context mContext = null;

    private boolean isOpen = false;
    private boolean isDraggable = true;

    private View mActionView = null;
    private ItemPanel mItemPanel = null;


    public FloatingMenu(View actionView) {
        mContext = actionView.getContext();

        mActionView = actionView;

        final Context ctx = actionView.getContext();
        mItemPanel = new CircleMenuPanel(ctx);


        for (int i = 0; i < 16; i++) {
            ImageView imageView = new ImageView(ctx);
            imageView.setImageResource(R.drawable.ic_launcher);
            imageView.setBackgroundColor(Color.YELLOW);

            MenuItem menuItem = new MenuItem(imageView, 50, 50);
            mItemPanel.addItem(menuItem);
        }

        mActionView.setOnTouchListener(mActionViewTouchListener);
        mActionView.setOnClickListener(mActionViewClickListener);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void open() {
        Activity activity = Util.findActivity(mActionView.getContext());
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mItemPanel.showIn(decorView,getActionViewCenter());
    }

    @Override
    public void close() {
        mItemPanel.hide(getActionViewCenter());
    }

    @Override
    public void setDraggable(boolean draggable) {
        isDraggable = draggable;
    }

    @Override
    public boolean isDraggale() {
        return isDraggable;
    }

    public void toggle() {
        if (isOpen) {
            close();
        } else {
            open();
        }
    }

    private Point getActionViewCenter() {
        Point point = getActionViewPointOfContentView();

        point.x += mActionView.getMeasuredWidth() / 2;
        point.y += mActionView.getMeasuredHeight();

        return point;
    }

    /**
     * 计算 ActionView 在 Activity 的 ContentView 位置.
     */
    private Point getActionViewPointOfContentView() {
        final Context context = mContext;

        // 获取在屏幕上的位置
        int[] locationOnScreen = new int[2];
        mActionView.getLocationOnScreen(locationOnScreen);
        Log.i(TAG, "locationOnScreen: " + Arrays.toString(locationOnScreen));

        Rect contentViewFrame = new Rect();
        View contentView = Util.getActivityContentView(context);
        contentView.getWindowVisibleDisplayFrame(contentViewFrame);
        Log.i(TAG, "contentViewFrame: " + contentViewFrame);

        int x = locationOnScreen[0] - (Util.getScreenSize(mContext).x - contentViewFrame.width());
        int y = locationOnScreen[1] - contentViewFrame.top;

        return new Point(x, y);

    }

    private final View.OnClickListener mActionViewClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            toggle();
        }
    };

    private final View.OnTouchListener mActionViewTouchListener = new View.OnTouchListener() {
        private float downX = 0;
        private float downY = 0;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (isDraggable) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getX();
                        downY = motionEvent.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int viewWidth = view.getWidth();
                        int viewHeight = view.getHeight();

                        float dx = motionEvent.getX() - viewWidth / 2;
                        float dy = motionEvent.getY() - viewHeight / 2;
                        Log.i(TAG, "onTouch: (" + dx + "," + dy + ")");
                        int l = (int) (view.getLeft() + dx);
                        int r = l + view.getWidth();
                        int t = (int) (view.getTop() + dy);
                        int b = t + view.getHeight();
                        view.layout(l, t, r, b);
                        return true;
                    case MotionEvent.ACTION_UP:
                        float upX = motionEvent.getX();
                        float upY = motionEvent.getY();
                        if (upX == downX && upY == downY) {
                            view.callOnClick();
                        }
                        return true;
                }

            }
            return false;
        }
    };
}
