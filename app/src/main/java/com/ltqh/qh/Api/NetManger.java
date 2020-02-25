package com.ltqh.qh.Api;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OMarketEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.Util;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.ltqh.qh.operation.base.OConstant.PARAM_CALLBACK;
import static com.ltqh.qh.operation.base.OConstant.PARAM_CODE;
import static com.ltqh.qh.operation.base.OConstant.PARAM_SIMPLE;

public class NetManger {

    public static NetManger instance;

    public static String BUSY = "busy";
    public static String SUCCESS = "success";
    public static String FAILURE = "failure";

    private String BASE_URL = "https://data.fk7h.com";


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
                .params(Constant.PARAM_IS_VALIDATE, "2")
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
    /*API*/
    public void api(OnNetResult onNetResult) {
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

                            digitaList = new ArrayList<>();
                            List<OApiEntity.DigitalCommdsBean> digitalCommds = oApiEntity.getDigitalCommds();
                            for (OApiEntity.DigitalCommdsBean y :
                                    digitalCommds) {
                                digitaList.add(y.getCode());
                            }

                            QuoteProxy.getInstance().setDigitalList(digitaList);


                            Log.d("SplashActivity", "onSuccess:guide 197: " + contractsList);
                            Log.d("SplashActivity", "onSuccess:guide 198:" + foreignList);
                            Log.d("SplashActivity", "onSuccess:guide 199: " + stockindexList);
                            Log.d("SplashActivity", "onSuccess:guide 200: " + domesList);
                            Log.d("SplashActivity", "onSuccess:guide 201: " + digitaList);

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

                            getDigitaList = new ArrayList<>();
                            for (int i = 0; i < contractsList.size(); i++) {
                                for (int j = 0; j < digitaList.size(); j++) {
                                    if (contractsList.get(i).startsWith(digitaList.get(j))) {
                                        getDigitaList.add(contractsList.get(i));
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
                            getalllist.addAll(getDigitaList);
                            onNetResult.onNetResult(SUCCESS, getalllist);

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

    int count = 0;

    //行情的请求
    public void postQuote() {

        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        //判断当前是否有api缓存 没有缓存先获取api
        if (oApiEntity == null) {
            getApi();
        } else {

            String quoteDomain = oApiEntity.getQuoteDomain();
            //按顺序去读取行情的接口
            try {
                String urlList = AES.HexDecrypt(quoteDomain.getBytes(), "1111111122222222");
                String[] split = urlList.split(";");
                int length = split.length;
                if (count < length) {
                    String indexUrl = split[count] + "/quote.jsp";
                    getQuote(indexUrl, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void getQuote(String url, int length) {

        String string = SPUtils.getString(OUserConfig.ALLDEX);
        if (string != null) {
            String code = string.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");

            OkGo.<String>post(url)
                    .tag(this)
                    .params(PARAM_CALLBACK, "?")
                    .params(PARAM_CODE, code)
                    .params("_", Calendar.getInstance().getTimeInMillis())
                    .params(PARAM_SIMPLE, true)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);

                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            if (!TextUtils.isEmpty(response.body())) {

                                QuoteProxy.getInstance().setQuoteUrl(url);
                                String responese = Util.jsonReplace(response.body());
                                OMarketEntity oMarketEntity = new Gson().fromJson(responese, OMarketEntity.class);
                                String data = oMarketEntity.getData();
                                if (data == null) {
                                    return;
                                }
                                if (data != null) {
                                    String[] split = data.split(";");
                                    dataList = new ArrayList<>();
                                    for (String a : split) {
                                        dataList.add(a);
                                    }
                                }
                                //所有的行情
                                QuoteProxy.getInstance().setDataList(dataList);
                                foreigndataList = new ArrayList<>();
                                List<String> foreignList = QuoteProxy.getInstance().getForeignList();

                                if (foreignList != null) {
                                    for (String quote : dataList) {
                                        String[] split = quote.split(",");
                                        if (foreignList.toString().contains(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                            foreigndataList.add(quote);
                                        }
                                    }
                                    QuoteProxy.getInstance().setForeigndataList(foreigndataList);
                                } else {
                                    getApi();
                                }


                                stockindexdataList = new ArrayList<>();
                                List<String> stockindexList = QuoteProxy.getInstance().getStockindexList();
                                if (stockindexList != null) {
                                    for (String quote : dataList) {
                                        String[] split = quote.split(",");
                                        if (stockindexList.toString().contains(split[0].replaceAll("[^a-z^A-Z]", "")) & !split[0].replaceAll("[^a-z^A-Z]", "").equals("SI")) {
                                            stockindexdataList.add(quote);
                                        }
                                    }
                                    QuoteProxy.getInstance().setStockindexdataList(stockindexdataList);
                                } else {
                                    getApi();
                                }

                                domesdataList = new ArrayList<>();
                                List<String> domesList = QuoteProxy.getInstance().getDomesList();
                                if (domesList != null) {
                                    for (String quote : dataList) {
                                        String[] split = quote.split(",");
                                        if (domesList.toString().contains(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                            domesdataList.add(quote);
                                        }
                                    }
                                    QuoteProxy.getInstance().setDomesdataList(domesdataList);
                                } else {
                                    getApi();
                                }

                                digitalDataList = new ArrayList<>();
                                List<String> digitalList = QuoteProxy.getInstance().getDigitalList();
                                if (digitalList != null) {
                                    for (String digital : digitalList) {
                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (digital.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                digitalDataList.add(quote);
                                            }
                                        }
                                    }


                                    QuoteProxy.getInstance().setDigitalDataList(digitalDataList);
                                } else {
                                    getApi();
                                }
                            }
                        }


                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                            if (count >= length - 1) {
                                getApi();
                                count = 0;
                            }
                            count++;


                        }
                    });

        }
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


}
