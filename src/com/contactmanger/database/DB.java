package com.contactmanger.database;

import com.contactmanger.R;
import com.contactmanger.R.drawable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {
  
  private static final String DB_NAME = "mydb";
  private static final int DB_VERSION = 1;
  private static final String DB_TABLE = "mytab";
  
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_IMG = "imgPath";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_BOOLEAN = "isfavorite";
  private static final String TAG = "myLogs";
  
  private static final String DB_CREATE = 
    "create table " + DB_TABLE + "(" +
      COLUMN_ID + " integer primary key autoincrement, " +
      COLUMN_IMG + " text, " +
      COLUMN_DESCRIPTION + " text, " +
      COLUMN_BOOLEAN + " boolean" +
    ");";
  
  private final Context mCtx;
  
  
  private DBHelper mDBHelper;
  
  private SQLiteDatabase mDB;
  
  public DB(Context ctx) {
    mCtx = ctx;
  }
  
  public void open() {
    mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
    mDB = mDBHelper.getWritableDatabase();
  }
  
  public void close() {
    if (mDBHelper!=null) mDBHelper.close();
  }
  
  public Cursor getAllData() {
    return mDB.query(DB_TABLE, null, null, null, null, null, null);
  }
  
  public void addRec(String description, String imgPath, boolean isFavorite) {
    ContentValues cv = new ContentValues();
    cv.put(COLUMN_DESCRIPTION, description);
    cv.put(COLUMN_IMG, imgPath);
    cv.put(COLUMN_BOOLEAN, isFavorite);
    mDB.insert(DB_TABLE, null, cv);
  }
  
  public void delRec(long id) {
    mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
  }
  
  private class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, CursorFactory factory,
        int version) {
      super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(DB_CREATE);
      
      
      /*ContentValues cv = new ContentValues();
      for (int i = 1; i < 9; i++) {
        cv.put(COLUMN_DESCTRIPTION, "sometext " + i);
        cv.put(COLUMN_IMG, R.drawable.ic_launcher);
        cv.put(COLUMN_BOOLEAN, false);
        db.insert(DB_TABLE, null, cv);
      }*/
      Log.d(TAG, "Create db suscesfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
  }
}