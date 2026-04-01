/*
 * Copyright (c) 2016. This App is Created by Ehsan Ul Haq & Used for Bahria Town official Used . Any other personal or Business can cause strict legal Action .
 */

package com.bahriatownpowerdivision.ehsan.mtp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class noreading extends AppCompatActivity {
    public EditText from;
    public EditText To;
    public Button btnSave;
    public static final String DB_NAME = "mrs.sqlite";
    //??????? ????????? ???????? ??????? ???? ????? ?? ???????????
    public static final String TABLE_NAME = "noreading";
    public static final String SELECT_SQL = "SELECT * FROM noreading";
    private ArrayList<String> results = new ArrayList<String>();
    public SQLiteDatabase database;
    public Cursor c;
    public int Ser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readingtodaylimit);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        from = (EditText) findViewById(R.id.from);
        To = (EditText) findViewById(R.id.to);
        btnSave=(Button)findViewById(R.id.btnSave);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        // displayResultList();
        // openAndQueryDatabase();
        c = database.rawQuery(SELECT_SQL, null);
    }
    public void readingsave(View view)
    {
        try
        {
            String Srfrom = from.getText().toString();
            String Srto = To.getText().toString();

            int Srfromint=Integer.parseInt(Srfrom);
            int SrtoInt=Integer.parseInt(Srto);


            if (from.getText().toString() == "" && To.getText().toString().equals("")){
                Toast.makeText(getBaseContext(), "First Enter limit then Save Please", Toast.LENGTH_SHORT).show();
            }
            else if (Srfromint > SrtoInt)
            {
                Toast.makeText(getBaseContext(), "Start Value cannot greater than end value", Toast.LENGTH_SHORT).show();
                from.setText("");
                To.setText("");
            }
            else
            {
                if (c!=null)
                {
                    c.moveToNext();
                    int rownum=1;
                    String sql = "UPDATE noreading SET start='" + Srfromint + "',end='" + SrtoInt +"'  WHERE ID=" + rownum + ";";
                    database.execSQL(sql);
                    c = database.rawQuery(SELECT_SQL, null);
                    Toast.makeText(getApplicationContext(), "New Limit Value Update ", Toast.LENGTH_LONG).show();
                    from.setText("");
                    To.setText("");
                }
                else
                {
                    int recnum=1;
                    String sql = "INSERT INTO '" + TABLE_NAME + "' (ID,start,End) VALUES ('" + recnum + "','" + Srfromint + "','" + Srto + "')";
                    database.execSQL(sql);
                    c = database.rawQuery(SELECT_SQL, null);
                    Toast.makeText(getApplicationContext(), "Limit Set of today reading ", Toast.LENGTH_LONG).show();
                    from.setText("");
                    To.setText("");
                }
            }
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
