package com.reader.books.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.reader.R;
import com.reader.books.bll.BookBiz;
import com.reader.books.dal.BookDao;
import com.reader.books.entify.Book;
import com.reader.books.manager.LoadImageManager;
import com.reader.books.util.Err;

public class BookDetailedActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BookDetailedActivity";
    private Book book;
    private ImageView imgBack;
    private ImageView imgShare;
    private ImageView imgBookPhoto;
    private TextView bookName;
    private TextView bookAuthor;
    private TextView bookOnline;
    private ListView bookSub;
    private TextView tryRead;
    private TextView addShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detailed);
        initView();
        setListener();
    }

    private void setListener() {
        imgBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        tryRead.setOnClickListener(this);
        addShelf.setOnClickListener(this);
    }

    private void initView() {
        book = TransUtil.getBook();
//        Log.i(TAG, "initView: book.toString="+mbook.toString());

        imgBack = (ImageView) findViewById(R.id.img_book_back);
        imgShare = (ImageView) findViewById(R.id.img_book_share);
        imgBookPhoto = (ImageView) findViewById(R.id.img_book_photo);

        bookName = (TextView) findViewById(R.id.tv_book_name);
        bookAuthor = (TextView) findViewById(R.id.tv_book_author);
        bookOnline = (TextView) findViewById(R.id.tv_book_online);
        tryRead = (TextView) findViewById(R.id.tv_book_try_read);
        addShelf = (TextView) findViewById(R.id.tv_book_add_shelf);

//        imgBookPhoto.setImageResource(R.mipmap.ic_wu);
        LoadImageManager.getBitmapWithCache(this, imgBookPhoto, book.getImg());
        bookName.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookOnline.setText(book.getReading());

        bookSub = (ListView) findViewById(R.id.lv_book_sub);
//        Log.i(TAG, "initView: " + book.getSub());
        String[] str = {book.getSub()};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str);
        bookSub.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_book_back:
                finish();
                break;
            case R.id.img_book_share:
                break;
            case R.id.tv_book_try_read:
                break;
            case R.id.tv_book_add_shelf:
                BookBiz biz = new BookBiz();
                long id = biz.addBook(book);
                if (id == Err.BOOk_IS_EXISTS) {
                    Toast.makeText(this, "该书已经在书架中。。。", Toast.LENGTH_SHORT).show();
                } else if (id == Err.BOOK_IS_ERR) {
                    Toast.makeText(this, "该书信息有误", Toast.LENGTH_SHORT).show();
                } else if (id > 0) {
                    Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "添加失败!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
