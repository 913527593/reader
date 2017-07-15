package com.reader.books.db;

/**
 * 数据库
 * Created by Administrator on 2017/6/18.
 */

public class DB {
    public static class Book {
        public static String TABLE = "book";

        public static class Colunms {
            public static String ID = "_id";
            public static String PHOTO = "photo";
            public static String NAME = "name";
            public static String AUTHOR = "author";
            public static String SUB = "sub";
            public static String READ = "read";
            public static String TIME = "time";


        }
    }

}
