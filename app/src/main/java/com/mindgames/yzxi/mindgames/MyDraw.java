package com.mindgames.yzxi.mindgames;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyDraw extends SurfaceView {
Bitmap hist,info,sett,isp,zag;
    int mViewWidth, mViewHeight;

    Paint paint = new Paint();



    public MyDraw(Context context, AttributeSet attrs)
    {
        super(context);
        zag = BitmapFactory.decodeResource(getResources(), R.drawable.game);
        isp = BitmapFactory.decodeResource(getResources(), R.drawable.ispit);
        sett = BitmapFactory.decodeResource(getResources(), R.drawable.setting);
        info = BitmapFactory.decodeResource(getResources(), R.drawable.info);
        hist = BitmapFactory.decodeResource(getResources(), R.drawable.hist);

    }

    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
        mViewWidth = width;
        mViewHeight = height;
        System.out.print(mViewHeight + mViewWidth);
    }



    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(zag, mViewWidth/2, 10, null);
        canvas.drawBitmap(info, 1, 1, null);
        canvas.drawBitmap(sett, mViewWidth, 1, null);
        canvas.drawBitmap(hist, mViewWidth/2-20, mViewHeight+20, null);
        canvas.drawBitmap(isp, mViewWidth/2-20, mViewHeight+60, null);
    }
}
