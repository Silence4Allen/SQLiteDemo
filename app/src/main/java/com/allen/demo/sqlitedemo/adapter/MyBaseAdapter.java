package com.allen.demo.sqlitedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.demo.sqlitedemo.R;
import com.allen.demo.sqlitedemo.bean.Person;

import java.util.List;

/**
 * Created by Allen on 2017/3/30.
 */

public class MyBaseAdapter extends BaseAdapter {
    private Context context;
    private List<Person> list;

    public MyBaseAdapter(Context context, List<Person> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_id.setText(list.get(position).get_id() + "");
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_age.setText(list.get(position).getAge() + "");
        return convertView;
    }

    static class ViewHolder {
        TextView tv_id, tv_name, tv_age;
    }
}
