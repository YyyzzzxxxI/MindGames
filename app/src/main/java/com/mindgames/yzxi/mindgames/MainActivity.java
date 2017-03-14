package com.mindgames.yzxi.mindgames;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
    boolean animGo=false;
    static boolean offMusic=false;
    boolean logoMistake=true;

    private VideoView animMain, logoView;
    static MediaPlayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(logoMistake){
            musicMain();
            hello();
        }
        else {
            animMain.start(); if(!offMusic)musicPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicPlayer.pause();
        if(logoMistake)logoView.stopPlayback();
        else { animMain.stopPlayback();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //останавливаем музыку и чистим ее память
        musicPlayer.stop();
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer musicPlayer)
            {
                musicPlayer.release();
            }
        });

        if(logoMistake)logoView.stopPlayback();
        else { animMain.stopPlayback();}

    }

    private void hello(){        //запуск лого и анимации мейна

        logoView = (VideoView) findViewById(R.id.mainAnim);
        logoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.logo));
        logoView.start();

        logoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                logoMistake=false;

                animMain();
                buttons();
            }
        });
    }

    private void animMain(){
        //анимация начало
        animMain = (VideoView) findViewById(R.id.mainAnim);
        animMain.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.anim));
        animMain.start();

        animGo=true;
        animMain.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer animPlayer) {  //зацикливание видюхи
                if(animGo) animPlayer.start();
            }
        });
    }


    private void musicMain(){
        musicPlayer = MediaPlayer.create(this, R.raw.song);

        musicPlayer.setLooping(true);
        //Установка обработчика события на момент готовности проигрывателя:
        musicPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            public void onPrepared(MediaPlayer musicPlayer)
            {
                //При готовности к проигрыванию запуск вывода звука:
                musicPlayer.start();
            }
        });

    }

    private void buttons(){  //раскрываем кнопки
        Button music = (Button) findViewById(R.id.music);
        Button setting = (Button) findViewById(R.id.setting);
        Button play = (Button) findViewById(R.id.play);
        Button records = (Button) findViewById(R.id.records) ;
        music.setVisibility(View.VISIBLE);
        setting.setVisibility(View.VISIBLE);
        play.setVisibility(View.VISIBLE);
        records.setVisibility(View.VISIBLE);
    }


    public void offMusic(View view) {

        Button music = (Button) findViewById(R.id.music);

        if(!offMusic) {musicPlayer.pause(); offMusic=true; music.setAlpha((float)0.5);}

       else {musicPlayer.start(); offMusic=false; music.setAlpha(1);}
    }


    public void play(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0);
        overridePendingTransition(0,0); //0 for no animation
    }
}
