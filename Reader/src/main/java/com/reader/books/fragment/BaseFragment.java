package com.reader.books.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reader.R;
import com.reader.books.activity.LoginActivity;
import com.reader.books.activity.UserInfoActivity;
import com.reader.books.view.CircleImageView;
import com.squareup.picasso.Picasso;

import static com.reader.books.activity.MainActivity.isLogin;
import static com.reader.books.activity.MainActivity.user;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "BaseFragment";

    protected LinearLayout actionBar;
    protected View contentView;

    public BaseFragment() {
        super();
    }


    /**
     * 显示actionBar
     *
     * @param rightId 右边的图片
     */
    public void initActionBar(String titleName, int rightId) {
        if (actionBar == null)
            return;
        CircleImageView userPhoto = (CircleImageView) actionBar.findViewById(R.id.img_user_photo);
        TextView title1 = (TextView) actionBar.findViewById(R.id.tv_search);
        TextView title2 = (TextView) actionBar.findViewById(R.id.tv_fragment_title);
        ImageView search = (ImageView) actionBar.findViewById(R.id.img_right);

        if (isLogin) {
            Log.i(TAG, "initActionBar: imgpath=" + user.getUserPhoto());
            Picasso.with(getContext()).load(user.getUserPhoto()).error(R.mipmap.ic_contact).into(userPhoto);
        } else {
            userPhoto.setImageResource(R.mipmap.ic_contact);
        }

        if (titleName == null) {
            title1.setVisibility(View.VISIBLE);
            title2.setVisibility(View.GONE);
        } else {
            title1.setVisibility(View.GONE);
            Log.i(TAG, "initActionBar: title1.setVisibility(View.GONE)");
            title2.setVisibility(View.VISIBLE);
            title2.setText(titleName);
        }
        if (rightId > 0) {
            search.setVisibility(View.VISIBLE);
            search.setImageResource(rightId);
        } else {
            search.setVisibility(View.INVISIBLE);
        }
        //为标题栏中不变的内容添加监听
        userPhoto.setOnClickListener(this);
        title1.setOnClickListener(this);
    }

    protected abstract void showActionBar();


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_user_photo:
                if (isLogin) {
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.tv_search:
                Toast.makeText(getActivity(), "查询", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
