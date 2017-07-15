package com.reader.books.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reader.R;
import com.reader.books.entify.Book;
import com.reader.books.manager.LoadImageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 图书列表
 * Created by Administrator on 2017/6/5.
 */

public class BookListAdapter extends MyBaseAdapter<Book> {
    private static final String TAG = "BookListAdapter";

    public BookListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = layout.inflate(R.layout.inflate_book_list, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imgBook = (ImageView) view.findViewById(R.id.book_photo);
            viewHolder.tvBookName = (TextView) view.findViewById(R.id.book_name);
            viewHolder.tvBookSub = (TextView) view.findViewById(R.id.book_intro);
            viewHolder.tvBookAuthor = (TextView) view.findViewById(R.id.book_author);
            viewHolder.tvBookReading = (TextView) view.findViewById(R.id.book_read_num);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Book book= (Book) getItem(i);
//        Log.i(TAG, "getView: viewHolder.imgBook="+viewHolder.imgBook);
        LoadImageManager.getBitmapWithCache(viewGroup.getContext(),viewHolder.imgBook,book.getImg());
        viewHolder.tvBookName.setText(book.getTitle());
        viewHolder.tvBookSub.setText(book.getSub());
        viewHolder.tvBookAuthor.setText(book.getAuthor());
        viewHolder.tvBookReading.setText(book.getReading());
        return view;
    }
    private class ViewHolder {
        ImageView imgBook;
        TextView tvBookName;
        TextView tvBookSub;
        TextView tvBookAuthor;
        TextView tvBookReading;
    }
}
