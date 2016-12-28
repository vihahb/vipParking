package com.xtel.vparking.vip.commons;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.model.entity.PlaceModel;
import com.xtel.vparking.vip.view.MyApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Lê Công Long Vũ on 11/4/2016.
 */

public class Constants {
    public static final String SHARED_USER_NAME = "share_user_name";
    public static final String USER_FACEBOOK_ID = "user_facebook_id";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_AVATAR = "user_avatar";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_BIRTH_DAY = "user_bitrh_day";
    public static final String USER_ADDRESS = "user_address";
    public static final String USER_EMAIL = "user_email";

    public static final String USER_FULL_NAME = "user_full_name";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_SEX = "user_sex";
    public static final String USER_AUTH_ID = "user_auth_id";
    public static final String USER_SESSION = "user_sesstion";
    public static final String USER_LOGIN_TIME = "user_login_time";
    public static final String USER_EXPIRED_TIME = "user_expired_time";
    public static final String USER_DEV_INFO_STATUS = "user_dev_info_status";
    public static final String USER_FLAG = "user_flag";
    public static final String USER_SERVICE_CODE = "user_service_code";
    public static final String USER_ACC_KIT = "user_acc_kit";
    public static final String USER_QR = "user_qr";
    public static final String USER_BAR = "user_bar";

    //Device Info
    public static final String DEVICE_ID = "deviceid";
    public static final String DEVICE_OS_NAME = "os_name";
    public static final String DEVICE_OS_VER = "os_version";
    public static final String DEVICE_OTHER = "other";
    public static final String DEVICE_TYPE = "type";
    public static final String DEVICE_VENDOR = "vendor";


    //    Google map
    public static final String POLY_HTTP = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    public static final String POLY_DESTINATION = "&destination=";

    //Server Authen
    public static final String SERVER_AUTHEN = "http://124.158.5.112:9180/nipum/";
    public static final String AUTHEN_FB_LOGIN = "v1.0/m/user/fb/login";
    public static final String UPDATE_USER = "v1.0/user";
    public static final String AUTHEN_AUTHENTICATE = "v1.0/m/user/authenticate";
    public static final String AUTHEN_ACCOUNT_KIT = "v1.0/m/user/accountkit/login";

    //    Google
    public static final String GET_ADDRESS_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    public static final String GET_ADDRESS_KEY = "&key=";

    //Server home
    public static final String GET_BRANDNAME = "v1.0/verhicle/";
    public static final String GET_FLAG = "v1.0/user/parking/flag";
    public static final String GET_USER = "v1.0/user";
    public static final String SERVER_UPLOAD = "http://124.158.5.112:9180/s/files/upload";
    public static final String SERVER_PARKING = "http://124.158.5.112:9180/p/";
    public static final String PARKING_ACTIVE = "v1.0/user/parking/active";
    public static final String PARKING_FAVORITE = "v1.0/user/favorite";
    public static final String PARKING_FIND = "v1.0/find";
    public static final String PARKING_INFO = "v1.0/info/";
    public static final String PARKING_GET_CHECK_IN_BY_USER = "v1.0/user/checkin";
    public static final String PARKING_VERHICLE = "v1.0/user/verhicle";
    public static final String PARKING_CHECK_IN = "v1.0/user/checkin";
    public static final String PARKING_CHECK_OUT = "v1.0/user/checkout";
    public static final String PARKING_VERHICLE_BY_ID = "v1.0/user/verhicle/";
    public static final String PARKING_ADD_PARKING = "v1.0/admin/parking";
    public static final String PARKING_GET_FAVORITE = "v1.0/user/favorite";
    public static final String PARKING_LAT = "?lat=";
    public static final String PARKING_LNG = "&lng=";
    public static final String PARKING_PRICE = "&price=";
    public static final String PARKING_PRICE_TYPE = "&price_type=";
    public static final String PARKING_TYPE = "&type=";
    public static final String PARKING_BEGIN_TIME = "&begin_time=";
    public static final String PARKING_END_TIME = "&end_time=";
    public static final String PARKING_FLAG = "parking_flag";
    public static final String BRAND_NAME = "brandname";
    public static final String BRANDNAME_VERSION = "?version=";
    public static final String ADD_VERHICLE = "v1.0/user/verhicle";
    public static final String PARKING_GET_CHECKIN_BY_PARKING_ID = "v1.0/admin/parking/";
    public static final String PARKING_DELETE_PICTURE = "v1.0/admin/parking/picture/";
    public static final String PARKING_DELETE_PRICE = "v1.0/admin/parking/price/";
    public static final String PARKING_ADD_PRICES = "v1.0/admin/parking/prices";
    public static final String PARKING_ADD_PICTURE = "v1.0/admin/parking/pictures";
    public static final String PARKING_CHECKIN_BY_PARKING_ID = "v1.0/admin/parking/";
    public static final String PARKING_CHECKIN_PAGE = "/checkin?page=";
    public static final String PARKING_CHECKIN_SIZE = "&pagesize=";
    public static final String HISTORY_CHECK = "v1.0/admin/parking/";
    public static final String HISTORY = "/history";
    public static final String BEGIN_TIME = "?begin=";
    public static final String END_TIME = "&end=";
    public static final String PAGE = "&page=";
    public static final String PAGE_SIZE = "&pagesize=";

