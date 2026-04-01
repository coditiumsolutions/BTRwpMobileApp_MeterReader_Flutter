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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;


public class Exportdata extends Activity implements View.OnClickListener {

    public static final String DB_NAME = "mrs.sqlite";
    //������� ��������� �������� ������� ���� ����� �� �����������
    public SQLiteDatabase database;
    private Button login;
    private EditText name;
    private EditText pwd;
    String owner = "";
    String formattedDate;
    String shortname = "";
    String Msg;
    private DeviceAdminInfo context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exportdata);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.text2);
        pwd = (EditText) findViewById(R.id.text4);
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        String androidID = Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidID.equals("fc2f6a7323f61969")) { owner = "Mustfeez Ur Rehman";shortname = "MR"; }   // Done
        else if (androidID.equals("b41ad8c87889a7eb")) { owner = "Kashif Hanif";shortname = "MMS"; }    // Done
        else if (androidID.equals("f9c09d0d5c4fb2df")) { owner = "Sharjeel Arshad";shortname = "SA"; }  // Done
        else if (androidID.equals("17e1a1ed87e20512")) { owner = "Ahtisham Khaliq";shortname = "AK"; }    // Done
        else if (androidID.equals("4a608a5317248f11")) { owner = "Kashif Hanif";shortname = "EXH"; }    // Done
        else if (androidID.equals("40747db6efad489e")) { owner = "Kashif Ali Khan";shortname = "KA"; }  // Done
        else if (androidID.equals("7b3bff9ed18a2c4e")) { owner = "Muhammad Yousaf";shortname = "MY"; }  // Done
        else if (androidID.equals("572B88C95CF1DEA8")) { owner = "Mian Imran Akhtar";shortname = "MIA"; } // Ni dy rha bol rha hy personal device hy
        else if (androidID.equals("302d9329d58259bf")) { owner = "Mudassir Ameen";shortname = "MA"; }    // Done
        else if (androidID.equals("b51a8aaa93f900b9")) { owner = "Rashid Abbas";shortname = "RA"; }   // Done
        else if (androidID.equals("9c26ea45d368a2ad")) { owner = "Mansoor Liaquat";shortname = "ML"; } // Done

//          Phase 8 Meter Reader
        else if (androidID.equals("af669d13929dd2ac")) { owner = "Seemab Ishtiaq";shortname = "SI"; }
        else if (androidID.equals("d95ba7ec583f1afe")) { owner = "Sajjad Hussain";shortname = "SH"; }
        else if (androidID.equals("4bf439a3813a8e8b")){ owner = "Hassan Fida";shortname = "HF"; }
        else if (androidID.equals("b0bfbbba199a6eef")) { owner = "Tayyab Mehmood";shortname = "TM"; }
        else if (androidID.equals("c673b880dbc5dabf")) { owner = "Faiz Ul Hassan";shortname = "FH"; }
        else if (androidID.equals("bbb0b371e12aa47e")) { owner = "Ahad Mehmood";shortname = "AM"; }
        else if (androidID.equals("3c6c2808f362729b")) { owner = "Moiz Rajpoot";shortname = "MR"; }
        else if (androidID.equals("ea00e8ff18ebca0e")) { owner = "Saad Hafeez";shortname = "SM"; }
        else if (androidID.equals("970440d4f9ce7927")) { owner = "Rashid Abbas";shortname = "RA"; }
        else if (androidID.equals("d8f320b88a1c3604")) { owner = "Zeeshan Ahmed";shortname = "ZA"; }
        else if (androidID.equals("7a5a28a153c6fc0e")) { owner = "Muhammad Mohsin";shortname = "MK"; }
        else if (androidID.equals("aa2ef86e759dacfb")) { owner = "Shahab Ahmed";shortname = "SA"; }
        else if (androidID.equals("cb2dab9dac54cf6f")) { owner = "Manan Qadeer";shortname = "MQ"; }
        else if (androidID.equals("e3d7e11a2ce0f55d")) { owner = "Mubashar Nawaz";shortname = "MN"; }

