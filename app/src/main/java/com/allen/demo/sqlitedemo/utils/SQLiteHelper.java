package com.allen.demo.sqlitedemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.allen.demo.sqlitedemo.bean.Constant;

/**
 * Created by Allen on 2017/3/28.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Constant.TABLE_NAME + "(" + Constant._ID + " Integer " +
                "primary key AUTOINCREMENT, " + Constant.NAME + " varchar(10) , " + Constant.AGE
                + " Integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
