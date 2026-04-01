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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Exhan on 12/1/2015.
 */
public class checkreport2 extends ListActivity {
    public static final String DB_NAME = "mrs.sqlite";
    //??????? ????????? ???????? ??????? ???? ????? ?? ???????????
    public static final String TABLE_NAME = "Sheet";
    private ArrayList<String> results = new ArrayList<String>();
    public SQLiteDatabase database;
    public Cursor c;
    public int Ser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkreport2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        displayResultList();
        openAndQueryDatabase();
    }
    private void displayResultList() {
        TextView tView = new TextView(this);
        tView.setText("    Sr \t\tRefrence \t\t\t\t\tPrevious \tPresent\tStatus ");
        getListView().addHeaderView(tView);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);

    }
    private void openAndQueryDatabase() {
        try {
            String Status="RG";
            String Status1="";
            String Status2="PD";
            ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
            database = dbOpenHelper.openDataBase();
            String sql="SELECT * FROM Sheet WHERE (Status <> '" + Status +"') And (Status <> '" + Status1 +"') And (Status <> '" + Status2 +"')";
//            String sql=("select * from Accounts where Status like " + "\'" +Status + "\' OR Status like \'" + Status1 + "\'");
            Cursor c = database.rawQuery(sql, null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        Ser = c.getPosition()+1;
                        String Refrence = c.getString(c.getColumnIndex("Refrence"));
                        int previous = c.getInt(c.getColumnIndex("Previous"));
                        int present = c.getInt(c.getColumnIndex("Present"));
                        String pd=c.getString(c.getColumnIndex("Status"));
                        results.add( String.format("%02d",Ser) +"\t\t"+ Refrence+ "\t\t" + previous + "\t\t\t\t" + present+ "\t\t" + pd);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}
