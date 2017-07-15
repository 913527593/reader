package com.reader.books.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.reader.books.activity.SplashActivity;
import com.reader.books.db.DB;
import com.reader.books.entify.Book;
import com.reader.books.entify.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */

public class UserDao {
    private static final String TAG = "UserDao";

    private SQLiteDatabase db;

    public UserDao() {

    }

    /**
     * 向数据库添加数据
     *
     * @param user 添加值
     */
    public long add(User user, String pass) {
        db = SplashActivity.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB.User.Colunms.OBJECT_ID, user.getObjectId());
        values.put(DB.User.Colunms.PHOTO, user.getUserPhoto());
        values.put(DB.User.Colunms.NAME, user.getUsername());
        values.put(DB.User.Colunms.PASS, pass);
        values.put(DB.User.Colunms.EMAIL, user.getEmail());
        long id = db.insert(DB.User.TABLE, DB.Book.Colunms.NAME, values);
//        Log.i(TAG, "add: pass=" + pass);
        db.close();
        return id;
    }

    /**
     * 向数据库查找数据
     *
     * @param selection     查找条件
     * @param selectionArgs 条件值
     * @return
     */
    public List<User> query(String selection, String[] selectionArgs) {
        List<User> list = new ArrayList<>();
        db = SplashActivity.helper.getReadableDatabase();
        String[] columns = {DB.User.Colunms.ID, DB.User.Colunms.OBJECT_ID, DB.User.Colunms.NAME,
                DB.User.Colunms.PASS, DB.User.Colunms.PHOTO, DB.User.Colunms.EMAIL,};
        Cursor cursor = db.query(DB.User.TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            for (; !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DB.User.Colunms.ID));
                String name = cursor.getString(cursor.getColumnIndex(DB.User.Colunms.NAME));
                String pass = cursor.getString(cursor.getColumnIndex(DB.User.Colunms.PASS));
                String photoPath = cursor.getString(cursor.getColumnIndex(DB.User.Colunms.PHOTO));
                String email = cursor.getString(cursor.getColumnIndex(DB.User.Colunms.EMAIL));

                User user = new User();
                user.setObjectId(String.valueOf(id));
                user.setUserPhoto(photoPath);
                user.setUsername(name);
                user.setPassword(pass);
                user.setEmail(email);
//                Log.i(TAG, "query: photo=" + photo);
                list.add(user);
            }
        }
        db.close();
        return list;
    }


    /**
     * @param whereCaluse
     * @param whereArgs
     * @return
     */
    public int delect(String whereCaluse, String[] whereArgs) {
        db = SplashActivity.helper.getWritableDatabase();
        int id = db.delete(DB.User.TABLE, whereCaluse, whereArgs);
        db.close();
        return id;
    }

    /**
     * 更新数据库
     *
     * @param i    更新条件
     * @param user 更新内容
     * @return
     */
    public int update(int i, User user) {
        db = SplashActivity.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String whereCaluse = DB.User.Colunms.ID + "=?";
        String[] whereArgs = {String.valueOf(i)};
        int id = db.update(DB.User.TABLE, values, whereCaluse, whereArgs);
        db.close();
        return id;
    }
}
