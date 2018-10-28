package com.loosu.floatingmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.view.View;

import java.util.List;

public class AnimationHelper {


    private AnimationStateListener mAnimationStateListener = null;

    public AnimationStateListener getAnimationStateListener() {
        return mAnimationStateListener;
    }

    public void setAnimationStateListener(AnimationStateListener animationStateListener) {
        mAnimationStateListener = animationStateListener;
    }

    public void anmateMenuOpen(Point anchor, List<IFloatingMenu.Item> items) {
        if (items == null || items.size() <= 0) {
            return;
        }

        if (mAnimationStateListener != null) {
            mAnimationStateListener.onOpenAnimationStart();
        }

        ObjectAnimator lastAnimator = null;
        for (int i = 0; i < items.size(); i++) {
            IFloatingMenu.Item item = items.get(i);

            View itemView = item.getView();
            itemView.setScaleX(0);
            itemView.setScaleY(0);
            itemView.setAlpha(0);
            itemView.setRotation(0);
            itemView.setTranslationX(anchor.x);
            itemView.setTranslationY(anchor.y);

            PropertyValuesHolder[] valuesHolders = {
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1),
                    PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.ROTATION, 720),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, item.getX() - anchor.x),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, item.getY() - anchor.y),
            };

            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(itemView, valuesHolders);
            animator.setDuration(1000);
            animator.start();

            lastAnimator = animator;
        }
        lastAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mAnimationStateListener != null) {
                    mAnimationStateListener.onOpenAnimationEnd();
                }
            }
        });
    }

    public void animatMenuClose(Point anchor, List<IFloatingMenu.Item> items) {
        for (int i = 0; i < items.size(); i++) {
            IFloatingMenu.Item item = items.get(i);

            View itemView = item.getView();
            PropertyValuesHolder[] valuesHolders = {
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0),
                    PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.ROTATION, 0),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, anchor.x),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, anchor.y),
            };

            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(itemView, valuesHolders);
            animator.setDuration(1000);
            animator.start();
        }
    }

    public interface AnimationStateListener {

        void onOpenAnimationStart();

        void onOpenAnimationEnd();
    }


    public interface Action {
        int OPEN = 0;
        int CLODE = 1;
    }
}
