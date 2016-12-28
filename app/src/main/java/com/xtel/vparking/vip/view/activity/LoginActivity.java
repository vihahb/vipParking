package com.xtel.vparking.vip.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.accountkit.ui.AccountKitActivity;
import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.presenter.LoginPresenter;
import com.xtel.vparking.vip.utils.PermissionHelper;
import com.xtel.vparking.vip.view.activity.inf.LoginView;

/**
 * Created by vivhp on 12/8/2016.
 */

public class LoginActivity extends BasicActivity implements LoginView, View.OnClickListener {

    private final int MY_REQUEST_CODE = 1001;
    private final int FB_REQUEST_CODE = 1003;
    String[] PermissionListAccKit = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE};
    String[] PermissionListFb = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private Button btn_signin, btn_Facebook_login;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new LoginPresenter(this);
        presenter.createCallBackManager(this);
        initView();
    }

    private void initView() {
        btn_signin = (Button) findViewById(R.id.btn_Signin);
        btn_Facebook_login = (Button) findViewById(R.id.btn_fb_signin);
        btn_Facebook_login.setOnClickListener(this);
        btn_signin.setOnClickListener(this);
    }

    @Override
    public void onLoginSuccess() {
    }

    @Override
    public void onLoginErrors(Error error) {
    }

    @Override
    public void startActivity(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    protected void startActivityForResult(Class clazz, int requestCode) {
        super.startActivityForResult(clazz, requestCode);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showProgressBar(boolean isTouchOutside, boolean isCancel, String title, String message) {
        super.showProgressBar(isTouchOutside, isCancel, title, message);
    }

    @Override
    public void closeProgressBar() {
        super.closeProgressBar();
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_fb_signin) {
            checkNetWork(LoginActivity.this, 1);
        } else if (id == R.id.btn_Signin) {
            checkNetWork(LoginActivity.this, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_REQUEST_CODE) {
            Log.e("size permission:", String.valueOf(grantResults.length));

            boolean chk = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    chk = false;
                    break;
                }
            }

            if (chk) {
                presenter.getDeviceData(getApplicationContext());
                presenter.initOnLoginAccountKit(this, AccountKitActivity.class);
                showShortToast(getActivity().getString(R.string.permission_checked));
            } else
                showShortToast(getActivity().getString(R.string.permission_not_check));
        } else if (requestCode == FB_REQUEST_CODE) {
            Log.e("size permission:", String.valueOf(grantResults.length));

            boolean chk_fb = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    chk_fb = false;
                    break;
                }
            }

            if (chk_fb) {
                presenter.getDeviceData(this);
                presenter.initOnLoginFacebook(this);
                showShortToast(getActivity().getString(R.string.permission_checked));
            } else
                showShortToast(getActivity().getString(R.string.permission_not_check));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.requestCallbackManager(requestCode, resultCode, data);
        presenter.requestAccountKitResult(requestCode, resultCode, data, this);
    }

    @Override
    protected void onDestroy() {
        presenter.stopTracking();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        presenter.showConfirmExitApp();
    }

    private void checkNetWork(final Context context, int type) {
        if (!NetWorkInfo.isOnline(context)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.TimePicker);
            dialog.setTitle("Kết nối không thành công");
            dialog.setMessage("Rất tiếc, không thể kết nối internet. Vui lòng kiểm tra kết nối Internet.");
            dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            dialog.show();
        } else {
            if (type == 1) {
                if (PermissionHelper.checkListPermission(PermissionListFb, this, FB_REQUEST_CODE)) {
                    Log.v("acc", "fb");
                    presenter.initOnLoginFacebook(this);
                }
            } else if (type == 2){
                if (PermissionHelper.checkListPermission(PermissionListAccKit, this, MY_REQUEST_CODE)) {
                    Log.v("acc", "kit");
                    presenter.initOnLoginAccountKit(this, AccountKitActivity.class);
                }
            }
        }
    }
}
