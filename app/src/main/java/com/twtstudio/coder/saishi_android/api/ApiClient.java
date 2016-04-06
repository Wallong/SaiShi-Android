package com.twtstudio.coder.saishi_android.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import com.twtstudio.coder.saishi_android.ContestApp;
import com.twtstudio.coder.saishi_android.support.DeviceUtils;
import com.twtstudio.coder.saishi_android.support.LogHelper;
import com.twtstudio.coder.saishi_android.support.PrefUtils;

import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.protocol.HttpContext;

/**
 * Created by clifton on 16-2-20.
 */
public class ApiClient {

    public static final String RESP_MSG_KEY = "msg";
    public static final String RESP_RESULT_CODE_KEY = "result_code";

    public static final String NO_USER_CODE = "00000";
    public static final String PWD_ERROR_CODE = "00001";
    public static final String LOG_IN_FAILURE_CODE = "00002";
    public static final String LOG_IN_SUCCESS_CODE = "00003";
    public static final String LOG_OUT_SUCCESS_CODE = "00004";
    public static final String LOG_OUT_FAILURE_CODE = "00005";
    public static final String NO_LOG_IN_CODE = "00006";
    public static final String LOG_IN_TIME_OUT_CODE = "00007";
    public static final String PWD_CHANGE_FAILURE_CODE = "00008";
    public static final String PWD_CHANGE_SUCCESS_CODE = "00009";
    public static final String FILE_NO_FOUND_CODE = "00010";
    public static final String IMG_NO_FOUND_CODE = "00011";
    public static final String UPDATE_NO_CODE = "10000";
    public static final String UPDATE_NEW_CODE = "20000";
    public static final String CANNOT_VERIFY_CODE = "10010";
    public static final String RXIST_USERNAME_CODE = "10002";
    public static final String REGISTER_FAILURE_CODE = "10001";
    public static final String REGISTER_SUCCESS_CODE = "10000";

    private static final AsyncHttpClient sClient = new AsyncHttpClient();
    private static final PersistentCookieStore sCookieStore = new PersistentCookieStore(ContestApp.getContext());
    public static final int DEFAULT_TIMEOUT = 20000;

    private static final String BASE_URL = "http://121.42.157.180/qgfdyjnds/";
    private static final String REGISTER_URL = "index.php/Api/register";
    private static final String VERIFY_URL = "index.php/Api/getverify";
    private static final String LOGIN_URL = "index.php/Api/log_in";
    private static final String LOGOUT_URL = "index.php/Api/log_out";
    private static final String DATA_URL = "index.php/Api/data";
    private static final String FANGWEN_URL = "index.php/Api/fangwen";
    private static final String FILE_URL = "index.php/Api/wenjian";
    private static final String FILE_GET_URL = "index.php/Api/get_file_url";
    private static final String IMAGE_GET_URL = "index.php/Api/get_img_url";
    private static final String USERINFO_URL = "index.php/Api/get_userinfo";
    private static final String MODIFY_URL = "index.php/Api/change_pwd";
    public static final String SCHEDULE_URL = "http://121.42.157.180/qgfdyjnds/index.php/Index/schedule";
    public static final String UPDATE_URL = "index.php/Api/update";

    static {
        sClient.setTimeout(DEFAULT_TIMEOUT);
        sClient.setCookieStore(sCookieStore);
        sClient.addHeader("User-Agent", getUserAgent());
    }

    public static String getVerifyUrl() {
        return VERIFY_URL;
    }

    public static String getImageUrl() {
        return IMAGE_GET_URL;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getImageGetUrl() {
        return IMAGE_GET_URL;
    }

    public static AsyncHttpClient getInstance() {
        return sClient;
    }

    public static String getUserAgent() {
        String isRooted = DeviceUtils.isRooted() ? "rooted" : "unrooted";
        String userAgent = "ContestApp/" + DeviceUtils.getVersionName() + " (" +
                "Android; " +
                DeviceUtils.getSystemVersion() + "; " +
                DeviceUtils.getBrand() + "; " +
                DeviceUtils.getModel() + "; " +
                DeviceUtils.getNetworkType() + "; " +
                isRooted +
                ")";
        return userAgent;
    }

    public static void getUserInfo(TextHttpResponseHandler handler){
        sClient.post(BASE_URL + USERINFO_URL, handler);
    }

    public static void userLogin(String username, String password, JsonHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("phone", username);
        params.put("pwd", password);

        sClient.post(BASE_URL + LOGIN_URL, params, handler);
    }

    public static void userLogout(TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        sCookieStore.clear();
        PrefUtils.setLogin(false);

        sClient.post(BASE_URL + LOGOUT_URL, params, handler);
    }

    public static void getData(String type, int page, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("yeshu", page);

        sClient.post(BASE_URL + DATA_URL, params, handler);
    }

    public static void getFile(TextHttpResponseHandler handler){
        RequestParams params = new RequestParams();

        sClient.post(BASE_URL + FILE_URL, params, handler);
    }

    public static void downloadFile(String fileID, TextHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("fileid", fileID);

        sClient.post(BASE_URL + FILE_GET_URL, params, handler);
    }

    public static void haveDownloadFile(String fileUrl, FileAsyncHttpResponseHandler handler){
        sClient.post(BASE_URL + fileUrl, handler);
    }

    public static void modifyPwd(String oldPwd, String newPwd, TextHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("old_pwd", oldPwd);
        params.put("new_pwd", newPwd);

        sClient.post(BASE_URL + MODIFY_URL, params, handler);
    }

    public static void getImage(String icon, TextHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("imgid", icon);

        sClient.post(BASE_URL + IMAGE_GET_URL, params, handler);
    }

    public static void addViews(String id, TextHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);

        sClient.post(BASE_URL + FANGWEN_URL, params, handler);
    }

    public static void update(String type, TextHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("type", type);

        sClient.post(BASE_URL + UPDATE_URL, params, handler);
    }

    public static void register(String username, String sex, String age, String pwd,
                                String danwei, String zhiwu, String phone, String verify, String cookie, TextHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("sex", sex);
        params.put("age", age);
        params.put("pwd", pwd);
        params.put("danwei", danwei);
        params.put("zhiwu", zhiwu);
        params.put("phone", phone);
        params.put("verify", verify);
        sClient.addHeader("Cookie", cookie);

        sClient.post(BASE_URL + REGISTER_URL, params, handler);
    }

}
