package com.mindgames.yzxi.mindgames;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mindgames.yzxi.mindgames.DataBase.DBManager;



import static com.mindgames.yzxi.mindgames.MainActivity.musicPlayer;
import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;
import static com.mindgames.yzxi.mindgames.ChooseLevelActivity.name;
import static com.mindgames.yzxi.mindgames.DataBase.DBManager.id;

public class SettingsActivity extends AppCompatActivity {
    DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет

        dbManager = DBManager.getInstance(this);
        if (!offMusic) musicPlayer.start();


        final ListView allPlayer = (ListView) this.findViewById(R.id.set_lv);

        int k = dbManager.getNprofile();
        final String[] players;
        if (k == 0) {
            players = new String[1];
            players[0] = "Вы не добавили ни одного пользователя! Сделайте это сейчас.";
        } else {
            players = new String[k];

            for (int i = 0; i < k; i++) {
                players[i] = dbManager.getProfile(i);

            }

        }

        ArrayAdapter<String> adapterTT1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, players);

        allPlayer.setAdapter(adapterTT1);


        allPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                TextView tv =(TextView) v;
                Toast.makeText(getApplicationContext(), "Выбран пользователь " +tv.getText(), Toast.LENGTH_SHORT).show();


                name=false;


            }


        });

    }



    public void set_clear(View view) {
        dbManager.clear();
        //пересоздаю для обновления листа

        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 0);
        overridePendingTransition(0,0); //0 for no animation
        finish();
        id=0;
        Toast.makeText(getApplicationContext(),
                "Готово!", Toast.LENGTH_SHORT).show();
        name=true;
    }

    public void set_add(View view) {
        {new MaterialDialog.Builder(this)
                .title("Пользователь:")
                .titleGravity(GravityEnum.CENTER)
                .inputRange(2, 10, Color.RED)
                .input("Введите ваше имя (от 2 до 10 символов)", "Игрок1", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                        if( dbManager.checkProfile(input.toString())) {
                            Toast.makeText(getApplicationContext(),
                                    "Такой пользователь уже существует!", Toast.LENGTH_SHORT).show();

                        }
                        else {dbManager.addProfile(input.toString());
                            //пересоздаю для обновления листа

                            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivityForResult(intent, 0);
                            overridePendingTransition(0,0); //0 for no animation
                            finish();

                        }
                    }
                }).show();
        }


    }
}
