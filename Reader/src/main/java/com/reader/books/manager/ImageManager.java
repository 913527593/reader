package com.reader.books.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 *
 * Created by john on 2017/5/30.
 */

public class ImageManager {

    public static Bitmap getCircleImage(Context context,Bitmap bitmap) {
        //得到bitmap的长和宽以及圆的半径
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width,height)/2;

        //创建一个画布的背景图片
        Bitmap backBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(backBitmap);
        //创建一个画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //画圆
        canvas.drawCircle(width/2,height/2,radius,paint);

        //设置为相交模式(取重合部分)
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //画图片
        canvas.drawBitmap(bitmap,0,0,paint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawCircle(width/2,height/2,radius-2,paint);

        return backBitmap;
    }
}
