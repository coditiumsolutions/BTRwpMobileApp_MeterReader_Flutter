package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


/**
 * Created by EHSAN on 12/11/2015.
 */
public class newc extends Activity implements View.OnClickListener
{

    //������� ��������� �������� ������� ���� ����� �� �����������
    public static final String DB_NAME = "mrs.sqlite";
    //??????? ????????? ???????? ??????? ???? ????? ?? ???????????
    public static final String TABLE_NAME = "Sheet";
    private static final int CAMERA_REQUEST = 1888;
    public EditText editTextRefrence;
    public EditText editTextPlot;
    public EditText editTextStreet;
    public EditText editTextMeter;
    public EditText editTextCurrent;
    public Button btnSave;
    public SQLiteDatabase database;
    public Cursor c;

    public static final String SELECT_SQL = "SELECT * FROM Sheet";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRinger();
        setContentView(R.layout.newconnection);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        editTextRefrence = (EditText) findViewById(R.id.editref);
        editTextPlot = (EditText) findViewById(R.id.editPlot);
        editTextStreet = (EditText) findViewById(R.id.editstreet);
        editTextMeter = (EditText) findViewById(R.id.editmeter);
        editTextCurrent = (EditText) findViewById(R.id.currentreading);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    public void PicsTake(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String NMFolder="NM";
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder,  editTextMeter.getText().toString()+ ".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Cursor c1;
        String ref,meter;
        ref=editTextRefrence.getText().toString();
        meter=editTextMeter.getText().toString();
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
//                String ID = editID.getText().toString().trim();
//                File image = new File(Environment.getExternalStorageDirectory(), "MyCameraImages" + "/" + editTextRefrence.getText().toString() + ".jpg");
//                int flagyes = 1;
                Toast.makeText(getBaseContext(),editTextMeter+" with "+meter+" number image saved",Toast.LENGTH_LONG).show();
//
            } catch (OutOfMemoryError e) {

            }
        }
    }
    public void savenewconn() {
        String ref = editTextRefrence.getText().toString();
        String plot = editTextPlot.getText().toString();
        String street = editTextStreet.getText().toString();
        String Meter = editTextMeter.getText().toString();
        String currentreading = editTextCurrent.getText().toString();
        String upperref=ref.toUpperCase();
        String status="NM";
        try
        {
            int i = getvalue();
            i = i +1;
            String sql = "INSERT INTO '" + TABLE_NAME + "' (ID,Refrence, Plot, Street,Meter ,Present ,Status) VALUES ('" + i + "','" + upperref + "', '" + plot + "', '" + street + "' , '" + Meter + "' , '" + currentreading + "' , '" + status + "')";
            database.execSQL(sql);
             c = database.rawQuery(SELECT_SQL, null);
              Toast.makeText(getApplicationContext(), "Data Enter Succesfully ", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            displayExceptionMessageprimary(e.getMessage());
        }

    }

    public void setRinger()
    {
        AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        audioManager.setRingerMode(audioManager.RINGER_MODE_SILENT);
    }
    public int getvalue() {
        String  value;
        c = database.rawQuery("SELECT ID FROM Sheet ORDER BY ID DESC LIMIT 1", null);
        c.moveToLast();
        value = String.valueOf(Integer.parseInt(c.getString(0)));
        return Integer.parseInt(value);
    }

    public void displayExceptionMessageprimary(String msg) {
        Toast.makeText(this, "Same Refrence Already Exist", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            savenewconn();
        }
    }
}