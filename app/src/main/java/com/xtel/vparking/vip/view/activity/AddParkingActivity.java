package com.xtel.vparking.vip.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.ParkingInfo;
import com.xtel.vparking.vip.model.entity.Pictures;
import com.xtel.vparking.vip.model.entity.PlaceModel;
import com.xtel.vparking.vip.model.entity.Prices;
import com.xtel.vparking.vip.presenter.AddParkingPresenter;
import com.xtel.vparking.vip.utils.JsonParse;
import com.xtel.vparking.vip.utils.PermissionHelper;
import com.xtel.vparking.vip.view.activity.inf.AddParkingView;
import com.xtel.vparking.vip.view.adapter.AddParkingAdapter;
import com.xtel.vparking.vip.view.adapter.PriceAdapter;
import com.xtel.vparking.vip.view.fragment.ManagementFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 11/28/2016
 */

public class AddParkingActivity extends BasicActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, AddParkingView {
    private AddParkingPresenter presenter;

    private ImageButton img_pick_map;
    private TextView txt_image_number;
    private EditText edt_parking_name, edt_address, edt_place_number, edt_parking_phone, edt_begin_time, edt_end_time;
    private Spinner sp_transport_type;

    private PriceAdapter priceAdapter;
    private ArrayList<Prices> arrayList_price;

    private ViewPager viewPager;
    private ImageView img_load, img_delete;

    private PlaceModel placeModel;
    private ArrayList<Pictures> arrayList_picture;
    private Button btn_action;

