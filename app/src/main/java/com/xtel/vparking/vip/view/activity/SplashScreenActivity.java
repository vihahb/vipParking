package com.xtel.vparking.vip.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.utils.SharedPreferencesUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferencesUtils.getInstance().getStringValue(Constants.USER_AUTH_ID) != null
                        && SharedPreferencesUtils.getInstance().getStringValue(Constants.USER_SESSION) != null) {
                    Log.e("auth_id", SharedPreferencesUtils.getInstance().getStringValue(Constants.USER_AUTH_ID));
                    Log.e("auth_session", SharedPreferencesUtils.getInstance().getStringValue(Constants.USER_SESSION));
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 1000);
    }

}
