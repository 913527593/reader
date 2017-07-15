package com.reader.books.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;


/**
 * BaseAdapter列表的adapter父类
 * Created by Administrator on 2017/6/14.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private static final String TAG = "MyBaseAdapter";
    private Context context;
    private List<T> dataList = new ArrayList<>();
    protected LayoutInflater layout;

    public MyBaseAdapter(Context context) {
        this.context = context;
        layout = LayoutInflater.from(context);
    }

    /**
     * 为adapter添加数据
     *
     * @param list 数据集合
     * @param flag 是否清楚原数据，true为清除
     */
    public void addDatas(List<T> list, boolean flag) {
        if (list==null){
            return;
        }
        if (flag) {
            dataList.clear();
        }
        dataList.addAll(list);
        notifyDataSetChanged();
//        for (T t : dataList) {
//            Log.i(TAG, "onLoadBooksEnd: book=" + t);
//        }
    }

    /**
     * 清除所有数据
     */
    public void removeDatas() {
        dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 清除具体数据
     *
     * @param t
     */
    public void removeDatas(T t) {
        dataList.remove(t);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
