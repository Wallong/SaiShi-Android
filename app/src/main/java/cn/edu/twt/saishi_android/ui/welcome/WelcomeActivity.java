package cn.edu.twt.saishi_android.ui.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import android.os.Handler;

import cn.edu.twt.saishi_android.R;
import cn.edu.twt.saishi_android.support.ExitApplication;
import cn.edu.twt.saishi_android.support.PrefUtils;
import cn.edu.twt.saishi_android.ui.login.LoginActivity;
import cn.edu.twt.saishi_android.ui.main.MainActivity;

/**
 * Created by clifton on 16-2-19.
 */
public class WelcomeActivity extends Activity{
    private ImageView imageView_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ExitApplication.getInstance().addActivity(this);
        imageView_welcome = (ImageView)findViewById(R.id.iv_welcome);
        imageView_welcome.setImageResource(R.mipmap.welcome);
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
        }, 2000);
    }

}
