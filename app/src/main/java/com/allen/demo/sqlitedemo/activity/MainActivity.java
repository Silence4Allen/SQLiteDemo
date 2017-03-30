package com.allen.demo.sqlitedemo.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.allen.demo.sqlitedemo.adapter.MyBaseAdapter;
import com.allen.demo.sqlitedemo.adapter.MyCursorAdapter;
import com.allen.demo.sqlitedemo.R;
import com.allen.demo.sqlitedemo.bean.Constant;
import com.allen.demo.sqlitedemo.bean.Person;
import com.allen.demo.sqlitedemo.utils.DBManager;
import com.allen.demo.sqlitedemo.utils.SQLiteHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "SQLiteDemo";

    private SQLiteHelper helper;
    private SQLiteDatabase db;
    private ListView lv;
    private int totalNum;//表示当前控件加载数据的总条目
    private int pageSize = 15;//表示每页展示数据的条目
    private int pageNum;//表示总页码
    private int currentPage = 1;//当前页码
    private List<Person> totalList;
    private MyBaseAdapter baseAdapter;
    private boolean isDivPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        helper = DBManager.getInstance(this);
    }

    /**
     * 点击创建数据库并插入数据
     *
     * @param view
     */
    public void createDB(View view) {
        db = helper.getWritableDatabase();
        //1.数据库显示开启事务
        db.beginTransaction();
        for (int i = 1; i <= 30; i++) {
            String sql = "insert into " + Constant.TABLE_NAME + " (" + Constant.NAME + "," +
                    Constant.AGE + ")" + " values ('张三" + i + "'," + i + ")";
            db.execSQL(sql);
        }
        //2.提交当前事务
        db.setTransactionSuccessful();
        //3.关闭事务
        db.endTransaction();
        db.close();
    }

    /**
     * 点击按钮查询表中的数据
     *
     * @param view
     */
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_querySimpleCursorAdapter:
                db = helper.getWritableDatabase();
                String selectSql = "select * from " + Constant.TABLE_NAME;
                Cursor cursor = DBManager.selectDataBySql(db, selectSql, null);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item,
                        cursor, new String[]{Constant._ID, Constant.NAME, Constant.AGE}, new
                        int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age}, SimpleCursorAdapter
                        .FLAG_REGISTER_CONTENT_OBSERVER);
                lv.setAdapter(adapter);
                db.close();
                break;
            case R.id.btn_queryCursorAdapter:
                db = helper.getWritableDatabase();
                selectSql = "select * from " + Constant.TABLE_NAME;
                cursor = DBManager.selectDataBySql(db, selectSql, null);
                MyCursorAdapter cursorAdapter = new MyCursorAdapter(this, cursor, CursorAdapter
                        .FLAG_REGISTER_CONTENT_OBSERVER);
                lv.setAdapter(cursorAdapter);
                db.close();
                break;
            case R.id.btn_queryApi:
                db = helper.getWritableDatabase();
                /*
                String table 表示查询的表名
                String[] columns 表示查询表中的字段名称（null表示查询所有）
                String selection 表示查询条件
                String[] selectionArgs 表示查询条件的占位符的取值
                String groupBy 表示分组条件
                String having 表示筛选条件
                String orderBy 表示排序条件
                 */
                cursor = db.query(Constant.TABLE_NAME, null, Constant._ID + ">?", new
                        String[]{"10"}, null, null, Constant._ID + " desc");
                List<Person> list = DBManager.cursorToList(cursor);
                for (Person person : list) {
                    Log.i(TAG, "onClick: " + person.toString());
                }
                db.close();
                break;
            case R.id.btn_queryWithPaging:
                if (totalList != null) {
                    totalList.clear();
                    currentPage = 1;
                }
                db = helper.getWritableDatabase();
                totalNum = DBManager.getDataCount(db, Constant.TABLE_NAME);
                pageNum = (int) Math.ceil(totalNum / (double) pageSize);
                if (currentPage == 1) {
                    totalList = DBManager.getListByCurrentPage(db, Constant.TABLE_NAME,
                            currentPage, pageSize);
                }
                baseAdapter = new MyBaseAdapter(this, totalList);
                lv.setAdapter(baseAdapter);
                lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE ==
                                scrollState) {
                            if (currentPage < pageNum) {
                                currentPage++;
                                totalList.addAll(DBManager.getListByCurrentPage(db, Constant
                                        .TABLE_NAME, currentPage, pageSize));
                                baseAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int
                            visibleItemCount, int totalItemCount) {
                        isDivPage = (firstVisibleItem + visibleItemCount) == totalItemCount;
                    }
                });
                break;
        }
    }
}
