package com.mindgames.yzxi.mindgames;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import static com.mindgames.yzxi.mindgames.MainActivity.musicPlayer;
import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;

public class PlayActivity extends Activity {  //после нажатия на 'play', мутим слайдер

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!offMusic)musicPlayer.start();

        // Получаем объект ViewFlipper
        final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);

        // Создаем View и добавляем их в уже готовый flipper
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layouts[] = new int[]{ R.layout.pvlevel1, R.layout.pvlevel2 };

        for (int layout : layouts) flipper.addView(inflater.inflate(layout, null));

        // Устанавливаем listener касаний, для последующего перехвата жестов
        RelativeLayout play = (RelativeLayout) findViewById(R.id.activity_play);


        play.setOnTouchListener(new View.OnTouchListener() {
            float fromPosition,toPosition;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) fromPosition = event.getX();

                if(event.getAction()==MotionEvent.ACTION_UP){
                    toPosition = event.getX();
                    if ((fromPosition - toPosition)>=30) {
                        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_next_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_next_out));
                        flipper.showNext();
                    }
                    else if((toPosition-fromPosition)>=30) {
                        flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_prev_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_prev_out));
                        flipper.showPrevious();
                    }}

                return true;
            }
        });



        final Button button = (Button) findViewById(R.id.buttonlevel1);


      Thread thread = new Thread(new Runnable() {


            @Override
            public void run() {
                while (true) {
                    button.setAlpha(1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    button.setAlpha(0);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();



    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
