package io.qxtno.carols.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import io.qxtno.carols.model.Carol;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "carols.db";
    private final Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    private SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                SQLiteDatabase checkDB = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                if (checkDB != null) {
                    checkDB.close();
                }
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (inputStream.read(buffer) > 0) {
            outputStream.write(buffer);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public ArrayList<Carol> getCarols() {
        ArrayList<Carol> carolList = new ArrayList<>();
        SQLiteDatabase db = openDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM " + "Carols", null);

        if (!(cursor.getCount() == 0)) {
            while (cursor.moveToNext()) {
                carolList.add(new Carol(cursor.getString(1), cursor.getString(2)));
            }
        }
        db.close();

        return carolList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