    public static final String JSON_ERROR = "error";
    public static final String JSON_MESSAGE = "message";

    public static final String JSON_SESSION = "session";
    public static final String JSON_ID = "id";
    public static final String JSON_UID = "uid";
    public static final String JSON_LAT = "lat";
    public static final String JSON_LNG = "lng";
    public static final String JSON_TYPE = "type";
    public static final String JSON_STATUS = "status";
    public static final String JSON_CODE = "code";
    public static final String JSON_BEGIN_TIME = "begin_time";
    public static final String JSON_END_TIME = "end_time";
    public static final String JSON_TOTAL_PLACE = "total_place";
    public static final String JSON_PARKING_NAME = "parking_name";
    public static final String JSON_PARKING_DESC = "parking_desc";
    public static final String JSON_EMPTY_NUMBER = "empty_number";
    public static final String JSON_ADDRESS = "address";

    public static final String JSON_PRICES = "prices";
    public static final String JSON_NAME = "name";
    public static final String JSON_PRICE = "price";
    public static final String JSON_PRICE_TYPE = "price_type";
    public static final String JSON_PRICE_FOR = "price_for";
    public static final String JSON_PICTURES = "pictures";
    public static final String JSON_URL = "url";
    public static final String JSON_PARKING_ID = "parking_id";

    //    RESP_Parking
    public static final String INTENT_PARKING_ID = "intent_parking_id";
    public static final int ADD_PARKING_REQUEST = 100, ADD_PARKING_RESULT = 99, FIND_ADVANDCED_RQ = 9, FIND_ADVANDCED_RS = 6;
    public static final String PARKING_MODEL = "parking_model";
    public static final String TYPE_OF_TRANSPORT = "type_of_transport";
    public static final String SCAN_RESULT = "scan_result";

    //    Chi tiết home
    public static final String PK_MODEL = "parking_model";
    public static final String PK_IMAGE = "parking_image";
    public static PlaceModel my_location = new PlaceModel(null, 21.026529, 105.831361);

    //    Favorite
    public static final String ID_PARKING = "id_parking";
    public static final String LATLNG_PARKING = "id_parking";

    //    Verhicle
    public static final String VERHICLE_ID = "verhicle_id";
    public static final String VERHICLE_MODEL = "verhicle_model";

    //    Check in
    public static final String CHECKING_TIME = "checkin_time";
    public static final String VERHICLE_NAME = "verhicle_name";
    public static final String PLATE_NUMBER = "plate_number";


    public static String getUserInfo(String content) {
        if (content == null || content.isEmpty())
            return MyApplication.context.getString(R.string.not_update);
        else
            return content;
    }

    public static String getTime(String begin, String end) {
        if (begin == null && end == null)
            return "Cả ngày";
        else if (begin == null)
            return "Đóng cửa: " + end;
        else if (end == null)
            return "Mở cửa: " + begin;
        else if (begin.equals(end))
            return "Cả ngày";
        return begin + " đến " + end;
    }

    public static String convertDate(String date) {
        String newData[] = date.split("/");
        return newData[2] + "/" + newData[1] + "/" + newData[0];
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDataTime(String dateTime) {
        try {
            DateFormat defaultFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            Date date = defaultFormat.parse(dateTime);
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String getPlaceNumber(String number) {
        if (number == null || number.isEmpty())
            return MyApplication.context.getString(R.string.unlimited);
        return String.valueOf(number) + " chỗ";
    }

    public static String getPlaceNumberNoText(String number) {
        if (number == null || number.isEmpty())
            return MyApplication.context.getString(R.string.unlimited);

        int total = Integer.parseInt(number);
        Random r = new Random();
        int rand = r.nextInt(50 - 30) + 30;
        total = (int) ((total * rand) / 100);
        return String.valueOf(total);
    }

    public static String getPlaceNumberAndTotal(Activity activity, String empty, String total) {
        if (empty == null)
            return activity.getString(R.string.unlimited);
        else if (Integer.parseInt(empty) == Integer.parseInt(total)) {
            return activity.getString(R.string.limited);
        } else
            return empty + " / " + total;
    }

    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    //    Find RESP_Parking
    public static final String FIND_MODEL = "find_model";
}