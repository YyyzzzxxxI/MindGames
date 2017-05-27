package com.mindgames.yzxi.mindgames.DataBase;


import java.math.BigDecimal;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static java.math.BigDecimal.ROUND_HALF_UP;


public class DBManager {
    public static int id;
    private Context context;
    private String DB_NAME = "game.db";

    private SQLiteDatabase db;

    private static DBManager dbManager;

    public static DBManager getInstance(Context context) {    //ДБменеджер создает себя сам, никто кроме него не сможет его создать

        if (dbManager == null) {                              //получается программа будет работать с одной базой данных
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    private DBManager(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        createTablesIfNeedBe();
        createTableProfile();
    }

    private void createTablesIfNeedBe() {
        db.execSQL("CREATE TABLE IF NOT EXISTS RESULTS (USERNAME TEXT, SCORE INTEGER);"); //создать таблицу results
    }

    private void createTableProfile() {
        db.execSQL("CREATE TABLE IF NOT EXISTS PROFILE (USERNAME TEXT);");
    }

   public void addResult(String username, int score) {
        db.execSQL("INSERT INTO RESULTS VALUES ('" + username + "', " + score + ");");
    }

    public void addProfile(String username) {
        db.execSQL("INSERT INTO PROFILE VALUES ('" + username + "');");
        Log.i("YzxI add profile", username);
    }

    public void clear(){
        db.execSQL("delete from RESULTS");
        db.execSQL("delete from PROFILE");
    }



    public boolean checkProfile(String username) {         //чекаем, есть ли уже такой пользователь, также подходит для getId_by name


            Cursor cursor = db.rawQuery("SELECT * FROM PROFILE;", null);

            boolean go=cursor.moveToFirst();

            while (go){
                Log.i("YzxI chek cursor posit", cursor.getPosition()+"");
                Log.i("YzxI chek cursor string",cursor.getString(cursor.getColumnIndex("USERNAME"))+"");
                if(username.equals(cursor.getString(cursor.getColumnIndex("USERNAME")))){
                    Log.i("YzxI check",cursor.getString(cursor.getColumnIndex("USERNAME")));
                    id=cursor.getPosition();
                    return true;
                }
                go=cursor.moveToNext();
            }

            cursor.moveToLast();
            id=cursor.getPosition()+1;
            return false;

        }



    public String getProfile(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM PROFILE;", null);
        cursor.moveToPosition(id);                                            //курсор переходит к строчке id
        Log.i("YzxIdb", id+"");
        Log.i("YzxIdb", cursor.getString(cursor.getColumnIndex("USERNAME")));
        return  cursor.getString(cursor.getColumnIndex("USERNAME"));
    }





    public int getNprofile(){        //количество профилей
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM PROFILE;", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

   public int  getNgames()
    {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM RESULTS;", null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public float getNSrednee(){                 //всего в среднем набранных очков

        Cursor cursor = db.rawQuery("SELECT AVG(SCORE) FROM RESULTS;", null);  //AVG среднее
        cursor.moveToFirst();

        return Float.parseFloat ( (new BigDecimal(cursor.getFloat(0))).setScale(2,ROUND_HALF_UP).toString() );

    }

  public String[] getSrednee() {

      Cursor cursor = db.rawQuery("SELECT AVG(SCORE) FROM RESULTS GROUP by USERNAME ORDER by AVG(SCORE) DESC;", null);  //AVG среднее
          boolean go =cursor.moveToFirst();

      if(go){
          int countUsers = 1;                                                        //группы юзеров, то есть сколько уникальных юзеров
          while (cursor.moveToNext()) countUsers++;

          String[] values = new String[countUsers];

          cursor.moveToFirst();

          for (int i = 0; i < countUsers; i++) {
              values[i] = (new BigDecimal((cursor.getFloat(cursor.getColumnIndex("AVG(SCORE)"))))).setScale(2,ROUND_HALF_UP).toString();
              cursor.moveToNext();
          }

          return values;
      }
      else{
          String[] values = new String[1];
          values[0] = "Тут пока что пусто...";
          return values;
      }

  }

    public String[] getName_sortBySrednee(){


            Cursor cursor = db.rawQuery("SELECT USERNAME FROM RESULTS GROUP by USERNAME ORDER by AVG(SCORE) DESC;", null);
            boolean go = cursor.moveToFirst();

        if(go) {
            int countUsers = 1;
            while (cursor.moveToNext()) countUsers++;

            String[] values = new String[countUsers];

            cursor.moveToFirst();

            for (int i = 0; i < countUsers; i++) {
                values[i] = cursor.getString(cursor.getColumnIndex("USERNAME"));
                cursor.moveToNext();
            }
            return values;
        }
        else{
            String[] values = new String[1];
            values[0]=" ";
            return values;
        }


    }


    public String[] getAllPlayer(){                         //тупо все игры по списку

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM RESULTS;", null);
        cursor.moveToFirst();

        int N=cursor.getInt(0);

        String[] games = new String[N];

        cursor = db.rawQuery("SELECT * FROM RESULTS;", null);


        boolean go = cursor.moveToFirst();

        while (go){
            games[cursor.getPosition()]=cursor.getString(cursor.getColumnIndex("USERNAME"));
            go=cursor.moveToNext();
        }
        return  games;
    }

    public String[] getAllScores(){                         //тупо все очки по списку

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM RESULTS;", null);
        cursor.moveToFirst();

        int N=cursor.getInt(0);

        String[] scores = new String[N];

        cursor = db.rawQuery("SELECT * FROM RESULTS;", null);


        boolean go = cursor.moveToFirst();

        while (go){
            scores[cursor.getPosition()]= Integer.toString(cursor.getInt(cursor.getColumnIndex("SCORE")));
            go=cursor.moveToNext();
        }
        return  scores;
    }

}