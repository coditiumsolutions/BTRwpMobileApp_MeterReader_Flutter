package com.bahriatownpowerdivision.ehsan.mtp;

import android.annotation.SuppressLint;
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
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Exhan on 11/30/2015.
 */
public class byMeter extends Activity {
    private static final String TAG = "MTP";
    public EditText editTextRefrence, editTextPlotStreet, editTextMeter;
    public EditText editTextPrevious, editTextPresent, editpeak, edittariff, editoffpeak, editcameratake, editpreviouspeak, editpreviousoffpeak;
    public TextView previousload;
    public EditText editcurrentload;
    public EditText editTextAdvance, editID;
    public Button btnPrev, btnpeak, btnoffpeak, btnNext, btnCapture, btnSave, btnloadpics;
    public long rowid;
    public ImageView imageView;
    public static final String DB_NAME = "mrs.sqlite";
    public static final String TABLE_NAME = "Sheet";
    public SQLiteDatabase database;
    public static final String SELECT_SQL = "SELECT * FROM Sheet";
    public Cursor c;
    public static final int CAMERA_REQUEST = 1888, CAMERA_REQUEST1 = 1889, CAMERA_REQUEST2 = 1890, CAMERA_REQUEST_LOAD = 1891;
    String owner = "", formattedDate, shortname = "", Msg;
    String currentPhotoPath;
    Bitmap imageReturned;
    Uri imageUri;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.bymeternum);
        editTextRefrence = (EditText) findViewById(R.id.editref);
        editTextPlotStreet = (EditText) findViewById(R.id.editPS);
        editTextMeter = (EditText) findViewById(R.id.editmeter);
        editTextPrevious = (EditText) findViewById(R.id.editprevious);
        editTextPresent = (EditText) findViewById(R.id.editpresent);
        editTextAdvance = (EditText) findViewById(R.id.editadvance);
        editcameratake = (EditText) findViewById(R.id.editcameratake);
        edittariff = (EditText) findViewById(R.id.edittariff);
        imageView = (ImageView) findViewById(R.id.imageView);

        editpreviouspeak = (EditText) findViewById(R.id.previouspeak);
        editpreviousoffpeak = (EditText) findViewById(R.id.previousoffpeak);
        previousload = (TextView) findViewById(R.id.previousload);
        editcurrentload = (EditText) findViewById(R.id.editcurrentload);

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSave = (Button) findViewById(R.id.btnSave);
        editID = (EditText) findViewById(R.id.editID);

        editpeak = (EditText) findViewById(R.id.editpeak);
        editoffpeak = (EditText) findViewById(R.id.editoffpeak);
        btnpeak = (Button) findViewById(R.id.btnpeak);
        btnoffpeak = (Button) findViewById(R.id.btnoffpeak);
        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnloadpics = (Button) findViewById(R.id.btnloadpics);

    }

    public void SearchContant(View view) {
        String catenate;
        EditText beforeN = (EditText) findViewById(R.id.beforeN);
        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        catenate = beforeN.getText().toString();
        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Meter like '" + catenate + "'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    rowid = c.getLong(0);
                    Button btnsearch = (Button) findViewById(R.id.searchbyref);
                    btnsearch.setVisibility(View.GONE);
                    beforeN.setVisibility(View.GONE);
                    String ID = c.getString(c.getColumnIndex("ID"));
                    String Refrence = c.getString(c.getColumnIndex("Refrence"));
                    String Plot = c.getString(c.getColumnIndex("Plot"));
                    String Street = c.getString(c.getColumnIndex("Street"));
                    String Meter = c.getString(c.getColumnIndex("Meter"));
                    String Previous = c.getString(c.getColumnIndex("Previous"));
                    String Present = c.getString(c.getColumnIndex("Present"));
                    String Advance = c.getString(c.getColumnIndex("advance"));

                    String Peak = c.getString(c.getColumnIndex("Peak"));
                    String OffPeak = c.getString(c.getColumnIndex("OffPeak"));

                    String tariff = c.getString(c.getColumnIndex("Tariff"));

                    String previouspeak = c.getString(c.getColumnIndex("previouspeak"));
                    String previousoffpeak = c.getString(c.getColumnIndex("previousoffpeak"));

                    int previousLoadIdx = c.getColumnIndex("PreviousLoad");
                    if (previousLoadIdx < 0) previousLoadIdx = c.getColumnIndex("previousload");
                    String previousloadValue = previousLoadIdx >= 0 ? c.getString(previousLoadIdx) : previousoffpeak;

                    int currentLoadIdx = c.getColumnIndex("CurrentLoad");
                    if (currentLoadIdx < 0) currentLoadIdx = c.getColumnIndex("currentload");
                    String currentloadValue = currentLoadIdx >= 0 ? c.getString(currentLoadIdx) : OffPeak;

                    EditText editTextID = (EditText) findViewById(R.id.editID);
                    editTextID.setText(ID);
                    TextView txtref = (TextView) findViewById(R.id.refrence);
                    txtref.setVisibility(View.VISIBLE);
                    EditText editTextRefrence = (EditText) findViewById(R.id.editref);
                    editTextRefrence.setVisibility(View.VISIBLE);
                    editTextRefrence.setText(Refrence);
                    TextView txtPS = (TextView) findViewById(R.id.PS);
                    txtPS.setVisibility(View.VISIBLE);
                    EditText editTextPlotStreet = (EditText) findViewById(R.id.editPS);
                    editTextPlotStreet.setVisibility(View.VISIBLE);
                    editTextPlotStreet.setText(Plot + "/" + Street);
                    TextView txtmeter = (TextView) findViewById(R.id.meternum);
                    txtmeter.setVisibility(View.VISIBLE);
                    EditText editTextMeter = (EditText) findViewById(R.id.editmeter);
                    editTextMeter.setVisibility(View.VISIBLE);
                    editTextMeter.setText(Meter);
                    TextView txtprevious = (TextView) findViewById(R.id.previous);
                    txtprevious.setVisibility(View.VISIBLE);
                    EditText editTextPrevious = (EditText) findViewById(R.id.editprevious);
                    editTextPrevious.setVisibility(View.VISIBLE);
                    editTextPrevious.setText(Previous);
                    TextView txtpresent = (TextView) findViewById(R.id.present);
                    txtpresent.setVisibility(View.VISIBLE);
                    EditText editTextPresent = (EditText) findViewById(R.id.editpresent);
                    editTextPresent.setVisibility(View.VISIBLE);
                    editTextPresent.setText(Present);
                    editTextAdvance.setVisibility(View.VISIBLE);
                    editTextAdvance.setText(Advance);


                    editpeak.setVisibility(View.VISIBLE);
                    editpeak.setText(Peak);
                    editoffpeak.setVisibility(View.VISIBLE);
                    editoffpeak.setText(OffPeak);

                    edittariff.setVisibility(View.VISIBLE);
                    edittariff.setText(tariff);

                    editpreviouspeak.setVisibility(View.VISIBLE);
                    editpreviouspeak.setText(previouspeak);
                    editpreviousoffpeak.setVisibility(View.VISIBLE);
                    editpreviousoffpeak.setText(previousoffpeak);

                    previousload.setVisibility(View.VISIBLE);
                    previousload.setText(previousloadValue);

                    editcurrentload.setVisibility(View.VISIBLE);
                    editcurrentload.setText(currentloadValue);

                    btnpeak.setVisibility(View.VISIBLE);
                    btnoffpeak.setVisibility(View.VISIBLE);
                    btnloadpics.setVisibility(View.VISIBLE);

                    imageView.setVisibility(View.VISIBLE);

                    editcameratake.setVisibility(View.GONE);
                    Button btnPre = (Button) findViewById(R.id.btnPrev);
                    btnPre.setVisibility(View.VISIBLE);
                    Button btnNexts = (Button) findViewById(R.id.btnNexts);
                    btnNexts.setVisibility(View.VISIBLE);
                    Button btnSaves = (Button) findViewById(R.id.btnSave);
                    btnSaves.setVisibility(View.VISIBLE);
                    Button btncapture = (Button) findViewById(R.id.btnCapture);
                    btncapture.setVisibility(View.VISIBLE);
                    RadioGroup radiogroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
                    String status = c.getString(7);
                    RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
                    RadioGroup rdgrp = (RadioGroup) findViewById(R.id.radioGroup1);
                    String radiovalue = ((RadioButton) this.findViewById(rdgrp.getCheckedRadioButtonId())).getText().toString();
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
                    radiogroup1.setVisibility(View.VISIBLE);
                    editTextPresent.requestFocus();
                }
                while (c.moveToNext());
            } else {
                Msg = "Refrence Enter for Search are Wrong :" + catenate;
                showSnackbar();
                beforeN.setText("");
                beforeN.requestFocus();
            }
        }
    }

    /// pICS Take
    public void PicsTakes(View v) {

        /*
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, editTextRefrence.getText().toString() + ".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

         */

        createNewIntent(CAMERA_REQUEST, editTextRefrence.getText().toString() + ".jpg");

    }

    public void PicsTake1(View v) {

        /*
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, editTextRefrence.getText().toString() + "P" + ".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST1);

         */
        createNewIntent(CAMERA_REQUEST1, editTextRefrence.getText().toString() + "P" + ".jpg");

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

    public void PicsTake2(View v) {

        /*
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, editTextRefrence.getText().toString() + "OP" + ".jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST2);

         */
        createNewIntent(CAMERA_REQUEST2, editTextRefrence.getText().toString() + "OP" + ".jpg");

    }

    public void PicsTakeLoad(View v) {
        // Creates the image file named with suffix "L" (Load pics).
        createNewIntent(CAMERA_REQUEST_LOAD, editTextRefrence.getText().toString() + "L" + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST1 && resultCode == -1) {
            btnpeak.setBackgroundColor(Color.GREEN);
            imageReturned = BitmapFactory.decodeFile(currentPhotoPath);

            Toast.makeText(getBaseContext(), "Peak Image Saved", Toast.LENGTH_SHORT).show();
            decodeAndSaveImage(editTextRefrence.getText().toString() + "P" + ".jpg", CAMERA_REQUEST1);

            /*
            File decodeimage = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp/DecodedImages");
            decodeimage.mkdirs();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeterReadingApp" + "/" + editTextRefrence.getText().toString() + "P" + ".jpg";
            Bitmap bb1 = BitmapFactory.decodeFile(path);
            Bitmap out1 = Bitmap.createScaledBitmap(bb1, 150, 150, false);
            File file = new File(decodeimage, editTextRefrence.getText().toString() + "P" + ".jpg");

            FileOutputStream fOut1;
            FileOutputStream fos1 = null;
            ///////////////// TO Compress Bitmap to Set Quality
            try {
                fOut1 = new FileOutputStream(file);
                out1.compress(Bitmap.CompressFormat.JPEG, 80, fOut1);
                fOut1.flush();
                fOut1.close();
                bb1.recycle();
                out1.recycle();
            } catch (Exception e) {
            }

             */
        }
        if (requestCode == CAMERA_REQUEST2 && resultCode == -1) {
            btnoffpeak.setBackgroundColor(Color.BLUE);
            imageReturned = BitmapFactory.decodeFile(currentPhotoPath);

            Toast.makeText(getBaseContext(), "OFF Peak Image Saved", Toast.LENGTH_SHORT).show();
            decodeAndSaveImage(editTextRefrence.getText().toString() + "OP" + ".jpg", CAMERA_REQUEST2);
        }

        if (requestCode == CAMERA_REQUEST_LOAD && resultCode == -1) {
            btnloadpics.setBackgroundColor(Color.CYAN);
            imageReturned = BitmapFactory.decodeFile(currentPhotoPath);
            Toast.makeText(getBaseContext(), "Load Image Saved", Toast.LENGTH_SHORT).show();
            decodeAndSaveImage(editTextRefrence.getText().toString() + "L" + ".jpg", CAMERA_REQUEST_LOAD);
        }

        Cursor c1;
        String sql2;
        String ref, meter;
        ref = editTextRefrence.getText().toString();
        meter = editTextMeter.getText().toString();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDates = df.format(cal.getTime());
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {

                Msg = ref + " with " + meter + " number image saved";
                btnCapture.setBackgroundColor(Color.RED);
                imageReturned = BitmapFactory.decodeFile(currentPhotoPath);

                String Msg1 = ref + " with " + meter + " number image saved on Date: " + formattedDates + "\n";
                Toast.makeText(getBaseContext(), Msg, Toast.LENGTH_SHORT).show();
                decodeAndSaveImage(editTextRefrence.getText().toString() + ".jpg", CAMERA_REQUEST);
            } catch (OutOfMemoryError e) {
            }
        }
//               DISPLAY ON RUNTIME
        String imagename = ref + ".jpg";
        switch (requestCode) {
            case CAMERA_REQUEST1:
                imagename = ref + "P" + ".jpg";
                break;
            case CAMERA_REQUEST2:
                imagename = ref + "OP" + ".jpg";
                break;
            case CAMERA_REQUEST_LOAD:
                imagename = ref + "L" + ".jpg";
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
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            imageUri = overrideFileIfFOund(imagename, imageUri, false);
            Log.i("path", String.valueOf(imageUri.getPath()));


            try {
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                if (imageReturned != null) {
                    imageReturned.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                }
                fos.close();

                /**
                 * delete Temp file
                 */
                deleteTempFile();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
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

    public void moveNexts(View view) {
        btnCapture.setBackgroundResource(android.R.drawable.btn_default);
        btnpeak.setBackgroundResource(android.R.drawable.btn_default);
        btnoffpeak.setBackgroundResource(android.R.drawable.btn_default);
        btnloadpics.setBackgroundResource(android.R.drawable.btn_default);
        try {
            rowid = rowid + 1;
            Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + String.valueOf(rowid) + ";", null);
            c.moveToNext();
            showRecords(c);
//            ////// Set Radio Button Checked
            String status = c.getString(7);
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
        } catch (Exception e) {
            e.printStackTrace();
            displayExceptionMessagenext(e.getMessage());
        }
    }

    public void displayExceptionMessageprev(String msg) {
    }

    public void displayExceptionMessagenext(String msg) {
    }

    public void showRecords(Cursor c) {
        String ID = c.getString(0);
        String ref = c.getString(1);
        String ps = c.getString(0) + "/" + c.getString(2) + "/" + c.getString(3);
        String meter = c.getString(4);
        String previous = c.getString(5);
        String present = c.getString(6);
        String advance = c.getString(10);
        String cameratake = c.getString(8);

        String peak = c.getString(11);
        String offpeak = c.getString(12);
        String tariff = c.getString(13);

        String previouspeak = c.getString(14);
        String previousoffpeak = c.getString(15);

        if (tariff.equals("E-1-i") | tariff.equals("A-1") | tariff.equals("A-1b(03T)")) {
            btnpeak.setEnabled(true);
            btnoffpeak.setEnabled(true);
            btnloadpics.setEnabled(true);
            editpeak.setEnabled(true);
            editoffpeak.setEnabled(true);
            editcurrentload.setEnabled(true);

            editTextPresent.setHint("Total Reading");
            editpeak.setHint("New Peak");
            editoffpeak.setHint("New Off Peak");

            btnpeak.setText("PEAK PICS");
            btnoffpeak.setText("OFFPEAK PICS");
            btnCapture.setText("FULL PICTURE");

        }else if (tariff.equals("A-2-b") | tariff.equals("E-1-ii")) {
            btnpeak.setEnabled(true);
            btnoffpeak.setEnabled(true);
            btnloadpics.setEnabled(true);
            editpeak.setEnabled(true);
            editoffpeak.setEnabled(false);
            editcurrentload.setEnabled(true);

            editTextPresent.setHint("Total Reading");
            editpeak.setHint("New Load");
            editoffpeak.setHint("New Off Peak");

            btnpeak.setText("LOAD PICS");
            btnoffpeak.setText("OFFPEAK PICS");
            btnCapture.setText("FULL PICTURE");
        }
        else if (tariff.equals("A-1b(03)T")) {
            btnpeak.setEnabled(true);
            btnoffpeak.setEnabled(true);
            btnloadpics.setEnabled(true);
            editpeak.setEnabled(true);
            editoffpeak.setEnabled(true);
            editcurrentload.setEnabled(true);

            btnpeak.setText("Peak Import");
            btnoffpeak.setText("OffPeak Import");
            btnCapture.setText("Solar Export");

            editTextPresent.setHint("Solar Export");
            editpeak.setHint("Peak Import");
            editoffpeak.setHint("OffPeak Import");
        } else {
            btnpeak.setEnabled(false);
            btnoffpeak.setEnabled(false);
            btnloadpics.setEnabled(false);
            editpeak.setEnabled(false);
            editoffpeak.setEnabled(false);
            editcurrentload.setEnabled(false);

            editTextPresent.setHint("Total Reading");
            editpeak.setHint("New Peak");
            editoffpeak.setHint("New Off Peak");

            btnpeak.setText("PEAK PICS");
            btnoffpeak.setText("OFFPEAK PICS");
            btnCapture.setText("FULL PICTURE");
        }

        editID.setText(ID);
        editTextRefrence.setText(ref);
        editTextPlotStreet.setText(ps);
        editTextMeter.setText(meter);
        editTextMeter.setText(meter);
        editTextPrevious.setText(previous);
        editTextPresent.setText(present);
        editTextAdvance.setText(advance);
        edittariff.setText(tariff);

        editpeak.setText(peak);
        editoffpeak.setText(offpeak);

        editpreviouspeak.setText(previouspeak);
        editpreviousoffpeak.setText(previousoffpeak);

        int previousLoadIdx = c.getColumnIndex("PreviousLoad");
        if (previousLoadIdx < 0) previousLoadIdx = c.getColumnIndex("previousload");
        String previousloadValue = previousLoadIdx >= 0 ? c.getString(previousLoadIdx) : previousoffpeak;
        previousload.setVisibility(View.VISIBLE);
        previousload.setText(previousloadValue);

        int currentLoadIdx = c.getColumnIndex("CurrentLoad");
        if (currentLoadIdx < 0) currentLoadIdx = c.getColumnIndex("currentload");
        String currentloadValue = currentLoadIdx >= 0 ? c.getString(currentLoadIdx) : offpeak;
        editcurrentload.setVisibility(View.VISIBLE);
        editcurrentload.setText(currentloadValue);

        String imagename = ref + ".jpg";
        String dir = Environment.getExternalStorageDirectory() + File.separator + "MeterReadingApp" + File.separator + "DecodedImages";
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

    public void movePrevs(View view) {
        btnCapture.setBackgroundResource(android.R.drawable.btn_default);
        btnpeak.setBackgroundResource(android.R.drawable.btn_default);
        btnoffpeak.setBackgroundResource(android.R.drawable.btn_default);
        btnloadpics.setBackgroundResource(android.R.drawable.btn_default);
        try {
            rowid = rowid - 1;
            Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + String.valueOf(rowid) + ";", null);
            c.moveToNext();
            showRecords(c);
            String status = c.getString(7);
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
        } catch (Exception e) {
            e.printStackTrace();
            displayExceptionMessageprev(e.getMessage());
        }
    }

    public void saveRecords(View view) {
        try {
            String ID = editID.getText().toString().trim();
            int diff;
            String ref = editTextRefrence.getText().toString().trim();
            String ps = editTextPresent.getText().toString().trim();
            String pv = editTextPrevious.getText().toString().trim();

            String peaks = editpeak.getText().toString().trim();
            String offpeaks = editoffpeak.getText().toString().trim();

            String tariffdetail = edittariff.getText().toString().trim();

            String previousloadValue = previousload != null ? previousload.getText().toString().trim() : "";
            if (previousloadValue.equals("")) {
                previousloadValue = "0";
            }
            String currentloadValue = editcurrentload != null ? editcurrentload.getText().toString().trim() : "";
            if (currentloadValue.equals("")) {
                currentloadValue = "0";
            }

            int valuepeak = 0, valueoffpeak = 0, valuesumof = 0;
            try {
                valuepeak = Integer.valueOf(editpeak.getText().toString());
                valueoffpeak = Integer.valueOf(editoffpeak.getText().toString());
                valuesumof = valuepeak + valueoffpeak;
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            String space = "  ";
            Integer previous = Integer.parseInt(editTextPrevious.getText().toString().trim());
            Integer present = 0;
            RadioGroup rdgrp = (RadioGroup) findViewById(R.id.radioGroup1);
            String radiovalue = ((RadioButton) this.findViewById(rdgrp.getCheckedRadioButtonId())).getText().toString();
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String address = info.getMacAddress();
            Calendar e = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = df.format(e.getTime());
            String x = getMacAddr();

            String androidID = Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (androidID.equals("60ec98d17e2eb222")) { owner = "Mustfeez Ur Rehman";shortname = "MR"; }
            else if (androidID.equals("dc71ec99b985746e")) { owner = "Kashif Hanif";shortname = "KH"; }
            else if (androidID.equals("a9aff77ca635a181")) { owner = "Mansoor Liaqat";shortname = "ML";}
            else if (androidID.equals("11955c8e9759f409")) {owner = "Muhammad Musab Sultan";shortname = "MS"; }
            else if (androidID.equals("205cdc7cd0caab7e")) { owner = "Etsham Khaliq";shortname = "EK"; }
            else if (androidID.equals("6cfc812c76b3a38e")) { owner = "AHSAN UL HAQ";shortname = "EXH"; }
            else if (androidID.equals("855ddc19f2b72710")) { owner = "Muhammad Mubashar";shortname = "MM"; }
            else if (androidID.equals("b70dd092049c278c")) { owner = "Muhammad Yousaf";shortname = "MY"; }
            else if (androidID.equals("675ca3fbac75e8a3")) { owner = "Mian Imran Akhtar";shortname = "MIA"; }
            else if (androidID.equals("474a99348d463219")) { owner = "Mudassir Ameen";shortname = "MA"; }

//          Phase 8 Meter Reader
            else if (androidID.equals("af669d13929dd2ac")) { owner = "Seemab Ishtiaq";shortname = "SI"; }
            else if (androidID.equals("d95ba7ec583f1afe")) { owner = "Sajjad Hussain";shortname = "SH"; }
            else if (androidID.equals("4bf439a3813a8e8b")) { owner = "Hassan Fida";shortname = "HF"; }
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

            if (editTextPresent.getText().toString() == "" || editTextPresent.getText().toString().equals("")) {
                present = 0;
                diff = 0;
                String sql = "UPDATE Sheet SET Present='" + ps + "',advance='" + diff + "', PreviousLoad='" + previousloadValue + "', CurrentLoad='" + currentloadValue + "', Status='" + radiovalue + "',time='" + formattedDate + "' , readingby='" + shortname + "'  WHERE ID=" + rowid + ";";
                database.execSQL(sql);
                Msg = "First Enter Correct Reading and Then Press Save";
                showSnackbar();
                c = database.rawQuery(SELECT_SQL, null);
                editTextAdvance.setText(Integer.toString(0));
                c.moveToPosition((int) rowid);
            } else {
                present = Integer.parseInt(editTextPresent.getText().toString());
                RadioGroup group1 = (RadioGroup) findViewById(R.id.radioGroup1);
                int selected = group1.getCheckedRadioButtonId();
                RadioButton button1 = (RadioButton) findViewById(selected);
                if (previous > present) {
                    if ("RP".equals(button1.getText())) {
                        diff = 0;
                        String sql = "UPDATE Sheet SET Present='" + ps + "',advance='" + diff + "', peak='" + peaks + "', offpeak='" + offpeaks + "', PreviousLoad='" + previousloadValue + "', CurrentLoad='" + currentloadValue + "', Status='" + radiovalue + "',time='" + formattedDate + "' , readingby='" + shortname + "'  WHERE ID=" + rowid + ";";
                        database.execSQL(sql);
                        // Get current position of Cursor
                        //   int curdorPos = c.getPosition();
                        Msg = "Records Saved Successfully";
                        showSnackbar();
                        c = database.rawQuery(SELECT_SQL, null);
                        // And then move cursor to specific position
                        editTextAdvance.setText(Integer.toString(0));
                        c.moveToPosition((int) rowid);
                    } else {
                        editTextPresent.setText("");
                        Msg = "You cannot save less value other than replace";
                        showSnackbar();
                    }
                } else if (previous <= present) {
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
                        Integer valueprev = Integer.valueOf(editTextPrevious.getText().toString());
                        Integer valuepres = Integer.valueOf(editTextPresent.getText().toString());
                        diff = Integer.parseInt(String.valueOf(valuepres)) - Integer.parseInt(String.valueOf(valueprev));
                        String sql = "UPDATE Sheet SET Present='" + ps + "',advance='" + diff + "', peak='" + peaks + "', offpeak='" + offpeaks + "', PreviousLoad='" + previousloadValue + "', CurrentLoad='" + currentloadValue + "', Status='" + radiovalue + "',time='" + formattedDate + "' , readingby='" + shortname + "'  WHERE ID=" + rowid + ";";
                        database.execSQL(sql);
                        // Get current position of Cursor
                        //   int curdorPos = c.getPosition();
                        Msg = "Records Saved Successfully";
                        showSnackbar();
                        c = database.rawQuery(SELECT_SQL, null);
                        // And then move cursor to specific position
                        editTextAdvance.setText(Integer.toString(diff));
                        c.moveToPosition((int) rowid);
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

            dispatchTakePictureIntent(requestId);


//
//            String imageFileName = fileName;
//            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//            File newImage = null;
//            try {
//                newImage = File.createTempFile(
//                        imageFileName,  /* prefix */
//                        ".jpg",         /* suffix */
//                        storageDir      /* directory */
//                );
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            /**
//             * getting the TempFileCreated by currentPhoto Path :
//             */
//            currentPhotoPath = newImage.getAbsolutePath();
//
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            File photoFile = newImage;
//            if (photoFile != null) {
//                try {
//                    /**
//                     * The nwew way for api 29+ , is Using FileProvider and attaching it in manifest
//                     */
//                    Uri photoURI = FileProvider.getUriForFile(this,
//                            "com.bahriatownpowerdivision.ehsan.mtp",
//                            photoFile);
//                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                    startActivityForResult(takePictureIntent, requestId);
//                } catch (Exception e) {
//                    Toast.makeText(this, "Check Your SD Card Please", Toast.LENGTH_SHORT).show();
//                }
//            }
        }


    }


    private void decodeAndSaveImage(String typeOfSaveImage, Integer typeOfRequestCode) {
        if (typeOfRequestCode == CAMERA_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                File imageFile;
//                Bitmap out;
//                Bitmap bb;
//                imageFile = new File(currentPhotoPath);
//                bb = BitmapFactory.decodeFile(imageFile.toString());
//                out = Bitmap.createScaledBitmap(bb, 300, 150, false);
                try {
                    saveImage(imageReturned, typeOfSaveImage, true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File decodeimage = new File(Environment.getExternalStorageDirectory(), "MeterReadingApp/DecodedImages");
                decodeimage.mkdirs();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MeterReadingApp" + "/" + typeOfSaveImage;
                Bitmap bb = BitmapFactory.decodeFile(path);
                Bitmap out = Bitmap.createScaledBitmap(bb, 300, 150, false);
                File file = new File(decodeimage, typeOfSaveImage);

                FileOutputStream fOut;
                FileOutputStream fos = null;
                //////////////// TO Save Picture Record in Internal Memory
                try {
                    fos = openFileOutput("picstakerecord.txt", MODE_APPEND);
                    fos.write(Msg.getBytes());
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
                    out.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
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
//                imageFile = new File(currentPhotoPath);
////                decodeimage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MeterReadingApp/DecodedImages");
//                bb1 = BitmapFactory.decodeFile(imageFile.toString());
//                out1 = Bitmap.createScaledBitmap(bb1, 150, 150, false);
                try {
                    saveImage(imageReturned, typeOfSaveImage, false);
                } catch (IOException e) {
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
                    out1.compress(Bitmap.CompressFormat.JPEG, 80, fOut1);
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

    private void saveImage(Bitmap bitmap, @NonNull String name, boolean fullPictureScale) throws IOException {
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
            Bitmap bb = BitmapFactory.decodeFile(currentPhotoPath);
            Bitmap scaledBitmap;
            if (fullPictureScale) scaledBitmap = Bitmap.createScaledBitmap(bb, 300, 150, false);
            else scaledBitmap = Bitmap.createScaledBitmap(bb, 150, 150, false);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.close();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "test",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.i("current", currentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent(Integer requestId) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(this,
                        "com.bahriatownpowerdivision.ehsan.mtp",
                        photoFile);
                Log.i("current", imageUri.toString());

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, requestId);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        if (currentPhotoPath != null)
            savedInstanceState.putString("camera_image", currentPhotoPath);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("camera_image")) {
                currentPhotoPath = savedInstanceState.getString("camera_image");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void deleteTempFile() {
        if (currentPhotoPath != null) {
            File file = new File(currentPhotoPath);
            if (file.exists()) {
                if (file.delete()) {
                  //  Toast.makeText(this, "file delete : " + currentPhotoPath, Toast.LENGTH_SHORT).show();
                } else {
                 //   Toast.makeText(this, "file not deleted : " + currentPhotoPath, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }


}