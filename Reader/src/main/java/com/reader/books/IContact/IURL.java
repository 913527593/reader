package com.reader.books.IContact;

/**
 * 网络请求URL
 * Created by Administrator on 2017/6/4.
 */

public interface IURL {
    //查询图书种类
    String SPECIES_URL = "http://apis.juhe.cn/goodbook/catalog";
    //查询具体种类的图书
    String BOOKS_URL = "http://apis.juhe.cn/goodbook/query";

    String KEY = "05981e0269d994a0078a8a724f62948f";
    String DTYPE = "json";



}
