package com.reader.books.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.reader.R;
import com.reader.books.adapter.BookShelfAdapter;
import com.reader.books.bll.BookBiz;
import com.reader.books.entify.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * 书架
 * A simple {@link Fragment} subclass.
 */
public class BookShelfFragment extends BaseFragment {
    private static final String TAG = "BookShelfFragment";
    private List<Book> booksList;
    private BookShelfAdapter adapter;

    public BookShelfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_book_shelf, container, false);
        showActionBar();
        setRecycleView();
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();

    }

    private void refreshList() {
        booksList = new ArrayList<>();
        BookBiz biz = new BookBiz();
        booksList = biz.findAllBooks();
//        if (booksList.size() == 0) {
//            Log.i(TAG, "refreshList: refreshList:refreshList");
//        }
//        for (Book book:booksList){
//            Log.i(TAG, "refreshList: book="+book.toString());
//        }
        Book book = new Book(R.mipmap.txt, "本地书籍");
        booksList.add(0, book);
        book = new Book(R.mipmap.add, "添加书籍");
        booksList.add(booksList.size(), book);
        adapter.AddBookList(booksList,true);
    }

    private void setRecycleView() {
        RecyclerView recycleView = (RecyclerView) contentView.findViewById(R.id.book_shelf_grid_view);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        adapter = new BookShelfAdapter(getContext());
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);

        //
        adapter.setOnItemClickListener(new BookShelfAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position==0){
                    Toast.makeText(getContext(), "本地书籍", Toast.LENGTH_SHORT).show();

                }else if (position==booksList.size()){
                    Toast.makeText(getContext(), "添加书籍", Toast.LENGTH_SHORT).show();

                }else{
                    Book book = booksList.get(position);
                    Toast.makeText(getContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void showActionBar() {
        actionBar = (LinearLayout) contentView.findViewById(R.id.book_shelf_action_bar);
        initActionBar(null,R.mipmap.ic_menu_delete);

        ImageView delected = (ImageView) actionBar.findViewById(R.id.img_right);

        //添加监听
        delected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),SearchFragment.class);
                Toast.makeText(getActivity(), "批量删除", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
