package com.mindgames.yzxi.mindgames;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mindgames.yzxi.mindgames.DataBase.DBManager;
import com.mindgames.yzxi.mindgames.Level1.Level1Activity;

import static com.mindgames.yzxi.mindgames.MainActivity.musicPlayer;
import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;

public class ChooseLevelActivity extends Activity  {  //после нажатия на 'play', мутим слайдер



   public static boolean name=true;
     DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chooselevel);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет

        dbManager = DBManager.getInstance(this);
        Log.i("YzxI","22:29");


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



        Button playlevel1 = (Button) findViewById(R.id.playlevel1);
        playlevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Level1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                musicPlayer.pause();

                finish();
            }
        });


        if(name){new MaterialDialog.Builder(this)
                .title("Добро пожаловать!")
                .titleGravity(GravityEnum.CENTER)
                .content("Представьтесь")
                .contentGravity(GravityEnum.CENTER)
                .inputRange(2, 10, Color.RED)
                .input("Введите ваше имя (от 2 до 10 символов)", "Игрок1", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                        if( dbManager.checkProfile(input.toString())) {
                            Toast.makeText(getApplicationContext(),
                                    "Выбран уже существующий пользователь.Изменить можно в настройках", Toast.LENGTH_SHORT).show();
                            name=false;

                        }
                        else {
                            dbManager.addProfile(input.toString());
                        name=false;}
                    }
                }).show();
    }}

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
