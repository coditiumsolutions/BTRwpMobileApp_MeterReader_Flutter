/*
 * Copyright (c) 2016. This App is Created by Ehsan Ul Haq & Used for Bahria Town official Used . Any other personal or Business can cause strict legal Action .
 */

package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class checkreport3 extends ListActivity {
    public static final String DB_NAME = "mrs.sqlite";
    //??????? ????????? ???????? ??????? ???? ????? ?? ???????????
    public static final String TABLE_NAME = "Sheet";
    private ArrayList<String> results = new ArrayList<String>();
    public SQLiteDatabase database;
    public Cursor c;
    public int Ser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkreport);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        displayResultList();
        openAndQueryDatabase();

    }
    public void displayResultList() {
        TextView tView = new TextView(this);
        tView.setText("\tSer\t\t\tMeter\t\t\tPresent\t\t\tStatus");
        getListView().addHeaderView(tView);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);

    }
    public void openAndQueryDatabase() {
        try {
            String Status = "RG";
            String Status1 = "";
            String Status2 = "PD";
            ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
            database = dbOpenHelper.openDataBase();

            String query = "SELECT * FROM noreading";
            Cursor cursor = database.rawQuery(query, null);
            int startlimit=0;
            int endlimit = 0;
            if (cursor != null) {
               {
                   cursor.moveToFirst();
                    startlimit = cursor.getInt(cursor.getColumnIndex("start"));
                    endlimit = cursor.getInt(cursor.getColumnIndex("end"));
                }
            }
            //// Limit Find
            String remaining="";
            int pictakes=1;
            String sql = "SELECT * FROM Sheet where ID BETWEEN '" + startlimit + "' AND '" + endlimit + "' AND (Present = '" + remaining +"') ORDER BY ID ASC";
            Cursor c = database.rawQuery(sql, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        int valuedisplay = 0;
                        String textdisplay = null;
                        String setvalue=null;
                        int Ser = c.getInt(c.getColumnIndex("ID"));;
                        int present = c.getInt(c.getColumnIndex("Present"));
                        int meter = c.getInt(c.getColumnIndex("Meter"));
                        String pd =c.getString(c.getColumnIndex("Status")) ;


                        if (pd == "") {setvalue="";}
                        else{setvalue=pd;}
                        results.add(Ser+ "\t\t\t" + meter + "\t\t\t" + present +  "\t\t\t" +  setvalue);
                    } while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}
