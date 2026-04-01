/*
 * Copyright (c) 2016. This App is Created by Ehsan Ul Haq & Used for Bahria Town official Used . Any other personal or Business can cause strict legal Action .
 */

package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by EXHAN on 1/31/2016.
 */
public class counter extends Activity {
    public static final String DB_NAME = "mrs.sqlite";
    //??????? ????????? ???????? ??????? ???? ????? ?? ???????????
    public static final String TABLE_NAME = "Sheet";
    public SQLiteDatabase database;
    public Cursor c,c1,c2,c3,c4,c5,c6;
    public int Ser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        String Status1="PD" , Status2="DF" , Status3 ="LK" , Status4 = "RP";
        String picsnottake="1";
        String query1 = "Select * from Sheet";
        String query2 = "SELECT * FROM Sheet WHERE Status ='" + Status1 +"'";
        String query3 = "SELECT * FROM Sheet WHERE Status ='" + Status2 +"'";
        String query4 = "SELECT * FROM Sheet WHERE Status ='" + Status3 +"'";
        String query5 = "SELECT * FROM Sheet WHERE Status ='" + Status4 +"'";

        c = database.rawQuery(query1, null);
        c1 = database.rawQuery(query2, null);
        c2 = database.rawQuery(query3, null);
        c3 = database.rawQuery(query4, null);
        c4 = database.rawQuery(query5, null);

        TextView tv=(TextView)findViewById(R.id.totalconnection);
        TextView tv1=(TextView)findViewById(R.id.pd);
        TextView tv2=(TextView)findViewById(R.id.df);
        TextView tv3=(TextView)findViewById(R.id.lk);
        TextView tv4=(TextView)findViewById(R.id.rp);
        TextView tv5=(TextView)findViewById(R.id.previousreading);
        TextView tv6=(TextView)findViewById(R.id.presentreading);
        TextView tv7=(TextView)findViewById(R.id.advance);
        tv.setText("Total Number of Connection : " + String.valueOf(c.getCount()));
        tv1.setText("Total Number of PD Connection: " + String.valueOf(c1.getCount()));
        tv2.setText("Total Number of DF Connection: " + String.valueOf(c2.getCount()));
        tv3.setText("Total Number of LK Connection: " + String.valueOf(c3.getCount()));
        tv4.setText("Total Number of RP Connection: " + String.valueOf(c4.getCount()));
        int total = 0 , total2=0;
        String query6 = "SELECT Sum(Previous) FROM Sheet";
        String query7 = "SELECT Sum(Present) FROM Sheet";
        c5 = database.rawQuery(query6, null);
        if (c5.moveToFirst())
        {
            total = c5.getInt(0);
        } while (c5.moveToNext());
        tv5.setText("Sum of Previous Reading: " + String.valueOf(total));
        // Present Reading
        c6 = database.rawQuery(query7, null);
        if (c6.moveToFirst())
        {
            total2 = c6.getInt(0);
        } while (c6.moveToNext());
        tv6.setText("Sum of Present Reading: " + String.valueOf(total2));

        int advance=total2 - total;
        tv7.setText("Current Month Advance: " + String.valueOf(advance));

    }
}