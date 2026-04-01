/*
 * Copyright (c) 2016. This App is Created by Ehsan Ul Haq & Used for Bahria Town official Used . Any other personal or Business can cause strict legal Action .
 */

package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.InputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.String;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class bybatch extends Activity implements View.OnClickListener {

    public static final String DB_NAME = "mrs.sqlite";
    //??????? ????????? ???????? ??????? ???? ????? ?? ???????????
    public static final String TABLE_NAME = "Sheet";
    // String tag=
    public EditText editTextRefrence, editTextPresent, editTextAdvance, editID, editpeak, editoffpeak;
    ;
    public EditText editTextPlotStreet, editTextMeter, editTextPrevious, editpreviouspeak, editpreviousoffpeak, edittariff;
    public Button btnPrev, btnNext, btnSave, btnpeak, btnoffpeak, btnCaputure;
    public ImageView imageView;
    public int curdorPos;
    public SQLiteDatabase database;

    public Cursor c;
    public static final String SELECT_SQL = "SELECT * FROM Sheet";
    public static final int CAMERA_REQUEST = 1888, CAMERA_REQUEST1 = 1889, CAMERA_REQUEST2 = 1890;
    String owner = "", formattedDate, shortname = "", Msg, Msg1;
    String currentPhotoPath;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.completebilling);
