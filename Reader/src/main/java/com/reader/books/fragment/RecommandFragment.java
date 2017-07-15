package com.reader.books.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.reader.R;

/**
 * 精选书库
 * A simple {@link Fragment} subclass.
 */
public class RecommandFragment extends BaseFragment {


    public RecommandFragment() {
        // Required empty public constructor
    }

    @Override
    protected void showActionBar() {
        actionBar = (LinearLayout) contentView.findViewById(R.id.recommand_action_bar);
        initActionBar(null,0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_recommand, container, false);
        showActionBar();
        return contentView;
    }


}
