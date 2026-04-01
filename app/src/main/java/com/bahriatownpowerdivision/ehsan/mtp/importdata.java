package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by EHSAN on 12/11/2015.
 */
public class importdata extends Activity implements View.OnClickListener {

    public static final String DB_NAME = "mrs.sqlite";
    //������� ��������� �������� ������� ���� ����� �� �����������
    public SQLiteDatabase database;
    private Button login;
    private EditText name;
    private EditText pwd;
    String Msg;
    private DeviceAdminInfo context;
    private static String DB_PATH = "/data/data/com.bahriatownpowerdivision.ehsan.mtp/databases/";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.importdatabase);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.text2);
        pwd = (EditText) findViewById(R.id.text4);
    }

    public void importdatabase(View v) throws IOException {
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.text2);
        pwd = (EditText) findViewById(R.id.text4);

//        name.setText("androidimport");
//        pwd.setText("3720");


        Context context = getApplicationContext();
        if (name.getText().toString().equals("androidimport") && pwd.getText().toString().equals("3720")) {

            String fileName = "mrs.sqlite";
            String path = Environment.getExternalStorageDirectory() + "/" + fileName;
/*
            File currentDB = context.getDatabasePath(DB_NAME);
            FileInputStream myInput = new FileInputStream(currentDB);
 */

            InputStream myInput = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
             //   intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, 2);

            } else {
                File file = new File(path);
                myInput = new FileInputStream(file);

                String outFileName = DB_PATH + DB_NAME;
                //Open the empty db as the output stream
                OutputStream myOutput = new FileOutputStream(outFileName);
                //transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
                //Close the streams
                Msg = "Database Imported Succesfully";
                showSnackbar();
            }



        }

        else {
            Msg = "Provide Correct Username & Password";
            showSnackbar();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode ==Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                InputStream fis = null;
                if (uri != null) {
                    try {
                        fis = getContentResolver().openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                String outFileName = DB_PATH + DB_NAME;

                try {
                    OutputStream myOutput = new FileOutputStream(outFileName);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) != -1) {
                        myOutput.write(buffer, 0, len);

                    }
                    myOutput.close();
                    fis.close();
                    //Close the streams
                    Msg = "Database Imported Succesfully";
                    showSnackbar();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public void displaybackuperror(String msg) {
        Msg = "File Not Found";
        showSnackbar();
    }

    @Override
    public void onClick(View v) {

    }

    public void showSnackbar() {
        // Create and show the snackbar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), Msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.rgb(173, 138, 46));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.snackbar_textsize));
        snackbar.show();
    }
}