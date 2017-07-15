package com.reader.books.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.reader.books.MyApp;
import com.reader.books.activity.SplashActivity;
import com.reader.books.db.DB;
import com.reader.books.db.DBOpenHelper;
import com.reader.books.entify.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * 直接对数据库的操作
 * Created by Administrator on 2017/6/18.
 */

public class BookDao {
    private static final String TAG = "BookDao";

    private SQLiteDatabase db;

    public BookDao() {
    }

    /**
     * 向数据库添加数据
     *
     * @param book 添加值
     */
    public long addBook(Book book) {
        db = SplashActivity.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB.Book.Colunms.NAME, book.getTitle());
        values.put(DB.Book.Colunms.AUTHOR, book.getAuthor());
        values.put(DB.Book.Colunms.READ, book.getReading());
        values.put(DB.Book.Colunms.TIME, book.getBytime());
        values.put(DB.Book.Colunms.SUB, book.getSub());
        values.put(DB.Book.Colunms.PHOTO, book.getImg());
        values.get(DB.Book.Colunms.PHOTO);
        long id = db.insert(DB.Book.TABLE, DB.Book.Colunms.NAME, values);
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
    public List<Book> query(String selection, String[] selectionArgs) {
        List<Book> list = new ArrayList<>();
        db = SplashActivity.helper.getReadableDatabase();
        String[] columns = {DB.Book.Colunms.ID, DB.Book.Colunms.NAME,
                DB.Book.Colunms.AUTHOR, DB.Book.Colunms.PHOTO,
                DB.Book.Colunms.READ, DB.Book.Colunms.TIME,
                DB.Book.Colunms.SUB};
        Cursor cursor = db.query(DB.Book.TABLE, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            for (; !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DB.Book.Colunms.ID));
                String name = cursor.getString(cursor.getColumnIndex(DB.Book.Colunms.NAME));
                String author = cursor.getString(cursor.getColumnIndex(DB.Book.Colunms.AUTHOR));
                String reading = cursor.getString(cursor.getColumnIndex(DB.Book.Colunms.READ));
                String time = cursor.getString(cursor.getColumnIndex(DB.Book.Colunms.TIME));
                String photo = cursor.getString(cursor.getColumnIndex(DB.Book.Colunms.PHOTO));
                String sub = cursor.getString(cursor.getColumnIndex(DB.Book.Colunms.SUB));
                Book book = new Book(id, photo, name, sub, author, reading, null, time);
//                Log.i(TAG, "query: photo=" + photo);
                list.add(book);
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
        int id = db.delete(DB.Book.TABLE, whereCaluse, whereArgs);
        db.close();
        return id;
    }

    /**
     * 更新数据库
     *
     * @param i    更新条件
     * @param book 更新内容
     * @return
     */
    public int update(int i, Book book) {
        db = SplashActivity.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String whereCaluse = DB.Book.Colunms.ID + "=?";
        String[] whereArgs = {String.valueOf(i)};
        int id = db.update(DB.Book.TABLE, values, whereCaluse, whereArgs);
        db.close();
        return id;
    }
}
