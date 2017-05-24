package com.example.ja010.mycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ja010 on 17-05-18.
 */

public class mycanvas extends View {
    Bitmap mBitmap;
    Canvas mCanvas;
    int oldx = -1 ;
    int oldy = -1;
    int move =0;
    boolean checked = false;
    Paint mpaint= new Paint();
    Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    Bitmap img1 = img;

    public mycanvas(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }
    public mycanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }
    private void drawstamp(int x, int y){
        mCanvas.drawBitmap(img,x,y,mpaint);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap =Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.WHITE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x =-1;
        int y = -1;

        x = (int) event.getX();
        y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                oldx = x;
                oldy = y;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (oldx != -1) {
                    if(!checked) {
                        mCanvas.drawLine(oldx, oldy, x, y, mpaint);
                    }
                    invalidate();
                    oldx = x;
                    oldy = y;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (oldx != -1) {
                    if(checked){
                        drawstamp(oldx,oldy);
                    }
                    else{
                        mCanvas.drawLine(oldx, oldy, x, y, mpaint);
                    }
                    invalidate();

                }
                oldx = -1;
                oldy = -1;
            }
        return true;
    }
    public void rotate(boolean checked){
        if(checked){
            mCanvas.rotate(30,mCanvas.getWidth()/2,mCanvas.getHeight()/2);
        }
        else{ // rotate 한 값들을 다시 바꿔줘야하는데 이를 어쩌나 ...
            img = img1;
        }

    }
    public void move(boolean checked){
        if(checked) {
            mCanvas.translate(move, move );
            move += 10;
        }
        else { // move 한 값 변경
            img = img1;
        }

    }
    public void sizeup(){
        if(checked){
            img = Bitmap.createScaledBitmap(img,(int)(img.getWidth()*1.5),(int)(img.getHeight()*1.5),false);

        }
        else if(!checked){
           img = img1;
        }
    }
    public void ch(boolean check){
        checked = check;
    } // check 여부
    public void clear(){// eraser 버튼
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    } //erase
    public void skew(){
        if(checked){
            mCanvas.skew(0.2f,0.2f);
        }
        else{
            img = img1;
        }
    }  // skew
    public void red(){
        mpaint.setColor(Color.RED);
    }
    public void blue(){
        mpaint.setColor(Color.BLUE);
    }
    public void pwb(boolean ch){
        if(ch){
            mpaint.setStrokeWidth(5);
        }
        else {
            mpaint.setStrokeWidth(3);
        }

    }
    public void colors(boolean mcheck, boolean stampch){
        if(stampch==true&&mcheck==true){
            float arr[] = {
                    2.0f,0,0,0,-2.5f,
                    0,2.0f,0,0,-2.5f,
                    0,0,2.0f,0,-2.5f,
                    0,0,0,1.0f,0
            };
            ColorMatrix mat = new ColorMatrix(arr);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(mat);
            mpaint.setColorFilter(filter);
        }
        else{
            float arr[] = {
                    1.0f,0,0,0,2.5f,
                    0,1.0f,0,0,2.5f,
                    0,0,1.0f,0,2.5f,
                    0,0,0,1.0f,0
            };
            ColorMatrix mat = new ColorMatrix(arr);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(mat);
            mpaint.setColorFilter(filter);
        }

    }
    public void blur(boolean mcheck, boolean stampch){
        if(mcheck==true&&stampch==true){
            BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.INNER);
            mpaint.setMaskFilter(blur);
        }
        else{
            mpaint.setMaskFilter(null);
        }
    }
    public void load(String filename){
        try (FileInputStream in = new FileInputStream(filename)) {
            clear();
            mCanvas.scale(0.5f, 0.5f);
            mCanvas.drawBitmap(BitmapFactory.decodeStream(in), mCanvas.getWidth() / 2, mCanvas.getHeight() / 2, mpaint);
            in.close();
            invalidate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"저장된 파일이 없습니다.",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void save(String filename){
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            Toast.makeText(getContext(),"not found file",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG,100,fo);
        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
