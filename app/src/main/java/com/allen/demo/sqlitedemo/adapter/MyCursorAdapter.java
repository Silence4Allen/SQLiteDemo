package com.allen.demo.sqlitedemo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.allen.demo.sqlitedemo.R;
import com.allen.demo.sqlitedemo.bean.Constant;

/**
 * Created by Allen on 2017/3/30.
 */

public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * 表示创建适配器控件中每个item对应的view对象
     *
     * @param context 上下文
     * @param cursor  数据源cursor对象
     * @param parent  当前item的父布局
     * @return 每项item的view对象
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        return view;
    }

    /**
     * 通过newView()方法确定了每个item展示的view对象，在bindView()中对布局中的控件进行填充
     *
     * @param view    由newView()返回的每个view对象
     * @param context 上下文
     * @param cursor  数据源cursor对象
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant._ID)) + "");
        tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant.NAME)));
        tv_age.setText(cursor.getInt(cursor.getColumnIndex(Constant.AGE)) + "");
    }
}
