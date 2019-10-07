package com.test1.calculator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test1.calculator.history.ResultEntry;

import java.util.ArrayList;

import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.CREATE_TABLE_HISTORY;
import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.HISTORY_DATABASE_NAME;
import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.KEY_HISTORY_COLOR;
import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.KEY_HISTORY_ID;
import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.KEY_HISTORY_INPUT;
import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.KEY_HISTORY_RESULT;
import static com.test1.calculator.data.DatabaseHelper.HISTORY_DATABASE.KEY_HISTORY_TYPE;
import static com.test1.calculator.data.DatabaseHelper.VARIABLE_DATABASE.CREATE_TABLE_VARIABLE;
import static com.test1.calculator.data.DatabaseHelper.VARIABLE_DATABASE.KEY_VAR_NAME;
import static com.test1.calculator.data.DatabaseHelper.VARIABLE_DATABASE.TABLE_VARIABLE_NAME;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "history_manager";
    public static final int DATABASE_VERSION = 5;
    private String TAG = DatabaseHelper.class.getName();

        public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_VARIABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VARIABLE_NAME);
        onCreate(db);
    }

    /**
     * 将项目历史保存到数据库
     *
     * @param resultEntry - {@link ResultEntry}
     * @return - <tt>true</tt> if ok, otherwise <tt>false</tt>
     */
    public long saveHistory(ResultEntry resultEntry) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_HISTORY_ID, resultEntry.getTime());
        contentValues.put(KEY_HISTORY_INPUT, resultEntry.getExpression());
        contentValues.put(KEY_HISTORY_RESULT, resultEntry.getResult());
        contentValues.put(KEY_HISTORY_COLOR, resultEntry.getColor());
        contentValues.put(KEY_HISTORY_TYPE, resultEntry.getType());
        return sqLiteDatabase.insert(HISTORY_DATABASE_NAME, null, contentValues);
    }

    public ArrayList<ResultEntry> getAllItemHistory() {
        ArrayList<ResultEntry> itemHistories = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + HISTORY_DATABASE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        long time = cursor.getLong(cursor.getColumnIndex(KEY_HISTORY_ID));
                        String math = cursor.getString(cursor.getColumnIndex(KEY_HISTORY_INPUT));
                        String result = cursor.getString(cursor.getColumnIndex(KEY_HISTORY_RESULT));
                        int color = cursor.getInt(cursor.getColumnIndex(KEY_HISTORY_COLOR));
                        int type = cursor.getInt(cursor.getColumnIndex(KEY_HISTORY_TYPE));
                        ResultEntry resultEntry = new ResultEntry(math, result, color, time, type);
                        itemHistories.add(resultEntry);
                        Log.d(TAG, "getAllItemHistory: " + time + " " + math + " " + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemHistories;
    }

    /**
     * 清除所有历史 mHistoryDatabase
     *
     * @return
     */
    public long clearHistory() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(HISTORY_DATABASE_NAME, null, null);
    }



    public void saveHistory(ArrayList<ResultEntry> histories) {
        for (ResultEntry h : histories) {
            saveHistory(h);
        }

    }

    public static final class VARIABLE_DATABASE {
        public static final String TABLE_VARIABLE_NAME = "tbl_variable";
        public static final String KEY_VAR_NAME = "name";
        public static final String KEY_VAR_VALUE = "value";
        public static final String CREATE_TABLE_VARIABLE =
                "CREATE TABLE " + TABLE_VARIABLE_NAME +
                        "(" + KEY_VAR_NAME + " TEXT PRIMARY KEY, "
                        + KEY_VAR_VALUE + " TEXT" + ")";
        //CREATE TABLE tbl_variable(name TEXT PRIMARY KEY, value TEXT)
    }

    public static final class HISTORY_DATABASE {
        public static final String HISTORY_DATABASE_NAME = "tbl_history";
        public static final String KEY_HISTORY_ID = "time";
        public static final String KEY_HISTORY_INPUT = "math";
        public static final String KEY_HISTORY_RESULT = "result";
        public static final String KEY_HISTORY_COLOR = "color";
        public static final String KEY_HISTORY_TYPE = "color";
        public static final String CREATE_TABLE_HISTORY =
                "create table " + HISTORY_DATABASE_NAME + "(" +
                        KEY_HISTORY_ID + " LONG PRIMARY KEY, " +
                        KEY_HISTORY_INPUT + " TEXT, " +
                        KEY_HISTORY_RESULT + " TEXT, " +
                        KEY_HISTORY_COLOR + " INTEGER," +
                        KEY_HISTORY_TYPE + "INTEGER" + ")";
        //create table tbl_history(time LONG PRIMARY KEY, math TEXT, result TEXT, color INTEGER,colorINTEGER)
    }
}
