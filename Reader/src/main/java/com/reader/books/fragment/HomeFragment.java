package com.reader.books.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.reader.R;
import com.reader.books.activity.ListActivity;
import com.reader.books.adapter.BookKindsListAdapter;
import com.reader.books.entify.BookSpecies;
import com.reader.books.manager.HttpManager;

import java.util.List;

/**
 * 书库
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    private BookKindsListAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void showActionBar() {
        actionBar = (LinearLayout) contentView.findViewById(R.id.book_shop_action_bar);
        initActionBar(null,R.mipmap.ic_search);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_home, container, false);
        showActionBar();
        initListView();
        asyncLoadAllBookSpecies();
//        Log.i(TAG, "onCreateView: ");
        return contentView;
    }

    private void initListView() {
        ListView listView = (ListView) contentView.findViewById(R.id.book_shop_list);
        adapter = new BookKindsListAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookSpecies bookSpecied = (BookSpecies) adapterView.getItemAtPosition(i);
//                Toast.makeText(getActivity(), "id="+bookSpecied.getId()+",catalog="+bookSpecied.getCatalog(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), ListActivity.class);
                intent.putExtra("id",bookSpecied.getId());
                intent.putExtra("catalog",bookSpecied.getCatalog());
                startActivity(intent);
            }
        });
    }

    private void asyncLoadAllBookSpecies() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<BookSpecies> list = HttpManager.getBooksAllKindsWithHttpGet();
                Message message = handler.obtainMessage();
                message.obj = list;
//                for (BookSpecies book : list) {
//                    Log.i(TAG, "onLoadBooksEnd: book=" + book);
//                }
                handler.sendMessage(message);
            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<BookSpecies> bookSpecies = (List<BookSpecies>) msg.obj;
            adapter.addDatas(bookSpecies,true);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.i(TAG, "onDestroyView: ");
    }
}
