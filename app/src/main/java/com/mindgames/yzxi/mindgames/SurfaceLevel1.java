package com.mindgames.yzxi.mindgames;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

 class SurfaceLevel1 extends android.view.SurfaceView implements SurfaceHolder.Callback {


     SurfaceLevel1(Context context, AttributeSet attrs) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.YELLOW);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }



}
