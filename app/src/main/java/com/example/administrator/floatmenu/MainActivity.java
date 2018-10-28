package com.example.administrator.floatmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.loosu.floatingmenu.FloatingMenu;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox mCbDraggable;
    private FloatingMenu mFloatingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View btnActionMenu = findViewById(R.id.btn_action_menu);
        mCbDraggable = (CheckBox) findViewById(R.id.cb_draggable);

        mFloatingMenu = new FloatingMenu(btnActionMenu);

        mCbDraggable.setChecked(mFloatingMenu.isDraggale());

        mCbDraggable.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mFloatingMenu.setDraggable(b);
    }
}
