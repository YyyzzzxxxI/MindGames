package com.mindgames.yzxi.mindgames.Level1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindgames.yzxi.mindgames.R;

import java.util.Arrays;
import java.util.Random;

import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;


public class Level1Activity extends Activity {

    boolean white=true, musicLevel1ActivityStart=false;
    MediaPlayer musicLevel1Activity;
    CountDownTimer countDownTimer;


    @Override
    protected void onDestroy() {
        super.onDestroy();
       if(!offMusic) { //останавливаем музыку и чистим ее память
           musicLevel1Activity.stop();
           musicLevel1Activity.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
               public void onCompletion(MediaPlayer musicLevel1Activity) {
                   musicLevel1Activity.release();
               }
           });
       }

       countDownTimer.cancel();
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет

         drawText();




    }







   static public  int[] createArray(int libSize, int countRandTales) {  //уникальные числа
        Random rnd = new Random();
        int[] arr = new int[libSize];
        for (int i = 0; i < libSize; i++) arr[i] = i;
        for (int i = libSize - 1; i > 0; i--) {
            int pos = rnd.nextInt(i + 1);
            int a = arr[pos];
            arr[pos] = arr[i];
            arr[i] = a;
        }
        return Arrays.copyOf(arr, countRandTales);
    }
     void drawText(){
        final String[] array = new String[]{ "Яблоко", "Апельсин", "Машина","Комната","Касса","Ответ","Свидание",
                                            "Юла", "Президент", "Конус","Создать","Избрать","Футбол","СССР",
                                            "Россия", "Трамп", "Путин","Трампутыров","Победа","Поражение"};



        final int[] unicalN = createArray(20,8);
        String[] unicalAhelp = new String[8];   //уникальные слова, временная переменная, служит для передачи слов в финал

        TextView slovo1 = (TextView) findViewById(R.id.slovo1);  TextView slovo5 = (TextView) findViewById(R.id.slovo5);
        TextView slovo2 = (TextView) findViewById(R.id.slovo2);  TextView slovo6 = (TextView) findViewById(R.id.slovo6);
        TextView slovo3 = (TextView) findViewById(R.id.slovo3);  TextView slovo7 = (TextView) findViewById(R.id.slovo7);
        TextView slovo4 = (TextView) findViewById(R.id.slovo4);  TextView slovo8 = (TextView) findViewById(R.id.slovo8);

        final TextView timer = (TextView) findViewById(R.id.timer);

        final TextView[] slova = {slovo1,slovo2,slovo3,slovo4,slovo5,slovo6,slovo7,slovo8};


        for(int i =0; i<8; i++)
            unicalAhelp[i]=array[unicalN[i]];

        final String[] unicalA = new String[]{unicalAhelp[0],unicalAhelp[1],unicalAhelp[2],unicalAhelp[3],
                                            unicalAhelp[4],unicalAhelp[5],unicalAhelp[6],unicalAhelp[7]};

        for(int i=0; i<8;i++) slova[i].setText(unicalA[i]);





        countDownTimer= new CountDownTimer(13700, 600) {  //таймер на 13.7сек, каждые 0.6сек инфа обновляется

            public void onTick(long millisUntilFinished) {
                timer.setText("Времени осталось " + millisUntilFinished / 1000);
                if(!musicLevel1ActivityStart && !offMusic)musicLevel1Activity();
                Log.i("offMusicLevel1Activity", offMusic+"");
                if(millisUntilFinished<=9500) changeMaket(slova,timer,unicalA);


            }

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), Level1Itog.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("unicalA", unicalA);
                intent.putExtra("unicalN",unicalN);
                intent.putExtra("array",array);
                if(!offMusic)if(musicLevel1Activity.isPlaying())musicLevel1Activity.stop();
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                finish();

            }
        }.start();




    }




    void changeMaket(TextView[] slova, TextView timer, String[] unicalA){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_level1);
        if(white){
            linearLayout.setBackgroundResource(R.drawable.foninvers);
            for(int i =0; i<8; i++) slova[i].setTextColor(Color.BLACK);
            timer.setTextColor(Color.BLACK);
            white=false;
        }
        else{
            linearLayout.setBackgroundResource(R.drawable.fon);
            for(int i =0; i<8; i++) slova[i].setTextColor(Color.WHITE);
            timer.setTextColor(Color.WHITE);
            white=true;
        }

        int[] swap = createArray(8,8);
        String[] unicalSwap= new String[8];

        for(int i=0;i<8;i++) {
            unicalSwap[i] = unicalA[swap[i]];
            slova[i].setText(unicalSwap[i]);
        }

    }

    private void musicLevel1Activity(){
        musicLevel1ActivityStart=true;
        musicLevel1Activity = MediaPlayer.create(this, R.raw.start128);

        musicLevel1Activity.setLooping(true);
        //Установка обработчика события на момент готовности проигрывателя:
        musicLevel1Activity.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            public void onPrepared(MediaPlayer musicLevel1Activity)
            {
                //При готовности к проигрыванию запуск вывода звука:
                musicLevel1Activity.start();
            }
        });
    }

}


