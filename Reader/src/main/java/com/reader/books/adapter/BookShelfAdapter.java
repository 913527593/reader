package com.reader.books.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reader.R;
import com.reader.books.entify.Book;
import com.reader.books.manager.LoadImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 2017/5/30.
 */

public class BookShelfAdapter extends RecyclerView.Adapter<BookShelfAdapter.InnerViewHolder> {
    private static final String TAG = "BookShelfAdapter";
    private List<Book> books;
    private Context context;
    private onItemClickListener listener;

    public BookShelfAdapter(Context context) {
        this.context = context;
        books = new ArrayList<>();
    }

    public void AddBookList(List<Book> list, boolean flag) {
        if (flag) {
            books.clear();
        }
        books.addAll(list);
        notifyDataSetChanged();
    }

    class InnerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_book_photo)
        ImageView bookPhoto;
        @BindView(R.id.tv_book_name)
        TextView bookName;

        public InnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public InnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_card_view, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InnerViewHolder holder,final int position) {
        Book book = books.get(position);
//        Log.i(TAG, "onBindViewHolder: book.toString=" + book.toString());
        //区别书架中的第一个(本地书籍)和最后一个(添加)
        //book.getImg1() == 0,即不是第一个和最后一个
        if (book.getImg1() == 0) {
            holder.bookPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            LoadImageManager.getBitmapWithCache(context, holder.bookPhoto, book.getImg());
//            holder.bookPhoto.setImageResource(R.mipmap.ic_wu);
        } else {
            holder.bookPhoto.setScaleType(ImageView.ScaleType.CENTER);
            holder.bookPhoto.setImageResource(book.getImg1());
        }
        holder.bookName.setText(book.getTitle());
        View view = holder.itemView;
        if (listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }


}
