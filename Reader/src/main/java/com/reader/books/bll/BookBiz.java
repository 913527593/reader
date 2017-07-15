package com.reader.books.bll;

import android.content.Context;

import com.reader.books.dal.BookDao;
import com.reader.books.db.DB;
import com.reader.books.entify.Book;
import com.reader.books.util.Err;

import java.util.List;

/**
 * Created by john on 2017/6/18.
 */

public class BookBiz {


    public BookBiz() {
    }

    /**
     * 添加图书到书架
     *
     * @param book
     * @return
     */
    public long addBook(Book book) {
        long id = 0;
        if (book == null || book.getTitle() == null)
            return Err.BOOK_IS_ERR;
        Book result = findBookByName(book.getTitle());
        if (result == null) {
            BookDao dao = new BookDao();
            id = dao.addBook(book);
        } else {
            return Err.BOOk_IS_EXISTS;
        }
        return id;
    }

    /**
     * 通过书名查询概述是否存在
     *
     * @param name
     * @return
     */
    public Book findBookByName(String name) {

        BookDao dao = new BookDao();
        String selection = DB.Book.Colunms.NAME + "=?";
        String[] selectionArgs = {name};
        List<Book> books = dao.query(selection, selectionArgs);
        if (books.size() > 0) {
            return books.get(0);
        }
        return null;
    }

    public List<Book> findAllBooks() {
        List<Book> books = null;
        BookDao dao = new BookDao();
        books = dao.query(null, null);
        return books;
    }
}
