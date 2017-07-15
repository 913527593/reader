package com.reader.books.manager;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.reader.R;
import com.reader.books.activity.MainActivity;

/**
 *
 * Created by Administrator on 2017/5/28.
 */

public class AnimationManager {

    public static void showSplashAnimation(final Activity mActivity, ImageView img) {

        Animation animation = AnimationUtils.loadAnimation(mActivity,R.anim.alpha_animation_splash);
        img.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                mActivity.startActivity(intent);
                mActivity.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
