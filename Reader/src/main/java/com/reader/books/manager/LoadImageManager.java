package com.reader.books.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.reader.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片下载管理
 * Created by john on 2017/6/12.
 */

public class LoadImageManager {
    private static final String TAG = "LoadImageManager";

    /**
     * LruCache 集合最近最少使用存在于该集合中的数据都是强引用
     * 我们可以在定义这样的集合时设置一个最大的存储空间,当空间满了要加新的数据时会根据最近
     * 最少使用的算法
     */
    public static LruCache<String, Bitmap> lruCache = null;

    static {
        //设置最大缓存区
        long maxSize = Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>((int) maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight() * value.getRowBytes();
            }
        };
    }

    /**
     * 如果我们需要加载一个音乐的专辑图片，可以先从内存缓存中去进行该专辑
     * 图片的查找，如果内存缓存中没有的话，再从文件缓存中查找，如果文件缓存中也
     * 没有的话，说明我们还从来没有网络上加载过要使用的图片，这时我们再从网络上加载
     * 加载完成之再分别把这个图片缓存到内存和文件中，以便于下次再使用的时候，可以直接
     * 从缓存中拿到。
     *
     * @param context
     * @param imageView img控件
     * @param imgurl
     */
    public static void getBitmapWithCache(Context context, ImageView imageView, String imgurl) {
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(imgurl))
            return;
        //从内存的缓存中查找图片
        bitmap = getBitmapFromMpmery(imgurl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //从文件缓存中查找图片
        bitmap = getBitmapFromFiles(context, imgurl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //用volley从网络加载图片
        getBitmapWithVolley(context, imageView, imgurl);
    }

    /**
     * 用volley从网络加载图片
     *
     * @param imgurl
     */
    private static void getBitmapWithVolley(final Context context, ImageView imageView, String imgurl) {
        //得到请求队列
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageLoader loder = new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s, bitmap);
                saveBitmapTofiles(context, s, bitmap);
            }
        });
//        Log.i(TAG, "getBitmapWithVolley: loder="+loder);
        loder.get(imgurl, ImageLoader.getImageListener(imageView, R.mipmap.ic_wu, R.mipmap.ic_wu), 70, 70);//0：图片不压缩

    }

    /**
     * 保存图片到缓存文件中
     *
     * @param context
     * @param path    图片路径
     * @param bitmap  图片
     */
    private static void saveBitmapTofiles(Context context, String path, Bitmap bitmap) {

        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        String imgName = path.substring(path.lastIndexOf("/") + 1);
        File file = new File(cacheDir, imgName);
        try {
            OutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从缓存文件中获取图片
     *
     * @param imgurl 图片路径
     * @return
     */
    private static Bitmap getBitmapFromFiles(Context context, String imgurl) {
        Bitmap bitmap = null;
        String imgName = imgurl.substring(imgurl.lastIndexOf("/") + 1);
        //得到缓存目录
        File cachePath = context.getCacheDir();
        if (cachePath != null) {
            //得到缓存目录下的所有文件的集合
            File[] files = cachePath.listFiles();
            //遍历集合
            for (int i = 0; i < files.length; i++) {
                if (imgName.equals(files[i].getName())) {
                    bitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
                }
            }
        }
        return bitmap;
    }

    /**
     * 从内存中获取图片
     *
     * @param imgurl 图片路径
     * @return
     */
    private static Bitmap getBitmapFromMpmery(String imgurl) {
        Bitmap bitmap = null;
        String imgName = imgurl.substring(imgurl.lastIndexOf("/") + 1);
        bitmap = lruCache.get(imgName);
        return bitmap;
    }
}
