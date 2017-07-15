package com.reader.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reader.R;
import com.reader.books.entify.BookSpecies;

import java.util.ArrayList;
import java.util.List;

/**
 * 图书种类列表适配器
 * Created by Administrator on 2017/6/5.
 */

public class BookKindsListAdapter extends MyBaseAdapter<BookSpecies> {
    private static final String TAG = "BookKindsListAdapter";

    public BookKindsListAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = layout.inflate(R.layout.inflate_book_shop_list, viewGroup, false);
//            Log.i(TAG, "getView: view= "+view);
            viewHolder = new ViewHolder();
            viewHolder.imgName = (ImageView) view.findViewById(R.id.book_species_photo);
            viewHolder.tvCatalog = (TextView) view.findViewById(R.id.book_species_catalog);
            viewHolder.tvNum = (TextView) view.findViewById(R.id.book_species_num);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        BookSpecies bookSpecies = (BookSpecies) getItem(i);
        viewHolder.imgName.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tvCatalog.setText(bookSpecies.getCatalog());
        viewHolder.tvNum.setText("共1002册");
        return view;
    }

    private class ViewHolder {
        ImageView imgName;
        TextView tvCatalog;
        TextView tvNum;
    }
}
