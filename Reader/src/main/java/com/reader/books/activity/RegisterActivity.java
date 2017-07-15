package com.reader.books.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reader.R;
import com.reader.books.bll.UserBiz;
import com.reader.books.view.CircleImageView;
import com.reader.books.entify.User;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    @BindView(R.id.et_user_account)
    EditText etUserAccount;
    @BindView(R.id.et_user_pass_word)
    EditText etUserPassWord;
    @BindView(R.id.et_user_pass_word_ok)
    EditText etUserPassWordOk;
    @BindView(R.id.et_user_email)
    EditText etUserEmail;
    @BindView(R.id.btn_reginst)
    Button btnReginst;
    @BindView(R.id.circle_image_view)
    CircleImageView userPhoto;

    private String path;//头像本地路径
    private String avatar;//头像网络路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.circle_image_view, R.id.btn_reginst})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_image_view:
                selectorUserPhoto();
                break;
            case R.id.btn_reginst:
                userReginst();
                break;
        }
    }

    /**
     * 用户注册
     */
    private void userReginst() {
        final String userAccount = etUserAccount.getText().toString();
        final String passWord = etUserPassWord.getText().toString();
        String passWordOk = etUserPassWordOk.getText().toString();
        if (TextUtils.isEmpty(userAccount) || TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passWord.equals(passWordOk)) {
            Toast.makeText(this, "密码和确认密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = etUserEmail.getText().toString();
        final User user = new User();

        user.setUsername(userAccount);
        user.setPassword(passWord);
        user.setEmail(email);
        user.setUserPhoto(avatar);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                if (TextUtils.isEmpty(avatar)) {
                    user.setUserPhoto(avatar);
                }
                //保存注册信息到数据库
                saveDataBase(passWord, user);
                //跳转到登录界面
                Intent data = new Intent(RegisterActivity.this, LoginActivity.class);
                data.putExtra("username",user.getUsername());
                data.putExtra("imgpath",user.getUserPhoto());
                data.putExtra("password", passWord);
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                //查询用户名是否已经存在
                queryUserByName(userAccount);
                Log.i(TAG, "userReginst()  onFailure: i=" + i + ",s=" + s);
            }
        });
    }

    private void saveDataBase(String passWord, User user) {
        UserBiz biz = new UserBiz();
        String pass = new String(Hex.encodeHex(DigestUtils.sha(passWord)));
        long id = biz.add(user, pass);
        if (id > 0) {
            Log.i(TAG, "onSuccess: 本地数据库保存成功");
        } else {
            Log.i(TAG, "onSuccess: 本地数据库保存失败");
//            saveDataBase(passWord, user);

        }
    }

    private void queryUserByName(String userAccount) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", userAccount);
        query.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list.size() > 0) {
                    Toast.makeText(RegisterActivity.this, "该用户名已注册", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(RegisterActivity.this, "系统繁忙，请稍后重试", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "queryUserByName() onFailure: i=" + i + ",s=" + s);
            }
        });
    }

    /**
     * 选择用户头像
     */
    private void selectorUserPhoto() {
        //开启系统图库的Intent
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        //开启系统相机的Intent
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        path = file.getAbsolutePath();
        Uri paths = Uri.fromFile(file);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, paths);

        Intent chooser = Intent.createChooser(intent1, "选择头像...");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(chooser, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String filePath;
        if (requestCode == 200 && resultCode == RESULT_OK) {
            //头像是从图库选择的图片
            if (data != null) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                cursor.moveToNext();
                filePath = cursor.getString(0);
                Log.i(TAG, "onActivityResult: filePath=" + filePath);
                cursor.close();
            } else { //头像是拍摄的图片
                filePath = path;
            }
            //上传图片文件到Bmob服务器
            final BmobFile bmobFile = new BmobFile(new File(filePath));
            bmobFile.uploadblock(this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    avatar = bmobFile.getFileUrl(RegisterActivity.this);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    userPhoto.setImageBitmap(bitmap);
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(RegisterActivity.this, "系统繁忙，请稍后重试", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onFailure: i=" + i + ",s=" + s);
                }
            });
        }
    }
}
