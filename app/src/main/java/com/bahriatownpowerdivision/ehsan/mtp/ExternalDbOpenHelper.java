package com.bahriatownpowerdivision.ehsan.mtp;

/**
 * Created by EHSAN on 12/8/2015.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExternalDbOpenHelper extends SQLiteOpenHelper {

    //���� � ����� � ������ �� ����������
    public static String DB_PATH;
    //��� ����� � �����
    public static String DB_NAME;
    public SQLiteDatabase database;
    public final Context context;

    public SQLiteDatabase getDb() {
        return database;
    }

    public ExternalDbOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, 2);
        this.context = context;
        //�������� ������ ���� � ����� ��� ������ ����������
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
        DB_NAME = databaseName;
        openDataBase();
    }

    //������� ����, ���� ��� �� �������
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        this.getReadableDatabase();
        this.close();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }
    //�������� ������������� ���� ������
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //������� �� ����� ������ ��������, ��� ������ �����������
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }
    //����� ����������� ����
    private void copyDataBase() throws IOException {
        // ��������� ����� ��� ������ �� ��� ��������� ���� ��
        //�������� � assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

        // ���� � ��� ��������� ������ ���� � ��������
        String outFileName = DB_PATH + DB_NAME;

        // ������ �������� ����� ��� ������ � ��� �� ��������
        OutputStream localDbStream = new FileOutputStream(outFileName);

        // ���������� �����������
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        // �� ����� �������� ����������(���������) � ������� ������
        localDbStream.close();
        externalDbStream.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        ensureSheetColumns(database);
        return database;
    }

    private void ensureSheetColumns(SQLiteDatabase db) {
        try {
            if (!columnExists(db, "Sheet", "PreviousLoad")) {
                db.execSQL("ALTER TABLE Sheet ADD COLUMN PreviousLoad INTEGER");
            }
            if (!columnExists(db, "Sheet", "CurrentLoad")) {
                db.execSQL("ALTER TABLE Sheet ADD COLUMN CurrentLoad INTEGER");
            }

            // Backfill for existing rows (best-effort; columns must exist)
            db.execSQL("UPDATE Sheet SET PreviousLoad = previousoffpeak WHERE PreviousLoad IS NULL");
            db.execSQL("UPDATE Sheet SET CurrentLoad = offpeak WHERE CurrentLoad IS NULL");
        } catch (Exception e) {
            Log.e("ExternalDbOpenHelper", "ensureSheetColumns failed", e);
        }
    }

    private boolean columnExists(SQLiteDatabase db, String tableName, String columnName) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
            if (cursor == null) return false;
            int nameIndex = cursor.getColumnIndex("name");
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                if (name != null && name.equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("ExternalDbOpenHelper", "columnExists failed: " + columnName, e);
        } finally {
            if (cursor != null) cursor.close();
        }
        return false;
    }
    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion) {
            try {
                this.copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
