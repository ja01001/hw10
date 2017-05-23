package com.example.ja010.mycanvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {
    mycanvas mc;
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mc = (mycanvas)findViewById(R.id.canvas);
        cb = (CheckBox)findViewById(R.id.cb);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mc.ch(b);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Bluring");
        menu.add(0,2,0,"Coloring");
        menu.add(0,3,0,"Pen Width Big");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void clcl(View v){
        switch (v.getId()){
            case R.id.b1:

                break;
            case R.id.b2:
                break;
            case R.id.b3:
                break;
            case R.id.b4:
                mc.rotate(cb.isChecked());
                break;
            case R.id.b5:
                mc.move(cb.isChecked());
                break;
            case R.id.b6:
                break;
            case R.id.b7:
                break;
        }
    }
}
