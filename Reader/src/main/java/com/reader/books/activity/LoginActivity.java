package com.reader.books.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reader.R;
import com.reader.books.bll.UserBiz;
import com.reader.books.entify.User;
import com.reader.books.util.SPUtil;
import com.reader.books.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.et_user_name)
    EditText userName;
    @BindView(R.id.img_use_photo)
    CircleImageView usePhoto;
    @BindView(R.id.img_user_name_delector)
    ImageView userNameDelector;
    @BindView(R.id.et_pass_word)
    EditText passWord;
    @BindView(R.id.img_pass_word_visible)
    ImageView passWOrdVisible;
    @BindView(R.id.img_pass_word_delector)
    ImageView passWordDelector;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_pass_word)
    TextView tvPassWord;
    @BindView(R.id.tv_user_register)
    TextView tvUserRegister;
    @BindView(R.id.gif_view)
    GifImageView gifView;


    private boolean isVisible = false; //false为不可见
    private boolean isReginst = false;//是否注册，在输入监听中判断

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    /**
     * 输入框输入内容监听
     */
    @OnTextChanged(value = R.id.et_user_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterNameTextChange(Editable s) {
        Log.i(TAG, "afterTextChange: s=" + s.toString());
        String name = s.toString();
        usePhoto.setVisibility(View.INVISIBLE);
        if (s.toString().length() > 0) {
            userNameDelector.setVisibility(View.VISIBLE);
            //查询本地数据库
            UserBiz biz = new UserBiz();
            User user = biz.query(name);
            if (user != null) {
                isReginst = true;
                String imgPath = user.getUserPhoto();
                usePhoto.setVisibility(View.VISIBLE);
                Picasso.with(this).load(imgPath).into(usePhoto);
            } else {
                //从Bmob服务器中查找
                findUserFromBmobByName(name);
            }
        } else {
            userNameDelector.setVisibility(View.INVISIBLE);
        }
    }

    private void findUserFromBmobByName(String name) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", name);
        query.findObjects(LoginActivity.this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list.size() > 0) {
                    isReginst = true;
                    User user = list.get(0);
                    String photoPath = user.getUserPhoto();
                    usePhoto.setVisibility(View.VISIBLE);
                    Picasso.with(LoginActivity.this).load(photoPath).into(usePhoto);
                } else {
                    isReginst = false;
                    usePhoto.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onFailure: i=" + i + ",s=" + s);
            }
        });
    }


    /**
     * 输入框输入内容监听
     */
    @OnTextChanged(value = R.id.et_pass_word, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterPassTextChange(Editable s) {
        if (s.toString().length() > 0) {
            passWordDelector.setVisibility(View.VISIBLE);
            passWOrdVisible.setVisibility(View.VISIBLE);
        } else {
            passWordDelector.setVisibility(View.INVISIBLE);
            passWOrdVisible.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 点击监听
     *
     * @param view
     */
    @OnClick({R.id.img_user_name_delector, R.id.img_pass_word_delector, R.id.img_pass_word_visible,
            R.id.btn_login, R.id.tv_pass_word, R.id.tv_user_register})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.img_user_name_delector:
                userName.setText("");
                break;
            case R.id.img_pass_word_delector:
                passWord.setText("");
                break;
            case R.id.img_pass_word_visible:
                if (!isVisible) {
                    isVisible = true;
                    passWord.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);//TYPE_NULL
                    passWOrdVisible.setImageResource(R.drawable.eye_open);
                } else {
                    isVisible = false;
                    passWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//TYPE_TEXT_VARIATION_PASSWORD
                    passWOrdVisible.setImageResource(R.drawable.eye_close);
                }
                break;
            case R.id.btn_login:
                String name = userName.getText().toString();
                final String pass = passWord.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断用户是否注册
                if (!isReginst) {
                    Toast.makeText(LoginActivity.this, "该用户还没注册", Toast.LENGTH_SHORT).show();
                    return;
                }
                final User user = new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.login(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //保存登录信息
                        SPUtil sp = SPUtil.getInstance();
                        sp.saveLoginInfo(user.getUsername(), pass, true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(LoginActivity.this, "系统繁忙，请稍后重试", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onFailure: i=" + i + ",s=" + s);
                    }
                });
                break;
            case R.id.tv_pass_word:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("username");
            String imgpath = data.getStringExtra("imgpath");
            String pass = data.getStringExtra("password");
            userName.setText(name);
            passWord.setText(pass);
            usePhoto.setVisibility(View.VISIBLE);
            Picasso.with(this).load(imgpath).into(usePhoto);
        }
    }
}
