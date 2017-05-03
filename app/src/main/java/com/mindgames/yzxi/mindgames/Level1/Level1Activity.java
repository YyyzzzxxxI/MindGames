package com.mindgames.yzxi.mindgames.Level1;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindgames.yzxi.mindgames.R;

import java.util.Arrays;
import java.util.Random;



public class Level1Activity extends Activity {

    boolean white=true;


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







    public  int[] createArray(int libSize, int countRandTales) {  //уникальные числа
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
        String[] array = new String[20];
        array[0] = "Яблоко";
        array[1] = "Апельсин";
        array[2] = "Машина";
        array[3] = "Комната";
        array[4] = "Касса";
        array[5] = "Ответ";
        array[6] = "Свидание";
        array[7] = "Юла";
        array[8] = "Президент";
        array[9] = "Конус";
        array[10] = "Создать";
        array[11] = "Избрать";
        array[12] = "Футбол";
        array[13] = "СССР";
        array[14] = "Россия";
        array[15] = "Трамп";
        array[16] = "Путин";
        array[17] = "Трампутыров";
        array[18] = "Победа";
        array[19] = "Поражение";

        int[] unicalN = createArray(20,8);
        final String[] unicalA = new String[8];

        TextView slovo1 = (TextView) findViewById(R.id.slovo1);  TextView slovo5 = (TextView) findViewById(R.id.slovo5);
        TextView slovo2 = (TextView) findViewById(R.id.slovo2);  TextView slovo6 = (TextView) findViewById(R.id.slovo6);
        TextView slovo3 = (TextView) findViewById(R.id.slovo3);  TextView slovo7 = (TextView) findViewById(R.id.slovo7);
        TextView slovo4 = (TextView) findViewById(R.id.slovo4);  TextView slovo8 = (TextView) findViewById(R.id.slovo8);

        final TextView timer = (TextView) findViewById(R.id.timer);

        final TextView[] slova = {slovo1,slovo2,slovo3,slovo4,slovo5,slovo6,slovo7,slovo8};


        for(int i =0; i<8; i++) {
            unicalA[i]=array[unicalN[i]];
            slova[i].setText(unicalA[i]);
        }


        new CountDownTimer(10000, 1000) {  //таймер на 10сек, каждую сек инфа обновляется

            public void onTick(long millisUntilFinished) {
                timer.setText("Времени осталось " + millisUntilFinished / 1000);
                changeMaket(slova,timer,unicalA);

            }

            public void onFinish() {
                timer.setText("done!");

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

}
