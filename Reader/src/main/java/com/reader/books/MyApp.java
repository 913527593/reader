package com.reader.books;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.reader.books.db.DBOpenHelper;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //Bmob默认初始化
//        Bmob.initialize(this, "772f0d5bcb45dfd29579c82dd05510aa");//留言板
        Bmob.initialize(this, "d2178c806206417fdd638febe1ddf775");//阅读器
        context = this;
    }
}
