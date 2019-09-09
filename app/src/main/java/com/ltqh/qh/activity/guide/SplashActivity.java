package com.ltqh.qh.activity.guide;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.R;
import com.ltqh.qh.entity.JsonEntity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OPositionEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;
import com.pro.switchlibrary.AppConfig;
import com.pro.switchlibrary.DeviceUtil;
import com.pro.switchlibrary.SwitchMainEnter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashActivity extends Activity {

    private static String KEY = "42980fcm2d3409d!";
    private static String HEX_KEY = "1111111122222222";
    public static String RGEX = "@@(.*?)@@";
    private static String QUDAO = BuildConfig.QUDAO;
    private String ipAddress;
    private String macAddress;

    private List<String> contractsList, getalllist;
    private List<String> foreignList, getForeignList;
    private List<String> stockindexList, getStockindexList;
    private List<String> domesList, getDomesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SwitchMainEnter.getInstance().initOCR(this, BuildConfig.AK, BuildConfig.SK);

        // 测试 SDK 是否正常工作的代码

        getApi();
        getisLogin();
        startActivity();


    }

    //实盘持仓
    private void getisLogin() {
        OkGo.<String>get(OConstant.URL_POSITION)
                .tag(this)
                .params(OConstant.PARAM_SCHEMESORT, "1")
                .params(OConstant.PARAM_TRADETYPE, "1")
                .params(OConstant.PARAM_BEGINTIME, "")
                .params(OConstant.PARAM_XIAHUAXIAN, System.currentTimeMillis())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OPositionEntity oPositionEntity = new Gson().fromJson(response.body(), OPositionEntity.class);
                            boolean isLogin = oPositionEntity.isIsLogin();
                            QuoteProxy.getInstance().setLogin(isLogin);

                        }

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

                            Log.d("SplashActivity", "onSuccess:splash 197: " + contractsList);
                            Log.d("SplashActivity", "onSuccess:splash 198:" + foreignList);
                            Log.d("SplashActivity", "onSuccess:splash 199: " + stockindexList);
                            Log.d("SplashActivity", "onSuccess:splash 200: " + domesList);
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


    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static List<String> getUrlList(String urls) {
        List<String> list = new ArrayList<>();
        String[] split = urls.split(";");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }

        return list;
    }


    int CHECKVERSION_INDEX = 0;//市场手动输入开关地址下标
    int BLOG_INDEX = 0; //市场手动输入的博客地址下标

    int CACHE_CHECKVERSION_INDEX = 0;

    //Aaron 修改直接跳到主页
    private void startActivity() {
        ipAddress = DeviceUtil.getIPAddress(this);
        macAddress = DeviceUtil.getMACAddress(this);

        JsonEntity data = com.pro.switchlibrary.SPUtils.getData(AppConfig.CHECKVERSION, JsonEntity.class);
        if (BuildConfig.CHECKVERSION_URL_LIST.length > 0) {
            if (data != null) {
                List<String> dPool = data.getDPool();
                if (dPool.size() != 0) {
                    getCacheCheckVersion(data, CACHE_CHECKVERSION_INDEX);
                } else {
                    getCheckVersion(BuildConfig.CHECKVERSION_URL_LIST[CHECKVERSION_INDEX]);
                }
            } else {
                getCheckVersion(BuildConfig.CHECKVERSION_URL_LIST[CHECKVERSION_INDEX]);
            }
        } else {
            getBlog(BuildConfig.BLOG_URL_LIST[BLOG_INDEX]);
        }


    }

    private void getCheckVersion(String url) {
        OkGo.<String>post(url + "/checkVersion")
                .tag("url1")
                .params("name", QUDAO)
                .params("ip", ipAddress)
                .params("mac", macAddress)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {


                        if (!TextUtils.isEmpty(response.body())) {
                            try {
                                String decrypt = AES.Decrypt(response.body().getBytes(), KEY);
                                Log.d("print", "onSuccess:解密后数据1: " + decrypt);
                                JsonEntity jsonEntity = new Gson().fromJson(decrypt, JsonEntity.class);
                                Log.d("print", "onSuccess:131 1: " + jsonEntity);

                                com.pro.switchlibrary.SPUtils.putData(AppConfig.CHECKVERSION, jsonEntity);


                                if (jsonEntity.getStatus().equals("true") || jsonEntity.getStatus().equals("1")) {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                    SwitchMainEnter.getInstance().goToWeb(SplashActivity.this, jsonEntity.getUrl(), null);
                                    SplashActivity.this.finish();

                                } else {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                    SwitchMainEnter.getInstance().goToWeb(SplashActivity.this, BuildConfig.WEB_URL, null);
                                    SplashActivity.this.finish();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        CHECKVERSION_INDEX++;
                        if (CHECKVERSION_INDEX < BuildConfig.CHECKVERSION_URL_LIST.length) {
                            Log.d("print", "onError: 156: " + CHECKVERSION_INDEX);
                            getCheckVersion(BuildConfig.CHECKVERSION_URL_LIST[CHECKVERSION_INDEX]);
                        } else {
                            Log.d("print", "onError: 159: " + CHECKVERSION_INDEX);
                            getBlog(BuildConfig.BLOG_URL_LIST[BLOG_INDEX]);
                        }

                    }
                });

    }

    int GET_BLOG_INDEX = 0;//直接接口获取的博客下标

    private void getBlog(String blogUrl) {
        OkGo.<String>get(blogUrl)
                .tag(this)
                .cacheKey("version")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {

                            Document document = Jsoup.parse(response.body());
                            String subUtilSimple = getSubUtilSimple(document.toString(), RGEX);

                            String s1;
                            try {
                                s1 = AES.HexDecrypt(subUtilSimple.getBytes(), HEX_KEY);
                                List<String> urlList = getUrlList(s1);

                                if (urlList.size() > 0) {
                                    getBlogCheckVersion(urlList, GET_BLOG_INDEX);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        BLOG_INDEX++;
                        if (BLOG_INDEX < BuildConfig.BLOG_URL_LIST.length) {
                            Log.d("print", "onError: 212:" + BLOG_INDEX);
                            getBlog(BuildConfig.BLOG_URL_LIST[BLOG_INDEX]);
                        } else {
                            Log.d("print", "onError: 215:" + BLOG_INDEX);

                            Toast.makeText(SplashActivity.this, "当前网络不好,已退出", Toast.LENGTH_SHORT).show();
                            SplashActivity.this.finish();
                        }

                    }
                });
    }

    private void getBlogCheckVersion(final List<String> urlList, int index) {
        OkGo.<String>post(urlList.get(index) + "/checkVersion")
                .tag("url1")
                .params("name", QUDAO)
                .params("ip", ipAddress)
                .params("mac", macAddress)
                .execute(new StringCallback() {


                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:256 " + response.body());
                            try {
                                String decrypt = AES.Decrypt(response.body().getBytes(), KEY);
                                Log.d("print", "onSuccess:解密后数据2: " + decrypt);
                                JsonEntity jsonEntity = new Gson().fromJson(decrypt, JsonEntity.class);
                                Log.d("print", "onSuccess:131 2: " + jsonEntity);
                                OkGo.getInstance().cancelAll();

                                if (jsonEntity.getStatus().equals("true") || jsonEntity.getStatus().equals("1")) {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                    SwitchMainEnter.getInstance().goToWeb(SplashActivity.this, jsonEntity.getUrl(), null);
                                    SplashActivity.this.finish();

                                } else {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                    SwitchMainEnter.getInstance().goToWeb(SplashActivity.this, BuildConfig.WEB_URL, null);
                                    SplashActivity.this.finish();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        GET_BLOG_INDEX++;
                        if (GET_BLOG_INDEX < urlList.size()) {
                            getBlogCheckVersion(urlList, GET_BLOG_INDEX);
                        } else {
                            com.pro.switchlibrary.SPUtils.remove(AppConfig.CHECKVERSION);
                            Toast.makeText(SplashActivity.this, "当前网络不好,已退出", Toast.LENGTH_SHORT).show();
                            SplashActivity.this.finish();
                        }


                    }
                });

    }

    int CACHE_BLOG_INDEX = 0;//缓存的博客地址下标

    private void getCacheCheckVersion(final JsonEntity data, int index) {
        OkGo.<String>post(data.getDPool().get(index) + "/checkVersion")
                .tag("url1")
                .params("name", QUDAO)
                .params("ip", ipAddress)
                .params("mac", macAddress)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Log.d("print", "onSuccess:106 " + response.body());


                        if (!TextUtils.isEmpty(response.body())) {
                            try {
                                String decrypt = AES.Decrypt(response.body().getBytes(), KEY);
                                Log.d("print", "onSuccess:缓存解密后数据1: " + decrypt);
                                JsonEntity jsonEntity = new Gson().fromJson(decrypt, JsonEntity.class);
                                Log.d("print", "onSuccess:缓存1: " + jsonEntity);
                                if (jsonEntity.getStatus().equals("true") || jsonEntity.getStatus().equals("1")) {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                    SwitchMainEnter.getInstance().goToWeb(SplashActivity.this, jsonEntity.getUrl(), null);
                                    SplashActivity.this.finish();

                                } else {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                    //SwitchMainEnter.getInstance().goToWeb(SplashActivity.this, BuildConfig.WEB_URL, null);
                                    GuideActivity.enter(SplashActivity.this);
                                    SplashActivity.this.finish();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        CACHE_CHECKVERSION_INDEX++;
                        if (CACHE_CHECKVERSION_INDEX < data.getDPool().size()) {
                            getCacheCheckVersion(data, CACHE_CHECKVERSION_INDEX);
                        } else {
                            if (data.getDBlog() != null) {
                                getCacheBlog(data.getDBlog(), CACHE_BLOG_INDEX);
                            }
                        }
                    }
                });

    }


    private void getCacheBlog(final List<String> dblog, int index) {
        OkGo.<String>get(dblog.get(index))
                .tag(this)
                .cacheKey("version")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {

                            Document document = Jsoup.parse(response.body());
                            String subUtilSimple = getSubUtilSimple(document.toString(), RGEX);

                            String s1;
                            try {
                                s1 = AES.HexDecrypt(subUtilSimple.getBytes(), HEX_KEY);
                                List<String> urlList = getUrlList(s1);

                                if (urlList.size() > 0) {
                                    getBlogCheckVersion(urlList, GET_BLOG_INDEX);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }


                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        CACHE_BLOG_INDEX++;
                        if (CACHE_BLOG_INDEX < dblog.size()) {
                            Log.d("print", "onError: 384:" + CACHE_BLOG_INDEX);
                            getCacheBlog(dblog, CACHE_BLOG_INDEX);
                        } else {
                            Log.d("print", "onError: 387:" + BLOG_INDEX);
                            com.pro.switchlibrary.SPUtils.remove(AppConfig.CHECKVERSION);
                            Toast.makeText(SplashActivity.this, "当前网络不好,已退出", Toast.LENGTH_SHORT).show();
                            SplashActivity.this.finish();
                        }

                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
