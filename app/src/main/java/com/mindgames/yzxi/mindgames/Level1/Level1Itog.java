package com.mindgames.yzxi.mindgames.Level1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mindgames.yzxi.mindgames.ChooseLevelActivity;
import com.mindgames.yzxi.mindgames.DataBase.DBManager;
import com.mindgames.yzxi.mindgames.R;

import java.util.ArrayList;

import static com.mindgames.yzxi.mindgames.ChooseLevelActivity.ProfilePlayer;
import static com.mindgames.yzxi.mindgames.Level1.Level1Activity.createArray;
import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;


public class Level1Itog extends Activity {
    CheckBox[] checkBoxes= new CheckBox[12];
    CheckBox[] badCheckBoxes = new CheckBox[4];
    CheckBox[] goodCheckBoxes = new CheckBox[8];
    int checkValue=0;  //чтобы не было нажато больше 8 чеков (MyOnCheckedChangeListener)
    boolean showResult=false; //если результат будет показан, то кнопка "ок" будет перекидывать на выбор уровня
    MediaPlayer  musicLevel1Itog;
    private DBManager dbManager;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!offMusic) {
            //останавливаем музыку и чистим ее память
            musicLevel1Itog.stop();
            musicLevel1Itog.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer musicLevel1Itog) {
                    musicLevel1Itog.release();
                }
            });
        }
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level1_itog);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет
        if(!offMusic)musicLevel1Itog();
        Log.i("offMusic", offMusic+"");

        dbManager = DBManager.getInstance(this);
        setValue();

        for(int i=0;i<12;i++)
            checkBoxes[i].setOnCheckedChangeListener(new MyOnCheckedChangeListener());



        Log.i("YzxIitog", "15:21");
    }

    void setValue(){
        final String[] unicalA = (String[]) getIntent().getSerializableExtra("unicalA");
        String[] array = (String[]) getIntent().getSerializableExtra("array");
        final int [] unicalN = (int[]) getIntent().getSerializableExtra("unicalN");

        checkBoxes[0] =(CheckBox) findViewById(R.id.check1);   checkBoxes[6]=(CheckBox) findViewById(R.id.check7);
        checkBoxes[1]=(CheckBox) findViewById(R.id.check2);   checkBoxes[7]=(CheckBox) findViewById(R.id.check8);
        checkBoxes[2]=(CheckBox) findViewById(R.id.check3);   checkBoxes[8]=(CheckBox) findViewById(R.id.check9);
        checkBoxes[3]=(CheckBox) findViewById(R.id.check4);   checkBoxes[9]=(CheckBox) findViewById(R.id.check10);
        checkBoxes[4]=(CheckBox) findViewById(R.id.check5);   checkBoxes[10]=(CheckBox) findViewById(R.id.check11);
        checkBoxes[5]=(CheckBox) findViewById(R.id.check6);   checkBoxes[11]=(CheckBox) findViewById(R.id.check12);


        int[] goodCheck = createArray(12,8);  //генерация случайных чеков с правильными ответами

        for(int i=0;i<8;i++) {
            checkBoxes[goodCheck[i]].setText(unicalA[i]);
            goodCheckBoxes[i]=checkBoxes[goodCheck[i]];    //заполняется массив правильных чекбоксов
        }


        ArrayList<Integer> N = new ArrayList<>();           //номера массива слов
        for (int i=0;i<20;i++) N.add(i,i);

        int k=0;                        //переделываю значения из N в неправильные значения
        for(int i=0;i<8;i++){
            for(int j=0;j<20-k;j++){
                if(unicalN[i]==N.get(j)) {N.remove(j); k++; break;}
            }
        }





          //провожу манипуляции с получением 4 уникальных неправильных ответов
        int[] badN = new int[4];
        int[] a = createArray(12,4);
        for(int i=0; i<4;i++) badN[i]=N.get(a[i]);   //тут создались индексы 4 уникальных неправильных ответов



        String[] badArray = new String[4];

        for(int i=0; i<4;i++)
         badArray[i] = array[badN[i]];    //тут создался массив из 4 неправ ответов


        for (int i=0;i<4;i++){
                for(int j=0;j<12;j++)
                if(checkBoxes[j].getText().equals("NULL")) {   //заполняется массив неправильных чекбоксов
                    badCheckBoxes[i]=checkBoxes[j];
                    checkBoxes[j].setText(badArray[i]);
                    break;}
            }

            for (int i=0;i<8;i++)
            Log.i("unicalA",unicalA[i]);
    }




    int score(CheckBox[] goodCheckBoxes, CheckBox[] badCheckBoxes){    //подсчет очков
        int score = 0;
        int goodScore=0, badScore=0;


        for(int i=0;i<8;i++){
            if(goodCheckBoxes[i].isChecked()) goodScore++;

        }

        for(int i=0;i<4;i++){
            if(badCheckBoxes[i].isChecked()) badScore--;
        }

        score= goodScore+badScore;
        return score;   //8 - максимум очков
    }





    public void level1checkresult(View view) {          //при нажатии на "ок"
        if(!showResult) {

            int score = score(goodCheckBoxes,badCheckBoxes);
            String content="";
            int r=0,g=0;
            if (score <= 2) {
                content = "Плохо!";
                r = 139;
                g = 0;
            }   //темно-красный цвет
            else if (score <= 4) {
                content = "Неплохо";
                r = 255;
                g = 140;
            } //темно-оранжеый цвет
            else if (score <= 6) {
                content = "Хорошо";
                r = 10;
                g = 255;

            }
            else if (score == 7) {
                content = "Отлично!";
                r = 0;
                g = 255;
            } // типа зеленый
            else {
                content = "Великолепно!";
                r = 71;
                g = 198;
            }  //зеленый
            Log.i("YzxI", ProfilePlayer);
            Log.i("YzxI", score+"");
            dbManager.addResult(ProfilePlayer,score );

            new MaterialDialog.Builder(this)
                    .titleGravity(GravityEnum.CENTER)
                    .title(score + "/8 (баллов)")
                    .contentGravity(GravityEnum.CENTER)
                    .content(content)
                    .positiveText("Выбор уровня")
                    .negativeText("Узнать правильные ответы")
                    .btnStackedGravity(GravityEnum.CENTER)
                    .titleColor(Color.rgb(r, g, 0))
                    .contentColor(Color.rgb(r, g, 0))

                    .cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            onCansel();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(getApplicationContext(), ChooseLevelActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            if(!offMusic) musicLevel1Itog.stop();
                            startActivityForResult(intent, 0);
                            overridePendingTransition(0, 0); //0 for no animation
                            finish();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            onCansel();
                        }
                    })
                    .show();


            }

        else {
            Intent intent = new Intent(getApplicationContext(), ChooseLevelActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if(!offMusic) musicLevel1Itog.stop();
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0); //0 for no animation
            finish();
            }

    }


    public void onCansel(){             //при отмене всплывающего окна с результатами (и при выборе "Узнать ответ")
    for(int i=0;i<8;i++) {goodCheckBoxes[i].setTextColor(Color.BLUE); goodCheckBoxes[i].setEnabled(false);}
    for(int i=0;i<4;i++) {badCheckBoxes[i].setTextColor(Color.DKGRAY);    badCheckBoxes[i].setEnabled(false);}

        showResult=true;
    }



    private void musicLevel1Itog(){
        musicLevel1Itog = MediaPlayer.create(this, R.raw.end128);
        musicLevel1Itog.setLooping(true);
        //Установка обработчика события на момент готовности проигрывателя:
        musicLevel1Itog.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            public void onPrepared(MediaPlayer musicLevel1Itog)
            {
                //При готовности к проигрыванию запуск вывода звука:
                musicLevel1Itog.start();
            }
        });
    }



    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener  {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(checkValue<8) {
                if (buttonView.isChecked()) checkValue++;
                else checkValue--;
            }
            else {if(buttonView.isChecked()) {buttonView.setChecked(false); Toast.makeText(getApplicationContext(), "Не больше восьми!", Toast.LENGTH_SHORT).show();}
            else checkValue--;}
        }
    }
}



