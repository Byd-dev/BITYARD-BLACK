package com.ltqh.qh.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.config.IntentConfig;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OMarketEntity;
import com.ltqh.qh.operation.entity.OMineEntity;
import com.ltqh.qh.operation.entity.OPositionEntity;
import com.ltqh.qh.operation.entity.ORechargeEntity;
import com.ltqh.qh.operation.entity.ORuleEntity;
import com.ltqh.qh.operation.entity.OTradeListEntity;
import com.ltqh.qh.operation.entity.OwithdrawEntity;
import com.ltqh.qh.operation.fragment.home.OHomeFragment;
import com.ltqh.qh.operation.fragment.info.OInfoFragment;
import com.ltqh.qh.operation.fragment.market.OMarketFragment;
import com.ltqh.qh.operation.fragment.quote.OQuoteFragment;
import com.ltqh.qh.operation.fragment.user.OMyFragment;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.quotebase.QuoteSubject;
import com.ltqh.qh.operation.utils.ODateUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.Util;
import com.ltqh.qh.view.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.MARKET_PERIOD;
import static com.ltqh.qh.operation.base.OConstant.PARAM_CALLBACK;
import static com.ltqh.qh.operation.base.OConstant.PARAM_CODE;
import static com.ltqh.qh.operation.base.OConstant.PARAM_SIMPLE;

public class SecondActivity extends OBaseActivity implements RadioGroup.OnCheckedChangeListener {


