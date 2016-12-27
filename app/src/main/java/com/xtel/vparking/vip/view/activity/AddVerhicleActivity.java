package com.xtel.vparking.vip.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.entity.Brandname;
import com.xtel.vparking.vip.model.entity.Verhicle;
import com.xtel.vparking.vip.presenter.AddVerhiclePresenter;
import com.xtel.vparking.vip.view.activity.inf.AddVerhicleView;
import com.xtel.vparking.vip.view.adapter.CustomAddVerhicleAdapterSpinner;
import com.xtel.vparking.vip.view.fragment.VerhicleFragment;

import java.util.ArrayList;

/**
 * Created by vivhp on 12/9/2016.
 */

public class AddVerhicleActivity extends BasicActivity implements AdapterView.OnItemSelectedListener, AddVerhicleView {
    private Toolbar toolbar;
    private Menu menu;
    private EditText edt_verhicle_name, edt_verhicle_plate, edt_verhicle_descriptions;
    private Spinner sp_verhicle_brandname;
    private CheckBox chk_verhicle_default;
    private Button btn_verhicle_add, btn_vehicle_update;
    private RadioGroup radioGroup;
    private RadioButton rdo_oto, rdo_xemay;
    AddVerhiclePresenter verhiclePresenter;
    String mess;
    int getId;
    String brand_code;
    Verhicle verhicle;

    //Update
    String v_name, v_plate, v_des;
    int v_type, v_flag;

