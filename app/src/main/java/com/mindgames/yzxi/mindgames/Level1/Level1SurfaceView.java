package com.mindgames.yzxi.mindgames.Level1;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class Level1SurfaceView extends SurfaceView {
    Paint p = new Paint();

    public Level1SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setTextSize(30);
        p.setColor(Color.WHITE);
        canvas.drawText("Testttttt",50,50,p);
        p.setColor(Color.RED);
        canvas.drawCircle(canvas.getWidth()-30,30,30,p);



    }
}