    @Override
    protected int setContentLayout() {
        return R.layout.activity_second;
    }


    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.radio_2)
    TextView radioButton_2;


    /*@BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;*/
    private List<String> contractsList, getContractsList, getalllist;
    private List<String> foreignList, getForeignList;
    private List<String> stockindexList, getStockindexList;
    private List<String> domesList, getDomesList;

    private List<String> dataList;
    private List<String> foreigndataList;
    private List<String> stockindexdataList;
    private List<String> domesdataList;
    private QuoteSubject quoteSubject;
    private FragmentTransaction ft;

    private OHomeFragment oHomeFragment;

    private OMarketFragment oMarketFragment;

    private OQuoteFragment oQuoteFragment;

    private OInfoFragment oInfoFragment;

    private OMyFragment oMyFragment;
    // private List<String> foreignList, stockindexList,domesList;


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    /**
     * 首页Tab索引
     */
    public static final class TAB_TYPE {
        public static final int COUNT = 4;
        public static final int TAB_HOME = 0;
        public static final int TAB_HALL = TAB_HOME + 1;
        public static final int TAB_POSITION = TAB_HALL + 1;
        public static final int TAB_INFORMATION = TAB_POSITION + 1;
        //   public static final int TAB_DISCOVERY = TAB_INFORMATION + 1;


        public static final int TAB_HALL_QUOTATION = 100;
        public static final int TAB_MY = 3;

    }

    public static void enter(Context context, int tabIndex) {

        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(IntentConfig.Keys.POSITION, tabIndex);
        context.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(IntentConfig.Keys.REGISTER_SUCCESS)) {
            //showCompleteInfoDialog(intent.getStringExtra(IntentConfig.Keys.REGISTER_SUCCESS));
        } else if (intent.hasExtra(IntentConfig.Keys.POSITION)) {
            int position = intent.getIntExtra(IntentConfig.Keys.POSITION, 0);
            // radioGroup.getChildAt(position).performClick();
        }
    }

    @Override
    protected void initView(View view) {

        oHomeFragment = new OHomeFragment();
        oMarketFragment = new OMarketFragment();

        oInfoFragment = new OInfoFragment();
        oMyFragment = new OMyFragment();

        if (!ODateUtil.isWeekend()) {
            startScheduleJob(mHandler, MARKET_PERIOD, MARKET_PERIOD);
        }


        StatusBarUtil.setRootViewFitsSystemWindows(this, false);


        // bottomNavigationView.setOnNavigationItemSelectedListener(this);


        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(0).performClick();

        radioButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> dataList = QuoteProxy.getInstance().getDataList();

                if (dataList == null) {
                    return;
                }

                String s = dataList.get(0);
                OMarketActivity.enter(SecondActivity.this, OConstant.OQUETO, "1", s);

            }
        });


    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            postQuote();


            if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                getShipanPosition();
                getMoniPosition();
                getMOniTradelist();
                getShipanTradelist();

                getMine();
                getWithdraw();
                getBaseMine();
                postRecharge();
            } else {
                SPUtils.remove(OUserConfig.LOGIN_USER);
            }


        }
    };

    private void getMine() {
        OkGo.<String>get(OConstant.URL_USER_MINE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                            if (!TextUtils.isEmpty(response.body())) {
                                OMineEntity oMineEntity = new Gson().fromJson(response.body(), OMineEntity.class);
                                SPUtils.putData(OUserConfig.USER, oMineEntity);

                                if (oMineEntity != null) {

                                    //登录成功需要更新下
                                    OMineEntity.AssetBean asset = oMineEntity.getAsset();

                                    if (asset != null) {
                                        double eagle = asset.getEagle();

                                        double eagleRatio = oMineEntity.getEagleRatio();

                                      //  double div_eagle = OUtil.div(eagle, eagleRatio, 1);


                                        if (eagle != 0) {

                                            QuoteProxy.getInstance().setEagle(eagle);
                                        }
                                        QuoteProxy.getInstance().setoMineEntity(oMineEntity);
                                    }
                                }
                            }
                        }
                    }
                });

    }

    private void getWithdraw() {

        OkGo.<String>get(OConstant.URL_WITHDRAW)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                            if (!TextUtils.isEmpty(response.body())) {
                                // Log.d("print", "onSuccess: 270:  " + response.body());
                                OwithdrawEntity owithdrawEntity = new Gson().fromJson(response.body(), OwithdrawEntity.class);
                                QuoteProxy.getInstance().setOwithdrawEntity(owithdrawEntity);

                            }
                        }
                    }
                });
    }

    private void getBaseMine() {
        OkGo.<String>get(OConstant.URL_USER_BASE_MINE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                            if (!TextUtils.isEmpty(response.body())) {
                                OBaseMineEntity oBaseMineEntity = new Gson().fromJson(response.body(), OBaseMineEntity.class);

                                QuoteProxy.getInstance().setoBaseMineEntity(oBaseMineEntity);


                            }

                        }
                    }
                });
    }

    private void postRecharge() {

        OkGo.<String>post(OConstant.URL_RECHARGE)
                .params(OConstant.PARAM_ACTION, OConstant.STAY_GET_PAY_LIST)
                .params(OConstant.PARAM_SWITCH_TYPE, "1")
                .params(OConstant.PARAM_PLATFORM, OConstant.STAY_ANDROID)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                            if (!TextUtils.isEmpty(response.body())) {

                                ORechargeEntity oRechargeEntity = new Gson().fromJson(response.body(), ORechargeEntity.class);
                                QuoteProxy.getInstance().setoRechargeEntity(oRechargeEntity);

                            }
                        } else {

                        }
                    }
                });
    }


    private Fragment currentFragment = new Fragment();


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String string = SPUtils.getString(OUserConfig.P_NIGHT);

        switch (checkedId) {

            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, oHomeFragment, null, null);
                StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, false);
                break;
            case R.id.radio_1:

                if (string.equals("")) {
                    StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, true);

                } else {
                    if (string.equals("night")) {
                        StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, false);

                    } else if (string.equals("day")) {
                        StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, true);
                    }
                }
                showFragment(R.id.layout_fragment_containter, oMarketFragment, null, null);
                break;
