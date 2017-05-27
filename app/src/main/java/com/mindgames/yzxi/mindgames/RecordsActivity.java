package com.mindgames.yzxi.mindgames;


import android.content.pm.ActivityInfo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.mindgames.yzxi.mindgames.DataBase.DBManager;


import static com.mindgames.yzxi.mindgames.MainActivity.musicPlayer;
import static com.mindgames.yzxi.mindgames.MainActivity.offMusic;

public class RecordsActivity extends ActionBarActivity {
    private DBManager dbManager;




    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Общие рекорды","Последние игры","Подробнее"};
    int Numboftabs =3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_records);




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //экран не погаснет

        if (!offMusic) musicPlayer.start();

        dbManager = DBManager.getInstance(this);


        //Создание тулбара и его настройка

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // создание адаптера
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);


        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);


        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);


        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                if(position==0) list1();
                if (position==1)list2();
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });


        tabs.setViewPager(pager);

        ///




    }



    void list1(){
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv1.setText("Всего сыгранных игр: "+dbManager.getNgames()+"");
        tv2.setText("В среднем набрано очков: "+dbManager.getNSrednee()+"");
    }

    void list2(){
        ListView userName = (ListView)this.findViewById(R.id.tab2_lv);

        int k =dbManager.getNgames();

        String[] stata = new String[k];

        String[] Games = dbManager.getAllPlayer();
        String[] Scores = dbManager.getAllScores();

        for (int i=0; i<k;i++){
            stata[i]= Games[i]+"       :       "+Scores[i];
        }




        ArrayAdapter<String> adapterTT1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, stata);

        userName.setAdapter(adapterTT1);
    }

}
