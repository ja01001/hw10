package com.example.ja010.mycanvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
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
                mc.ch(b);// 눌렸을때의 값을 전달
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.blur:
                if(item.isChecked()){
                    item.setChecked(false);
                    mc.blur(false,cb.isChecked());
                }
                else{
                    item.setChecked(true);
                    mc.blur(true,cb.isChecked());
                }
                break;
            case R.id.col:
                if(item.isChecked()){
                    item.setChecked(false);
                    mc.colors(false,cb.isChecked());
                }
                else{
                    item.setChecked(true);
                    mc.colors(true,cb.isChecked());
                }
                break;
            case R.id.pwb:
                if(item.isChecked()){
                    item.setChecked(false);
                    mc.pwb(false);
                }
                else{
                    item.setChecked(true);
                    mc.pwb(true);

                }
                break;
            case R.id.red:
                mc.red();
                break;
            case R.id.blue:
                mc.blue();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clcl(View v){
        switch (v.getId()){
            case R.id.b1:
                mc.clear();
                break;
            case R.id.b2:
                break;
            case R.id.b3:
                break;
            case R.id.b4:
                mc.rotate(cb.isChecked());
                cb.setChecked(true);
                break;
            case R.id.b5:
                mc.move(cb.isChecked());
                cb.setChecked(true);
                break;
            case R.id.b6:

                mc.sizeup();
                cb.setChecked(true);
                break;
            case R.id.b7:
                mc.skew();
                cb.setChecked(true);
                break;
        }
    }
}
