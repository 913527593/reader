package com.reader.books.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.reader.books.IContact.IConstant;
import com.reader.books.MyApp;

/**
 * Created by Administrator on 2017/7/15.
 */

public class SPUtil {

    private Context context;
    private static SPUtil Instance;
    private SharedPreferences sp;

    private SPUtil() {
        sp = MyApp.context.getSharedPreferences("reader", Context.MODE_PRIVATE);
    }

    public static SPUtil getInstance() {
        if (Instance == null) {
            synchronized (SPUtil.class) {
                if (Instance == null) {
                    Instance = new SPUtil();
                }
            }
        }
        return Instance;
    }


    public void saveLoginInfo(String name, String pass, boolean islogin) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(IConstant.LOGIN_NAME, name);
        edit.putString(IConstant.LOGIN_PASS, pass);
        edit.putBoolean(IConstant.IS_LOGIN, islogin);
        edit.commit();
    }

    public String getStrng(String key) {
        return sp.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key,false);
    }
}
