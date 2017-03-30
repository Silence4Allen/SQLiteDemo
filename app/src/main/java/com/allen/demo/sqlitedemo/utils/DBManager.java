package com.allen.demo.sqlitedemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.allen.demo.sqlitedemo.bean.Constant;
import com.allen.demo.sqlitedemo.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/3/28.
 */

public class DBManager {
    private static SQLiteHelper helper;

    public static SQLiteHelper getInstance(Context context) {
        if (helper == null) {
            helper = new SQLiteHelper(context);
        }
        return helper;
    }

    /**
     * 根据sql语句查询获得cursor对象
     *
     * @param db            数据库对象
     * @param sql           查询的sql语句
     * @param selectionArgs 查询条件的占位符
     * @return 查询结果
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs) {
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, selectionArgs);
        }
        return cursor;
    }

    /**
     * cursor对象转换成list集合
     *
     * @param cursor 游标对象
     * @return 集合对象
     */
    public static List<Person> cursorToList(Cursor cursor) {
        List<Person> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex(Constant._ID));
            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person p = new Person(_id, name, age);
            list.add(p);
        }
        return list;
    }

    public static int getDataCount(SQLiteDatabase db, String tableName) {
        int count = 0;
        if (db != null) {
            Cursor cursor = db.rawQuery("select * from " + Constant.TABLE_NAME, null);
            count = cursor.getCount();//获取cursor中的数据总数
        }
        return count;
    }

    /**
     * 根据当前页码查询获取该页码对应的集合数据
     *
     * @param db          数据库对象
     * @param tableName   数据库名称
     * @param currentPage 当前页码
     * @return 当前页对应的集合
     */
    public static List<Person> getListByCurrentPage(SQLiteDatabase db, String tableName, int
            currentPage, int pageSize) {
        int index = (currentPage - 1) * pageSize;//当前页码第一条数据的下标
        Cursor cursor = null;
        if (db != null) {
            String sql = "select * from " + tableName + " limit ?,? ";
            cursor = db.rawQuery(sql, new String[]{index + "", pageSize + ""});
        }
        return cursorToList(cursor);
    }
}