//            BAHRIA ENCLAVE BILLING TEAM
        else if (androidID.equals("96E70AD0BD45769B")) { owner = "Taimoor Hussain";shortname = "TM"; }
        else if (androidID.equals("DFC48E5E15B96475")) { owner = "Zulfiqar Ahmed";shortname = "ZF"; }
        else if (androidID.equals("E9108BBEAD020BC7")) { owner = "Mohsin Shareef";shortname = "MS"; }
        else if (androidID.equals("3CCB4AF0B639EC46")) { owner = "Faizan Haider";shortname = "FH"; }
        else { owner = "";shortname = ""; }
        }

    public void BackupDatabase(View v) throws IOException {
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.text2);
        pwd = (EditText) findViewById(R.id.text4);


//        name.setText("androidexport");
//        pwd.setText("3720");

        Context context = getApplicationContext();
        try {
          //  if (name.getText().toString().equals("androidexport") && pwd.getText().toString().equals("3720")) {
            if (name.getText().toString().equals("") && pwd.getText().toString().equals("")) {
                   File backupDB;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    backupDB = new File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "ExportedSQl");
                } else {
                    backupDB = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
                }

                File file = new File(backupDB, DB_NAME + "-" + shortname + "-" + formattedDate);


                File currentDB = context.getDatabasePath(DB_NAME);
                Toast.makeText(getBaseContext(), currentDB.toString(), Toast.LENGTH_SHORT).show();
                if (currentDB.exists()) {
                    Msg = "Database Export Complete";
                    showSnackbar();
                    FileInputStream fis = new FileInputStream(currentDB);
                  //  InputStream inputStream = new InputStream(currentDB.toPath());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        ContentResolver resolver = getContentResolver();
                        ContentValues values = new ContentValues();
                        ContentValues values2 = new ContentValues();

                        // File newFile = new File(backupDB, DB_NAME + "-" + shortname + "-" + formattedDate);

                        values.put(MediaStore.MediaColumns.DISPLAY_NAME, DB_NAME + "-" + shortname + "-" + formattedDate);
                        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.sqlite3");
                        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" +  "MeterReading App DB");
//                        values2.put(MediaStore.MediaColumns.DISPLAY_NAME, DB_NAME);
//                        values2.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" +  "Database");

                        Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);
                        Uri uri2 = resolver.insert(MediaStore.Files.getContentUri("external"), values2);
                        OutputStream fos =resolver.openOutputStream(uri);
                        OutputStream fos2 =resolver.openOutputStream(uri2);

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos2.write(buffer, 0, len);

                        }
                        fos.close();
                        fos2.close();
                        fis.close();
                    }else{
                        String path = Environment.getExternalStorageDirectory() + "/" + DB_NAME;
                        File file2 = new File(path);
                        OutputStream myOutput = new FileOutputStream(file2,false);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            myOutput.write(buffer, 0, length);
                        }

                        myOutput.flush();
                        myOutput.close();
                      //  myInput.close();

                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        FileOutputStream fos = new FileOutputStream(file, false);
                        fos.getChannel().transferFrom(fis.getChannel(), 0, fis.getChannel().size());
                        // or fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                        fis.close();
                        fos.close();
                    }

                    name.setText("");
                    pwd.setText("");
                } else {
                    Msg = "Database not exist";
                    showSnackbar();
                }
                ;
            } else {
                Msg = "Wrong Username & Password Supplied";
                showSnackbar();
            }

        } catch (IOException e) {
            e.printStackTrace();
            displaybackuperror(e.getMessage());
        }
    }

    public void displaybackuperror(String msg) {
        Msg = "Error in Copying Database";
        showSnackbar();
    }

    @Override
    public void onClick(View v) {

    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
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