    public static final String MODEL_FIND = "model_find";
    public static final int REQUEST_LOCATION = 88, RESULT_LOCATION = 66, REQUEST_PHONE = 100, REQUEST_CAMERA = 10001;
    private String[] permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);

        presenter = new AddParkingPresenter(this);
        initToolbar(R.id.toolbar_add_parking, null);
        initWidger();
        initSpinner();
        initRecyclerview();
        initListener();
        initViewPager();
        presenter.getData();
    }

    private void initWidger() {
        txt_image_number = (TextView) findViewById(R.id.txt_add_parking_image_number);

        img_pick_map = (ImageButton) findViewById(R.id.img_add_parking_pick_map);
        img_delete = (ImageView) findViewById(R.id.img_add_parking_delete);
        img_load = (ImageView) findViewById(R.id.img_add_parking_picture);

        edt_parking_name = (EditText) findViewById(R.id.edt_add_parking_name);
        edt_place_number = (EditText) findViewById(R.id.edt_add_parking_empty);
        edt_parking_phone = (EditText) findViewById(R.id.edt_add_parking_phone);
        edt_address = (EditText) findViewById(R.id.edt_add_parking_diachi);
        edt_begin_time = (EditText) findViewById(R.id.edt_add_parking_begin_time);
        edt_end_time = (EditText) findViewById(R.id.edt_add_parking_end_time);

        viewPager = (ViewPager) findViewById(R.id.viewpager_add_parking);
        btn_action = (Button) findViewById(R.id.btn_add_parking);
    }

    private void initSpinner() {
        sp_transport_type = (Spinner) findViewById(R.id.sp_add_parking_type);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner_normal,
                getResources().getStringArray(R.array.add_transport));
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown_item);
        sp_transport_type.setAdapter(arrayAdapter);
        sp_transport_type.setSelection(5);
    }

    private void initRecyclerview() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_add_parking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        arrayList_price = new ArrayList<>();
        arrayList_price.add(new Prices(-1, 0, 1, 3));
        priceAdapter = new PriceAdapter(getApplicationContext(), arrayList_price, presenter, true);
        recyclerView.setAdapter(priceAdapter);
    }

    private void initListener() {
        img_pick_map.setOnClickListener(this);
        edt_parking_phone.setOnClickListener(this);
        edt_begin_time.setOnClickListener(this);
        edt_end_time.setOnClickListener(this);
    }

    private void initViewPager() {
        arrayList_picture = new ArrayList<>();
        AddParkingAdapter viewImageAdapter = new AddParkingAdapter(getSupportFragmentManager(), arrayList_picture);
        viewPager.setAdapter(viewImageAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    public void TakePicture(View view) {
        if (!PermissionHelper.checkListPermission(permission, this, REQUEST_CAMERA))
            return;

        takePicrute();
    }

    private void takePicrute() {
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        //Create any other intents you want
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Add them to an intent array
        Intent[] intents = new Intent[]{cameraIntent};

        //Create a choose from your first intent then pass in the intent array
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Chọn ảnh");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        startActivityForResult(chooserIntent, 101);
    }

    public void DeletePicture(View view) {
        if (arrayList_picture.size() > 0) {
            showProgressBar(false, false, null, "Đang xóa file...");
            presenter.deletePicture((int) arrayList_picture.get(viewPager.getCurrentItem()).getId());
        }
    }

    public void addParking(View view) {
        presenter.validateData(view, arrayList_picture, edt_parking_name.getText().toString(), edt_address.getText().toString(), placeModel,
                (sp_transport_type.getSelectedItemPosition() + 1), edt_place_number.getText().toString(), edt_parking_phone.getText().toString(),
                edt_begin_time.getText().toString(), edt_end_time.getText().toString(), arrayList_price);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_add_parking_pick_map) {
            if (PermissionHelper.checkOnlyPermission(Manifest.permission.ACCESS_FINE_LOCATION, this, REQUEST_LOCATION))
                startActivityForResult(ChooseMapsActivity.class, MODEL_FIND, placeModel, REQUEST_LOCATION);
        } else if (id == R.id.edt_add_parking_begin_time) {
            presenter.getTime(true);
        } else if (id == R.id.edt_add_parking_end_time) {
            presenter.getTime(false);
        }
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void showProgressBar(boolean isTouchOutside, boolean isCancel, String title, String message) {
        super.showProgressBar(isTouchOutside, isCancel, title, message);
    }

    @Override
    public void onGetDataSuccess(ParkingInfo object) {
        if (placeModel == null) {
            placeModel = new PlaceModel(object.getAddress(), object.getLat(), object.getLng());
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.title_activity_update_parking));
        }

        edt_address.setText(placeModel.getAddress());
        edt_parking_name.setText(object.getParking_name());
        sp_transport_type.setSelection((int) (object.getType() - 1));
        edt_place_number.setText(object.getTotal_place());
        edt_begin_time.setText(object.getBegin_time());
        edt_end_time.setText(object.getEnd_time());
        edt_parking_phone.setText(object.getParking_phone());

        arrayList_price.clear();
        arrayList_price.addAll(object.getPrices());
        if (arrayList_price.size() == 0)
            arrayList_price.add(new Prices(-1, 0, 1, 3));

        priceAdapter.setSetDefault(false);
        priceAdapter.notifyDataSetChanged();

        arrayList_picture.addAll(object.getPictures());
        viewPager.getAdapter().notifyDataSetChanged();

        String img_position = "";
        if (arrayList_picture.size() > 0) {
            img_position = (viewPager.getCurrentItem() + 1) + "/" + arrayList_picture.size();
            img_delete.setVisibility(View.VISIBLE);
            txt_image_number.setVisibility(View.VISIBLE);
        } else {
            img_delete.setVisibility(View.GONE);
            txt_image_number.setVisibility(View.GONE);
        }
        txt_image_number.setText(img_position);

        btn_action.setText(getString(R.string.update));
    }

    private void onTakePictureGallary(Uri uri) {
        if (!NetWorkInfo.isOnline(this)) {
            showShortToast(getString(R.string.no_internet));
            return;
        } else if (uri == null) {
            showShortToast("Không thể lấy ảnh");
            return;
        }

        showProgressBar(false, false, null, "Đang tải file...");

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        presenter.postImage(bitmap);
    }

    private void onTakePictureCamera(Bitmap bitmap) {
        if (!NetWorkInfo.isOnline(this)) {
            showShortToast(getString(R.string.no_internet));
            return;
        } else if (bitmap == null) {
            showShortToast("Không thể lấy ảnh");
            return;
        }

        showProgressBar(false, false, null, "Đang tải file...");

        presenter.postImage(bitmap);
    }

    @Override
    public void onPostPictureSuccess(String url) {
        arrayList_picture.add(new Pictures(-1, url));
        viewPager.getAdapter().notifyDataSetChanged();

        if (arrayList_picture.size() > 0) {
            if (img_delete.getVisibility() == View.GONE)
                img_delete.setVisibility(View.VISIBLE);
            if (txt_image_number.getVisibility() == View.GONE)
                txt_image_number.setVisibility(View.VISIBLE);
        }

        String text = (viewPager.getCurrentItem() + 1) + "/" + arrayList_picture.size();
        txt_image_number.setText(text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img_load.setImageResource(R.mipmap.ic_parking_background);
            }
        }, 1000);

        closeProgressBar();
    }

    @Override
    public void onPostPictureError(String error) {
        closeProgressBar();
        showShortToast(error);
        img_load.setImageResource(R.mipmap.ic_parking_background);
    }

    @Override
    public void onAddPictureSuccess(String url) {
        arrayList_picture.add(new Pictures(-1, url));
        viewPager.getAdapter().notifyDataSetChanged();

        if (arrayList_picture.size() > 0) {
            if (img_delete.getVisibility() == View.GONE)
                img_delete.setVisibility(View.VISIBLE);
            if (txt_image_number.getVisibility() == View.GONE)
                txt_image_number.setVisibility(View.VISIBLE);
        }

        String text = (viewPager.getCurrentItem() + 1) + "/" + arrayList_picture.size();
        txt_image_number.setText(text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img_load.setImageResource(R.mipmap.ic_parking_background);
            }
        }, 1000);

        closeProgressBar();
    }

    @Override
    public void onAddPictureError(Error error) {
        closeProgressBar();
        showShortToast(JsonParse.getCodeMessage(error.getCode(), getString(R.string.error)));
        img_load.setImageResource(R.mipmap.ic_parking_background);
    }

    @Override
    public void onDeletePictureSuccess() {
        closeProgressBar();
        arrayList_picture.remove(viewPager.getCurrentItem());

        String img_position = "";
        if (arrayList_picture.size() > 0)
            img_position = (viewPager.getCurrentItem() + 1) + "/" + arrayList_picture.size();
        else {
            img_delete.setVisibility(View.GONE);
            txt_image_number.setVisibility(View.GONE);
        }
        txt_image_number.setText(img_position);

        viewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDeletePictureError(Error error) {
        closeProgressBar();
        showShortToast(JsonParse.getCodeMessage(error.getCode(), getString(R.string.error)));
    }

    @Override
    public void onDeletePriceSuccess(int position) {
        closeProgressBar();
        priceAdapter.deleteItem(position);
    }

    @Override
    public void onDeletePriceError(Error error) {
        closeProgressBar();
        showShortToast(JsonParse.getCodeMessage(error.getCode(), getString(R.string.error)));
    }

    @Override
    public void onGetTimeSuccess(boolean isBegin, String hour, String minute) {
        if (isBegin)
            edt_begin_time.setText(hour + ":" + minute);
        else
            edt_end_time.setText(hour + ":" + minute);
    }

    @Override
    public void onAddParkingSuccess(final int id) {
        closeProgressBar();
        showDialog(false, false, "THÔNG BÁO", "Thêm bãi đỗ thành công", "OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                try {
                    intent.putExtra(Constants.INTENT_PARKING_ID, id);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                setResult(99, intent);
                finish();
            }
        });
    }

    @Override
    public void onUpdateParkingSuccess(final ParkingInfo parkingInfo) {
        closeProgressBar();
        showDialog(false, false, "THÔNG BÁO", "Cập nhật bãi đỗ thành công", "OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                try {
                    intent.putExtra(ManagementFragment.PARKING_MODEL, parkingInfo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                setResult(ManagementFragment.RESULT_UPDATE, intent);
                finish();
            }
        });
    }

    @Override
    public void onAddParkingError(Error error) {
        closeProgressBar();
        showShortToast(JsonParse.getCodeMessage(error.getCode(), getString(R.string.loi_addparking)));
    }

    @Override
    public void onUpdateParkingError(Error error) {
        closeProgressBar();
        showShortToast(JsonParse.getCodeMessage(error.getCode(), getString(R.string.error_updating)));
    }

    @Override
    public void onValidateError(View view, String error) {
        closeProgressBar();
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startActivityForResult(Intent intent) {
        startActivityForResult(intent, REQUEST_PHONE);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (arrayList_picture.size() > 0) {
            String text = (position + 1) + "/" + arrayList_picture.size();
            txt_image_number.setText(text);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        presenter.backToManagement(arrayList_picture, arrayList_price);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.backToManagement(arrayList_picture, arrayList_price);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startActivityForResult(ChooseMapsActivity.class, MODEL_FIND, placeModel, REQUEST_LOCATION);
            else
                showShortToast(getString(R.string.error_permission));
        } else if (requestCode == REQUEST_CAMERA) {
            boolean check = true;
            for (int grantresults : grantResults) {
                if (grantresults == PackageManager.PERMISSION_DENIED) {
                    check = false;
                    break;
                }
            }

            if (check)
                takePicrute();
            else
                showShortToast(getApplicationContext().getString(R.string.error_permission));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                onTakePictureGallary(uri);
            } else {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                onTakePictureCamera(bitmap);
            }
        } else if (requestCode == REQUEST_LOCATION && resultCode == RESULT_LOCATION) {
            if (data != null) {
                placeModel = (PlaceModel) data.getSerializableExtra(MODEL_FIND);
                edt_address.setText(placeModel.getAddress());
            }
        }
    }
}