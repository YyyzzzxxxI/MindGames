package com.mindgames.yzxi.mindgames;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class PlayActivity extends Activity  {  //после нажатия на 'play', мутим слайдер

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет


        Button buttonLevel1 = (Button) findViewById(R.id.buttonlevel1);
        buttonLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StartLevel1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!offMusic)musicPlayer.start();

        // Получаем объект ViewFlipper
        final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);



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
