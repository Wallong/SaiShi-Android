package com.twtstudio.coder.saishi_android.ui.welcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import android.os.Handler;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.twtstudio.coder.saishi_android.R;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;
import com.twtstudio.coder.saishi_android.myview.LetterSpacingTextView;
import com.twtstudio.coder.saishi_android.ui.login.LoginActivity;
import com.twtstudio.coder.saishi_android.ui.main.MainActivity;

/**
 * Created by clifton on 16-2-19.
 */
public class WelcomeActivity extends Activity{
    private final static String LOG_TAG = WelcomeActivity.class.getSimpleName();

    @Bind(R.id.iv_welcome_background)
    ImageView ivBackground;
    @Bind(R.id.iv_welcome_logo_medium)
    ImageView ivLogoRight;
    @Bind(R.id.tv_welcome)
    LetterSpacingTextView tvWelcome;
    @Bind(R.id.tv_welcome_CN1)
    TextView tvCN1;
    @Bind(R.id.tv_welcome_CN2)
    TextView tvCN2;
    @Bind(R.id.tv_welcome_EG)
    TextView tvEG;
    @Bind(R.id.iv_welcome_badge)
    ImageView ivBadge;

    private int heightPixels;
    private int widthPixels;
    private int densityDpi;
    private float density;

    private WelcomePresenter welcomePresenter = new WelcomePresenterImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        getInfo();
        initView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (PrefUtils.isLogin()) {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                WelcomeActivity.this.startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                WelcomeActivity.this.finish();
            }
        }, 4000);
        welcomePresenter.reLogin();
    }

    private void getInfo(){
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        heightPixels = outMetrics.heightPixels;
        widthPixels = outMetrics.widthPixels;
        densityDpi = outMetrics.densityDpi;
        density = outMetrics.density;
        LogHelper.e(LOG_TAG, "屏幕高：" + outMetrics.heightPixels + "  屏幕宽：" + outMetrics.widthPixels +"  " + outMetrics.density +"  "+ outMetrics.densityDpi);
    }


    private void initView(){
        int scaleHeight = Math.round(heightPixels * 0.20f);
        int scaleWidth = Math.round(widthPixels * 0.20f);
        int ivLogoHeight = Math.round(density * 200);
        int ivLogoLeftWidth = Math.round(widthPixels * 0.50f);
        int deltaX = ivLogoLeftWidth - scaleWidth;
        int deltaY = Math.round((ivLogoHeight - scaleHeight) * 2.1f);
        LogHelper.e(LOG_TAG, "deltaX----->" + deltaX + "  deltaY----->" + deltaY);

        Random random = new Random();
        int ran = random.nextInt(2);
        if(ran == 0){
            ivBackground.setImageResource(R.mipmap.welcome_old);
        }
        PrefUtils.setDefaultPrefHeader(ran);

        tvWelcome.setSpacing(8);
        tvWelcome.setText("欢迎您");
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/fangyasong.ttf");
        tvWelcome.setTypeface(typeface);

        ivBadge.setVisibility(View.VISIBLE);
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.20f, 1, 0.20f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, deltaX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, deltaY);
        animationSet.setDuration(1000);
        animationSet.setStartOffset(1000);
        animationSet.setFillAfter(true);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);
        ivBadge.setAnimation(animationSet);

        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        animation.setStartOffset(1000);
        tvCN1.setAnimation(animation);
        tvCN2.setAnimation(animation);
        tvEG.setAnimation(animation);
        animation.start();

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setStartOffset(2000);
        ivBackground.setAnimation(alphaAnimation);
        ivLogoRight.setAnimation(alphaAnimation);
        tvWelcome.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }

}
