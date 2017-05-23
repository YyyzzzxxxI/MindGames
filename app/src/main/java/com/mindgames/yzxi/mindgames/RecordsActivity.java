package com.mindgames.yzxi.mindgames;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mindgames.yzxi.mindgames.DataBase.DBManager;

import static com.mindgames.yzxi.mindgames.MainActivity.musicPlayer;
import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;

public class RecordsActivity extends AppCompatActivity {
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_records);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет

        if (!offMusic) musicPlayer.start();

        dbManager = DBManager.getInstance(this);

        TextView stata = (TextView)this.findViewById(R.id.stata);

        int nGames = dbManager.getNgames();
        double sred = dbManager.getSrednee();
        stata.setText("Количество сыгранных игр: "+ nGames + "\n"+"Среднее количество набранных очковv: "+ sred );

    }
}
