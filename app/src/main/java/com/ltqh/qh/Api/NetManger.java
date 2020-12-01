package com.ltqh.qh.Api;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.AppConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.InitEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.entity.TradeListEntity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetManger {

    public static NetManger instance;

    public static String BUSY = "busy";
    public static String SUCCESS = "success";
    public static String FAILURE = "failure";

    private String BASE_URL = "https://test.bityard.com";


    private List<String> contractsList, getalllist;
    private List<String> foreignList, getForeignList;
    private List<String> stockindexList, getStockindexList;
    private List<String> domesList, getDomesList;
    private List<String> digitaList, getDigitaList;


    private List<String> dataList;
    private List<String> foreigndataList;
    private List<String> stockindexdataList;
    private List<String> domesdataList;
    private List<String> digitalDataList;


    public static NetManger getInstance() {
        if (instance == null) {
            instance = new NetManger();
        }

        return instance;
    }

    /*登录*/
    public void login(final String username, final String password, OnNetResult onNetResult) {


        OkGo.<String>post(BASE_URL + "/user/communal/login")
                .tag(this)
                .params(Constant.PARAM_USERNAME, username)
                .params(Constant.PARAM_PASSWORD, password)
                .params(Constant.PARAM_DEVICE_TYPE, Constant.STAY_DEVICE)
                .params(Constant.PARAM_APP, "1")
                .params(Constant.PARAM_APPLY_NAME, BuildConfig.APPLICATION_ID)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("print", "onSuccess:109 " + response.body());
                        if (!TextUtils.isEmpty(response.body()) && !response.body().equals("{")) {

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);

                            if (codeMsgEntity.getCode() == 1) {

                                onNetResult.onNetResult(SUCCESS, response.body());


                            } else {
                                onNetResult.onNetResult(FAILURE, response.body());

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());


                    }
                });
    }


    /*注册*/
    public void register(final String num, final String pass, String code, OnNetResult onNetResult) {
        OkGo.<String>post(BASE_URL + "/user/communal/register")
                .headers(Constant.PARAM_XX_LANGUAGE, "zh-CN")
                .params(Constant.PARAM_USERNAME, num)
                .params(Constant.PARAM_PASSWORD, pass)
                .params(Constant.PARAM_VERIFICATION_CODE, code)
                .params(Constant.PARAM_APP, "1")
                .params(Constant.PARAM_APPLY_NAME, BuildConfig.APPLICATION_ID)
                .params(Constant.PARAM_IS_VALIDATE, "1")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            TipEntity tipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            if (tipEntity.getCode() == 1) {
                                onNetResult.onNetResult(SUCCESS, response.body());
                            } else {
                                onNetResult.onNetResult(FAILURE, response.body());

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());


                    }
                });
    }

    /*忘记密码找回密码*/
    public void reset(final String num, final String pass, String code, OnNetResult onNetResult) {
        OkGo.<String>post(BASE_URL + "/user/communal/passwordReset")
                .params(Constant.PARAM_USERNAME, num)
                .params(Constant.PARAM_PASSWORD, pass)
                .params(Constant.PARAM_VERIFICATION_CODE, code)
                .params(Constant.PARAM_APP, BuildConfig.APPLICATION_ID)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            TipEntity tipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            if (tipEntity.getCode() == 1) {
                                onNetResult.onNetResult(SUCCESS, response.body());

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());

                    }
                });
    }

    /*btc market*/
    public void market(OnNetResult onNetResult) {

        OkGo.<String>get("https://api.coincap.io/v2/markets")
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }

    //URL拼接参数
    public String getURL(String url, ArrayMap map) {

        String substring_url = null;
        if (map == null) {
            return BASE_URL + url;
        } else {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            StringBuilder stringBuilder = new StringBuilder();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                StringBuilder append = stringBuilder.append(key).append("=").append(value).append("&");
                substring_url = append.toString().substring(0, append.toString().length() - 1);
            }
            String url_result = BASE_URL + url + "?" + substring_url;

            return url_result;
        }


    }

    //get 请求
    public void getRequest(String url, ArrayMap map, OnNetResult onNetResult) {
        OkGo.<String>get(getURL(url, map))
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, null);
                    }
                });

    }

    /*获取初始化数据*/
    public void getInit(OnNetResult onNetResult) {
        /*获取行情的host*/
        NetManger.getInstance().getRequest("/api/trade/commodity/initial", null, (state, response) -> {
            if (state.equals(BUSY)) {
                onNetResult.onNetResult(BUSY, null);
            } else if (state.equals(SUCCESS)) {
                String s = response.toString().replaceAll(" ", "");
                if (s.startsWith("error")) {
                    onNetResult.onNetResult(FAILURE, response);
                } else {
                    TipEntity tipEntity = new Gson().fromJson(response.toString(), TipEntity.class);
                    if (tipEntity.getCode() == 200) {
                        InitEntity initEntity = new Gson().fromJson(response.toString(), InitEntity.class);
                        if (initEntity.getGroup() != null) {
                            onNetResult.onNetResult(SUCCESS, initEntity);
                        }
                    } else {
                        onNetResult.onNetResult(FAILURE, null);

                    }
                }

            } else if (state.equals(FAILURE)) {
                onNetResult.onNetResult(FAILURE, null);

            }
        });
    }


    int count = 0;


    /*获取行情*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getQuote(String quoteDomain, String url, String codeList, OnNetResult onNetResult) {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("callback", "?");
        map.put("code", codeList);
        map.put("_", String.valueOf(new Date().getTime()));
        map.put("simple", "true");

        try {
            String urlList = AES.HexDecrypt(quoteDomain.getBytes(), AppConfig.S_KEY);

            String[] split = urlList.split(";");
            int length = split.length;
            Log.d("NetManger", "getQuote:324: 请求次数: " + count + "请求地址长度: " + length + "  --   " + urlList);
            if (count < length) {
                getHostRequest(split[count], url, map, (state, response) -> {
                    if (state.equals(BUSY)) {

                    } else if (state.equals(SUCCESS)) {
                        onNetResult.onNetResult(SUCCESS, response.toString());
                    } else if (state.equals(FAILURE)) {
                        if (length == 0) {

                        } else {
                            count++;
                        }
                    }
                });
            } else {
                count = 0;//这里是重置
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //动态URL拼接参数
    public String getHostURL(String host, String url, ArrayMap map) {
        Log.d("NetManger", "getURL:HOST参数:  " + map);

        String substring_url = null;
        if (map == null) {
            return host + url;
        } else {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            StringBuilder stringBuilder = new StringBuilder();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                StringBuilder append = stringBuilder.append(key).append("=").append(value).append("&");
                substring_url = append.toString().substring(0, append.toString().length() - 1);
            }
            String url_result = host + url + "?" + substring_url;
            return url_result;
        }


    }

    //动态host get 请求
    public void getHostRequest(String host, String url, ArrayMap map, OnNetResult onNetResult) {
        OkGo.<String>get(getHostURL(host, url, map))
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!response.body().startsWith("err")) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, null);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }

    private void getApi() {
        OkGo.<String>get(OConstant.URL_API)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            OApiEntity oApiEntity = new Gson().fromJson(response.body(), OApiEntity.class);

                            QuoteProxy.getInstance().setoApiEntity(oApiEntity);

                            SPUtils.putData(OUserConfig.API, oApiEntity);
                            String contracts = oApiEntity.getContracts().replaceAll("\"", "")
                                    .replaceAll("\\[", "").replaceAll("]", "");

                            String[] split = contracts.split(",");

                            contractsList = new ArrayList<>();

                            for (String x : split) {
                                contractsList.add(x);
                            }


                            foreignList = new ArrayList<>();
                            List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
                            for (int i = 0; i < foreignCommds.size(); i++) {
                                foreignList.add(foreignCommds.get(i).getCode());
                            }

                            QuoteProxy.getInstance().setForeignList(foreignList);


                            List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
                            stockindexList = new ArrayList<>();
                            for (OApiEntity.StockIndexCommdsBean y :
                                    stockIndexCommds) {
                                stockindexList.add(y.getCode());
                            }

                            QuoteProxy.getInstance().setStockindexList(stockindexList);

                            domesList = new ArrayList<>();
                            List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();
                            for (OApiEntity.DomesticCommdsBean y :
                                    domesticCommds) {
                                domesList.add(y.getCode());
                            }

                            QuoteProxy.getInstance().setDomesList(domesList);

                            Log.d("SplashActivity", "onSuccess:second 197: " + contractsList);
                            Log.d("SplashActivity", "onSuccess:second 198:" + foreignList);
                            Log.d("SplashActivity", "onSuccess:second 199: " + stockindexList);
                            Log.d("SplashActivity", "onSuccess:second 200: " + domesList);
                            getForeignList = new ArrayList<>();
                            for (int i = 0; i < contractsList.size(); i++) {
                                for (int j = 0; j < foreignList.size(); j++) {
                                    if (contractsList.get(i).startsWith(foreignList.get(j))) {
                                        getForeignList.add(contractsList.get(i));
                                    }
                                }
                            }

                            getStockindexList = new ArrayList<>();
                            for (int i = 0; i < contractsList.size(); i++) {
                                for (int j = 0; j < stockindexList.size(); j++) {
                                    if (contractsList.get(i).startsWith(stockindexList.get(j))) {
                                        getStockindexList.add(contractsList.get(i));
                                    }
                                }
                            }

                            getDomesList = new ArrayList<>();
                            for (int i = 0; i < contractsList.size(); i++) {
                                for (int j = 0; j < domesList.size(); j++) {
                                    if (contractsList.get(i).startsWith(domesList.get(j))) {
                                        getDomesList.add(contractsList.get(i));
                                    }
                                }
                            }


                         /*   Log.d("print", "onSuccess:234: " + getForeignList);
                            Log.d("print", "onSuccess:235:" + getStockindexList);
                            Log.d("print", "onSuccess:236: " + getDomesList);*/
                            getalllist = new ArrayList<>();
                            getalllist.addAll(getForeignList);
                            getalllist.addAll(getStockindexList);
                            getalllist.addAll(getDomesList);

                            SPUtils.putString(OUserConfig.ALLDEX, getalllist.toString());
                         /*   SPUtils.putString(OUserConfig.FOREIGN, getForeignList.toString());
                            SPUtils.putString(OUserConfig.STOCKINDEX, getStockindexList.toString());
                            SPUtils.putString(OUserConfig.DOMESTIC, getDomesList.toString());*/


                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });

    }

    @SuppressLint("NewApi")
    public void initQuote() {

        NetManger.getInstance().getInit((state, response) -> {
            if (state.equals(SUCCESS)) {
                InitEntity initEntity = (InitEntity) response;
                if (initEntity.getGroup() != null) {
                    String quoteDomain = initEntity.getQuoteDomain();//获取域名
                    SPUtils.putString(AppConfig.QUOTE_HOST, quoteDomain);

                    List<InitEntity.GroupBean> group = initEntity.getGroup();
                    // TODO: 2020/3/13 暂时这里只固定是数字货币的遍历
                    for (InitEntity.GroupBean data : group) {
                        if (data.getName().equals("数字货币")) {
                            String list = data.getList();
                            Log.d("print", "onNetResult:338:  " + list);

                            SPUtils.putString(AppConfig.CONTRACT_ID, list);

                            NetManger.getInstance().getTradeList(list, (state1, response1) -> {
                                if (state1.equals(BUSY)) {
                                } else if (state1.equals(SUCCESS)) {
                                    List<TradeListEntity> tradeListEntityList = (List<TradeListEntity>) response1;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int i = 0; i < tradeListEntityList.size(); i++) {
                                        stringBuilder.append(tradeListEntityList.get(i).getContractCode() + ",");
                                    }
                                    Log.d("print", "initQuote:540:  "+stringBuilder.toString());
                                    SPUtils.putString(AppConfig.QUOTE_CODE, stringBuilder.toString());

                                } else if (state1.equals(FAILURE)) {
                                }
                            });//获取合约号
                        }
                    }
                }
            }
        });

    }
    /*articleList*/
    public void articleList(String token, int page, String userId,int isTopic,OnNetResult onNetResult) {

        OkGo.<String>get(BASE_URL + "/yapi/Circle/index")
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params("page", page)
                .params("num", 10)
                .params("order", "published_time")
                .params("category", "8007")
                .params("user_id", userId)
                .params("is_topic", isTopic)
                .params("title","")
                .params(Constant.PARAM_APPLY_NAME, BuildConfig.APPLICATION_ID)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }
    /*articleDetail*/
    public void articleDetail(String id,OnNetResult onNetResult) {

        OkGo.<String>get(BASE_URL + "/yapi/Circle/view")
                .params("id", id)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }

    /*articleDetail*/
    public void addComment(String token,String object_id,String content,String id,OnNetResult onNetResult) {

        OkGo.<String>post(BASE_URL + "/api/user/Circle/add_comment")
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_OBJECT_ID, object_id)
                .params(Constant.PARAM_CONTENT, content)
                .params("id",id)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }
    /*commentList*/
    public void commentList(String object_id,String user_id,int page,OnNetResult onNetResult) {

        OkGo.<String>get(BASE_URL + "/yapi/Circle/comment_list")
                .params("page",page)
                .params("num",10)
                .params("order","create_time")
                .params("object_id",object_id)
                .params("user_id",user_id)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }
    /*focus*/
    public void focusList(String token, OnNetResult onNetResult) {
        Log.d("print", "focus: " + token);

        OkGo.<String>get(BASE_URL + "/yapi/user/userFollowList")
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }

    /*focus*/
    public void focus(String token, int id, OnNetResult onNetResult) {

        OkGo.<String>get(BASE_URL + "/yapi/circle/follow")
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params("followUserId", id)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }

    /*send*/
    public void addArticle(String token, int id, String title, String source, String content, String more, int isTopic, String url, OnNetResult onNetResult) {

        OkGo.<String>post(BASE_URL + "/api/user/Circle/add_circle")
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_POST_TITLE, title)
                .params("post_keywords", title)
                .params("post_excerpt", title)
                .params("post_source", source)
                .params("post_content", content)
                .params(Constant.PARAM_MORE, "")
                .params("category", "8007")
                .params(Constant.PARAM_ID, id)
                .params("is_topic", isTopic)
                .params(Constant.PARAM_APPLY_NAME, BuildConfig.APPLICATION_ID)
                .params("video_url", url)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });

    }

    /*名人库*/
    public void celebrityList(OnNetResult onNetResult){
        OkGo.<String>get(BASE_URL + "/yapi/people/lists")
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });
    }
    /*名人详情*/
    public void celebrityDetail(String content,OnNetResult onNetResult){
        OkGo.<String>get(BASE_URL + "/yapi/people/introduce")
                .params("url",content)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        } else {
                            onNetResult.onNetResult(FAILURE, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());
                    }
                });
    }
    private List<TradeListEntity> tradeListEntityList;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getTradeList(String codeList, OnNetResult onNetResult) {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("code", codeList);
        String[] codeSplitList = codeList.split(";");
        getRequest("/api/trade/commodity/tradeList", map, new OnNetResult() {

            private TradeListEntity tradeListEntity;

            @Override
            public void onNetResult(String state, Object response) {
                if (state.equals(BUSY)) {
                } else if (state.equals(SUCCESS)) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.toString());
                        JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                        Log.d("NetManger", "onNetResult:250:  " + jsonObject1.length());

                        Log.d("NetManger", "onNetResult:252: " + codeSplitList.length);

                        tradeListEntityList = new ArrayList<>();
                        for (int i = 0; i < jsonObject1.length(); i++) {
                            for (int j = codeSplitList.length - 1; j > 0; j--) {
                                JSONObject trxusdt = (JSONObject) jsonObject1.get(codeSplitList[i]);  //trxusdt.length() =46
                                // Log.d("NetManger", "onNetResult: 258:"+trxusdt.length());
                                tradeListEntity = new Gson().fromJson(trxusdt.toString(), TradeListEntity.class);
                                //  Log.d("NetManger", "onNetResult:260: "+tradeListEntity);
                            }
                            tradeListEntityList.add(tradeListEntity);
                            // Log.d("NetManger", "onNetResult:263:  "+tradeListEntityList.size());


                        }
                        onNetResult.onNetResult(SUCCESS, tradeListEntityList);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else if (state.equals(FAILURE)) {
                    onNetResult.onNetResult(FAILURE, "获取合约号失败");
                }
            }
        });
    }

    public void btcNews(OnNetResult onNetResult) {
        OkGo.<String>get(BASE_URL + "/new_api/Newsletter/currencyNewsLetter")
                .tag(this)
                .params("is_cache", "0")
                .params(Constant.PARAM_PAGESIZE, 10)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());


                    }
                });


    }

    /*ranke*/
    public void btcQuote(OnNetResult onNetResult) {
        OkGo.<String>get(BASE_URL + "/new_api/Quotes/btcmoneyQuoteChangeList")
                .tag(this)
                .params("is_cache", "0")
                .params(Constant.PARAM_PAGESIZE, 10)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());


                    }
                });


    }


    /*比特币价格排行*/
    public void btcPrice(int page, String sortby, OnNetResult onNetResult) {
        OkGo.<String>get(BASE_URL + "/new_api/currency/HqzCurrencyList")
                .tag(this)
                .params(Constant.PARAM_PAGE, page)
                .params("sortBy", sortby)
                .params(Constant.PARAM_SORT, "desc")
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY, null);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            onNetResult.onNetResult(SUCCESS, response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE, response.body());


                    }
                });


    }
}
