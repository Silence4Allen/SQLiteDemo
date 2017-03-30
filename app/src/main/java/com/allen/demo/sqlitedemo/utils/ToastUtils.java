package com.allen.demo.sqlitedemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Allen on 2017/3/28.
 */

public class ToastUtils {
    public static void showShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