/*
            case R.id.radio_2:
                OMarketActivity.enter(SecondActivity.this, OConstant.OQUETO,"1", "CL1907,1,58.94,58.81");

                // setStatusBar(getResources().getColor(R.color.white));
                StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, true);

                break;*/
            case R.id.radio_3:


                showFragment(R.id.layout_fragment_containter, oInfoFragment, null, null);

                if (string.equals("")) {
                    StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, true);

                } else {
                    if (string.equals("night")) {
                        StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, false);

                    } else if (string.equals("day")) {
                        StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, true);

                    }
                }
                break;
            case R.id.radio_4:
                showFragment(R.id.layout_fragment_containter, oMyFragment, null, null);
                StatusBarUtil.setStatusBarDarkTheme(SecondActivity.this, false);

                break;
        }
    }


    @Override
    protected void initPresenter() {

    }


    private List<ORuleEntity> oRuleEntities;

    @Override
    protected void initData() {


        postQuote();
        if (QuoteProxy.getInstance().isLogin() == true) {
            getShipanPosition();
            getMoniPosition();
            getMOniTradelist();
            getShipanTradelist();
        } else {
            SPUtils.remove(OUserConfig.LOGIN_USER);

        }


        ORuleEntity rule = SPUtils.getData("rule", ORuleEntity.class);
        if (rule != null) {

        } else {
            String s = readTextFromSDcard();
            ORuleEntity oRuleEntity = new Gson().fromJson(s, ORuleEntity.class);
            SPUtils.putData("rule", oRuleEntity);
        }


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

    private void getQuote(String url, int length) {

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


   /* private void getApi() {
        OkGo.<String>get(OConstant.URL_API)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            OApiEntity oApiEntity = new Gson().fromJson(response.body(), OApiEntity.class);
                            QuoteProxy.getInstance().setoApiEntity(oApiEntity);
                            Log.d("print", "onSuccess:458:   " + oApiEntity);


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



                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });

    }*/


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

    //实盘持仓
    private void getShipanPosition() {
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
                            QuoteProxy.getInstance().setoPositionEntity(oPositionEntity);


                        }

                    }
                });

    }

    //模拟持仓
    private void getMoniPosition() {
        OkGo.<String>get(OConstant.URL_POSITION)
                .tag(this)
                .params(OConstant.PARAM_SCHEMESORT, "1")
                .params(OConstant.PARAM_TRADETYPE, "2")
                .params(OConstant.PARAM_BEGINTIME, "")
                .params(OConstant.PARAM_XIAHUAXIAN, System.currentTimeMillis())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OPositionEntity oPositionEntity = new Gson().fromJson(response.body(), OPositionEntity.class);
                            QuoteProxy.getInstance().setoPositionMoniEntity(oPositionEntity);

                        }

                    }
                });

    }

    //实盘tradelist
    private void getShipanTradelist() {
        OkGo.<String>get(OConstant.URL_POSITION)
                .tag(this)
                .params(OConstant.PARAM_SCHEMESORT, "0")
                .params(OConstant.PARAM_TRADETYPE, "1")
                .params(OConstant.PARAM_BEGINTIME, "")
                .params(OConstant.PARAM_XIAHUAXIAN, System.currentTimeMillis())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OTradeListEntity oPositionEntity = new Gson().fromJson(response.body(), OTradeListEntity.class);
                            QuoteProxy.getInstance().setoTradeListEntity(oPositionEntity);


                        }

                    }
                });

    }

    //模拟tradelist
    private void getMOniTradelist() {
        OkGo.<String>get(OConstant.URL_POSITION)
                .tag(this)
                .params(OConstant.PARAM_SCHEMESORT, "0")
                .params(OConstant.PARAM_TRADETYPE, "2")
                .params(OConstant.PARAM_BEGINTIME, "")
                .params(OConstant.PARAM_XIAHUAXIAN, System.currentTimeMillis())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OTradeListEntity oTradeListEntity = new Gson().fromJson(response.body(), OTradeListEntity.class);
                            QuoteProxy.getInstance().setoTradeListMoniEntity(oTradeListEntity);

                        }

                    }
                });

    }


    private String readTextFromSDcard() {
        InputStreamReader inputStreamReader;
        String resultString = null;
        try {
            inputStreamReader = new InputStreamReader(getAssets().open("rule.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            resultString = stringBuilder.toString();
            Log.i("TAG", stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultString;
    }


    @Override
    protected void initEvent() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SPUtils.remove("KDATA");
        cancelTimer();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