//        openDatabase();
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        editTextRefrence = (EditText) findViewById(R.id.editref);
        editTextPlotStreet = (EditText) findViewById(R.id.editPS);
        editTextMeter = (EditText) findViewById(R.id.editmeter);
        editTextPrevious = (EditText) findViewById(R.id.editprevious);
        editTextPresent = (EditText) findViewById(R.id.editpresent);
        editTextAdvance = (EditText) findViewById(R.id.editadvance);
        editpreviouspeak = (EditText) findViewById(R.id.previouspeak);
        editpreviousoffpeak = (EditText) findViewById(R.id.previousoffpeak);
        edittariff = (EditText) findViewById(R.id.edittariff);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        btnpeak = (Button) findViewById(R.id.btnpeak);
        btnoffpeak = (Button) findViewById(R.id.btnoffpeak);
        btnCaputure = (Button) findViewById(R.id.btnCapture);

        btnpeak.setOnClickListener(this);
        btnoffpeak.setOnClickListener(this);
        editID = (EditText) findViewById(R.id.editID);
        editpeak = (EditText) findViewById(R.id.editpeak);
        editoffpeak = (EditText) findViewById(R.id.editoffpeak);
        editID.setVisibility(View.INVISIBLE);
        c = database.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();
        String x = getMacAddr();
    }

    public void saveRecord() {
        try {
            String ID = editID.getText().toString().trim();
            int diff;
            String radiovalue = "";
            String ps = editTextPresent.getText().toString().trim();
            String pv = editTextPrevious.getText().toString().trim();
            String peak = editpeak.getText().toString().trim();
            String offpeak = editoffpeak.getText().toString().trim();
            String tariffdetail = edittariff.getText().toString().trim();

            int valuepeak = 0, valueoffpeak = 0, valuesumof = 0;
            try {
                valuepeak = Integer.valueOf(editpeak.getText().toString());
                valueoffpeak = Integer.valueOf(editoffpeak.getText().toString());
                valuesumof = valuepeak + valueoffpeak;
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            Integer previous = Integer.parseInt(editTextPrevious.getText().toString().trim());
            Integer valueprev = Integer.valueOf(editTextPrevious.getText().toString());
            Integer valuepres = Integer.valueOf(editTextPresent.getText().toString());

            diff = Integer.parseInt(String.valueOf(valuepres)) - Integer.parseInt(String.valueOf(valueprev));
            Integer present = 0;
            String status = c.getString(7);
            RadioGroup rdgrp = (RadioGroup) findViewById(R.id.radioGroup1);
            radiovalue = ((RadioButton) this.findViewById(rdgrp.getCheckedRadioButtonId())).getText().toString();
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();
            Calendar e = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = df.format(e.getTime());
            String x;
            x = getMacAddr();
            if (x.equals("08:21:EF:31:1C:43")) {   // 08:21:EF:31:1C:43" OLD HAFEEZ HASSAN
                owner = "Hafeez Hassan";
                shortname = "HH";
            } else if (x.equals("B4:CE:40:2C:62:DA")) {
                owner = "Mustfeez Ur Rehman";
                shortname = "MR";
            } else if (x.equals("34:82:C5:AC:20:01")) {
                owner = "Kashif Hanif";
                shortname = "KH";
            } else if (x.equals("A6:D6:80:99:2B:76")) {
                owner = "Mateen Ghulfam";
                shortname = "MG";
            } else if (x.equals("34:82:C5:A8:02:C7")) {
                owner = "Mudassir Ameen";
                shortname = "MA";
            } else if (x.equals("A2:F6:AF:3C:62:21")) {
                owner = "Mian Imran Akhtar";
                shortname = "MIA";
            } else if (x.equals("34:82:C5:A7:F9:B5")) {
                owner = "Etsham Khaliq";
                shortname = "EK";
            } else if (x.equals("3E:E3:F0:16:AE:29s")) {
                owner = "IMRAN Yousaf";
                shortname = "MIA";
            } else if (x.equals("54:FC:F0:E9:A5:CC")) {
                owner = "ADNAN MUNIR";
                shortname = "AMUNIR";
            } else if (x.equals("B2:04:A9:06:76:1E")) {
                owner = "Muhammad Mubashar";
                shortname = "MM";
            } else if (x.equals("3E:E3:F0:16:AE:29")) {
                owner = "Muhammad Yousaf";
                shortname = "MY";
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
            ///////////// Radio Button Value
            if (editTextPresent.getText().toString() == "" || editTextPresent.getText().toString().equals("")) {
                present = 0;
                diff = 0;
                String sql = "UPDATE Sheet SET Present='" + ps + "', advance='" + diff + "', Status='" + radiovalue + "',time='" + formattedDate + "' , readingby='" + shortname + "'  WHERE ID=" + ID + ";";
                database.execSQL(sql);
                // Get current position of Cursor
                curdorPos = c.getPosition();
                editTextAdvance.setText(Integer.toString(0));
                Msg = "First Enter Correct Reading and Then Press Save";
                showSnackbar();
                c = database.rawQuery(SELECT_SQL, null);
                diff = 0;
                // And then move cursor to specific position
                c.moveToPosition(curdorPos);
            } else {
                present = Integer.parseInt(editTextPresent.getText().toString());
                //  previous = Integer.parseInt(editTextPrevious.getText().toString());
                RadioGroup group1 = (RadioGroup) findViewById(R.id.radioGroup1);
                int selected = group1.getCheckedRadioButtonId();
                RadioButton button1 = (RadioButton) findViewById(selected);
                if (previous > present) {
                    //diff = Integer.parseInt(ps) - Integer.parseInt(pv);
                    diff = 0;
                    if ("RP".equals(button1.getText())) {
                        String sql = "UPDATE Sheet SET Present='" + ps + "', advance='" + diff + "' ,peak='" + peak + "' ,offpeak='" + offpeak + "', Status='" + radiovalue + "',time='" + formattedDate + "' , readingby='" + shortname + "'  WHERE ID=" + ID + ";";
                        database.execSQL(sql);
                        curdorPos = c.getPosition();
                        // Get current position of Cursor
                        //   int curdorPos = c.getPosition();
                        editTextAdvance.setText(Integer.toString(0));
                        Msg = "Records Saved Successfully";
                        showSnackbar();
                        //                    Log.d(TAG, ref + space + "with" + space + present + space + "Reading Saved");
                        c = database.rawQuery(SELECT_SQL, null);
                        // And then move cursor to specific position
                        c.moveToPosition(curdorPos);
                    } else {
                        editTextPresent.setText("");
                        Msg = "You cannot save less value other than replace";
                        showSnackbar();
                    }
                } else if (previous <= present) {
                    diff = Integer.parseInt(String.valueOf(valuepres)) - Integer.parseInt(String.valueOf(valueprev));

                    if ((present - valuesumof > 4) & (tariffdetail.equals("A-1b(03T)"))) {
                        Msg = "Present Reading is greater than Peak/OffPeak Please Check";
                        showSnackbar();

                        editTextPresent.setText("");
                        editpeak.setText("");
                        editoffpeak.setText("");
                        editTextAdvance.setText(Integer.toString(0));
                    } else if ((present - valuesumof < -4) & (tariffdetail.equals("A-1b(03T)"))) {
                        Msg = "OffPeak & Peak is greater than Present Reading ";
                        showSnackbar();

                        editTextPresent.setText("");
                        editpeak.setText("");
                        editoffpeak.setText("");
                        editTextAdvance.setText(Integer.toString(0));
                    } else {
                        String sql = "UPDATE Sheet SET Present='" + ps + "', advance='" + diff + "' ,peak='" + peak + "' ,offpeak='" + offpeak + "', Status='" + radiovalue + "',time='" + formattedDate + "' , readingby='" + shortname + "'  WHERE ID=" + ID + ";";
                        database.execSQL(sql);
                        // Get current position of Cursor
                        curdorPos = c.getPosition();
                        Msg = "Records Saved Successfully";
                        showSnackbar();
                        c = database.rawQuery(SELECT_SQL, null);
                        // And then move cursor to specific position
                        c.moveToPosition(curdorPos);
                        editTextAdvance.setText(Integer.toString(diff));
                    }
                } else {
                    editTextPresent.setText("");
                    editTextAdvance.setText(Integer.toString(0));
                    Msg = "You are Entering Wrong Reading";
                    showSnackbar();
                }
            }
        } catch (NumberFormatException ex) {
            Msg = "Error  Please Click the save button after reading input";
            showSnackbar();
        }
    }

    public void PicsTake(View v) {

        createNewIntent(CAMERA_REQUEST, editTextRefrence.getText().toString() + ".jpg");

    }

    public void PicsTake1() {
        createNewIntent(CAMERA_REQUEST1, editTextRefrence.getText().toString() + "P" + ".jpg");
    }


    public void PicsTake2() {
        createNewIntent(CAMERA_REQUEST2, editTextRefrence.getText().toString() + "OP" + ".jpg");

    }

    private void createNewIntent(Integer requestId, String fileName) {
        /**
         * Creeating Image File here new :
         */


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp");
            imagesFolder.mkdirs();
            File image = new File(imagesFolder, fileName);
            Uri uriSavedImage = Uri.fromFile(image);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(cameraIntent, requestId);
        } else {
            String imageFileName = fileName;
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File newImage = null;
            try {
                newImage = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            /**
             * getting the TempFileCreated by currentPhoto Path :
             */
            currentPhotoPath = newImage.getAbsolutePath();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = newImage;
            if (photoFile != null) {
                try {
                    /**
                     * The nwew way for api 29+ , is Using FileProvider and attaching it in manifest
                     */
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.bahriatownpowerdivision.ehsan.mtp",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, requestId);
                } catch (Exception e) {
                    Toast.makeText(this, "Check Your SD Card Please", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST1 && resultCode == -1) {
            btnpeak.setBackgroundColor(Color.GREEN);
            Toast.makeText(getBaseContext(), "Peak Image Saved", Toast.LENGTH_SHORT).show();
            decodeAndSaveImage(editTextRefrence.getText().toString() + "P" + ".jpg", CAMERA_REQUEST1);
        }
        if (requestCode == CAMERA_REQUEST2 && resultCode == -1) {
            btnoffpeak.setBackgroundColor(Color.BLUE);
            Toast.makeText(getBaseContext(), "OFF Peak Image Saved", Toast.LENGTH_SHORT).show();
            decodeAndSaveImage(editTextRefrence.getText().toString() + "OP" + ".jpg", CAMERA_REQUEST2);
        }

        Cursor c1;
        String ref, meter;
        ref = editTextRefrence.getText().toString();
        meter = editTextMeter.getText().toString();
        imageView = (ImageView) findViewById(R.id.imageView);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDates = df.format(cal.getTime());
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                Msg = ref + " with " + meter + " number image saved";
                Msg1 = ref + " with " + meter + " number image saved on Date: " + formattedDates + "\n";
                btnCaputure.setBackgroundColor(Color.RED);
                Toast.makeText(getBaseContext(), Msg, Toast.LENGTH_SHORT).show();
                decodeAndSaveImage(editTextRefrence.getText().toString() + ".jpg", CAMERA_REQUEST);
            } catch (OutOfMemoryError e) {
            }
        }
//                /dISPLAY ON RUNTIME
        String imagename = ref + ".jpg";
        switch (requestCode) {
            case CAMERA_REQUEST1:
                imagename = ref + "P" + ".jpg";
                break;
            case CAMERA_REQUEST2:
                imagename = ref + "OP" + ".jpg";
                break;
            case CAMERA_REQUEST:
                imagename = ref + ".jpg";
                break;


        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            OutputStream fos = null;
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imagename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + "MeterReadingApp");
            //Uri imageUri2 = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
          /*
          overide File
           */
            imageUri = overrideFileIfFOund(imagename, imageUri, false);


            Log.i("path", String.valueOf(imageUri.getPath()));


            try {
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                Bitmap bb;
                File imageFile = new File(currentPhotoPath);
                bb = BitmapFactory.decodeFile(imageFile.toString());
                bb.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.i("eror",e.getMessage() + " error last");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("eror",e.getMessage() + " error last");
                e.printStackTrace();
            }


        } else {
            String dir = Environment.getExternalStorageDirectory() + File.separator + "MeterReadingApp" + File.separator + "DecodedImages";
            File folder = new File(dir);
            File folderpath = new File(folder + File.separator + imagename);
            imageView = (ImageView) findViewById(R.id.imageView);
            if (folderpath.exists()) {
                String folderpath1 = folderpath.getAbsolutePath().toString().trim();
                imageView.setImageBitmap(BitmapFactory.decodeFile(folderpath1));
            }
        }

    }

    private Uri overrideFileIfFOund(String imagename, Uri imageUri, Boolean isDecoded) {
        Uri contentUri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?";
        String[] selectionArgs;
        if (isDecoded)
            selectionArgs = new String[]{Environment.DIRECTORY_PICTURES + "/MeterReadingApp" + "/DecodedImages/"};
        else
            selectionArgs = new String[]{Environment.DIRECTORY_PICTURES + "/MeterReadingApp/"};    //must include "/" in front and end
        Cursor cursor = getContentResolver().query(contentUri, null, selection, selectionArgs, null);

        if (cursor.getCount() == 0) {
            //  Toast.makeText(view.getContext(), "No file found in \"" + Environment.DIRECTORY_DOCUMENTS + "/Kamen Rider Decade/\"", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                String fileName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
                if (fileName.equals(imagename)) {                          //must include extension
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                    imageUri = ContentUris.withAppendedId(contentUri, id);
                    break;
                }
            }
        }
        return imageUri;
    }

    private void decodeAndSaveImage(String typeOfSaveImage, Integer typeOfRequestCode) {
        if (typeOfRequestCode == CAMERA_REQUEST) {
            btnCaputure.setBackgroundColor(Color.RED);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File imageFile;
                Bitmap out;
                Bitmap bb;
                imageFile = new File(currentPhotoPath);
                bb = BitmapFactory.decodeFile(imageFile.toString());
                out = Bitmap.createScaledBitmap(bb, 300, 150, false);

                try {
                    //  saveImage(rotateImageIfRequired(out, Uri.fromFile(imageFile)), typeOfSaveImage);
                    saveImage(out, typeOfSaveImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File decodeimage = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp/DecodedImages");
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeterReadingApp" + "/" + typeOfSaveImage;
                Bitmap bb = BitmapFactory.decodeFile(path);
                Bitmap out = Bitmap.createScaledBitmap(bb, 300, 150, false);
                File file = new File(decodeimage, typeOfSaveImage);

                FileOutputStream fOut;
                FileOutputStream fos = null;
                //////////////// TO Save Picture Record in Internal Memory
                try {
                    fos = openFileOutput("picstakerecord.txt", MODE_APPEND);
                    fos.write(Msg1.getBytes());
                } catch (FileNotFoundException ie) {
                    ie.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ///////////////// TO Compress Bitmap to Set Quality
                try {
                    fOut = new FileOutputStream(file);
                    rotateImageIfRequired(out, Uri.fromFile(file)).compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    //out.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    fOut.flush();
                    fOut.close();
                    bb.recycle();
                    out.recycle();
                } catch (Exception e) {
                } catch (OutOfMemoryError e) {

                }
            }

        } else {
            File imageFile;
            File decodeimage;
            Bitmap out1;
            Bitmap bb1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageFile = new File(currentPhotoPath);
                bb1 = BitmapFactory.decodeFile(imageFile.toString());
                out1 = Bitmap.createScaledBitmap(bb1, 150, 150, false);
                try {
                    saveImage(out1, typeOfSaveImage);
                } catch (IOException e) {
                    Log.i("eror", e.getMessage() + " error Decode and save Image");
                    e.printStackTrace();
                }

            } else {
                decodeimage = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp/DecodedImages");
                decodeimage.mkdirs();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeterReadingApp" + "/" + typeOfSaveImage;
                bb1 = BitmapFactory.decodeFile(path);
                out1 = Bitmap.createScaledBitmap(bb1, 150, 150, false);
                File file = new File(decodeimage, typeOfSaveImage);

                FileOutputStream fOut1;
                FileOutputStream fos1 = null;
                ///////////////// TO Compress Bitmap to Set Quality
                try {
                    fOut1 = new FileOutputStream(file, false);
                    rotateImageIfRequired(out1, Uri.fromFile(file)).compress(Bitmap.CompressFormat.JPEG, 80, fOut1);
                    //out1.compress(Bitmap.CompressFormat.JPEG, 80, fOut1);
                    fOut1.flush();
                    fOut1.close();
                    bb1.recycle();
                    out1.recycle();
                } catch (Exception e) {
                    Log.e("error Image", e.getMessage());

                }
            }
        }
    }

    private void saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        OutputStream fos = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + "MeterReadingApp" + File.separator + "DecodedImages");
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            /**
             * override
             */
            imageUri = overrideFileIfFOund(name, imageUri, true);

            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void showRecords() {
        String ID = c.getString(0);
        String ref = c.getString(1);
        String ps = c.getString(0)+"/"+c.getString(2) + "/" + c.getString(3);
        String meter = c.getString(4);
        String previous = c.getString(5);
        String present = c.getString(6);
        String cameratake = c.getString(8);
        String status = c.getString(7);
        String advance = c.getString(10);

        String peak = c.getString(11);
        String offpeak = c.getString(12);
        String tariff = c.getString(13);

        String previouspeak = c.getString(14);
        String previousoffpeak = c.getString(15);

        if (tariff.equals("E-1-i") | tariff.equals("A-1") | tariff.equals("A-1b(03T)")) {
//            text=tariff;
            btnpeak.setEnabled(true);
            btnoffpeak.setEnabled(true);
            editpeak.setEnabled(true);
            editoffpeak.setEnabled(true);

            editTextPresent.setHint("Total Reading");
            editpeak.setHint("New Peak");
            editoffpeak.setHint("New Off Peak");

            btnpeak.setText("PEAK PICS");
            btnoffpeak.setText("OFFPEAK PICS");
            btnCaputure.setText("FULL PICTURE");
        } else if (tariff.equals("A-1b(03)T")) {
            btnpeak.setEnabled(true);
            btnoffpeak.setEnabled(true);
            editpeak.setEnabled(true);
            editoffpeak.setEnabled(true);

            btnpeak.setText("Peak Import");
            btnoffpeak.setText("OffPeak Import");
            btnCaputure.setText("Solar Export");

            editTextPresent.setHint("Solar Export");
            editpeak.setHint("Peak Import");
            editoffpeak.setHint("OffPeak Import");
        } else {
            btnpeak.setEnabled(false);
            btnoffpeak.setEnabled(false);
            editpeak.setEnabled(false);
            editoffpeak.setEnabled(false);

            editTextPresent.setHint("Total Reading");
            editpeak.setHint("New Peak");
            editoffpeak.setHint("New Off Peak");

            btnpeak.setText("PEAK PICS");
            btnoffpeak.setText("OFFPEAK PICS");
            btnCaputure.setText("FULL PICTURE");
        }
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        if (status.equals("PD")) {
            radioGroup1.check(R.id.radio1);
        } else if (status.equals("DF")) {
            radioGroup1.check(R.id.radio2);
        } else if (status.equals("LK")) {
            radioGroup1.check(R.id.radio3);
        } else if (status.equals("RP")) {
            radioGroup1.check(R.id.radio4);
        } else {
            radioGroup1.check(R.id.radio0);
        }
        editID.setText(ID);
        editTextRefrence.setText(ref);
        editTextPlotStreet.setText(ps);
        editTextMeter.setText(meter);
        editTextMeter.setText(meter);
        editTextPrevious.setText(previous);
        editTextPresent.setText(present);
        editTextAdvance.setText(advance);

        editpreviouspeak.setText(previouspeak);
        editpreviousoffpeak.setText(previousoffpeak);

        edittariff.setText(tariff);

        editpeak.setText(peak);
        editoffpeak.setText(offpeak);


        String imagename = ref + ".jpg";
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dir = Environment.getStorageDirectory() + File.separator + "MeterReadingApp" + File.separator + "DecodedImages";
        } else {
            dir = Environment.getExternalStorageDirectory() + File.separator + "MeterReadingApp" + File.separator + "DecodedImages";
        }
        File folder = new File(dir);
        File folderpath = new File(folder + File.separator + imagename);
        imageView = (ImageView) findViewById(R.id.imageView);
        if (folderpath.exists()) {
            String folderpath1 = folderpath.getAbsolutePath().toString().trim();
            imageView.setImageBitmap(BitmapFactory.decodeFile(folderpath1));
        } else {
            imageView.setImageResource(android.R.color.transparent);
        }
    }

    public void moveNext() {
        btnCaputure.setBackgroundResource(android.R.drawable.btn_default);
        btnpeak.setBackgroundResource(android.R.drawable.btn_default);
        btnoffpeak.setBackgroundResource(android.R.drawable.btn_default);
        try {
            c.moveToNext();
            showRecords();
            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
            String status = c.getString(7);
            int cameratake = c.getInt(c.getColumnIndex("CameraTake"));
            if (cameratake != 1) {
                cameratake = 0;
                Button b = (Button) findViewById(R.id.btnCapture);
                b.setText("Meter Picture");
            } else {
                Button b = (Button) findViewById(R.id.btnCapture);
                b.setText("Pics Already Taken ! Take Again");
            }
            if (status.equals("PD")) {
                radioGroup1.check(R.id.radio1);
            } else if (status.equals("DF")) {
                radioGroup1.check(R.id.radio2);
            } else if (status.equals("LK")) {
                radioGroup1.check(R.id.radio3);
            } else if (status.equals("RP")) {
                radioGroup1.check(R.id.radio4);
            } else {
                radioGroup1.check(R.id.radio0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayExceptionMessagenext(e.getMessage());
        }
    }

    public void movePrev() {
        btnCaputure.setBackgroundResource(android.R.drawable.btn_default);
        btnpeak.setBackgroundResource(android.R.drawable.btn_default);
        btnoffpeak.setBackgroundResource(android.R.drawable.btn_default);
        try {
            if (!c.isLast()) {
                c.moveToPrevious();
                showRecords();
                int cameratake = c.getInt(c.getColumnIndex("CameraTake"));
                if (cameratake != 1) {
                    cameratake = 0;
                    Button b = (Button) findViewById(R.id.btnCapture);
                    b.setText("Meter Picture");
                } else {
                    Button b = (Button) findViewById(R.id.btnCapture);
                    b.setText("Pics Already Taken ! Take Again");
                }
                RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
                String status = c.getString(7);
                if (status.equals("PD")) {
                    radioGroup1.check(R.id.radio1);
                } else if (status.equals("DF")) {
                    radioGroup1.check(R.id.radio2);
                } else if (status.equals("LK")) {
                    radioGroup1.check(R.id.radio3);
                } else if (status.equals("RP")) {
                    radioGroup1.check(R.id.radio4);
                } else {
                    radioGroup1.check(R.id.radio0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayExceptionMessageprev(e.getMessage());
        }
    }

    public void displayExceptionMessageprev(String msg) {
    }

    public void displayExceptionMessagenext(String msg) {
    }

    @Override
    public void onClick(View v) {
        if (v == btnNext) {
            moveNext();
        }
        if (v == btnPrev) {
            movePrev();
        }
        if (v == btnSave) {
            saveRecord();
        }
        if (v == btnpeak) {
            PicsTake1();
        }
        if (v == btnoffpeak) {
            PicsTake2();
        }
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

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ei = new ExifInterface(Objects.requireNonNull(selectedImage.getPath()));
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}
