package com.xtel.vparking.vip.presenter;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TimePicker;

import com.google.gson.JsonObject;
import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.callback.ICmd;
import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.callback.RequestWithStringListener;
import com.xtel.vparking.vip.callback.ResponseHandle;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.LoginModel;
import com.xtel.vparking.vip.model.ParkingModel;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.ParkingInfo;
import com.xtel.vparking.vip.model.entity.Pictures;
import com.xtel.vparking.vip.model.entity.PlaceModel;
import com.xtel.vparking.vip.model.entity.Prices;
import com.xtel.vparking.vip.model.entity.RESP_Parking_Info;
import com.xtel.vparking.vip.utils.JsonHelper;
import com.xtel.vparking.vip.utils.Task;
import com.xtel.vparking.vip.view.activity.inf.AddParkingView;
import com.xtel.vparking.vip.view.fragment.ManagementFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Lê Công Long Vũ on 12/2/2016
 */

public class AddParkingPresenter extends BasicPresenter {
    private AddParkingView view;
    private ParkingInfo object;
    private int picture_id = -1;
    private boolean isUpdate = false;

    private ICmd cmd = new ICmd() {
        @Override
        public void execute(Object... params) {
            ParkingModel.getInstanse().deleteParkingPicrute(picture_id, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
                @Override
                public void onSuccess(RESP_Parking_Info obj) {
                    view.onDeletePictureSuccess();
                }

                @Override
                public void onError(Error error) {
                    if (error.getCode() == 2)
                        getNewSession(view.getActivity(), cmd);
                    else
                        view.onDeletePictureError(error);
                }
            });
        }
    };

    public AddParkingPresenter(AddParkingView view) {
        this.view = view;
    }

    public void getData() {
        try {
            object = (ParkingInfo) view.getActivity().getIntent().getSerializableExtra(Constants.PARKING_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (object != null) {
            isUpdate = true;
            view.onGetDataSuccess(object);
        }
    }

    public void postImage(Bitmap bitmap) {
        new Task.ConvertImage(view.getActivity(), true, new RequestWithStringListener() {
            @Override
            public void onSuccess(String url) {
                if (!isUpdate)
                    view.onPostPictureSuccess(url);
                else {
                    object.getPictures().clear();
                    object.getPictures().add(new Pictures(-1, url));
                    addPicture();
                }
            }

            @Override
            public void onError() {
                view.onPostPictureError("Xảy ra lỗi. Vui lòng thử lại");
            }
        }).execute(bitmap);
    }

    public void deletePicture(int id) {
        if (id != -1) {
            picture_id = id;
            cmd.execute();
        } else {
            view.onDeletePictureSuccess();
        }
    }

    private void addPicture() {
        ParkingModel.getInstanse().addPicture(JsonHelper.toJson(object), new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                view.onAddPictureSuccess(object.getPictures().get((object.getPictures().size() - 1)).getUrl());
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionAddPicture();
                else
                    view.onAddPictureError(error);
            }
        });
    }

    private void getNewSessionAddPicture() {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                addPicture();
            }

            @Override
            public void onError() {
                view.onAddPictureError(new Error(2, "", view.getActivity().getString(R.string.error)));
            }
        });
    }

    public void deletePrice(final int position, final int id) {
        view.showProgressBar(false, false, null, "Deleting price...");
        ParkingModel.getInstanse().deleteParkingPrice(id, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                view.onDeletePriceSuccess(position);

                for (int i = object.getPrices().size() - 1; i >= 0; i--) {
                    if (object.getPrices().get(i).getId() == id) {
                        object.getPrices().remove(i);
                        return;
                    }
                }
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionDeleteParkingPrices(position, id);
                else
                    view.onDeletePriceError(error);
            }
        });
    }

    private void getNewSessionDeleteParkingPrices(final int position, final int id) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                deletePrice(position, id);
            }

            @Override
            public void onError() {
                view.onDeletePriceError(new Error(2, "", view.getActivity().getString(R.string.error_session_invalid)));
            }
        });
    }

    public void validateData(View _view, ArrayList<Pictures> arrayList_picture, String parking_name, String parking_address, PlaceModel placeModel,
                             int transport_type, String total_place, String phone, String begin_time, String end_time,
                             ArrayList<Prices> arrayList_price) {

        if (arrayList_picture.size() == 0) {
            view.onValidateError(_view, view.getActivity().getString(R.string.loi_chonanh));
        } else if (parking_name.isEmpty()) {
            view.onValidateError(_view, view.getActivity().getString(R.string.loi_nhapten));
        } else if (placeModel == null) {
            view.onValidateError(_view, view.getActivity().getString(R.string.error_pick_address));
        } else if (parking_address.isEmpty()) {
            view.onValidateError(_view, view.getActivity().getString(R.string.error_input_address));
        } else if (transport_type == 0) {
            view.onValidateError(_view, view.getActivity().getString(R.string.error_choose_type_of_verhicle));
        } else if (checkNumberInput(total_place) <= 0) {
            view.onValidateError(_view, view.getActivity().getString(R.string.loi_chotrong));
        }
//        else if (phone.isEmpty()) {
//            view.onValidateError(_view, view.getActivity().getString(R.string.loi_phone_empty));
//        }
        else if (phone.length() < 10 || phone.length() > 11) {
            view.onValidateError(_view, view.getActivity().getString(R.string.loi_phone));
        } else if (!checkListPrice(arrayList_price)) {
            view.onValidateError(_view, view.getActivity().getString(R.string.error_choose_money_price));
        } else {
            if (!NetWorkInfo.isOnline(view.getActivity())) {
                view.onValidateError(_view, view.getActivity().getString(R.string.no_internet));
                return;
            }

            if (!isUpdate) {
                view.showProgressBar(false, false, null, view.getActivity().getString(R.string.adding));

                object = new ParkingInfo();
                object.setLat(placeModel.getLatitude());
                object.setLng(placeModel.getLongtitude());
                object.setType(transport_type);
                object.setAddress(parking_address);
                object.setParking_name(parking_name);
                object.setParking_phone(phone);

                if (!begin_time.isEmpty())
                    object.setBegin_time(begin_time);
                if (!end_time.isEmpty())
                    object.setEnd_time(end_time);

                object.setTotal_place(total_place);
                object.setEmpty_number(total_place);
                object.setPrices(arrayList_price);
                object.setPictures(arrayList_picture);

                addParking();
            } else {
                view.showProgressBar(false, false, null, view.getActivity().getString(R.string.updating));

                object.setLat(placeModel.getLatitude());
                object.setLng(placeModel.getLongtitude());
                object.setType(transport_type);
                object.setAddress(placeModel.getAddress());
                object.setParking_name(parking_name);
                object.setParking_phone(phone);

                if (!begin_time.isEmpty())
                    object.setBegin_time(begin_time);
                if (!end_time.isEmpty())
                    object.setEnd_time(end_time);

                object.setTotal_place(total_place);
                object.setEmpty_number(total_place);

                ArrayList<Prices> all_price = object.getPrices();
                object.setPrices(arrayList_price);

                deleteAllPrice(all_price);
            }
        }
    }

    private boolean checkListPrice(ArrayList<Prices> arrayList_price) {
        for (int i = (arrayList_price.size() - 1); i >= 0; i--) {
            if (arrayList_price.get(i).getPrice() == 0)
                return false;
        }
        return true;
    }

    private int checkNumberInput(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void addParking() {
        String url = Constants.SERVER_PARKING + Constants.PARKING_ADD_PARKING;
        String jsonObject = JsonHelper.toJson(object);
        String session = LoginModel.getInstance().getSession();

        ParkingModel.getInstanse().addParking(url, jsonObject, session, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                view.onAddParkingSuccess(obj.getId());
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionAddParking();
                else {
                    if (!isUpdate)
                        object = null;
                    view.onAddParkingError(error);
                }
            }
        });
    }

    private void getNewSessionAddParking() {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                addParking();
            }

            @Override
            public void onError() {
                if (!isUpdate)
                    object = null;
                view.onAddParkingError(new Error(2, "", view.getActivity().getString(R.string.error_session_invalid)));
            }
        });
    }

    private void deleteAllPrice(final ArrayList<Prices> arrayList) {
        if (arrayList.size() > 0) {
            ParkingModel.getInstanse().deleteParkingPrice(arrayList.get(0).getId(), new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
                @Override
                public void onSuccess(RESP_Parking_Info obj) {
                    arrayList.remove(0);
                    deleteAllPrice(arrayList);
                }

                @Override
                public void onError(Error error) {
                    if (error.getCode() == 2)
                        getNewSessionDeleteAllPrice(arrayList);
                    else
                        view.onUpdateParkingError(error);
                }
            });
        } else
            addAllPrice();
    }

    private void getNewSessionDeleteAllPrice(final ArrayList<Prices> arrayList) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                deleteAllPrice(arrayList);
            }

            @Override
            public void onError() {
                view.onUpdateParkingError(new Error(2, "", view.getActivity().getString(R.string.error_session_invalid)));
            }
        });
    }

    private void addAllPrice() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", object.getId());
        jsonObject.addProperty("prices", JsonHelper.toJson(object.getPrices()));

        ParkingModel.getInstanse().addPPrices(JsonHelper.toJson(object), new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                updateParking();
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionAddPrice();
                else
                    view.onUpdateParkingError(error);
            }
        });
    }

    private void getNewSessionAddPrice() {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                addAllPrice();
            }

            @Override
            public void onError() {
                view.onUpdateParkingError(new Error(2, "", view.getActivity().getString(R.string.error_session_invalid)));
            }
        });
    }

    private void updateParking() {
        String url = Constants.SERVER_PARKING + Constants.PARKING_ADD_PARKING;
        String jsonObject = JsonHelper.toJson(object);
        String session = LoginModel.getInstance().getSession();

        ParkingModel.getInstanse().updateParking(url, jsonObject, session, new ResponseHandle<RESP_Parking_Info>(RESP_Parking_Info.class) {
            @Override
            public void onSuccess(RESP_Parking_Info obj) {
                view.onUpdateParkingSuccess(object);
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2)
                    getNewSessionUpdateParking();
                else
                    view.onAddParkingError(error);
            }
        });
    }

    private void getNewSessionUpdateParking() {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                addParking();
            }

            @Override
            public void onError() {
                view.onAddParkingError(new Error(2, "", view.getActivity().getString(R.string.error_session_invalid)));
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    public void getTime(final boolean isBegin) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(view.getActivity(), R.style.TimePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                view.onGetTimeSuccess(isBegin, getHour(selectedHour), getMinute(selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time.
        mTimePicker.show();
    }

    private String getHour(int hour) {
        if (hour < 10)
            return "0" + hour;
        else
            return String.valueOf(hour);
    }

    private String getMinute(int minute) {
        if (minute < 10)
            return "0" + minute;
        else
            return String.valueOf(minute);
    }

    @Override
    public void getSessionError() {
        view.onDeletePictureError(new Error(2, "", view.getActivity().getString(R.string.error_session_invalid)));
    }

    public void backToManagement(ArrayList<Pictures> picturesArrayList, ArrayList<Prices> pricesArrayList) {
        if (object != null) {
            object.getPictures().clear();
            object.getPictures().addAll(picturesArrayList);

            Intent intent = new Intent();
            intent.putExtra(ManagementFragment.PARKING_MODEL, object);
            view.getActivity().setResult(ManagementFragment.RESULT_UPDATE, intent);
        }

        view.getActivity().finish();
    }
}