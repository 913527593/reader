package com.reader.books.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reader.R;
import com.reader.books.IContact.IConstant;
import com.reader.books.adapter.ViewPagerAdapter;
import com.reader.books.bll.UserBiz;
import com.reader.books.entify.User;
import com.reader.books.fragment.BookShelfFragment;
import com.reader.books.fragment.FoundFragment;
import com.reader.books.fragment.HomeFragment;
import com.reader.books.fragment.RecommandFragment;
import com.reader.books.manager.ImageManager;
import com.reader.books.util.SPUtil;
import com.reader.books.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //主界面
    @BindView(R.id.drawer_layout)
    DrawerLayout draw;
    @BindView(R.id.main_activity)
    RelativeLayout layout;
    @BindView(R.id.main_radio_group)
    RadioGroup group;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatButton;
    @BindView(R.id.main_view_pager)
    ViewPager pager;
    //侧滑界面
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private View headerView;

    private float curX = 0, curY = 0;
    private float lastX = 0, lastY = 0;

    //登录标志，登录为true
    public static boolean isLogin = false;
    //登录用户名
    public static String username;
    //当前登录用户
    public static User user;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);

        //初始化主界面
        initView();
        //初始化侧滑部分
        initNavigationView();
        //设置监听
        setClickListener();

    }

    /**
     * 初始化侧滑
     */
    private void initNavigationView() {
        //得到NavigationView的头部
        headerView = navigationView.getHeaderView(0);
//        Log.i(TAG, "onCrea/te: view--"+view);
        //侧滑头部背景图片
        ImageView drawableHeaderBg = (ImageView) headerView.findViewById(R.id.drawable_header_bg);
        //给背景图片设置上下移动动画

        //侧滑头部用户头像
        CircleImageView userPhoto = (CircleImageView) headerView.findViewById(R.id.user_photo);
        userPhoto.setBorderColor(Color.WHITE);
        userPhoto.setBorderWidth(3);
        //侧滑头部用户名
        TextView userName = (TextView) headerView.findViewById(R.id.user_name);

        //判断用户是否登录
        if (isLogin) {
            UserBiz biz = new UserBiz();
            user = biz.query(username);
            Picasso.with(this).load(user.getUserPhoto()).error(R.mipmap.ic_contact).into(userPhoto);
            userName.setText(user.getUsername());

        } else {
            userPhoto.setImageResource(R.mipmap.ic_contact);
            userName.setText("未登录");
        }
    }

    /**
     * 初始化主界面
     */
    private void initView() {

        //查询登录信息
        SPUtil sp = SPUtil.getInstance();
        isLogin = sp.getBoolean(IConstant.IS_LOGIN);
        if (isLogin) {
            username = sp.getStrng(IConstant.LOGIN_NAME);
        }

        //准备数据
        List<Fragment> list = new ArrayList<>();
        list.add(new BookShelfFragment());
        list.add(new RecommandFragment());
        list.add(new HomeFragment());
        list.add(new FoundFragment());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(adapter);
    }

    /**
     * 设置监听
     * ViewPager
     * RadioGroup
     * 侧滑header
     * 侧滑menu
     */
    private void setClickListener() {
        //为RadioGroup设置监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {//i为选中的button的id
                RadioButton button = (RadioButton) radioGroup.findViewById(i);
                int position = radioGroup.indexOfChild(button);
                pager.setCurrentItem(position, false);
            }
        });
        //为ViewPager设置监听
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton button = (RadioButton) group.getChildAt(position);
                button.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    startActivity(new Intent(MainActivity.this, UserInfoActivity.class));

                }else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });

        //FloatingActionButton设置移动监听
        floatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int screenWidth = layout.getWidth();
                int screenHeight = layout.getHeight();
//                Log.i(TAG, "onTouch: screenWidth=" + screenWidth + ",screenHeight=" + screenHeight);
                float offsetX = 0, offsetY = 0;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        curX = event.getRawX();
                        curY = event.getRawY();
                        lastX = curX;
                        lastY = curY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getRawX();
                        curY = event.getRawY();
                        offsetX = curX - lastX;
                        offsetY = curY - lastY;
//                        Log.i(TAG, "onTouch: offsetX=" + offsetX + ",offsetY=" + offsetY);
                        //移动
                        if (Math.abs(offsetX) > 5 || Math.abs(offsetY) > 5) {
                            int left = (int) (v.getLeft() + offsetX);
                            int top = (int) (v.getTop() + offsetY);
                            int right = (int) (v.getRight() + offsetX);
                            int bottom = (int) (v.getBottom() + offsetY);
                            if (left < 0) {
                                left = 0;
                                right = v.getWidth();
                            }
                            if (top <= 0) {
                                top = 0;
                                bottom = 168 / 2;
                            }
                            if (right > screenWidth) {
                                right = screenWidth;
                                left = right - v.getWidth();
                            }
                            if (bottom > (screenHeight - 168 / 2)) {
                                bottom = screenHeight - 168 / 2;
                                top = bottom - v.getHeight();
                            }
                            v.layout(left, top, right, bottom);
//                            v.postInvalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = lastX - curX;
                        offsetY = lastY - curY;
                        if (Math.abs(offsetX) <= 5 && Math.abs(offsetY) <= 5) {
                            if (!draw.isDrawerOpen(navigationView)) {
                                draw.openDrawer(navigationView);
                                floatButton.setImageResource(R.drawable.back);
                            } else {
                                draw.closeDrawer(navigationView);
                                floatButton.setImageResource(R.drawable.right);
                            }
                        }
                        break;
                }
                return true;
            }
        });
        //为侧滑menu添加监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "menu", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
