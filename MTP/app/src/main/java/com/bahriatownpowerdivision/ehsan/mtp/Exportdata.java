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
        String x = getMacAddr();
        if (x.equals("08:21:EF:31:1C:43")) {
            owner = "Hafeez Hassan";
            shortname = "HH";
        } else if (x.equals("A6:D6:80:99:2B:76")) {
            owner = "Mateen Ghulfam";
            shortname = "MG";
        } else if (x.equals("B4:CE:40:2C:62:DA")) {
            owner = "Mustfeez Ur Rehman";
            shortname = "MR";
        } else if (x.equals("34:82:C5:AC:20:01")) {
            owner = "Kashif Hanif";
            shortname = "KH";
        } else if (x.equals("34:82:C5:A8:02:C7")) {
            owner = "Mudassir Ameen";
            shortname = "MA";
        } else if (x.equals("A2:F6:AF:3C:62:21")) {
            owner = "Mian Imran Akhtar";
            shortname = "MIA";
        } else if (x.equals("B2:04:A9:06:76:1E")) {
            owner = "Muhammad Mubashar";
            shortname = "MM";
        } else if (x.equals("34:82:C5:A7:F9:B5")) {
            owner = "Etsham Khaliq";
            shortname = "EK";
        } else if (x.equals("3E")) {
            owner = "MUHAMMAD YOUSAF";
            shortname = "MY";
        } else if (x.equals("54:FC:F0:E9:A5:CC")) {
            owner = "ADNAN MUNIR";
            shortname = "AMUNIR";
        } else if (x.equals("3E:E3:F0:16:AE:29")) {
            owner = "Muhammad Yousaf";
            shortname = "IY";
        } else if (x.equals("71:1C:68:A0:16:50")) {
            owner = "Muhammad Haroon";
            shortname = "MH";
        } else if (x.equals("54:FC:F0:E5:FE:DA")) {
            owner = "EHSAN UL HAQ";
            shortname = "EXH";
        } else if (x.equals("48:C7:96:2A:AA:25")) {
            owner = "Taimoor";
            shortname = "TM";
        } else if (x.equals("b4:86:55:a0:fb:10")) {
            owner = "Zulfiqar";
            shortname = "ZF";
        } else if (x.equals("14:96:E5:4D:5F:78")) {
            owner = "Khawaja Mohsin";
            shortname = "KM";
        }

        else if(x.equals("4C:DD:31:6D:48:17") | address.equals("4C:DD:31:6D:48:17")) {
            owner = "MUHAMMAD UMAIR";
            shortname = "MU";
            //J5
        }
        else if(x.equals("F4:C2:48:AF:F0:89") | address.equals("F4:C2:48:AF:F0:89")) {
            owner = "Mubashar Nawaz";
            shortname = "MN";
            //J6
        }
        else if(x.equals("EE:24:08:4F:03:5A") | address.equals("EE:24:08:4F:03:5A")) {
            owner = "BILAL KHAN";
            shortname = "BK";
            //J6
        }

        else if(x.equals("F0:8A:76:E5:60:E4")| address.equals("F0:8A:76:E5:60:E4")) {
            owner = "SEMAB ISHTIAQ";
            shortname = "SI";
            //J5
        }
        else if(x.equals("3A:A5:EE:34:C8:0F") | address.equals("3A:A5:EE:34:C8:0F") ) {
            owner = "Sajjad Hussain";
            shortname = "SH";
            //J5
        }

        else if(x.equals("42:D6:AF:A5:93:CA") | address.equals("42:D6:AF:A5:93:CA") ) {
            owner = "Tanzeel Rashid";
            shortname = "TR";
            //J5
        }
        else if(x.equals("56:BF:A7:6B:FF:DC") | address.equals("56:BF:A7:6B:FF:DC") ) {
            owner = "Rashid Abbas";
            shortname = "RA";
        }
        else if(x.equals("1A:32:00:DF:FA:CD") | address.equals("1A:32:00:DF:FA:CD")) {
            owner = "Hassan Fida";
            shortname = "HF";
            //J5
        }
        else if(x.equals("B0:6F:E0:4E:B4:16") | address.equals("B0:6F:E0:4E:B4:16") ) {
            owner = "Tayyab Mehmood";
            shortname = "TM";
            //J5
        }
        else if(x.equals("12:07:7B:32:00:76") | address.equals("12:07:7B:32:00:76")) {
            owner = "Faiz Ul Hassan";
            shortname = "FH";
            //J5
        }
        else if(x.equals("D8:55:75:85:CD:B2") | address.equals("D8:55:75:85:CD:B2")) {
            owner = "Zeeshan Ahmed";
            shortname = "ZA";
        }
        else if(x.equals("2E:99:10:27:B7:08") | address.equals("2E:99:10:27:B7:08")) {
            owner = "Habib Ur Rehman";
            shortname = "MH";
        }
        else if (x.equals("48:C7:96:2A:AA:25") | address.equals("48:C7:96:2A:AA:25") ) {
            owner = "Taimoor";
            shortname = "TM";}
        else if (x.equals("b4:86:55:a0:fb:10") | address.equals("b4:86:55:a0:fb:10")) {
            owner = "Zulfiqar";
            shortname = "ZF";}
        else if (x.equals("14:96:E5:4D:5F:78") | address.equals("14:96:E5:4D:5F:78")) {
            owner = "Khawaja Mohsin";
            shortname = "KM";}

        else {
            owner = "";
            shortname = "";
        }
    }

    public void BackupDatabase(View v) throws IOException {
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.text2);
        pwd = (EditText) findViewById(R.id.text4);


//        name.setText("androidexport");
//        pwd.setText("3720");

        Context context = getApplicationContext();
        try {
            if (name.getText().toString().equals("androidexport") && pwd.getText().toString().equals("3720")) {
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