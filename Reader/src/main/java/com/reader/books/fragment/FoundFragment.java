package com.reader.books.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.reader.R;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends BaseFragment {


    public FoundFragment() {
        // Required empty public constructor
    }

    @Override
    protected void showActionBar() {
        actionBar = (LinearLayout) contentView.findViewById(R.id.found_action_bar);
        initActionBar("发现", R.mipmap.add_blog);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_found, container, false);
        return contentView;
    }


}
