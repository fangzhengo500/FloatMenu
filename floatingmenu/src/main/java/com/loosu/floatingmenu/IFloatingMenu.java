package com.loosu.floatingmenu;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public interface IFloatingMenu {

    public boolean isOpen();

    public void open();

    public void close();

    public void setDraggable(boolean darggable);

    public boolean isDraggale();


    public interface Item {

        View getView();

        int getX();

        void setX(int x);

        int getY();

        void setY(int y);

        int getWidth();

        int getHeight();

    }


    public interface ItemPanel {

        void showIn(ViewGroup containerPoint, Point anchor);

        void hide(Point anchor);

        public void addItem(Item item);

        public void removeItem(Item item);

        @Nullable
        public List<Item> getItems();
    }


    public interface State {
        int OPENED = 0;
        int CLOSE = 1;
        int OPENING = 2;
        int CLOSEING = 3;
    }
}
