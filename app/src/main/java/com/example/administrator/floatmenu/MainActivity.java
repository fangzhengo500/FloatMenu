package com.example.administrator.floatmenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.loosu.floatingmenu.boommenu.BoomMenu;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox mCbDraggable;
    private BoomMenu mFloatingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View btnActionMenu = findViewById(R.id.btn_action_menu);
        mCbDraggable = (CheckBox) findViewById(R.id.cb_draggable);

        mFloatingMenu = new BoomMenu(btnActionMenu);

        mCbDraggable.setChecked(mFloatingMenu.isDraggale());

        mCbDraggable.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mFloatingMenu.setDraggable(b);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_floating_menu_demo:
                jump2Activity(FloatingMenuActivity.class);
                break;
        }
    }

    private void jump2Activity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
