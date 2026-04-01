package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Exhan on 12/1/2015.
 */
public class checkreport extends ListActivity {
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
        displayResultList();
        openAndQueryDatabase();
    }
    private void displayResultList() {
        TextView tView = new TextView(this);
        tView.setText("    Sr \t\tRefrence \t\t\t\t\tPrevious \tPresent ");
        getListView().addHeaderView(tView);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);

    }
    private void openAndQueryDatabase() {
        try {
            ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
            database = dbOpenHelper.openDataBase();
            String Status="";
            Cursor c = database.rawQuery("SELECT * from " + TABLE_NAME + " ORDER BY Refrence ASC" , null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        Ser = c.getPosition()+1;
                        String Refrence = c.getString(c.getColumnIndex("Refrence"));
                        int previous = c.getInt(c.getColumnIndex("Previous"));
                        int present = c.getInt(c.getColumnIndex("Present"));
                        String pd=c.getString(c.getColumnIndex("Status"));
                        results.add( String.format("%02d",Ser) +"\t\t"+ Refrence+ "\t\t" + previous + "\t\t" + present);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}
