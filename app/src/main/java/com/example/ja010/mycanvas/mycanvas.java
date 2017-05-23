package com.example.ja010.mycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ja010 on 17-05-18.
 */

public class mycanvas extends View {
    Bitmap mBitmap;
    Canvas mCanvas;
    int oldx = -1 ;
    int oldy = -1;
    boolean checked;
    Paint mpaint= new Paint();
    Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    public mycanvas(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(5);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    public mycanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(5);
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
        int x = (int) event.getX();
        int y = (int) event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                oldx = x;
                oldy = y;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (oldx != -1) {
                    if(!checked)
                    mCanvas.drawLine(oldx, oldy, x, y, mpaint);
                    invalidate();
                    oldx = x;
                    oldy = y;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (oldx != -1) {
                    mCanvas.drawLine(oldx, oldy, x, y, mpaint);
                    invalidate();
                }
                oldx = -1;
                oldy = -1;
            }
        return true;
    }
    public void rotate(boolean checked){
        if(checked){
            mCanvas.rotate(30,this.getWidth()/2,this.getHeight()/2);
            invalidate();
        }
    }
    public void move(boolean checked){
        if(checked) {
            mCanvas.translate(10, 10);
            invalidate();
        }
    }
    public void ch(boolean check){
        checked = check;
    }
}