    private ArrayList<Brandname> brandNames_arr;
    private CustomAddVerhicleAdapterSpinner adapter_spinner_brand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_verhicle);
        verhiclePresenter = new AddVerhiclePresenter(this);
        initToolbar();
        initView();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_add_verhicle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initView() {
        edt_verhicle_name = (EditText) findViewById(R.id.edt_verhicle_name);
        edt_verhicle_plate = (EditText) findViewById(R.id.edt_verhicle_plate);
        edt_verhicle_descriptions = (EditText) findViewById(R.id.edt_verhicle_des);
        sp_verhicle_brandname = (Spinner) findViewById(R.id.spinner_brandname);
        chk_verhicle_default = (CheckBox) findViewById(R.id.chk_verhicle_default);
        btn_verhicle_add = (Button) findViewById(R.id.btn_verhicle_add);
        btn_vehicle_update = (Button) findViewById(R.id.btn_verhicle_update);
        radioGroup = (RadioGroup) findViewById(R.id.rdo_group);
        rdo_oto = (RadioButton) findViewById(R.id.rdo_oto);
        rdo_xemay = (RadioButton) findViewById(R.id.rdo_xemay);
        brandNames_arr = new ArrayList<Brandname>();
        verhiclePresenter.getVerhicleBrandname();
        getDataFromManager();
        OnClickButton();
    }

    private void initSpinnerBrandname() {
        adapter_spinner_brand = new CustomAddVerhicleAdapterSpinner(this, R.layout.custom_spinner_addverhicle, brandNames_arr);
        adapter_spinner_brand.setDropDownViewResource(R.layout.custom_spinner_addverhicle);
        sp_verhicle_brandname.setAdapter(adapter_spinner_brand);
        sp_verhicle_brandname.setOnItemSelectedListener(this);

        String brand_code = verhicle.getBrandname().getCode();
        for (int i = 0; i < brandNames_arr.size(); i++) {
            if (brand_code.equals(brandNames_arr.get(i).getCode())) {
                sp_verhicle_brandname.setSelection(i);
            }
        }
    }

    private void OnClickButton() {
        if (verhicle != null) {
            btn_verhicle_add.setVisibility(View.GONE);
            btn_vehicle_update.setVisibility(View.VISIBLE);
        }
        btn_verhicle_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetWork(AddVerhicleActivity.this, 1);
            }
        });

        btn_vehicle_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetWork(AddVerhicleActivity.this, 2);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int id_spinner = parent.getId();
        if (id_spinner == R.id.spinner_brandname) {
            brand_code = adapter_spinner_brand.getItem(position).getCode().toString();
            showShortToast(brand_code);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAddVerhicleSuccess() {

    }

    @Override
    public void onAddVerhicleError() {

    }

    @Override
    public void onGetBrandnameData(ArrayList<Brandname> brandNames) {
        brandNames_arr = getBrandNames_arr(brandNames);
        initSpinnerBrandname();
    }

    private ArrayList<Brandname> getBrandNames_arr(ArrayList<Brandname> arrayList) {
        ArrayList<Brandname> brandnames = new ArrayList<Brandname>();
        if (arrayList != null) {
            int total = arrayList.size();
            for (int i = 0; i < total; i++) {
                brandnames.add(i, arrayList.get(i));
            }
        }

        return brandnames;
    }

    private boolean valid() {
        if (edt_verhicle_name.getText().toString().isEmpty()) {
            mess = this.getString(R.string.check_verhicle_name);
            return false;
        } else if (edt_verhicle_plate.getText().toString().isEmpty() || edt_verhicle_plate.getText().toString() == "") {
            mess = this.getString(R.string.check_verhicle_plate);
            return false;
        }

        return true;
    }

    private int getVerhicleType() {
        int type = 0;
        int id = radioGroup.getCheckedRadioButtonId();
        if (id != -1) {
            if (id == R.id.rdo_oto) {
                type = 1;
            } else if (id == R.id.rdo_xemay) {
                type = 2;
            }
        } else
            type = 1;
        return type;
    }

    private int getFlagDefault() {
        int flag = 0;
        if (chk_verhicle_default.isChecked()) {
            flag = 1;
        } else
            flag = 0;

        return flag;
    }

    private void addVerhicle() {
        if (valid()) {
            v_name = edt_verhicle_name.getText().toString();
            v_plate = edt_verhicle_plate.getText().toString();
            if (edt_verhicle_descriptions.getText().toString().isEmpty() || edt_verhicle_descriptions.getText().toString() == "") {
                v_des = "Không có mô tả";
            } else {
                v_des = edt_verhicle_descriptions.getText().toString();
            }
            v_type = getVerhicleType();
            v_flag = getFlagDefault();
            verhiclePresenter.addVerhicle(v_name, v_plate, v_des, v_type, v_flag, brand_code);
        } else
            showShortToast(mess);

    }

    private void getDataFromManager() {
        if (validVerhicleModel()) {
            getSupportActionBar().setTitle(getActivity().getString(R.string.verhicle_update));
            final int id = verhicle.getId();
            final String name = verhicle.getName();
            final String plate_num = verhicle.getPlate_number();
            final String descriptions = verhicle.getDesc();
            final int type = verhicle.getType();
            final int flag_default = verhicle.getFlag_default();
            final String code = verhicle.getBrandname().getCode();
            fill_DataUpdate(id, name, descriptions, plate_num, type, flag_default, code);
        }
    }

    private void fill_DataUpdate(int id, String name, String desc, String plate_number, int type, int flag_default, String code) {
        btn_verhicle_add.setText(this.getString(R.string.update_btn));
        edt_verhicle_name.setText(name);
        edt_verhicle_descriptions.setText(desc);
        edt_verhicle_plate.setText(plate_number);
        getId = id;
        if (type == 1) {
            rdo_oto.setChecked(true);
        } else
            rdo_xemay.setChecked(true);

        if (flag_default == 1) {
            chk_verhicle_default.setChecked(true);
        }
    }

    private void updateVerhicle() {
        String update_name = edt_verhicle_name.getText().toString();
        String update_desc = edt_verhicle_descriptions.getText().toString();
        String update_plate = edt_verhicle_plate.getText().toString();
        int update_type = getVerhicleType();
        int update_flag = getFlagDefault();
        String update_code = brand_code;
        putData2Server(getId, update_name, update_plate, update_desc, update_type, update_flag, update_code);
    }

    private void putData2Server(int id, String name, String plate_number, String desc, int type, int flag_default, String code) {
        verhiclePresenter.updateVerhicle(id, name, plate_number, desc, type, flag_default, code);
    }

    private boolean validVerhicleModel() {
        try {
            verhicle = (Verhicle) getIntent().getSerializableExtra(Constants.VERHICLE_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (verhicle != null) {
            return true;
        }
        return false;
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public void putExtra(String key, int id) {
        Intent intent = new Intent();
        intent.putExtra(key, id);
        setResult(VerhicleFragment.RESULT_ADD_VERHICLE, intent);
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.nav_update_verhicle) {
            checkNetWork(AddVerhicleActivity.this, 2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update, menu);
        this.menu = menu;
        MenuItem itemUpdate = menu.findItem(R.id.nav_update_verhicle);
        if (validVerhicleModel()) {
            itemUpdate.setVisible(false);
        }

        return true;
    }

    private void checkNetWork(final Context context, int type){
        if (!NetWorkInfo.isOnline(context)){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.TimePicker);
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
                }
            });
            dialog.show();
        } else {
            if (type == 1){
                addVerhicle();
            } else if (type == 2){
                updateVerhicle();
            }
        }
    }
}
