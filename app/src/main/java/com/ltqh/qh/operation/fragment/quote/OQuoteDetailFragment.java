package com.ltqh.qh.operation.fragment.quote;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.operation.activity.OImmersiveActivity;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.adapter.OHomeMarketAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.chart.KData;
import com.ltqh.qh.operation.chart.KLineView;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OBondEntity;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OKlineEntity;
import com.ltqh.qh.operation.entity.OMarketEntity;
import com.ltqh.qh.operation.entity.OPositionEntity;
import com.ltqh.qh.operation.entity.OProfitEntity;
import com.ltqh.qh.operation.entity.ORechargeEntity;
import com.ltqh.qh.operation.entity.ORuleEntity;
import com.ltqh.qh.operation.entity.OTradeListEntity;
import com.ltqh.qh.operation.entity.OTrendEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.Util;
import com.ltqh.qh.view.Direction;
import com.ltqh.qh.view.GuideView;
import com.ltqh.qh.view.InfoEnhanceTabLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.FIRSTLOAD;
import static com.ltqh.qh.operation.base.OConstant.NULLTYPE;
import static com.ltqh.qh.operation.base.OConstant.ONE_PERIOD;
import static com.ltqh.qh.operation.base.OConstant.PARAM_CALLBACK;
import static com.ltqh.qh.operation.base.OConstant.PARAM_CODE;

public class OQuoteDetailFragment extends OBaseFragment implements View.OnClickListener {

    @BindView(R.id.layout_view)
    LinearLayout layout_view;


    @BindView(R.id.text_name)
    TextView text_name;

    @BindView(R.id.text_lastprice)
    TextView text_lastprice;

    @BindView(R.id.text_change)
    TextView text_change;

    @BindView(R.id.text_percent)
    TextView text_percent;

    @BindView(R.id.home_tab)
    InfoEnhanceTabLayout home_tab;

    @BindView(R.id.layout_ismoni)
    LinearLayout layout_ismoni;


    @BindView(R.id.layout_istrade)
    LinearLayout layout_istrade;

    @BindView(R.id.text_balance)
    TextView text_balance;


    @BindView(R.id.layout_fragment_containter)
    FrameLayout containter;

    @BindView(R.id.text_up)
    TextView text_up;

    @BindView(R.id.text_down)
    TextView text_down;


    @BindView(R.id.v_buy_many)
    View view_buy_many;

    @BindView(R.id.v_buy_less)
    View view_buy_less;

    @BindView(R.id.stay_line)
    View stay_line;

    @BindView(R.id.img_light)
    ImageView img_light;

    @BindView(R.id.layout_light_one)
    LinearLayout layout_light_one;

    @BindView(R.id.layout_light_two)
    RelativeLayout layout_light_two;

    @BindView(R.id.layout_light_three)
    RelativeLayout layout_light_three;

    @BindView(R.id.text_jia)
    TextView text_jia;

    @BindView(R.id.text_jian)
    TextView text_jian;

    @BindView(R.id.layout_volume)
    LinearLayout layout_volume;

    @BindView(R.id.layout_bar)
    LinearLayout layout_bar;

    @BindView(R.id.text_volume)
    TextView text_volume;

    @BindView(R.id.text_id)
    TextView text_id;


    private KLineView mKLineView, mOneKview, mThreeKview, mFifteenKview;

    private Set<String> setList;
    private String marketName = null;
    private String currency = null;

    private OHomeMarketAdapter oforeignAdapter, ostockAdapter, odomesAdapter;


    private String Titles[] = new String[]{"分时", "日线", "1分", "3分", "15分", "盘口"};
    private String code;
    private String[] split;
    private String isTrade;
    //private List<OProfitEntity> oProfitEntities;

    private String marketCode;
    private String marketCode_bus = null;

    private String type_minute;

    private Runnable singleDataAddRunnable;

    @BindView(R.id.layout_login)
    LinearLayout layout_login;

    @BindView(R.id.layout_unlogin)
    RelativeLayout layout_unlogin;
    private double pricechange;

    @BindView(R.id.img_tianjia)
    ImageView img_tianjia;

    @BindView(R.id.text_tianjia)
    TextView text_tianjia;

    @BindView(R.id.img_add)
    ImageView img_add;

    @BindView(R.id.text_add)
    TextView text_add;

    @BindView(R.id.text_jifen)
    TextView text_jifen;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    @BindView(R.id.radio_2)
    RadioButton radio_2;

    @BindView(R.id.radio_3)
    RadioButton radio_3;
    @BindView(R.id.radio_4)
    RadioButton radio_4;
    @BindView(R.id.radio_5)
    RadioButton radio_5;
    @BindView(R.id.radio_6)
    RadioButton radio_6;
    @BindView(R.id.text_stoploss)
    TextView text_stoploss;

    @BindView(R.id.text_stopprofit)
    TextView text_stopprofit;

    @BindView(R.id.text_profit)
    TextView text_profit;

    @BindView(R.id.stay_profit)
    RelativeLayout stay_prift;

    @BindView(R.id.view_line)
    View view_line;

    private List<String> idMoniList;
    private List<String> idShipanList;

    @BindView(R.id.text_unit)
    TextView text_unit;

    @BindView(R.id.img_money_type)
    ImageView img_money_type_light;

    @BindView(R.id.img_btn)
    ImageView img_btn;

    private String string;
    private WebView webView;
    private String unit;
    private String volatilityIncome;
    private String volatility;
    private String buyTimeAM;
    private String sellTimeAM;
    private String clearTime;
    private String rate;
    private String rateDetail;
    private String introduce;
    private int chargeUnit;


    private String name;
    private String highLimit;
    private String lowLimit;
    private String highClose;
    private String lowClose;
    private String codeNoNum;

    private List<String> datalist;
    OPositionEntity oPositionEntity;

    private double timeprice;
    private TextView text_lastprice1;
    private int flag;
    private double price;
    private double volumprice;
    private OTradeListEntity oTradeListEntity;
    private List<Integer> stopLossList;
    private List<Integer> stopProfitList;
    private double chargeUnit1;
    private List<Integer> depositList;
    private String commCode;
    private String contName;
    private double opPrice;
    private List<Integer> volumeList;
    private double stoploss;
    private double stopprofit;
    private List<Integer> moneyTypeList;
    private double eagle_result;
    private TextView text_jifen_pop;
    private String last;
    private double sub_price, sub_price1, sub_price2, sub_price3, sub_price4;
    private double stop_price;

    private GuideView mGVOne, mGVTwo, mGVThree;
    private TextView text_oprice;
    private TextView text_before_price;
    private TextView text_zhagndie;
    private TextView text_zhangfu;
    private TextView text_high;
    private TextView text_low;

    @BindView(R.id.text_zhang)
    TextView text_zhang;
    @BindView(R.id.text_die)
    TextView text_die;
    private boolean isBuy_bus = true;
    private double opPrice_bus;
    private int volume_bus;
    private String id_bus;
    private int moneyType_bus;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_quote_detail;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
            layout_login.setVisibility(View.VISIBLE);
            layout_unlogin.setVisibility(View.GONE);
        } else {
            layout_login.setVisibility(View.GONE);
            layout_unlogin.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onLazyLoad() {

        String string = SPUtils.getString(OUserConfig.O_FIRST_QUOTE);
        if (string.equals("")) {

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showGuideViews();
                }
            }, 100);
            SPUtils.putString(OUserConfig.O_FIRST_QUOTE, "QUOTE");

        }


        startScheduleJob(mHandler, ONE_PERIOD, ONE_PERIOD);
        postQuote(marketCode);
        //单个盘口的数据
        postItemQuote(marketCode);

        // getLineHistoryData(type_minute, Integer.valueOf(type_minute) * 60);

        getPosition();
        //刷新
        if (text_lastprice1 != null) {
            text_lastprice1.setText(timeprice + "");
            if (isAdded()) {
                if (flag == -1) {
                    text_lastprice1.setTextColor(getResources().getColor(R.color.greencolor));
                } else if (flag == 1) {
                    text_lastprice1.setTextColor(getResources().getColor(R.color.redcolor));
                } else if (flag == 0) {
                    text_lastprice1.setTextColor(getResources().getColor(R.color.o_text_3433));

                }
            }

        }
        getName();

        getLineData(type_minute);

        postForeignQuote();
        postStockQuote();
        postDomesQuote();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(OEventData oEventData) {


        if (oEventData.getKey().equals(OUserConfig.O_POSITION_DETAIL)) {

            view_line.setVisibility(View.VISIBLE);
            stay_prift.setVisibility(View.VISIBLE);

            OPositionEntity.DataBean dataBean = (OPositionEntity.DataBean) oEventData.getObject();
            marketCode_bus = dataBean.getContCode();
            marketCode = marketCode_bus;
            text_name.setText(dataBean.getCommodity() + dataBean.getContCode());
            isBuy_bus = dataBean.isIsBuy();
            opPrice_bus = dataBean.getOpPrice();
            volume_bus = dataBean.getVolume();
            moneyType_bus = dataBean.getMoneyType();
            if (isBuy_bus == true) {
                text_volume.setText("买多" + dataBean.getVolume() + "手");
                text_volume.setBackground(getResources().getDrawable(R.drawable.o_maiduo));

            } else if (isBuy_bus == false) {
                text_volume.setText("买空" + dataBean.getVolume() + "手");
                text_volume.setBackground(getResources().getDrawable(R.drawable.o_maikong));

            }
            id_bus = dataBean.getId();

            text_id.setText("仅平仓(ID: " + id_bus + ")");
        }
    }


    private void showGuideViews() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_step_four_layout, null);


        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.o_step_five_layout, null);


        mGVOne = new GuideView.Builder(getActivity())
                .setTargetView(R.id.layout_more_second)
                .setHintView(view)
                .setHintViewDirection(Direction.BOTTOM_ALIGN_RIGHT)
                .setHintViewMarginTop(-30)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVOne.hide();
                        mGVTwo.show();
                    }
                }).create();
        mGVOne.show();


        mGVTwo = new GuideView.Builder(getActivity())
                .setTargetView(R.id.img_light)
                .setHintView(view2)
                .setHintViewDirection(Direction.ABOVE_ALIGN_RIGHT)
                .setTransparentMarginBottom(30)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVTwo.hide();
                    }
                }).create();


    }

    @Override
    protected void initView(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        view.findViewById(R.id.text_login).setOnClickListener(this);
        view.findViewById(R.id.text_register).setOnClickListener(this);
        view.findViewById(R.id.layout_add).setOnClickListener(this);
        view.findViewById(R.id.layout_trade_add).setOnClickListener(this);
        view.findViewById(R.id.text_more).setOnClickListener(this);
        view.findViewById(R.id.layout_more).setOnClickListener(this);
        view.findViewById(R.id.layout_rule).setOnClickListener(this);
        view.findViewById(R.id.layout_trade_rule).setOnClickListener(this);
        view.findViewById(R.id.buy_many_position).setOnClickListener(this);
        view.findViewById(R.id.buy_less_position).setOnClickListener(this);
        view.findViewById(R.id.layout_shipan).setOnClickListener(this);
        view.findViewById(R.id.text_recharge).setOnClickListener(this);
        view.findViewById(R.id.text_pingcang).setOnClickListener(this);
        img_light.setOnClickListener(this);


        //  startScheduleJob(mHandler, ONE_PERIOD, ONE_PERIOD);

        if (split[4].equals("0")) {
            layout_istrade.setVisibility(View.GONE);
            layout_ismoni.setVisibility(View.VISIBLE);
        } else if (split[4].equals("1")) {
            layout_istrade.setVisibility(View.VISIBLE);
            layout_ismoni.setVisibility(View.GONE);
        }


        getIsAdd();

        //走势图
        View trendview = LayoutInflater.from(getContext()).inflate(R.layout.o_quote_trend_layout, null);
        webView = trendview.findViewById(R.id.webView);

        getTrendView(webView);

        //K图
        View klineView = LayoutInflater.from(getContext()).inflate(R.layout.o_quote_one_layout, null);
        mKLineView = klineView.findViewById(R.id.kline);


        View handicapView = LayoutInflater.from(getContext()).inflate(R.layout.o_quote_handicap_layout, null);

        text_oprice = handicapView.findViewById(R.id.text_oprice);
        text_before_price = handicapView.findViewById(R.id.text_before_price);
        text_zhagndie = handicapView.findViewById(R.id.text_zhangdie);
        text_zhangfu = handicapView.findViewById(R.id.text_zhangfu);
        text_high = handicapView.findViewById(R.id.text_high);
        text_low = handicapView.findViewById(R.id.text_low);

        text_high.setTextColor(getResources().getColor(R.color.redcolor));
        text_low.setTextColor(getResources().getColor(R.color.greencolor));


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                home_tab.getTabLayout().getTabAt(0);
                home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()) {
                            case 0:

                                containter.removeAllViews();
                                containter.addView(trendview);
                                break;

                            case 1:

                                containter.removeAllViews();

                                break;
                            case 2:

                                containter.removeAllViews();
                                containter.addView(klineView);
                                type_minute = "1";
                                getLineHistoryData(type_minute, Integer.valueOf(type_minute) * 10);

                                //getLineData(type_minute);
                                break;
                            case 3:

                                containter.removeAllViews();
                                containter.addView(klineView);
                                type_minute = "3";
                                // getLineData(type_minute);

                                //getLineHistoryData(type_minute, Integer.valueOf(type_minute) * 60);

                                // getLineData(type_minute);

                                break;
                            case 4:

                                containter.removeAllViews();
                                containter.addView(klineView);
                                type_minute = "15";
                                //getLineData(type_minute);

                                // getLineHistoryData(type_minute, Integer.valueOf(type_minute) * 60);


                                // getLineData(type_minute);

                                break;
                            case 5:
                                containter.removeAllViews();

                                containter.addView(handicapView);


                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


                for (int i = 0; i < Titles.length; i++) {
                    home_tab.addTab(Titles[i]);
                }
            }
        }, 100);
        //盘口

        setLight();
        // getName();


    }

    private String balance = null;
    private List<Double> incomelist;


    private void getPosition() {


        List<String> dataList = QuoteProxy.getInstance().getDataList();
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
        List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
        List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();
        double sub = 0;
        double incomeMoni = 0;
        double incomeShipan = 0;
        String lastPrice = null;

        double incomeItemMoni = 0;

        String priceChange = null;
        String price = null;

        if (dataList == null) {
            return;
        }

        if (foreignCommds == null) {
            return;
        }

        if (stockIndexCommds == null) {
            return;
        }

        if (domesticCommds == null) {
            return;
        }

        if (isTrade.equals("1")) {
            //这里计算有点麻烦
            OPositionEntity oPositionEntity = QuoteProxy.getInstance().getoPositionEntity();

            if (oPositionEntity != null) {
                if (oPositionEntity.isIsLogin() == true) {
                    balance = String.valueOf(oPositionEntity.getAsset().getMoney());
                    if (text_balance != null) {
                        text_balance.setText(balance);
                    }
                    List<OPositionEntity.DataBean> data = oPositionEntity.getData();
                    if (data.size() != 0) {
                        incomelist = new ArrayList<>();
                        if (foreignCommds != null) {
                            for (OPositionEntity.DataBean itemdata : data) {
                                for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                                    String contcode = itemdata.getContCode().replaceAll("[^a-z^A-Z]", "");
                                    if (foreign.getCode().equals(contcode)) {

                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (contcode.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                lastPrice = split[2];
                                            }
                                        }
                                        if (lastPrice != null) {
                                            opPrice = itemdata.getOpPrice();
                                            if (opPrice != 0.0) {
                                                if (itemdata.isIsBuy() == true) {
                                                    sub = OUtil.sub(Double.valueOf(lastPrice), opPrice);

                                                } else {
                                                    sub = OUtil.sub(opPrice, Double.valueOf(lastPrice));
                                                }
                                            }
                                        }
                                        price = foreign.getPrice();
                                        priceChange = foreign.getPriceChange();
                                        double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格
                                        double div2 = OUtil.div(sub, Double.valueOf(priceChange), 1);
                                        int moneyType = itemdata.getMoneyType();

                                        double multiple = 1;
                                        if (moneyType == 0) {
                                            multiple = 1;
                                        } else if (moneyType == 1) {
                                            multiple = 10;
                                        }

                                        double div_incomeshipan = OUtil.div(OUtil.mul(mul2, div2), multiple, 1);

                                        incomeShipan = OUtil.mul(div_incomeshipan, itemdata.getVolume());

                                        //   Log.d("print", "getPosition:284: "+incomeShipan);
                                        incomelist.add(incomeShipan);
                                    }


                                }


                            }
                            for (OPositionEntity.DataBean itemdata : data) {

                                for (OApiEntity.StockIndexCommdsBean stockIndexCommdsBean : stockIndexCommds) {

                                    String contcode = itemdata.getContCode().replaceAll("[^a-z^A-Z]", "");
                                    if (stockIndexCommdsBean.getCode().equals(contcode)) {

                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (contcode.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                lastPrice = split[2];
                                            }
                                        }
                                        if (lastPrice != null) {
                                            opPrice = itemdata.getOpPrice();
                                            if (opPrice != 0.0) {
                                                if (itemdata.isIsBuy() == true) {
                                                    sub = OUtil.sub(Double.valueOf(lastPrice), opPrice);

                                                } else {
                                                    sub = OUtil.sub(opPrice, Double.valueOf(lastPrice));
                                                }
                                            }
                                        }

                                        price = stockIndexCommdsBean.getPrice();
                                        priceChange = stockIndexCommdsBean.getPriceChange();

                                        double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                                        double div2 = OUtil.div(sub, Double.valueOf(priceChange), 1);
                                        int moneyType = itemdata.getMoneyType();

                                        double multiple = 1;
                                        if (moneyType == 0) {
                                            multiple = 1;
                                        } else if (moneyType == 1) {
                                            multiple = 10;
                                        }
                                        double div_incomeshipan = OUtil.div(OUtil.mul(mul2, div2), multiple, 1);

                                        incomeShipan = OUtil.mul(div_incomeshipan, itemdata.getVolume());

                                        //Log.d("print", "getPosition:284: "+incomeForeign);
                                        incomelist.add(incomeShipan);
                                    }
                                }
                            }
                            for (OPositionEntity.DataBean itemdata : data) {
                                for (OApiEntity.DomesticCommdsBean domesticCommdsBean : domesticCommds) {
                                    String contcode = itemdata.getContCode().replaceAll("[^a-z^A-Z]", "");
                                    if (domesticCommdsBean.getCode().equals(contcode)) {
                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (contcode.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                lastPrice = split[2];
                                            }
                                        }
                                        if (lastPrice != null) {
                                            opPrice = itemdata.getOpPrice();
                                            if (opPrice != 0.0) {
                                                if (itemdata.isIsBuy() == true) {
                                                    sub = OUtil.sub(Double.valueOf(lastPrice), opPrice);

                                                } else {
                                                    sub = OUtil.sub(opPrice, Double.valueOf(lastPrice));
                                                }
                                            }
                                        }

                                        price = domesticCommdsBean.getPrice();
                                        priceChange = domesticCommdsBean.getPriceChange();

                                        double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                                        double div2 = OUtil.div(sub, Double.valueOf(priceChange), 1);
                                        int moneyType = itemdata.getMoneyType();

                                        double multiple = 1;
                                        if (moneyType == 0) {
                                            multiple = 1;
                                        } else if (moneyType == 1) {
                                            multiple = 10;
                                        }
                                        double div_incomeshipan = OUtil.div(OUtil.mul(mul2, div2), multiple, 1);
                                        incomeShipan = OUtil.mul(div_incomeshipan, itemdata.getVolume());

                                        incomelist.add(incomeShipan);
                                    }
                                }
                            }

                            double income = 0;
                            for (int i = 0; i < incomelist.size(); i++) {

                                income += incomelist.get(i);

                                if (text_profit != null) {
                                    double div = OUtil.div(income, 1.0, 1);
                                    text_profit.setText(div + "");
                                    //double div_income = OUtil.div(income, multiple, 1);
                                    QuoteProxy.getInstance().setShipanincome(income);

                                }
                            }
                        }
                    } else {
                        QuoteProxy.getInstance().setShipanincome(0.0);
                        if (text_profit != null) {
                            text_profit.setText("0.0");
                        }
                    }
                } else {
                    SPUtils.remove(OUserConfig.USER);
                    Toast.makeText(getActivity(), "登录已失效,请重新登录", Toast.LENGTH_SHORT).show();
                }
            }
            //单个的盈亏

            if (marketCode_bus == null || marketCode_bus != marketCode) {
                if (isAdded()) {
                    view_line.setVisibility(View.GONE);
                    stay_prift.setVisibility(View.GONE);
                    text_profit.setText("--");
                    text_volume.setText("----");
                    text_id.setText("仅平仓(ID: --)");
                }
                return;
            }

            for (String quote : dataList) {
                String[] split = quote.split(",");
                if (marketCode_bus.replaceAll("[^a-z^A-Z]", "").equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                    lastPrice = split[2];
                }
            }
            String priceChange_item = null;
            String price_item = null;
            double sub_item = 0;
            if (isBuy_bus == true) {
                sub_item = OUtil.sub(Double.valueOf(lastPrice), opPrice_bus);
            } else {
                sub_item = OUtil.sub(opPrice_bus, Double.valueOf(lastPrice));
            }
            double income_item = 0;
            double allcome_item = 0;

            for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                if (foreign.getCode().equals(marketCode_bus.replaceAll("[^a-z^A-Z]", ""))) {
                    price_item = foreign.getPrice();
                    priceChange_item = foreign.getPriceChange();

                    double mul2 = OUtil.mul(Double.valueOf(price_item), Double.valueOf(priceChange_item));//单点波动的价格

                    double div2 = OUtil.div(sub_item, Double.valueOf(priceChange_item), 1);

                    income_item = OUtil.mul(mul2, div2);
                    allcome_item = OUtil.mul(income_item, volume_bus);


                }
            }

            for (OApiEntity.StockIndexCommdsBean foreign : stockIndexCommds) {
                if (foreign.getCode().equals(marketCode_bus.replaceAll("[^a-z^A-Z]", ""))) {
                    price_item = foreign.getPrice();
                    priceChange_item = foreign.getPriceChange();

                    double mul2 = OUtil.mul(Double.valueOf(price_item), Double.valueOf(priceChange_item));//单点波动的价格

                    double div2 = OUtil.div(sub_item, Double.valueOf(priceChange_item), 1);

                    income_item = OUtil.mul(mul2, div2);
                    allcome_item = OUtil.mul(income_item, volume_bus);
                }
            }
            for (OApiEntity.DomesticCommdsBean foreign : domesticCommds) {
                if (foreign.getCode().equals(marketCode_bus.replaceAll("[^a-z^A-Z]", ""))) {
                    price_item = foreign.getPrice();
                    priceChange_item = foreign.getPriceChange();

                    double mul2 = OUtil.mul(Double.valueOf(price_item), Double.valueOf(priceChange_item));//单点波动的价格

                    double div2 = OUtil.div(sub_item, Double.valueOf(priceChange_item), 1);

                    income_item = OUtil.mul(mul2, div2);
                    allcome_item = OUtil.mul(income_item, volume_bus);
                }
            }
            //Log.d("print", "onBindViewHolder:230模式:"+moneyType);
            double multiple = 1;
            if (moneyType_bus == 0) {
                multiple = 1;
            } else if (moneyType_bus == 1) {
                multiple = 10;
            }

            double all_div = OUtil.div(allcome_item, multiple, 1);

            double lastincome = OUtil.double1Point(all_div);
            Log.d("print", "getPosition:877:   " + lastincome);

            List<OPositionEntity.DataBean> data = oPositionEntity.getData();
            if (data.size() != 0) {
                idShipanList = new ArrayList<>();
                for (OPositionEntity.DataBean dataBean : data) {
                    idShipanList.add(dataBean.getId());
                }

                if (idShipanList.toString().contains(id_bus)) {
                    if (opPrice_bus == 0.0) {
                        text_profit.setText("加载中");
                    } else {
                        text_profit.setText(lastincome + "");
                    }
                } else {
                    if (isAdded()) {
                        view_line.setVisibility(View.GONE);
                        stay_prift.setVisibility(View.GONE);
                        text_profit.setText("--");
                        text_volume.setText("----");
                        text_id.setText("仅平仓(ID: --)");
                    }
                }

            } else {
                if (isAdded()) {
                    view_line.setVisibility(View.GONE);
                    stay_prift.setVisibility(View.GONE);
                    text_profit.setText("--");
                    text_volume.setText("----");
                    text_id.setText("仅平仓(ID: --)");
                }
            }


        } else if (isTrade.equals("2")) {

            OPositionEntity oPositionEntity1 = QuoteProxy.getInstance().getoPositionMoniEntity();

            if (oPositionEntity1 != null) {
                if (oPositionEntity1.isIsLogin() == true) {
                    balance = String.valueOf(oPositionEntity1.getAsset().getGame());
                    if (text_balance != null) {
                        text_balance.setText(balance);
                    }
                    List<OPositionEntity.DataBean> data = oPositionEntity1.getData();


                    if (data.size() != 0) {
                        incomelist = new ArrayList<>();

                        if (foreignCommds != null) {
                            for (OPositionEntity.DataBean itemdata : data) {
                                for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                                    String contcode = itemdata.getContCode().replaceAll("[^a-z^A-Z]", "");
                                    if (foreign.getCode().equals(contcode)) {
                                        int moneyType = itemdata.getMoneyType();
                                        double multiple_moni = 1;
                                        if (moneyType == 0) {
                                            multiple_moni = 1;
                                        } else if (moneyType == 1) {
                                            multiple_moni = 10;
                                        }

                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (contcode.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                lastPrice = split[2];
                                            }
                                        }

                                        if (lastPrice != null) {
                                            opPrice = itemdata.getOpPrice();
                                            if (opPrice != 0.0) {
                                                if (itemdata.isIsBuy() == true) {
                                                    sub = OUtil.sub(Double.valueOf(lastPrice), opPrice);

                                                } else {
                                                    sub = OUtil.sub(opPrice, Double.valueOf(lastPrice));
                                                }
                                            }
                                        }

                                        price = foreign.getPrice();
                                        priceChange = foreign.getPriceChange();

                                        double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                                        double div2 = OUtil.div(sub, Double.valueOf(priceChange), 1);
                                        double div_incomemoni = OUtil.div(OUtil.mul(mul2, div2), multiple_moni, 1);

                                        incomeMoni = OUtil.mul(div_incomemoni, itemdata.getVolume());
                                        incomelist.add(incomeMoni);
                                    }

                                }


                            }

                            for (OPositionEntity.DataBean itemdata : data) {
                                for (OApiEntity.StockIndexCommdsBean stockIndexCommdsBean : stockIndexCommds) {
                                    String contcode = itemdata.getContCode().replaceAll("[^a-z^A-Z]", "");
                                    if (stockIndexCommdsBean.getCode().equals(contcode)) {

                                        int moneyType = itemdata.getMoneyType();
                                        double multiple_moni = 1;
                                        if (moneyType == 0) {
                                            multiple_moni = 1;
                                        } else if (moneyType == 1) {
                                            multiple_moni = 10;
                                        }

                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (contcode.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                lastPrice = split[2];
                                            }
                                        }

                                        if (lastPrice != null) {
                                            opPrice = itemdata.getOpPrice();
                                            if (opPrice != 0.0) {
                                                if (itemdata.isIsBuy() == true) {
                                                    sub = OUtil.sub(Double.valueOf(lastPrice), opPrice);

                                                } else {
                                                    sub = OUtil.sub(opPrice, Double.valueOf(lastPrice));
                                                }
                                            }
                                        }


                                        price = stockIndexCommdsBean.getPrice();
                                        priceChange = stockIndexCommdsBean.getPriceChange();

                                        double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                                        double div2 = OUtil.div(sub, Double.valueOf(priceChange), 1);
                                        double div_incomemoni = OUtil.div(OUtil.mul(mul2, div2), multiple_moni, 1);

                                        incomeMoni = OUtil.mul(div_incomemoni, itemdata.getVolume());
                                        incomelist.add(incomeMoni);
                                    }


                                }
                            }
                            for (OPositionEntity.DataBean itemdata : data) {
                                for (OApiEntity.DomesticCommdsBean domesticCommdsBean : domesticCommds) {
                                    String contcode = itemdata.getContCode().replaceAll("[^a-z^A-Z]", "");
                                    if (domesticCommdsBean.getCode().equals(contcode)) {
                                        int moneyType = itemdata.getMoneyType();
                                        double multiple_moni = 1;
                                        if (moneyType == 0) {
                                            multiple_moni = 1;
                                        } else if (moneyType == 1) {
                                            multiple_moni = 10;
                                        }

                                        for (String quote : dataList) {
                                            String[] split = quote.split(",");
                                            if (contcode.equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                                lastPrice = split[2];
                                            }
                                        }

                                        if (lastPrice != null) {
                                            opPrice = itemdata.getOpPrice();
                                            if (opPrice != 0.0) {
                                                if (itemdata.isIsBuy() == true) {
                                                    sub = OUtil.sub(Double.valueOf(lastPrice), opPrice);

                                                } else {
                                                    sub = OUtil.sub(opPrice, Double.valueOf(lastPrice));
                                                }
                                            }
                                        }
                                        price = domesticCommdsBean.getPrice();
                                        priceChange = domesticCommdsBean.getPriceChange();

                                        double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                                        double div2 = OUtil.div(sub, Double.valueOf(priceChange), 1);

                                        double div_incomemoni = OUtil.div(OUtil.mul(mul2, div2), multiple_moni, 1);

                                        incomeMoni = OUtil.mul(div_incomemoni, itemdata.getVolume());

                                        incomelist.add(incomeMoni);
                                    }

                                }
                            }

                            Log.d("print", "getPosition:1115:   " + incomelist);
                            double income = 0;
                            for (int i = 0; i < incomelist.size(); i++) {
                                income += incomelist.get(i);
                                if (text_profit != null) {
                                    double div = OUtil.div(income, 1.0, 1);
                                    // text_profit.setText(div + "");
                                    QuoteProxy.getInstance().setMoniincome(income);

                                }
                            }

                        }

                    } else {
                        QuoteProxy.getInstance().setMoniincome(0.0);
                        if (text_profit != null) {
                            text_profit.setText("0.0");
                        }

                    }
                } else {
                    SPUtils.remove(OUserConfig.USER);
                    Toast.makeText(getActivity(), "登录已失效,请重新登录", Toast.LENGTH_SHORT).show();
                }
            }


            //单个的盈亏
            if (marketCode_bus == null || marketCode_bus != marketCode) {
                if (isAdded()) {
                    view_line.setVisibility(View.GONE);
                    stay_prift.setVisibility(View.GONE);
                    text_profit.setText("--");
                    text_volume.setText("----");
                    text_id.setText("仅平仓(ID: --)");
                }
                return;
            }

            for (String quote : dataList) {
                String[] split = quote.split(",");
                if (marketCode_bus.replaceAll("[^a-z^A-Z]", "").equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                    lastPrice = split[2];
                }
            }
            String priceChange_item = null;
            String price_item = null;
            double sub_item = 0;
            if (isBuy_bus == true) {
                sub_item = OUtil.sub(Double.valueOf(lastPrice), opPrice_bus);
            } else {
                sub_item = OUtil.sub(opPrice_bus, Double.valueOf(lastPrice));
            }
            double income_item = 0;
            double allcome_item = 0;

            for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                if (foreign.getCode().equals(marketCode_bus.replaceAll("[^a-z^A-Z]", ""))) {
                    price_item = foreign.getPrice();
                    priceChange_item = foreign.getPriceChange();

                    double mul2 = OUtil.mul(Double.valueOf(price_item), Double.valueOf(priceChange_item));//单点波动的价格

                    double div2 = OUtil.div(sub_item, Double.valueOf(priceChange_item), 1);

                    income_item = OUtil.mul(mul2, div2);
                    allcome_item = OUtil.mul(income_item, volume_bus);


                }
            }

            for (OApiEntity.StockIndexCommdsBean foreign : stockIndexCommds) {
                if (foreign.getCode().equals(marketCode_bus.replaceAll("[^a-z^A-Z]", ""))) {
                    price_item = foreign.getPrice();
                    priceChange_item = foreign.getPriceChange();

                    double mul2 = OUtil.mul(Double.valueOf(price_item), Double.valueOf(priceChange_item));//单点波动的价格

                    double div2 = OUtil.div(sub_item, Double.valueOf(priceChange_item), 1);

                    income_item = OUtil.mul(mul2, div2);
                    allcome_item = OUtil.mul(income_item, volume_bus);
                }
            }
            for (OApiEntity.DomesticCommdsBean foreign : domesticCommds) {
                if (foreign.getCode().equals(marketCode_bus.replaceAll("[^a-z^A-Z]", ""))) {
                    price_item = foreign.getPrice();
                    priceChange_item = foreign.getPriceChange();

                    double mul2 = OUtil.mul(Double.valueOf(price_item), Double.valueOf(priceChange_item));//单点波动的价格

                    double div2 = OUtil.div(sub_item, Double.valueOf(priceChange_item), 1);

                    income_item = OUtil.mul(mul2, div2);
                    allcome_item = OUtil.mul(income_item, volume_bus);
                }
            }
            //Log.d("print", "onBindViewHolder:230模式:"+moneyType);
            double multiple = 1;
            if (moneyType_bus == 0) {
                multiple = 1;
            } else if (moneyType_bus == 1) {
                multiple = 10;
            }

            double all_div = OUtil.div(allcome_item, multiple, 1);

            double lastincome = OUtil.double1Point(all_div);

            List<OPositionEntity.DataBean> data = oPositionEntity1.getData();
            if (data.size() != 0) {
                idMoniList = new ArrayList<>();
                for (OPositionEntity.DataBean dataBean : data) {
                    idMoniList.add(dataBean.getId());
                }

                if (idMoniList.toString().contains(id_bus)) {
                    if (opPrice_bus == 0.0) {
                        text_profit.setText("加载中");
                    } else {
                        text_profit.setText(lastincome + "");
                    }
                } else {
                    if (isAdded()) {

                        view_line.setVisibility(View.GONE);
                        stay_prift.setVisibility(View.GONE);
                        text_profit.setText("--");
                        text_volume.setText("----");
                        text_id.setText("仅平仓(ID: --)");
                    }
                }

            } else {
                if (isAdded()) {
                    view_line.setVisibility(View.GONE);
                    stay_prift.setVisibility(View.GONE);
                    text_profit.setText("--");
                    text_volume.setText("----");
                    text_id.setText("仅平仓(ID: --)");
                }
            }
        }
    }


    private void getName() {
        OApiEntity oApiEntity = SPUtils.getData(OUserConfig.API, OApiEntity.class);

        ORuleEntity ruleEntity = SPUtils.getData("rule", ORuleEntity.class);

        String code = ruleEntity.getYM().getCode();
        if (marketCode.contains(code)) {
            unit = ruleEntity.getYM().getUnit();
            volatility = ruleEntity.getYM().getVolatility();
            volatilityIncome = ruleEntity.getYM().getVolatilityIncome();
            buyTimeAM = ruleEntity.getYM().getBuyTimeAM();
            sellTimeAM = ruleEntity.getYM().getSellTimeAM();
            clearTime = ruleEntity.getYM().getClearTime();
            rate = ruleEntity.getYM().getRate();
            rateDetail = ruleEntity.getYM().getRateDetail();
            introduce = ruleEntity.getYM().getIntroduce();
            chargeUnit = ruleEntity.getYM().getChargeUnit();
            highLimit = ruleEntity.getYM().getHighLimit();
            lowLimit = ruleEntity.getYM().getLowLimit();
            highClose = ruleEntity.getYM().getHighClose();
            lowClose = ruleEntity.getYM().getLowClose();
        }
        String nqcode = ruleEntity.getNQ().getCode();
        if (marketCode.contains(nqcode)) {
            unit = ruleEntity.getNQ().getUnit();
            volatility = ruleEntity.getNQ().getVolatility();
            volatilityIncome = ruleEntity.getNQ().getVolatilityIncome();
            buyTimeAM = ruleEntity.getNQ().getBuyTimeAM();
            sellTimeAM = ruleEntity.getNQ().getSellTimeAM();
            clearTime = ruleEntity.getNQ().getClearTime();
            rate = ruleEntity.getNQ().getRate();
            rateDetail = ruleEntity.getNQ().getRateDetail();

            introduce = ruleEntity.getNQ().getIntroduce();
            chargeUnit = ruleEntity.getNQ().getChargeUnit();
            highLimit = ruleEntity.getNQ().getHighLimit();
            lowLimit = ruleEntity.getNQ().getLowLimit();
            highClose = ruleEntity.getNQ().getHighClose();
            lowClose = ruleEntity.getNQ().getLowClose();
        }

        String hgcode = ruleEntity.getHG().getCode();
        if (marketCode.contains(hgcode)) {
            unit = ruleEntity.getHG().getUnit();
            volatility = ruleEntity.getHG().getVolatility();
            volatilityIncome = ruleEntity.getHG().getVolatilityIncome();
            buyTimeAM = ruleEntity.getHG().getBuyTimeAM();
            sellTimeAM = ruleEntity.getHG().getSellTimeAM();
            clearTime = ruleEntity.getHG().getClearTime();
            rate = ruleEntity.getHG().getRate();
            rateDetail = ruleEntity.getHG().getRateDetail();

            introduce = ruleEntity.getHG().getIntroduce();
            chargeUnit = ruleEntity.getHG().getChargeUnit();
            highLimit = ruleEntity.getHG().getHighLimit();
            lowLimit = ruleEntity.getHG().getLowLimit();
            highClose = ruleEntity.getHG().getHighClose();
            lowClose = ruleEntity.getHG().getLowClose();
        }

        String hccode = ruleEntity.getHC().getCode();
        if (marketCode.contains(hccode)) {
            unit = ruleEntity.getHC().getUnit();
            volatility = ruleEntity.getHC().getVolatility();
            volatilityIncome = ruleEntity.getHC().getVolatilityIncome();
            buyTimeAM = ruleEntity.getHC().getBuyTimeAM();
            sellTimeAM = ruleEntity.getHC().getSellTimeAM();
            clearTime = ruleEntity.getHC().getClearTime();
            rate = ruleEntity.getHC().getRate();
            rateDetail = ruleEntity.getHC().getRateDetail();

            introduce = ruleEntity.getHC().getIntroduce();
            chargeUnit = ruleEntity.getHC().getChargeUnit();
            highLimit = ruleEntity.getHC().getHighLimit();
            lowLimit = ruleEntity.getHC().getLowLimit();
            highClose = ruleEntity.getHC().getHighClose();
            lowClose = ruleEntity.getHC().getLowClose();
        }

        String hsicode = ruleEntity.getHSI().getCode();
        if (marketCode.contains(hsicode)) {
            unit = ruleEntity.getHSI().getUnit();
            volatility = ruleEntity.getHSI().getVolatility();
            volatilityIncome = ruleEntity.getHSI().getVolatilityIncome();
            buyTimeAM = ruleEntity.getHSI().getBuyTimeAM();
            sellTimeAM = ruleEntity.getHSI().getSellTimeAM();
            clearTime = ruleEntity.getHSI().getClearTime();
            rate = ruleEntity.getHSI().getRate();
            rateDetail = ruleEntity.getHSI().getRateDetail();

            introduce = ruleEntity.getHSI().getIntroduce();
            chargeUnit = ruleEntity.getHSI().getChargeUnit();
            highLimit = ruleEntity.getHSI().getHighLimit();
            lowLimit = ruleEntity.getHSI().getLowLimit();
            highClose = ruleEntity.getHSI().getHighClose();
            lowClose = ruleEntity.getHSI().getLowClose();
        }

        String mdaxcode = ruleEntity.getMDAX().getCode();
        if (codeNoNum.equals(mdaxcode)) {
            unit = ruleEntity.getMDAX().getUnit();
            volatility = ruleEntity.getMDAX().getVolatility();
            volatilityIncome = ruleEntity.getMDAX().getVolatilityIncome();
            buyTimeAM = ruleEntity.getMDAX().getBuyTimeAM();
            sellTimeAM = ruleEntity.getMDAX().getSellTimeAM();
            clearTime = ruleEntity.getMDAX().getClearTime();
            rate = ruleEntity.getMDAX().getRate();
            rateDetail = ruleEntity.getMDAX().getRateDetail();

            introduce = ruleEntity.getMDAX().getIntroduce();
            chargeUnit = ruleEntity.getMDAX().getChargeUnit();
            highLimit = ruleEntity.getMDAX().getHighLimit();
            lowLimit = ruleEntity.getMDAX().getLowLimit();
            highClose = ruleEntity.getMDAX().getHighClose();
            lowClose = ruleEntity.getMDAX().getLowClose();
        }


        String macode = ruleEntity.getMA().getCode();
        if (marketCode.contains(macode)) {
            unit = ruleEntity.getMA().getUnit();
            volatility = ruleEntity.getMA().getVolatility();
            volatilityIncome = ruleEntity.getMA().getVolatilityIncome();
            buyTimeAM = ruleEntity.getMA().getBuyTimeAM();
            sellTimeAM = ruleEntity.getMA().getSellTimeAM();
            clearTime = ruleEntity.getMA().getClearTime();
            rate = ruleEntity.getMA().getRate();
            rateDetail = ruleEntity.getMA().getRateDetail();

            introduce = ruleEntity.getMA().getIntroduce();
            chargeUnit = ruleEntity.getMA().getChargeUnit();
            highLimit = ruleEntity.getMA().getHighLimit();
            lowLimit = ruleEntity.getMA().getLowLimit();
            highClose = ruleEntity.getMA().getHighClose();
            lowClose = ruleEntity.getMA().getLowClose();
        }


        String mhicode = ruleEntity.getMHI().getCode();
        if (marketCode.contains(mhicode)) {
            unit = ruleEntity.getMHI().getUnit();
            volatility = ruleEntity.getMHI().getVolatility();
            volatilityIncome = ruleEntity.getMHI().getVolatilityIncome();
            buyTimeAM = ruleEntity.getMHI().getBuyTimeAM();
            sellTimeAM = ruleEntity.getMHI().getSellTimeAM();
            clearTime = ruleEntity.getMHI().getClearTime();
            rate = ruleEntity.getMHI().getRate();
            rateDetail = ruleEntity.getMHI().getRateDetail();

            introduce = ruleEntity.getMHI().getIntroduce();
            chargeUnit = ruleEntity.getMHI().getChargeUnit();
            highLimit = ruleEntity.getMHI().getHighLimit();
            lowLimit = ruleEntity.getMHI().getLowLimit();
            highClose = ruleEntity.getMHI().getHighClose();
            lowClose = ruleEntity.getMHI().getLowClose();
        }

        String clcode = ruleEntity.getCL().getCode();
        if (marketCode.contains(clcode)) {
            unit = ruleEntity.getCL().getUnit();
            volatility = ruleEntity.getCL().getVolatility();
            volatilityIncome = ruleEntity.getCL().getVolatilityIncome();
            buyTimeAM = ruleEntity.getCL().getBuyTimeAM();
            sellTimeAM = ruleEntity.getCL().getSellTimeAM();
            clearTime = ruleEntity.getCL().getClearTime();
            rate = ruleEntity.getCL().getRate();
            rateDetail = ruleEntity.getCL().getRateDetail();

            introduce = ruleEntity.getCL().getIntroduce();
            chargeUnit = ruleEntity.getCL().getChargeUnit();
            highLimit = ruleEntity.getCL().getHighLimit();
            lowLimit = ruleEntity.getCL().getLowLimit();
            highClose = ruleEntity.getCL().getHighClose();
            lowClose = ruleEntity.getCL().getLowClose();
        }

        String gccode = ruleEntity.getGC().getCode();
        if (marketCode.contains(gccode)) {
            unit = ruleEntity.getGC().getUnit();
            volatility = ruleEntity.getGC().getVolatility();
            volatilityIncome = ruleEntity.getGC().getVolatilityIncome();
            buyTimeAM = ruleEntity.getGC().getBuyTimeAM();
            sellTimeAM = ruleEntity.getGC().getSellTimeAM();
            clearTime = ruleEntity.getGC().getClearTime();
            rate = ruleEntity.getGC().getRate();
            rateDetail = ruleEntity.getGC().getRateDetail();

            introduce = ruleEntity.getGC().getIntroduce();
            chargeUnit = ruleEntity.getGC().getChargeUnit();
            highLimit = ruleEntity.getGC().getHighLimit();
            lowLimit = ruleEntity.getGC().getLowLimit();
            highClose = ruleEntity.getGC().getHighClose();
            lowClose = ruleEntity.getGC().getLowClose();
        }

        String sicode = ruleEntity.getSI().getCode();
        if (marketCode.contains(sicode)) {
            unit = ruleEntity.getSI().getUnit();
            volatility = ruleEntity.getSI().getVolatility();
            volatilityIncome = ruleEntity.getSI().getVolatilityIncome();
            buyTimeAM = ruleEntity.getSI().getBuyTimeAM();
            sellTimeAM = ruleEntity.getSI().getSellTimeAM();
            clearTime = ruleEntity.getSI().getClearTime();
            rate = ruleEntity.getSI().getRate();
            rateDetail = ruleEntity.getSI().getRateDetail();

            introduce = ruleEntity.getSI().getIntroduce();
            chargeUnit = ruleEntity.getSI().getChargeUnit();
            highLimit = ruleEntity.getSI().getHighLimit();
            lowLimit = ruleEntity.getSI().getLowLimit();
            highClose = ruleEntity.getSI().getHighClose();
            lowClose = ruleEntity.getSI().getLowClose();
        }

        String cncode = ruleEntity.getCN().getCode();
        if (marketCode.contains(cncode)) {
            unit = ruleEntity.getCN().getUnit();
            volatility = ruleEntity.getCN().getVolatility();
            volatilityIncome = ruleEntity.getCN().getVolatilityIncome();
            buyTimeAM = ruleEntity.getCN().getBuyTimeAM();
            sellTimeAM = ruleEntity.getCN().getSellTimeAM();
            clearTime = ruleEntity.getCN().getClearTime();
            rate = ruleEntity.getCN().getRate();
            rateDetail = ruleEntity.getCN().getRateDetail();

            introduce = ruleEntity.getCN().getIntroduce();
            chargeUnit = ruleEntity.getCN().getChargeUnit();
            highLimit = ruleEntity.getCN().getHighLimit();
            lowLimit = ruleEntity.getCN().getLowLimit();
            highClose = ruleEntity.getCN().getHighClose();
            lowClose = ruleEntity.getCN().getLowClose();
        }
        String daxcode = ruleEntity.getDAX().getCode();
        if (codeNoNum.equals(daxcode)) {
            unit = ruleEntity.getDAX().getUnit();
            volatility = ruleEntity.getDAX().getVolatility();
            volatilityIncome = ruleEntity.getDAX().getVolatilityIncome();
            buyTimeAM = ruleEntity.getDAX().getBuyTimeAM();
            sellTimeAM = ruleEntity.getDAX().getSellTimeAM();
            clearTime = ruleEntity.getDAX().getClearTime();
            rate = ruleEntity.getDAX().getRate();
            rateDetail = ruleEntity.getDAX().getRateDetail();

            introduce = ruleEntity.getDAX().getIntroduce();
            chargeUnit = ruleEntity.getDAX().getChargeUnit();
            highLimit = ruleEntity.getDAX().getHighLimit();
            lowLimit = ruleEntity.getDAX().getLowLimit();
            highClose = ruleEntity.getDAX().getHighClose();
            lowClose = ruleEntity.getDAX().getLowClose();
        }

        String rbcode = ruleEntity.getRB().getCode();
        if (marketCode.contains(rbcode)) {
            unit = ruleEntity.getRB().getUnit();
            volatility = ruleEntity.getRB().getVolatility();
            volatilityIncome = ruleEntity.getRB().getVolatilityIncome();
            buyTimeAM = ruleEntity.getRB().getBuyTimeAM();
            sellTimeAM = ruleEntity.getRB().getSellTimeAM();
            clearTime = ruleEntity.getRB().getClearTime();
            rate = ruleEntity.getRB().getRate();
            rateDetail = ruleEntity.getRB().getRateDetail();

            introduce = ruleEntity.getRB().getIntroduce();
            chargeUnit = ruleEntity.getRB().getChargeUnit();
            highLimit = ruleEntity.getRB().getHighLimit();
            lowLimit = ruleEntity.getRB().getLowLimit();
            highClose = ruleEntity.getRB().getHighClose();
            lowClose = ruleEntity.getRB().getLowClose();
        }

        String ppcode = ruleEntity.getPP().getCode();
        if (marketCode.contains(ppcode)) {
            unit = ruleEntity.getPP().getUnit();
            volatility = ruleEntity.getPP().getVolatility();
            volatilityIncome = ruleEntity.getPP().getVolatilityIncome();
            buyTimeAM = ruleEntity.getPP().getBuyTimeAM();
            sellTimeAM = ruleEntity.getPP().getSellTimeAM();
            clearTime = ruleEntity.getPP().getClearTime();
            rate = ruleEntity.getPP().getRate();
            rateDetail = ruleEntity.getPP().getRateDetail();

            introduce = ruleEntity.getPP().getIntroduce();
            chargeUnit = ruleEntity.getPP().getChargeUnit();
            highLimit = ruleEntity.getPP().getHighLimit();
            lowLimit = ruleEntity.getPP().getLowLimit();
            highClose = ruleEntity.getPP().getHighClose();
            lowClose = ruleEntity.getPP().getLowClose();
        }

        String nicode = ruleEntity.getNI().getCode();
        if (marketCode.contains(nicode)) {
            unit = ruleEntity.getNI().getUnit();
            volatility = ruleEntity.getNI().getVolatility();
            volatilityIncome = ruleEntity.getNI().getVolatilityIncome();
            buyTimeAM = ruleEntity.getNI().getBuyTimeAM();
            sellTimeAM = ruleEntity.getNI().getSellTimeAM();
            clearTime = ruleEntity.getNI().getClearTime();
            rate = ruleEntity.getNI().getRate();
            rateDetail = ruleEntity.getNI().getRateDetail();

            introduce = ruleEntity.getNI().getIntroduce();
            chargeUnit = ruleEntity.getNI().getChargeUnit();
            highLimit = ruleEntity.getNI().getHighLimit();
            lowLimit = ruleEntity.getNI().getLowLimit();
            highClose = ruleEntity.getNI().getHighClose();
            lowClose = ruleEntity.getNI().getLowClose();
        }
        String cucode = ruleEntity.getCU().getCode();
        if (marketCode.contains(cucode)) {
            unit = ruleEntity.getCU().getUnit();
            volatility = ruleEntity.getCU().getVolatility();
            volatilityIncome = ruleEntity.getCU().getVolatilityIncome();
            buyTimeAM = ruleEntity.getCU().getBuyTimeAM();
            sellTimeAM = ruleEntity.getCU().getSellTimeAM();
            clearTime = ruleEntity.getCU().getClearTime();
            rate = ruleEntity.getCU().getRate();
            rateDetail = ruleEntity.getCU().getRateDetail();

            introduce = ruleEntity.getCU().getIntroduce();
            chargeUnit = ruleEntity.getCU().getChargeUnit();
            highLimit = ruleEntity.getCU().getHighLimit();
            lowLimit = ruleEntity.getCU().getLowLimit();
            highClose = ruleEntity.getCU().getHighClose();
            lowClose = ruleEntity.getCU().getLowClose();
        }
        String agcode = ruleEntity.getAG().getCode();
        if (marketCode.contains(agcode)) {
            unit = ruleEntity.getAG().getUnit();
            volatility = ruleEntity.getAG().getVolatility();
            volatilityIncome = ruleEntity.getAG().getVolatilityIncome();
            buyTimeAM = ruleEntity.getAG().getBuyTimeAM();
            sellTimeAM = ruleEntity.getAG().getSellTimeAM();
            clearTime = ruleEntity.getAG().getClearTime();
            rate = ruleEntity.getAG().getRate();
            rateDetail = ruleEntity.getAG().getRateDetail();

            introduce = ruleEntity.getAG().getIntroduce();
            chargeUnit = ruleEntity.getAG().getChargeUnit();
            highLimit = ruleEntity.getAG().getHighLimit();
            lowLimit = ruleEntity.getAG().getLowLimit();
            highClose = ruleEntity.getAG().getHighClose();
            lowClose = ruleEntity.getAG().getLowClose();
        }

        String aucode = ruleEntity.getAU().getCode();
        if (marketCode.contains(aucode)) {
            unit = ruleEntity.getAU().getUnit();
            volatility = ruleEntity.getAU().getVolatility();
            volatilityIncome = ruleEntity.getAU().getVolatilityIncome();
            buyTimeAM = ruleEntity.getAU().getBuyTimeAM();
            sellTimeAM = ruleEntity.getAU().getSellTimeAM();
            clearTime = ruleEntity.getAU().getClearTime();
            rate = ruleEntity.getAU().getRate();
            rateDetail = ruleEntity.getAU().getRateDetail();

            introduce = ruleEntity.getAU().getIntroduce();
            chargeUnit = ruleEntity.getAU().getChargeUnit();
            highLimit = ruleEntity.getAU().getHighLimit();
            lowLimit = ruleEntity.getAU().getLowLimit();
            highClose = ruleEntity.getAU().getHighClose();
            lowClose = ruleEntity.getAU().getLowClose();
        }

        String srcode = ruleEntity.getSR().getCode();
        if (marketCode.contains(srcode)) {
            unit = ruleEntity.getSR().getUnit();
            volatility = ruleEntity.getSR().getVolatility();
            volatilityIncome = ruleEntity.getSR().getVolatilityIncome();
            buyTimeAM = ruleEntity.getSR().getBuyTimeAM();
            sellTimeAM = ruleEntity.getSR().getSellTimeAM();
            clearTime = ruleEntity.getSR().getClearTime();
            rate = ruleEntity.getSR().getRate();
            rateDetail = ruleEntity.getSR().getRateDetail();

            introduce = ruleEntity.getSR().getIntroduce();
            chargeUnit = ruleEntity.getSR().getChargeUnit();
            highLimit = ruleEntity.getSR().getHighLimit();
            lowLimit = ruleEntity.getSR().getLowLimit();
            highClose = ruleEntity.getSR().getHighClose();
            lowClose = ruleEntity.getSR().getLowClose();
        }
        String rucode = ruleEntity.getRU().getCode();
        if (marketCode.contains(rucode)) {
            unit = ruleEntity.getRU().getUnit();
            volatility = ruleEntity.getRU().getVolatility();
            volatilityIncome = ruleEntity.getRU().getVolatilityIncome();
            buyTimeAM = ruleEntity.getRU().getBuyTimeAM();
            sellTimeAM = ruleEntity.getRU().getSellTimeAM();
            clearTime = ruleEntity.getRU().getClearTime();
            rate = ruleEntity.getRU().getRate();
            rateDetail = ruleEntity.getRU().getRateDetail();

            introduce = ruleEntity.getRU().getIntroduce();
            chargeUnit = ruleEntity.getRU().getChargeUnit();
            highLimit = ruleEntity.getRU().getHighLimit();
            lowLimit = ruleEntity.getRU().getLowLimit();
            highClose = ruleEntity.getRU().getHighClose();
            lowClose = ruleEntity.getRU().getLowClose();
        }

        String iccode = ruleEntity.getIC().getCode();
        if (marketCode.contains(iccode)) {
            unit = ruleEntity.getIC().getUnit();
            volatility = ruleEntity.getIC().getVolatility();
            volatilityIncome = ruleEntity.getIC().getVolatilityIncome();
            buyTimeAM = ruleEntity.getIC().getBuyTimeAM();
            sellTimeAM = ruleEntity.getIC().getSellTimeAM();
            clearTime = ruleEntity.getIC().getClearTime();
            rate = ruleEntity.getIC().getRate();
            rateDetail = ruleEntity.getIC().getRateDetail();

            introduce = ruleEntity.getIC().getIntroduce();
            chargeUnit = ruleEntity.getIC().getChargeUnit();
            highLimit = ruleEntity.getIC().getHighLimit();
            lowLimit = ruleEntity.getIC().getLowLimit();
            highClose = ruleEntity.getIC().getHighClose();
            lowClose = ruleEntity.getIC().getLowClose();
        }
        String ihcode = ruleEntity.getIH().getCode();
        if (marketCode.contains(ihcode)) {
            unit = ruleEntity.getIH().getUnit();
            volatility = ruleEntity.getIH().getVolatility();
            volatilityIncome = ruleEntity.getIH().getVolatilityIncome();
            buyTimeAM = ruleEntity.getIH().getBuyTimeAM();
            sellTimeAM = ruleEntity.getIH().getSellTimeAM();
            clearTime = ruleEntity.getIH().getClearTime();
            rate = ruleEntity.getIH().getRate();
            rateDetail = ruleEntity.getIH().getRateDetail();

            introduce = ruleEntity.getIH().getIntroduce();
            chargeUnit = ruleEntity.getIH().getChargeUnit();
            highLimit = ruleEntity.getIH().getHighLimit();
            lowLimit = ruleEntity.getIH().getLowLimit();
            highClose = ruleEntity.getIH().getHighClose();
            lowClose = ruleEntity.getIH().getLowClose();
        }
        String ifcode = ruleEntity.getIF().getCode();
        if (marketCode.contains(ifcode)) {
            unit = ruleEntity.getIF().getUnit();
            volatility = ruleEntity.getIF().getVolatility();
            volatilityIncome = ruleEntity.getIF().getVolatilityIncome();
            buyTimeAM = ruleEntity.getIF().getBuyTimeAM();
            sellTimeAM = ruleEntity.getIF().getSellTimeAM();
            clearTime = ruleEntity.getIF().getClearTime();
            rate = ruleEntity.getIF().getRate();
            rateDetail = ruleEntity.getIF().getRateDetail();

            introduce = ruleEntity.getIF().getIntroduce();
            chargeUnit = ruleEntity.getIF().getChargeUnit();
            highLimit = ruleEntity.getIF().getHighLimit();
            lowLimit = ruleEntity.getIF().getLowLimit();
            highClose = ruleEntity.getIF().getHighClose();
            lowClose = ruleEntity.getIF().getLowClose();
        }
        String fgcode = ruleEntity.getFG().getCode();
        if (marketCode.contains(fgcode)) {
            unit = ruleEntity.getFG().getUnit();
            volatility = ruleEntity.getFG().getVolatility();
            volatilityIncome = ruleEntity.getFG().getVolatilityIncome();
            buyTimeAM = ruleEntity.getFG().getBuyTimeAM();
            sellTimeAM = ruleEntity.getFG().getSellTimeAM();
            clearTime = ruleEntity.getFG().getClearTime();
            rate = ruleEntity.getFG().getRate();
            rateDetail = ruleEntity.getFG().getRateDetail();

            introduce = ruleEntity.getFG().getIntroduce();
            chargeUnit = ruleEntity.getFG().getChargeUnit();
            highLimit = ruleEntity.getFG().getHighLimit();
            lowLimit = ruleEntity.getFG().getLowLimit();
            highClose = ruleEntity.getFG().getHighClose();
            lowClose = ruleEntity.getFG().getLowClose();
        }
        String smcode = ruleEntity.getSM().getCode();
        if (marketCode.contains(smcode)) {
            unit = ruleEntity.getSM().getUnit();
            volatility = ruleEntity.getSM().getVolatility();
            volatilityIncome = ruleEntity.getSM().getVolatilityIncome();
            buyTimeAM = ruleEntity.getSM().getBuyTimeAM();
            sellTimeAM = ruleEntity.getSM().getSellTimeAM();
            clearTime = ruleEntity.getSM().getClearTime();
            rate = ruleEntity.getSM().getRate();
            rateDetail = ruleEntity.getSM().getRateDetail();

            introduce = ruleEntity.getSM().getIntroduce();
            chargeUnit = ruleEntity.getSM().getChargeUnit();
            highLimit = ruleEntity.getSM().getHighLimit();
            lowLimit = ruleEntity.getSM().getLowLimit();
            highClose = ruleEntity.getSM().getHighClose();
            lowClose = ruleEntity.getSM().getLowClose();
        }
        String ngcode = ruleEntity.getNG().getCode();
        if (marketCode.contains(ngcode)) {
            unit = ruleEntity.getNG().getUnit();
            volatility = ruleEntity.getNG().getVolatility();
            volatilityIncome = ruleEntity.getNG().getVolatilityIncome();
            buyTimeAM = ruleEntity.getNG().getBuyTimeAM();
            sellTimeAM = ruleEntity.getNG().getSellTimeAM();
            clearTime = ruleEntity.getNG().getClearTime();
            rate = ruleEntity.getNG().getRate();
            rateDetail = ruleEntity.getNG().getRateDetail();

            introduce = ruleEntity.getNG().getIntroduce();
            chargeUnit = ruleEntity.getNG().getChargeUnit();
            highLimit = ruleEntity.getNG().getHighLimit();
            lowLimit = ruleEntity.getNG().getLowLimit();
            highClose = ruleEntity.getNG().getHighClose();
            lowClose = ruleEntity.getNG().getLowClose();
        }
        String qmcode = ruleEntity.getQM().getCode();
        if (marketCode.contains(qmcode)) {
            unit = ruleEntity.getQM().getUnit();
            volatility = ruleEntity.getQM().getVolatility();
            volatilityIncome = ruleEntity.getQM().getVolatilityIncome();
            buyTimeAM = ruleEntity.getQM().getBuyTimeAM();
            sellTimeAM = ruleEntity.getQM().getSellTimeAM();
            clearTime = ruleEntity.getQM().getClearTime();
            rate = ruleEntity.getQM().getRate();
            rateDetail = ruleEntity.getQM().getRateDetail();

            introduce = ruleEntity.getQM().getIntroduce();
            chargeUnit = ruleEntity.getQM().getChargeUnit();
            highLimit = ruleEntity.getQM().getHighLimit();
            lowLimit = ruleEntity.getQM().getLowLimit();
            highClose = ruleEntity.getQM().getHighClose();
            lowClose = ruleEntity.getQM().getLowClose();
        }
        String rbbcode = ruleEntity.getRBB().getCode();
        if (marketCode.contains(rbbcode)) {
            unit = ruleEntity.getRBB().getUnit();
            volatility = ruleEntity.getRBB().getVolatility();
            volatilityIncome = ruleEntity.getRBB().getVolatilityIncome();
            buyTimeAM = ruleEntity.getRBB().getBuyTimeAM();
            sellTimeAM = ruleEntity.getRBB().getSellTimeAM();
            clearTime = ruleEntity.getRBB().getClearTime();
            rate = ruleEntity.getRBB().getRate();
            rateDetail = ruleEntity.getRBB().getRateDetail();

            introduce = ruleEntity.getRBB().getIntroduce();
            chargeUnit = ruleEntity.getRBB().getChargeUnit();
            highLimit = ruleEntity.getRBB().getHighLimit();
            lowLimit = ruleEntity.getRBB().getLowLimit();
            highClose = ruleEntity.getRBB().getHighClose();
            lowClose = ruleEntity.getRBB().getLowClose();
        }
        String sccode = ruleEntity.getSC().getCode();
        if (marketCode.contains(sccode)) {
            unit = ruleEntity.getSC().getUnit();
            volatility = ruleEntity.getSC().getVolatility();
            volatilityIncome = ruleEntity.getSC().getVolatilityIncome();
            buyTimeAM = ruleEntity.getSC().getBuyTimeAM();
            sellTimeAM = ruleEntity.getSC().getSellTimeAM();
            clearTime = ruleEntity.getSC().getClearTime();
            rate = ruleEntity.getSC().getRate();
            rateDetail = ruleEntity.getSC().getRateDetail();

            introduce = ruleEntity.getSC().getIntroduce();
            chargeUnit = ruleEntity.getSC().getChargeUnit();
            highLimit = ruleEntity.getSC().getHighLimit();
            lowLimit = ruleEntity.getSC().getLowLimit();
            highClose = ruleEntity.getSC().getHighClose();
            lowClose = ruleEntity.getSC().getLowClose();
        }

        List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
        for (OApiEntity.ForeignCommdsBean data : foreignCommds) {
            if (marketCode.startsWith(data.getCode())) {
                this.name = data.getName();

                marketName = this.name + marketCode;
                currency = data.getCurrency();
                pricechange = Double.parseDouble(data.getPriceChange());
                price = Double.parseDouble(data.getPrice());

                volumprice = OUtil.mul(pricechange, price);

            }
        }

        List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
        for (OApiEntity.StockIndexCommdsBean data : stockIndexCommds) {
            if (marketCode.startsWith(data.getCode())) {
                this.name = data.getName();

                marketName = this.name + marketCode;
                currency = data.getCurrency();

                pricechange = Double.parseDouble(data.getPriceChange());
                price = Double.parseDouble(data.getPrice());
                volumprice = OUtil.mul(pricechange, price);

            }
        }
        List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();
        for (OApiEntity.DomesticCommdsBean data : domesticCommds) {
            if (marketCode.startsWith(data.getCode())) {
                this.name = data.getName();

                marketName = this.name + marketCode;
                currency = data.getCurrency();

                pricechange = Double.parseDouble(data.getPriceChange());
                price = Double.parseDouble(data.getPrice());
                volumprice = OUtil.mul(pricechange, price);

            }
        }

        text_name.setText(marketName);
    }

    private void getIsAdd() {
        string = SPUtils.getString(OUserConfig.MINEDEX);

        if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {

            String codeadd = string.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");
            String[] splitcode = codeadd.split(",");

            setList = new HashSet<>();
            for (String a : splitcode) {
                setList.add(a);
            }

            if (setList.contains(marketCode)) {
                img_tianjia.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_shanchu));
                text_tianjia.setText("取关");
                img_add.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_shanchu));
                text_add.setText("取关");


            } else {
                img_tianjia.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_tianjia));
                text_tianjia.setText("关注");
                img_add.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_tianjia));
                text_add.setText("关注");

            }
        } else {

        }

    }

    private void getTrendView(WebView webView) {
        String s = marketCode.replaceAll("[^a-z^A-Z]", "");
        String url = "http://www.lt168168.com/" + "?code=" + s;
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        String string = SPUtils.getString(OUserConfig.P_NIGHT);

        if (string.equals("")) {

        } else {
            if (string.equals("night")) {
                webView.setBackgroundColor(getResources().getColor(R.color.o_bar_white_night));

            } else if (string.equals("day")) {
                webView.setBackgroundColor(getResources().getColor(R.color.o_bar_white));

            }
        }
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
            }

        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getString("code");
            split = code.split(",");
            marketCode = split[0];
            Log.d("print", "onCreate:最终得到： " + code);
            codeNoNum = marketCode.replaceAll("[^a-z^A-Z]", "");
            isTrade = split[4];

        }


    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            postQuote(marketCode);
            postItemQuote(marketCode);


            getPosition();
            //刷新
            if (text_lastprice1 != null) {
                text_lastprice1.setText(timeprice + "");
                if (isAdded()) {
                    if (flag == -1) {
                        text_lastprice1.setTextColor(getResources().getColor(R.color.greencolor));
                    } else if (flag == 1) {
                        text_lastprice1.setTextColor(getResources().getColor(R.color.redcolor));
                    } else if (flag == 0) {
                        text_lastprice1.setTextColor(getResources().getColor(R.color.o_text_3433));

                    }
                }

            }

            // getLineData(type_minute);

            postForeignQuote();
            postStockQuote();
            postDomesQuote();

            eagle_result = QuoteProxy.getInstance().getEagle();
            if (eagle_result != 0 & text_jifen != null) {
                text_jifen.setText(OUtil.doublePoint(eagle_result) + "  可抵扣" + OUtil.doublePoint(OUtil.div(eagle_result, 10,1)) + "元手续费");
            }
            if (eagle_result != 0 & text_jifen_pop != null) {
                text_jifen_pop.setText("(总红包余额" + OUtil.doublePoint(eagle_result) + ",可抵扣" + OUtil.doublePoint(OUtil.div(eagle_result, 10,1)) + "元)");
            }
        }
    };

    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {

        postQuote(marketCode);

        getPosition();


    }


    private List<OKlineEntity> oKlineEntities;
    private List<OKlineEntity> oKlineHistory;
    private Runnable dataListAddRunnable;


    private long time;

    private void getLineData(String resolution) {
        Calendar nowBefore = Calendar.getInstance();
        nowBefore.add(Calendar.MINUTE, -10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       /* Log.d("print", "getTrendData:当前时间： "+sdf.format(System.currentTimeMillis()));

        Log.d("print", "getTrendData:十分钟前 "+sdf.format(nowBefore.getTimeInMillis()));*/


        OkGo.<String>get(OConstant.URL_QUOTE)
                .params(OConstant.PARAM_CALLBACK, "%3F")
                .params(OConstant.PARAM_SYMBOL, marketCode)
                .params(OConstant.PARAM_RESOLUTION, resolution)
                .params(OConstant.PARAM_FROM, OUtil.dateToStamp(sdf.format(nowBefore.getTimeInMillis())))
                .params(OConstant.PARAM_TO, System.currentTimeMillis())
                .params(OConstant.PARAM_EQUAL, System.currentTimeMillis())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        OTrendEntity data = new Gson().fromJson(response.body(), OTrendEntity.class);
                        if (data != null) {


                            if (data.getS().contains("ok")) {


                                List<Long> t = data.getT();
                                List<Double> c = data.getC();
                                List<Double> o = data.getO();
                                List<Double> h = data.getH();
                                List<Double> l = data.getL();
                                List<Double> v = data.getV();

                                oKlineEntities = new ArrayList<>();

                                for (int i = 0; i < h.size(); i++) {
                                    oKlineEntities.add(new OKlineEntity(t.get(i), o.get(i), c.get(i), h.get(i), l.get(i), v.get(i)));
                                }


                                // oKlineEntities.add(new OKlineEntity(t.get(h.size() - 1), o.get(h.size() - 1), c.get(h.size() - 1), h.get(h.size() - 1), l.get(h.size() - 1), v.get(h.size() - 1)));
/*
                                //1559308500   1559308560
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        time = t.get(t.size() - 1);
                                    }
                                }, 1000);

                                if (t.get(t.size() - 1) - time == 60) {
                                    mKLineView.refreshSingleData(getKDataList(oKlineEntities).get(0));
                                    Toast.makeText(getActivity(), "添加了" + getKDataList(oKlineEntities), Toast.LENGTH_SHORT).show();
                                }*/


                                // mKLineView.refreshSingleData(getKDataList(oKlineEntities).get(0));


                                mKLineView.initKDataList(getKDataList(oKlineEntities));

                                //mKLineView.addSingleData(getKDataList(oKlineEntities).get(0));



                       /*     //初始化控件加载数据
                            mKLineView.initKDataList(getKDataList(oKlineEntities));
                            //设置十字线移动模式，默认为0：固定指向收盘价
                            mKLineView.setCrossHairMoveMode(KLineView.CROSS_HAIR_MOVE_OPEN);*/


                            }

                        }
                    }
                });
    }

    //历史数据
    private void getLineHistoryData(String resolution, int minute) {
        Calendar nowBefore = Calendar.getInstance();
        nowBefore.add(Calendar.MINUTE, -(minute * 10));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        OkGo.<String>get(OConstant.URL_QUOTE_HISTORY)
                .params(OConstant.PARAM_SYMBOL, marketCode)
                .params(OConstant.PARAM_RESOLUTION, resolution)
                .params(OConstant.PARAM_FROM, OUtil.dateToStamp(sdf.format(nowBefore.getTimeInMillis())))
                // .params(OConstant.PARAM_FROM, OUtil.dateToStamp("2019-05-31 09:00:00"))
                .params(OConstant.PARAM_TO, System.currentTimeMillis())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        OTrendEntity data = new Gson().fromJson(response.body(), OTrendEntity.class);
                        if (data != null) {

                            if (data.getS().contains("ok")) {
                                List<Long> t = data.getT();
                                List<Double> c = data.getC();
                                List<Double> o = data.getO();
                                List<Double> h = data.getH();
                                List<Double> l = data.getL();
                                List<Double> v = data.getV();

                                oKlineHistory = new ArrayList<>();


                                for (int i = 0; i < h.size(); i++) {
                                    oKlineHistory.add(new OKlineEntity(t.get(i), o.get(i), c.get(i), h.get(i), l.get(i), v.get(i)));
                                }


                                //初始化控件加载数据
                                mKLineView.initKDataList(getKDataList(oKlineHistory));
                                //设置十字线移动模式，默认为0：固定指向收盘价
                                mKLineView.setCrossHairMoveMode(KLineView.CROSS_HAIR_MOVE_OPEN);


                            }
                        }

                    }
                });
    }

    //
    private List<KData> getKDataList(List<OKlineEntity> oKlineEntities) {
        List<KData> dataList = new ArrayList<>();
        long start;
        double openPrice;
        double closePrice;
        double maxPrice;
        double minPrice;
        double volume;

        for (int i = 0; i < oKlineEntities.size(); i++) {
            start = oKlineEntities.get(i).getTime() * 1000;
            openPrice = oKlineEntities.get(i).getOpenPrice();
            closePrice = oKlineEntities.get(i).getCloasePrice();
            maxPrice = oKlineEntities.get(i).getHighPrice();
            minPrice = oKlineEntities.get(i).getLowPrice();
            volume = oKlineEntities.get(i).getVolume();
            dataList.add(new KData(start, openPrice, closePrice, maxPrice, minPrice, volume));

        }


        return dataList;

    }

    private String quotestr;

    //头部行情的请求
    private void postQuote(String code) {


        List<String> dataList = QuoteProxy.getInstance().getDataList();

        if (dataList != null & isAdded()) {


            for (String quote : dataList) {
                String[] split = quote.split(",");

                if (code.replaceAll("[^a-z^A-Z]", "").equals(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                    quotestr = quote;
                }
            }


            if (quotestr != null) {

                String[] split = quotestr.split(",");

                String change = split[1];

                last = split[2];

                String newprice = split[3];


                flag = Integer.valueOf(change);


                double v = Double.valueOf(last);
                double v1 = Double.valueOf(newprice);


                double a = (v - v1) / v * 100;

                String numberFormat2 = Util.formatDouble2(a);


                String percent = null;
                String pricechane = null;

                if (flag == 1) {

                    percent = "+" + numberFormat2 + "%";
                    pricechane = "+" + String.valueOf(Util.sub(v, v1));


                    text_lastprice.setTextColor(getResources().getColor(R.color.redcolor));
                    text_change.setTextColor(getResources().getColor(R.color.redcolor));
                    text_percent.setTextColor(getResources().getColor(R.color.redcolor));
                    text_zhagndie.setTextColor(getResources().getColor(R.color.redcolor));
                    text_zhangfu.setTextColor(getResources().getColor(R.color.redcolor));


                } else if (flag == -1) {

                    percent = numberFormat2 + "%";
                    pricechane = String.valueOf(Util.sub(v, v1));

                    text_lastprice.setTextColor(getResources().getColor(R.color.greencolor));
                    text_change.setTextColor(getResources().getColor(R.color.greencolor));
                    text_percent.setTextColor(getResources().getColor(R.color.greencolor));
                    text_zhagndie.setTextColor(getResources().getColor(R.color.greencolor));
                    text_zhangfu.setTextColor(getResources().getColor(R.color.greencolor));
                } else if (flag == 0) {

                    percent = numberFormat2 + "%";
                    pricechane = String.valueOf(Util.sub(v, v1));

                    text_lastprice.setTextColor(getResources().getColor(R.color.o_text_3433));
                    text_change.setTextColor(getResources().getColor(R.color.o_text_3433));
                    text_percent.setTextColor(getResources().getColor(R.color.o_text_3433));
                    text_zhagndie.setTextColor(getResources().getColor(R.color.o_text_3433));
                    text_zhangfu.setTextColor(getResources().getColor(R.color.o_text_3433));
                }
                text_percent.setText(percent);

                text_lastprice.setText(last);
                //盘口的
                text_zhagndie.setText(pricechane);
                text_zhangfu.setText(percent);

                text_change.setText(pricechane);
                timeprice = Double.parseDouble(last);


                text_up.setText(String.valueOf(Util.add(timeprice, pricechange)));
                text_down.setText(String.valueOf(Util.sub(timeprice, pricechange)));


            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
        EventBus.getDefault().unregister(this);
    }

    private int light_flag = 0;

    private String light_volume = null;

    String service = null;
    String eagle = null;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (radio_1.isChecked()) {
            light_volume = radio_1.getText().toString();
        } else if (radio_2.isChecked()) {
            light_volume = radio_2.getText().toString();

        } else if (radio_3.isChecked()) {
            light_volume = radio_3.getText().toString();

        } else if (radio_4.isChecked()) {
            light_volume = radio_4.getText().toString();

        } else if (radio_5.isChecked()) {
            light_volume = radio_5.getText().toString();

        } else if (radio_6.isChecked()) {
            light_volume = radio_6.getText().toString();

        }

        switch (v.getId()) {
            case R.id.text_login:
            case R.id.text_register:
                OUserActivity.enter(getActivity(), OConstant.LOGIN);

                break;

            case R.id.layout_add:
            case R.id.layout_trade_add:
                string = SPUtils.getString(OUserConfig.MINEDEX);

                if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {
                    if (setList.contains(marketCode)) {
                        img_tianjia.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_tianjia));
                        img_add.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_tianjia));
                        setList.remove(marketCode);
                        text_tianjia.setText("关注");
                        text_add.setText("关注");

                    } else {
                        img_tianjia.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_shanchu));
                        img_add.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_shanchu));

                        setList.add(marketCode);
                        text_tianjia.setText("取关");
                        text_add.setText("取关");

                    }
                    SPUtils.putString(OUserConfig.MINEDEX, setList.toString());


                } else {

                    setList = new HashSet<>();
                    if (setList.contains(marketCode)) {
                        img_tianjia.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_tianjia));
                        img_add.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_tianjia));
                        setList.remove(marketCode);
                        text_tianjia.setText("关注");
                        text_add.setText("关注");

                    } else {
                        img_tianjia.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_shanchu));
                        img_add.setImageDrawable(getResources().getDrawable(R.mipmap.o_quote_shanchu));

                        setList.add(marketCode);
                        text_tianjia.setText("取关");
                        text_add.setText("取关");

                    }
                    SPUtils.putString(OUserConfig.MINEDEX, setList.toString());
                }
                break;
            case R.id.layout_more:
            case R.id.text_more:
                showPopWindow();
                break;
            case R.id.layout_rule:
            case R.id.layout_trade_rule:

                showRulePopWindow();
                break;

            case R.id.buy_many_position:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    if (light_flag == 1) {
                        String replace_profit = text_stopprofit.getText().toString().replaceAll("元", "");
                        String replace_unit = text_unit.getText().toString().replaceAll("元", "");
                        Log.d("print", "onClick:1776:  " + btn_flag + "----" + replace_unit);


                        if (isTrade.equals("2")) {

                            service = replace_unit;
                            eagle = "0";


                        } else if (isTrade.equals("1")) {
                            if (btn_flag == 0) {
                                service = replace_unit;
                                eagle = "0";
                            } else if (btn_flag == 1) {
                                service = "0";
                                double mul = OUtil.mul(Double.parseDouble(replace_unit), 10);
                                eagle = mul + "";
                            }
                        }

                        postOpenTrade(commCode, contName, true, "0", replace_profit, text_stoploss.getText().toString(), service, Integer.parseInt(light_volume), String.valueOf(money_type_type_light), null, eagle);

                    } else {
                        money_much_flag = 0;
                        money_much_type = 0;

                        multiple = 1;
                        showBuyManyPopWindow();
                    }
                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }
                break;

            case R.id.buy_less_position:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    if (light_flag == 1) {
                        String replace_profit = text_stopprofit.getText().toString().replaceAll("元", "");
                        String replace_unit = text_unit.getText().toString().replaceAll("元", "");
                        Log.d("print", "onClick:1815:  " + btn_flag + "----" + replace_unit);

                        if (isTrade.equals("2")) {
                            service = replace_unit;
                            eagle = "0";
                        } else if (isTrade.equals("1")) {
                            if (btn_flag == 0) {
                                service = replace_unit;
                                eagle = "0";
                            } else if (btn_flag == 1) {
                                service = "0";
                                double mul = OUtil.mul(Double.parseDouble(replace_unit), 10);
                                eagle = mul + "";
                            }
                        }


                        postOpenTrade(commCode, contName, false, "0", replace_profit, text_stoploss.getText().toString(), service, Integer.parseInt(light_volume), String.valueOf(money_type_type_light), null, eagle);


                    } else {
                        money_less_flag = 0;
                        money_less_type = 0;
                        multiple = 1;
                        showBuyLessPopWindow();

                    }
                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }
                break;

            case R.id.layout_shipan:
                isTrade = "1";
                layout_ismoni.setVisibility(View.GONE);
                layout_istrade.setVisibility(View.VISIBLE);

                layout_light_one.setVisibility(View.GONE);
                layout_light_two.setVisibility(View.GONE);
                layout_light_three.setVisibility(View.GONE);
                stay_line.setVisibility(View.GONE);

                light_flag = 0;
                img_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_shandian_hui_icon));

                EventBus.getDefault().post(new OEventData(OUserConfig.O_TRADE, "1"));

                if (oTradeListEntity != null) {

                    boolean tradeQuick = oTradeListEntity.isTradeQuick();
                    //是实盘 并且有闪电模式
                    if (isTrade.equals("1")) {
                        if (tradeQuick == true) {
                            img_light.setVisibility(View.VISIBLE);
                        } else {
                            img_light.setVisibility(View.GONE);
                        }
                    }


                }
                break;

            case R.id.text_recharge:
                if (isTrade.equals("2")) {

                    getAddScore();
                } else if (isTrade.equals("1")) {
                    ORechargeEntity oRechargeEntity = QuoteProxy.getInstance().getoRechargeEntity();
                    if (oRechargeEntity == null) {
                        Toast.makeText(getActivity(), "请求中...", Toast.LENGTH_SHORT).show();
                        postRecharge();
                    } else {
                        int payFirst = oRechargeEntity.getPayFirst();
                        if (payFirst == 1) {
                            showItemPopWindow();
                        } else {
                            OUserActivity.enter(getActivity(), OConstant.O_RECHARGE);
                        }
                    }
                }
                break;

            case R.id.text_pingcang:
                if (isTrade.equals("2")) {

                    List<String> moniCloseList = QuoteProxy.getInstance().getMoniCloseList();
                    if (moniCloseList == null) {
                        Toast.makeText(getActivity(), "当前无委托", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    String replace = moniCloseList.toString().replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");
                    postClose(id_bus, "下单");

                    // QuoteProxy.getInstance().setMoniDeposit("0.0");
                    //  QuoteProxy.getInstance().setMoniService("0.0");


                } else if (isTrade.equals("1")) {

                    List<String> moniCloseList = QuoteProxy.getInstance().getShipanCloseList();
                    if (moniCloseList == null) {
                        Toast.makeText(getActivity(), "当前无委托", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    String replace = moniCloseList.toString().replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");

                    postClose(id_bus, "下单");

                    // QuoteProxy.getInstance().setShipanDeposit("0.0");
                    // QuoteProxy.getInstance().setShipanService("0.0");
                }
                break;

            case R.id.img_light:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    if (light_flag == 0) {
                        if (isTrade.equals("2")) {
                            layout_light_one.setVisibility(View.VISIBLE);
                            layout_light_two.setVisibility(View.VISIBLE);
                            layout_light_three.setVisibility(View.GONE);

                        } else if (isTrade.equals("1")) {
                            layout_light_one.setVisibility(View.VISIBLE);
                            layout_light_two.setVisibility(View.VISIBLE);
                            layout_light_three.setVisibility(View.VISIBLE);
                        }
                        stay_line.setVisibility(View.VISIBLE);
                        img_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_shandian_lan_icon));

                        light_flag = 1;
                    } else if (light_flag == 1) {
                        if (isTrade.equals("2")) {
                            layout_light_one.setVisibility(View.GONE);
                            layout_light_two.setVisibility(View.GONE);
                            layout_light_three.setVisibility(View.GONE);

                        } else if (isTrade.equals("1")) {
                            layout_light_one.setVisibility(View.GONE);
                            layout_light_two.setVisibility(View.GONE);
                            layout_light_three.setVisibility(View.GONE);
                        }

                        stay_line.setVisibility(View.GONE);
                        img_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_shandian_hui_icon));

                        light_flag = 0;
                    }

                    setLight();
                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showItemPopWindow() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.o_item_check_pop, null);
        EditText edit_name = view.findViewById(R.id.edit_name);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);


        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.6f;
        getActivity().getWindow().setAttributes(params);

        TextView text_back = view.findViewById(R.id.text_back);
        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        //关键
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString();
                postCheckName(name);
            }
        });

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }

    public void backgroundAlpha(float bgalpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgalpha;
        getActivity().getWindow().setAttributes(lp);


    }

    //核实用户验证
    private void postCheckName(String name) {

        String url = OConstant.URL_RECHARGE + "?" + OConstant.PARAM_ACTION + "=" + OConstant.STAY_CHECKNAME + "&" + OConstant.PARAM_NAME + "=" + name;
        Log.d("print", "postCheckName:309:   " + url);
        OkGo.<String>post(url)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:315:   " + response.body());
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            if (oCodeMsgEntity.isSuccess() == true) {
                                postRecharge();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), getString(R.string.o_text_err), Toast.LENGTH_SHORT).show();

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

    private void postClose(String idList, String source) {

        OkGo.<String>post(OConstant.URL_CLOSE)
                .params(OConstant.PARAM_BETTINGLIST, idList)
                .params(OConstant.PARAM_TRADETYPE, isTrade)
                .params(OConstant.PARAM_SOURCE, source)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();

                    }
                });
    }

    private void getAddScore() {
        OkGo.<String>get(OConstant.URL_ADDSCORE)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            Toast.makeText(getActivity(), oCodeMsgEntity.getResultMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                    }
                });


    }

    private int moreFlag = 1;
    int volume = 1;

    private double multiple = 1;
    String priceChange = null;


    private void setData(boolean isbuy, TextView text_stop_price, double multiple, int num, RadioButton profit1, RadioButton profit2, RadioButton profit3, RadioButton profit4, RadioButton profit5, TextView text_stopprofit, TextView text_unit, TextView text_lvyue, TextView text_input, List<Integer> depositList) {
        volume = num;
        int integer = 0;
        String lvyue = null;
      /*  if (money_much_type==0||money_less_type==0){
            multiple=1;
        }else if (money_much_type==1||money_less_type==1){
            multiple=10;
        }*//*else if (money_less_type==0){
            multiple=1;
        }else if (money_less_type==1){
            multiple=10;
        }*/

        Log.d("print", "setData:买多" + money_much_type + "----买少" + money_less_type + "----" + multiple);


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();

        List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
        List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
        List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();
        for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
            if (foreign.getCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                priceChange = foreign.getPriceChange();
                String price = foreign.getPrice();

                double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格
                Log.d("print", "setData:波动手数: " + volume + "    波动价格:" + priceChange + "   每点" + price + "     止盈价格:" + stopProfitList.get(0));

                double div0 = OUtil.div(volume * stopLossList.get(0), 1, 1);
                double stop_price_div = OUtil.div(OUtil.div(volume * stopProfitList.get(0), 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul = OUtil.mul(stop_price_div, Double.valueOf(priceChange));
                Log.d("print", "setData:2733:    " + stop_price_div);
                double div1 = OUtil.div(div0, mul2, 1);

                double mul = OUtil.mul(div1, Double.valueOf(priceChange));
                //买多是加 买空是减

                if (isbuy == true) {
                    sub_price = OUtil.add(Double.parseDouble(last), mul);
                    if (profit1.isChecked()) {
                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div);
                    }


                } else {
                    sub_price = OUtil.sub(Double.parseDouble(last), mul);
                    if (profit1.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div);
                    }

                }


                double div_1 = OUtil.div(volume * stopLossList.get(1), 1, 1);
                double div_11 = OUtil.div(div_1, mul2, 1);
                double stop_price_div1 = OUtil.div(OUtil.div(volume * stopProfitList.get(1), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul1 = OUtil.mul(stop_price_div1, Double.valueOf(priceChange));
                double mul_1 = OUtil.mul(div_11, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price1 = OUtil.add(Double.parseDouble(last), mul_1);
                    if (profit2.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div1);
                    }
                } else {
                    sub_price1 = OUtil.sub(Double.parseDouble(last), mul_1);
                    if (profit2.isChecked()) {

                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div1);
                    }


                }

                double div_2 = OUtil.div(volume * stopLossList.get(2), 1, 1);
                double div_22 = OUtil.div(div_2, mul2, 1);
                double stop_price_div2 = OUtil.div(OUtil.div(volume * stopProfitList.get(2), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul2 = OUtil.mul(stop_price_div2, Double.valueOf(priceChange));
                double mul_2 = OUtil.mul(div_22, Double.valueOf(priceChange));
                //买多是加 买空是减

                if (isbuy == true) {
                    sub_price2 = OUtil.add(Double.parseDouble(last), mul_2);
                    if (profit3.isChecked()) {
                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div2);
                    }
                } else {
                    sub_price2 = OUtil.sub(Double.parseDouble(last), mul_2);
                    if (profit3.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div2);

                    }
                }

                double div_3 = OUtil.div(volume * stopLossList.get(3), 1, 1);
                double div_33 = OUtil.div(div_3, mul2, 1);
                double stop_price_div3 = OUtil.div(OUtil.div(volume * stopProfitList.get(3), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul3 = OUtil.mul(stop_price_div3, Double.valueOf(priceChange));
                double mul_3 = OUtil.mul(div_33, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price3 = OUtil.add(Double.parseDouble(last), mul_3);
                    if (profit4.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div3);
                    }
                } else {
                    sub_price3 = OUtil.sub(Double.parseDouble(last), mul_3);
                    if (profit4.isChecked()) {

                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div3);

                    }
                }

                double div_4 = OUtil.div(volume * stopLossList.get(4), 1, 1);
                double div_44 = OUtil.div(div_4, mul2, 1);
                double stop_price_div4 = OUtil.div(OUtil.div(volume * stopProfitList.get(4), 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul4 = OUtil.mul(stop_price_div4, Double.valueOf(priceChange));
                double mul_4 = OUtil.mul(div_44, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price4 = OUtil.add(Double.parseDouble(last), mul_4);
                    if (profit5.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div4);
                    }
                } else {
                    sub_price4 = OUtil.sub(Double.parseDouble(last), mul_4);
                    if (profit5.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div4);
                    }
                }


            }
        }
        for (OApiEntity.StockIndexCommdsBean foreign : stockIndexCommds) {
            if (foreign.getCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                priceChange = foreign.getPriceChange();
                String price = foreign.getPrice();

                double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格
                Log.d("print", "setData:波动手数: " + volume + "    波动价格:" + priceChange + "   每点" + price + "     止盈价格:" + stopProfitList.get(0));

                double div0 = OUtil.div(volume * stopLossList.get(0), 1, 1);
                double stop_price_div = OUtil.div(OUtil.div(volume * stopProfitList.get(0), 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul = OUtil.mul(stop_price_div, Double.valueOf(priceChange));
                Log.d("print", "setData:2733:    " + stop_price_div);
                double div1 = OUtil.div(div0, mul2, 1);

                double mul = OUtil.mul(div1, Double.valueOf(priceChange));
                //买多是加 买空是减

                if (isbuy == true) {
                    sub_price = OUtil.add(Double.parseDouble(last), mul);
                    if (profit1.isChecked()) {
                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div);
                    }


                } else {
                    sub_price = OUtil.sub(Double.parseDouble(last), mul);
                    if (profit1.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div);
                    }

                }


                double div_1 = OUtil.div(volume * stopLossList.get(1), 1, 1);
                double div_11 = OUtil.div(div_1, mul2, 1);
                double stop_price_div1 = OUtil.div(OUtil.div(volume * stopProfitList.get(1), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul1 = OUtil.mul(stop_price_div1, Double.valueOf(priceChange));
                double mul_1 = OUtil.mul(div_11, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price1 = OUtil.add(Double.parseDouble(last), mul_1);
                    if (profit2.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div1);
                    }
                } else {
                    sub_price1 = OUtil.sub(Double.parseDouble(last), mul_1);
                    if (profit2.isChecked()) {

                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div1);
                    }


                }

                double div_2 = OUtil.div(volume * stopLossList.get(2), 1, 1);
                double div_22 = OUtil.div(div_2, mul2, 1);
                double stop_price_div2 = OUtil.div(OUtil.div(volume * stopProfitList.get(2), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul2 = OUtil.mul(stop_price_div2, Double.valueOf(priceChange));
                double mul_2 = OUtil.mul(div_22, Double.valueOf(priceChange));
                //买多是加 买空是减

                if (isbuy == true) {
                    sub_price2 = OUtil.add(Double.parseDouble(last), mul_2);
                    if (profit3.isChecked()) {
                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div2);
                    }
                } else {
                    sub_price2 = OUtil.sub(Double.parseDouble(last), mul_2);
                    if (profit3.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div2);

                    }
                }

                double div_3 = OUtil.div(volume * stopLossList.get(3), 1, 1);
                double div_33 = OUtil.div(div_3, mul2, 1);
                double stop_price_div3 = OUtil.div(OUtil.div(volume * stopProfitList.get(3), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul3 = OUtil.mul(stop_price_div3, Double.valueOf(priceChange));
                double mul_3 = OUtil.mul(div_33, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price3 = OUtil.add(Double.parseDouble(last), mul_3);
                    if (profit4.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div3);
                    }
                } else {
                    sub_price3 = OUtil.sub(Double.parseDouble(last), mul_3);
                    if (profit4.isChecked()) {

                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div3);

                    }
                }

                double div_4 = OUtil.div(volume * stopLossList.get(4), 1, 1);
                double div_44 = OUtil.div(div_4, mul2, 1);
                double stop_price_div4 = OUtil.div(OUtil.div(volume * stopProfitList.get(4), 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul4 = OUtil.mul(stop_price_div4, Double.valueOf(priceChange));
                double mul_4 = OUtil.mul(div_44, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price4 = OUtil.add(Double.parseDouble(last), mul_4);
                    if (profit5.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div4);
                    }
                } else {
                    sub_price4 = OUtil.sub(Double.parseDouble(last), mul_4);
                    if (profit5.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div4);
                    }
                }


            }
        }
        for (OApiEntity.DomesticCommdsBean foreign : domesticCommds) {
            if (foreign.getCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                priceChange = foreign.getPriceChange();
                String price = foreign.getPrice();

                double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格
                Log.d("print", "setData:波动手数: " + volume + "    波动价格:" + priceChange + "   每点" + price + "     止盈价格:" + stopProfitList.get(0));

                double div0 = OUtil.div(volume * stopLossList.get(0), 1, 1);
                double stop_price_div = OUtil.div(OUtil.div(volume * stopProfitList.get(0), 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul = OUtil.mul(stop_price_div, Double.valueOf(priceChange));
                Log.d("print", "setData:2733:    " + stop_price_div);
                double div1 = OUtil.div(div0, mul2, 1);

                double mul = OUtil.mul(div1, Double.valueOf(priceChange));
                //买多是加 买空是减

                if (isbuy == true) {
                    sub_price = OUtil.add(Double.parseDouble(last), mul);
                    if (profit1.isChecked()) {
                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div);
                    }


                } else {
                    sub_price = OUtil.sub(Double.parseDouble(last), mul);
                    if (profit1.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div);
                    }

                }


                double div_1 = OUtil.div(volume * stopLossList.get(1), 1, 1);
                double div_11 = OUtil.div(div_1, mul2, 1);
                double stop_price_div1 = OUtil.div(OUtil.div(volume * stopProfitList.get(1), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul1 = OUtil.mul(stop_price_div1, Double.valueOf(priceChange));
                double mul_1 = OUtil.mul(div_11, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price1 = OUtil.add(Double.parseDouble(last), mul_1);
                    if (profit2.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div1);
                    }
                } else {
                    sub_price1 = OUtil.sub(Double.parseDouble(last), mul_1);
                    if (profit2.isChecked()) {

                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div1);
                    }


                }

                double div_2 = OUtil.div(volume * stopLossList.get(2), 1, 1);
                double div_22 = OUtil.div(div_2, mul2, 1);
                double stop_price_div2 = OUtil.div(OUtil.div(volume * stopProfitList.get(2), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul2 = OUtil.mul(stop_price_div2, Double.valueOf(priceChange));
                double mul_2 = OUtil.mul(div_22, Double.valueOf(priceChange));
                //买多是加 买空是减

                if (isbuy == true) {
                    sub_price2 = OUtil.add(Double.parseDouble(last), mul_2);
                    if (profit3.isChecked()) {
                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div2);
                    }
                } else {
                    sub_price2 = OUtil.sub(Double.parseDouble(last), mul_2);
                    if (profit3.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div2);

                    }
                }

                double div_3 = OUtil.div(volume * stopLossList.get(3), 1, 1);
                double div_33 = OUtil.div(div_3, mul2, 1);
                double stop_price_div3 = OUtil.div(OUtil.div(volume * stopProfitList.get(3), 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul3 = OUtil.mul(stop_price_div3, Double.valueOf(priceChange));
                double mul_3 = OUtil.mul(div_33, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price3 = OUtil.add(Double.parseDouble(last), mul_3);
                    if (profit4.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div3);
                    }
                } else {
                    sub_price3 = OUtil.sub(Double.parseDouble(last), mul_3);
                    if (profit4.isChecked()) {

                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div3);

                    }
                }

                double div_4 = OUtil.div(volume * stopLossList.get(4), 1, 1);
                double div_44 = OUtil.div(div_4, mul2, 1);
                double stop_price_div4 = OUtil.div(OUtil.div(volume * stopProfitList.get(4), 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul4 = OUtil.mul(stop_price_div4, Double.valueOf(priceChange));
                double mul_4 = OUtil.mul(div_44, Double.valueOf(priceChange));
                //买多是加 买空是减
                if (isbuy == true) {
                    sub_price4 = OUtil.add(Double.parseDouble(last), mul_4);
                    if (profit5.isChecked()) {

                        stop_price = OUtil.add(Double.parseDouble(last), stop_price_div4);
                    }
                } else {
                    sub_price4 = OUtil.sub(Double.parseDouble(last), mul_4);
                    if (profit5.isChecked()) {
                        stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div4);
                    }
                }


            }
        }

        Log.d("print", "setData:2987:   " + volume * stopLossList.get(0));

        profit1.setText(OUtil.doublePoint(OUtil.div(volume * stopLossList.get(0), multiple, 1)) + "\n" + sub_price);
        profit2.setText(OUtil.doublePoint(OUtil.div(volume * stopLossList.get(1), multiple, 1)) + "\n" + sub_price1);
        profit3.setText(OUtil.doublePoint(OUtil.div(volume * stopLossList.get(2), multiple, 1)) + "\n" + sub_price2);
        profit4.setText(OUtil.doublePoint(OUtil.div(volume * stopLossList.get(3), multiple, 1)) + "\n" + sub_price3);
        profit5.setText(OUtil.doublePoint(OUtil.div(volume * stopLossList.get(4), multiple, 1)) + "\n" + sub_price4);
        Log.d("print", "setData:1782:  " + depositList);
        if (profit1.isChecked()) {
            integer = stopProfitList.get(0);
            if (depositList.size() != 0) {

                lvyue = String.valueOf(OUtil.div(depositList.get(0) * volume, multiple, 1));
                // lvyue=String.valueOf(depositList.get(0));
            } else {
                String[] split = profit1.getText().toString().split("\n");
                lvyue = split[0].replaceAll("-", "");
                // Log.d("print", "setData:2122:  "+profit1.getText().toString().replaceAll("元", "").replaceAll("-", "")+"-----"+multiple);
                //lvyue = String.valueOf(Double.parseDouble(profit1.getText().toString().replaceAll("元", "").replaceAll("-", "")));
            }
        }
        if (profit2.isChecked()) {
            integer = stopProfitList.get(1);
            if (depositList.size() != 0) {
                lvyue = String.valueOf(OUtil.div(depositList.get(1) * volume, multiple, 1));

                //   lvyue = String.valueOf(depositList.get(1) * volume*multiple);
            } else {
                // lvyue = String.valueOf(Integer.parseInt(profit2.getText().toString().replaceAll("元", "").replaceAll("-", ""))*multiple);

                String[] split = profit2.getText().toString().split("\n");
                lvyue = split[0].replaceAll("-", "");
                // lvyue = String.valueOf(Double.parseDouble(profit2.getText().toString().replaceAll("元", "").replaceAll("-", "")));

            }

        }
        if (profit3.isChecked()) {
            integer = stopProfitList.get(2);
            if (depositList.size() != 0) {
                lvyue = String.valueOf(OUtil.div(depositList.get(2) * volume, multiple, 1));

                //lvyue = String.valueOf(depositList.get(2) * volume*multiple);
            } else {
                String[] split = profit3.getText().toString().split("\n");
                lvyue = split[0].replaceAll("-", "");
                //   lvyue = String.valueOf(Integer.parseInt(profit3.getText().toString().replaceAll("元", "").replaceAll("-", ""))*multiple);
                //lvyue = String.valueOf(Double.parseDouble(profit3.getText().toString().replaceAll("元", "").replaceAll("-", "")));

            }


        }
        if (profit4.isChecked()) {
            integer = stopProfitList.get(3);
            if (depositList.size() != 0) {
                lvyue = String.valueOf(OUtil.div(depositList.get(3) * volume, multiple, 1));

                // lvyue = String.valueOf(depositList.get(3) * volume*multiple);
            } else {
                String[] split = profit4.getText().toString().split("\n");
                lvyue = split[0].replaceAll("-", "");
                // lvyue = String.valueOf(Integer.parseInt(profit4.getText().toString().replaceAll("元", "").replaceAll("-", ""))*multiple);
                // lvyue = String.valueOf(Double.parseDouble(profit4.getText().toString().replaceAll("元", "").replaceAll("-", "")));

            }


        }
        if (profit5.isChecked()) {
            integer = stopProfitList.get(4);
            if (depositList.size() != 0) {
                lvyue = String.valueOf(OUtil.div(depositList.get(4) * volume, multiple, 1));

                // lvyue = String.valueOf(depositList.get(4) * volume*multiple);
            } else {
                String[] split = profit5.getText().toString().split("\n");

                lvyue = split[0].replaceAll("-", "");
                // lvyue = String.valueOf(Integer.parseInt(profit5.getText().toString().replaceAll("元", "").replaceAll("-", ""))*multiple);
                // lvyue = String.valueOf(Double.parseDouble(profit5.getText().toString().replaceAll("元", "").replaceAll("-", "")));

                //  lvyue = profit5.getText().toString().replaceAll("元", "").replaceAll("-", "");
            }


        }

        text_stopprofit.setText(OUtil.doublePoint(OUtil.div(integer * volume, multiple, 1)) + "");

        text_unit.setText(OUtil.doublePoint(OUtil.div(chargeUnit1 * volume, multiple, 1)) + "");


        text_stop_price.setText(stop_price + "");

        text_lvyue.setText(OUtil.doublePoint(Double.parseDouble(lvyue)) + "");

        if (lvyue != null) {

            double div = OUtil.div(chargeUnit1 * volume, multiple, 1);
            double mul = OUtil.add(div, Double.parseDouble(lvyue));
            double i = chargeUnit1 * volume * multiple + Double.parseDouble(lvyue);
            text_input.setText(OUtil.doublePoint(mul) + "元");
        }


    }

    private int profit_flag = 0;
    private int loss_flag = 0;


    private int money_type_flag_light = 0;
    private int money_type_type_light = 0;
    private double multipleLight = 1;
    private int btn_flag = 0;

    private void setLightVolume(int i, double multipleLight) {
        double mul_volume = OUtil.mul(volumeList.get(i), stopLossList.get(0));

        double div_loss = OUtil.div(mul_volume, multipleLight, 1);
        double div_profit = OUtil.div(volumeList.get(i) * stopProfitList.get(0), multipleLight, 1);


        double div_unit = OUtil.div(chargeUnit1, multipleLight, 1);


        double mul = OUtil.mul(volumeList.get(i), div_unit);

        double v = OUtil.double1Point(mul);

        text_stoploss.setText(OUtil.doublePoint(div_loss) + "");
        text_stopprofit.setText(OUtil.doublePoint(div_profit) + "元");
        text_unit.setText(OUtil.doublePoint(v) + "元");
        loss_flag = 0;
    }


    private double setLightLoss(RadioButton radio) {
        double mul_loss = OUtil.mul(stopLossList.get(loss_flag), Integer.parseInt(radio.getText().toString()));
        double stoploss = OUtil.div(mul_loss, multipleLight, 1);

        return stoploss;

    }

    private double setLightProfit(RadioButton radio) {
        double mul_profit = OUtil.mul(stopProfitList.get(loss_flag), Integer.parseInt(radio.getText().toString()));
        double stopprofit = OUtil.div(mul_profit, multipleLight, 1);

        return stopprofit;

    }

    private void setLight() {
        if (isTrade.equals("2")) {
            oTradeListEntity = QuoteProxy.getInstance().getoTradeListMoniEntity();


        } else if (isTrade.equals("1")) {
            oTradeListEntity = QuoteProxy.getInstance().getoTradeListEntity();

        }

        if (oTradeListEntity == null) {
            return;
        }


        boolean tradeQuick = oTradeListEntity.isTradeQuick();
        if (isTrade.equals("1")) {
            if (tradeQuick == true) {
                img_light.setVisibility(View.VISIBLE);

            } else {
                img_light.setVisibility(View.GONE);
            }
        } else if (isTrade.equals("2")) {
            img_light.setVisibility(View.VISIBLE);

        }

        eagle_result = QuoteProxy.getInstance().getEagle();

        text_jifen.setText(OUtil.doublePoint(eagle_result) + "  可抵扣" + OUtil.doublePoint(OUtil.div(eagle_result, 10,1)) + "元手续费");


        List<OTradeListEntity.TradeListBean> tradeList = oTradeListEntity.getTradeList();
        for (OTradeListEntity.TradeListBean tradeBean : tradeList) {
            if (tradeBean.getCommCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                volumeList = tradeBean.getVolumeList();
                stopLossList = tradeBean.getStopLossList();
                stopProfitList = tradeBean.getStopProfitList();
                chargeUnit1 = tradeBean.getChargeUnit();
                depositList = tradeBean.getDepositList();
                commCode = tradeBean.getCommCode();
                contName = tradeBean.getContName();
                moneyTypeList = tradeBean.getMoneyTypeList();
            }
        }

        if (volumeList.size() != 0) {
            radio_1.setText(volumeList.get(0) + "");
            radio_2.setText(volumeList.get(1) + "");
            radio_3.setText(volumeList.get(2) + "");
            radio_4.setText(volumeList.get(3) + "");
            radio_5.setText(volumeList.get(4) + "");
            radio_6.setText(volumeList.get(5) + "");
        }
        radioGroup.getChildAt(0).performClick();
        radio_1.setChecked(true);


        if (isTrade.equals("2")) {
            img_money_type_light.setVisibility(View.GONE);
        } else if (isTrade.equals("1")) {
            if (moneyTypeList.size() > 1) {
                img_money_type_light.setVisibility(View.VISIBLE);
                money_type_type_light = moneyTypeList.get(0);
                if (money_type_type_light == 0) {
                    multipleLight = 1;
                    img_money_type_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_yuanmoshi));

                } else if (money_type_type_light == 1) {
                    multipleLight = 10;
                    img_money_type_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_jiaomoshi));

                }
            } else {
                img_money_type_light.setVisibility(View.GONE);
            }
        }


        double div_loss = OUtil.div(volumeList.get(0) * stopLossList.get(0), multipleLight, 1);
        double div_profit = OUtil.div(volumeList.get(0) * stopProfitList.get(0), multipleLight, 1);
        double div_unit = OUtil.div(chargeUnit1, multipleLight, 1);
        double mul = OUtil.mul(volumeList.get(0), div_unit);

        text_stoploss.setText(OUtil.doublePoint(div_loss) + "");
        text_stopprofit.setText(OUtil.doublePoint(div_profit) + "元");
        text_unit.setText(OUtil.doublePoint(mul) + "元");

        img_money_type_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money_type_flag_light == 0) {
                    img_money_type_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_jiaomoshi));

                    money_type_flag_light = 1;

                    money_type_type_light = moneyTypeList.get(1);
                    if (money_type_type_light == 0) {
                        multipleLight = 1;
                    } else if (money_type_type_light == 1) {
                        multipleLight = 10;
                    }

                    if (radio_1.isChecked()) {
                        setLightVolume(0, multipleLight);
                    } else if (radio_2.isChecked()) {
                        setLightVolume(1, multipleLight);
                    } else if (radio_3.isChecked()) {
                        setLightVolume(2, multipleLight);
                    } else if (radio_4.isChecked()) {
                        setLightVolume(3, multipleLight);
                    } else if (radio_5.isChecked()) {
                        setLightVolume(4, multipleLight);
                    } else if (radio_6.isChecked()) {
                        setLightVolume(5, multipleLight);
                    }


                } else if (money_type_flag_light == 1) {

                    img_money_type_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_yuanmoshi));
                    money_type_flag_light = 0;
                    money_type_type_light = moneyTypeList.get(0);
                    if (money_type_type_light == 0) {
                        multipleLight = 1;
                    } else if (money_type_type_light == 1) {
                        multipleLight = 10;
                    }

                    if (radio_1.isChecked()) {
                        setLightVolume(0, multipleLight);
                    } else if (radio_2.isChecked()) {
                        setLightVolume(1, multipleLight);
                    } else if (radio_3.isChecked()) {
                        setLightVolume(2, multipleLight);
                    } else if (radio_4.isChecked()) {
                        setLightVolume(3, multipleLight);
                    } else if (radio_5.isChecked()) {
                        setLightVolume(4, multipleLight);
                    } else if (radio_6.isChecked()) {
                        setLightVolume(5, multipleLight);
                    }


                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_1:

                        setLightVolume(0, multipleLight);

                        break;
                    case R.id.radio_2:
                        setLightVolume(1, multipleLight);


                        break;
                    case R.id.radio_3:

                        setLightVolume(2, multipleLight);

                        break;
                    case R.id.radio_4:

                        setLightVolume(3, multipleLight);

                        break;
                    case R.id.radio_5:

                        setLightVolume(4, multipleLight);

                        break;
                    case R.id.radio_6:
                       /* text_stoploss.setText(volumeList.get(5) * stopLossList.get(0) + "");
                        text_stopprofit.setText(volumeList.get(5) * stopProfitList.get(0) + "元");
                        text_unit.setText(volumeList.get(5) * chargeUnit1 + "元");
                        loss_flag = 0;*/
                        setLightVolume(5, multipleLight);

                        break;
                }
            }
        });

        text_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loss_flag < stopLossList.size() - 1 & loss_flag >= 0) {
                    loss_flag++;
                    if (radio_1.isChecked()) {
                      /*  double mul_loss = OUtil.mul(stopLossList.get(loss_flag), Integer.parseInt(radio_1.getText().toString()));
                        stoploss = OUtil.div(mul_loss, multipleLight, 1);

                        double mul_profit = OUtil.mul(stopLossList.get(loss_flag), Integer.parseInt(radio_1.getText().toString()));
                        stopprofit = OUtil.div(mul_profit, multipleLight, 1);*/

                        stoploss = setLightLoss(radio_1);
                        stopprofit = setLightProfit(radio_1);

                        // stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_1.getText().toString());
                        //  stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_1.getText().toString());

                    } else if (radio_2.isChecked()) {
                        // stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_2.getText().toString());
                        // stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_2.getText().toString());
                        stoploss = setLightLoss(radio_2);
                        stopprofit = setLightProfit(radio_2);
                    } else if (radio_3.isChecked()) {
                        /*stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_3.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_3.getText().toString());*/
                        stoploss = setLightLoss(radio_3);
                        stopprofit = setLightProfit(radio_3);
                    } else if (radio_4.isChecked()) {
                       /* stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_4.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_4.getText().toString());*/

                        stoploss = setLightLoss(radio_4);
                        stopprofit = setLightProfit(radio_4);
                    } else if (radio_5.isChecked()) {
                       /* stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_5.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_5.getText().toString());*/
                        stoploss = setLightLoss(radio_5);
                        stopprofit = setLightProfit(radio_5);
                    } else if (radio_6.isChecked()) {
                      /*  stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_6.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_6.getText().toString());*/
                        stoploss = setLightLoss(radio_6);
                        stopprofit = setLightProfit(radio_6);
                    }


                    text_stoploss.setText(OUtil.doublePoint(stoploss) + "");
                    text_stopprofit.setText(OUtil.doublePoint(stopprofit) + "元");
                } else {
                }
            }
        });

        text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (loss_flag > 0 & loss_flag <= stopLossList.size()) {
                    loss_flag--;

                    if (radio_1.isChecked()) {
                        /*stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_1.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_1.getText().toString());*/
                        stoploss = setLightLoss(radio_1);
                        stopprofit = setLightProfit(radio_1);
                    } else if (radio_2.isChecked()) {
                       /* stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_2.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_2.getText().toString());*/
                        stoploss = setLightLoss(radio_2);
                        stopprofit = setLightProfit(radio_2);
                    } else if (radio_3.isChecked()) {
                        /*stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_3.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_3.getText().toString());*/
                        stoploss = setLightLoss(radio_3);
                        stopprofit = setLightProfit(radio_3);
                    } else if (radio_4.isChecked()) {
                        /*stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_4.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_4.getText().toString());*/
                        stoploss = setLightLoss(radio_4);
                        stopprofit = setLightProfit(radio_4);
                    } else if (radio_5.isChecked()) {
                        /*stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_5.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_5.getText().toString());*/
                        stoploss = setLightLoss(radio_5);
                        stopprofit = setLightProfit(radio_5);
                    } else if (radio_6.isChecked()) {
                        /*stoploss = stopLossList.get(loss_flag) * Integer.parseInt(radio_6.getText().toString());
                        stopprofit = stopProfitList.get(loss_flag) * Integer.parseInt(radio_6.getText().toString());*/
                        stoploss = setLightLoss(radio_6);
                        stopprofit = setLightProfit(radio_6);
                    }

                    text_stoploss.setText(OUtil.doublePoint(stoploss) + "");
                    text_stopprofit.setText(OUtil.doublePoint(stopprofit) + "元");

                } else {

                }

            }
        });


        if (btn_flag == 0) {
            img_btn.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));

        } else if (btn_flag == 1) {
            img_btn.setImageDrawable(getResources().getDrawable(R.mipmap.o_open_btn));

        }

        String s1 = text_unit.getText().toString().replaceAll("元", "");
        double unit_v = Double.parseDouble(s1);
        double sub = OUtil.sub(eagle_result, unit_v);
        if (sub > 0) {
            img_btn.setEnabled(true);
        } else {
            img_btn.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
            btn_flag = 0;
            img_btn.setEnabled(false);

        }

        text_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String s1 = text_unit.getText().toString().replaceAll("元", "");
                    double unit_v = Double.parseDouble(s1);
                    double sub = OUtil.sub(eagle_result, unit_v);
                    if (sub > 0) {
                        img_btn.setEnabled(true);

                        if (btn_flag == 1) {

                            text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                        } else if (btn_flag == 0) {
                            text_unit.getPaint().setFlags(0);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.redcolor));

                        }


                    } else {
                        img_btn.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                        btn_flag = 0;
                        img_btn.setEnabled(false);

                        if (btn_flag == 1) {

                            text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                        } else if (btn_flag == 0) {
                            text_unit.getPaint().setFlags(0);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.redcolor));

                        }

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replace_unit = text_unit.getText().toString().replaceAll("元", "");

                if (btn_flag == 0) {
                    service = replace_unit;
                    eagle = "0";
                } else if (btn_flag == 1) {
                    service = "0";
                    double mul = OUtil.mul(Double.parseDouble(replace_unit), 10);
                    eagle = mul + "";
                }

                if (btn_flag == 0) {

                    img_btn.setImageDrawable(getResources().getDrawable(R.mipmap.o_open_btn));
                    Toast.makeText(getActivity(), getString(R.string.o_text_hongbao), Toast.LENGTH_SHORT).show();
                    btn_flag = 1;
                    //设置中线
                    text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    text_unit.getPaint().setAntiAlias(true);
                    text_unit.setText(replace_unit + "元");
                    text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));
                } else if (btn_flag == 1) {

                    img_btn.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                    btn_flag = 0;


                    text_unit.getPaint().setFlags(0);
                    text_unit.getPaint().setAntiAlias(true);
                    text_unit.setText(replace_unit + "元");
                    text_unit.setTextColor(getResources().getColor(R.color.redcolor));

                }
            }
        });

    }


    private void setDepositAndInput(boolean isbuy, TextView text_stop_price, int index, RadioButton profit, RadioButton radioButton1, RadioButton radioButton2, RadioButton radioButton3, RadioButton radioButton4, RadioButton radioButton5, RadioButton radioButton6, List<Integer> depositList, List<Integer> stopProfitList, TextView text_stopprofit, TextView text_lvyue, TextView text_unit, TextView text_input) {
        int volume = 0;
        int lvyue = 0;
        if (radioButton1.isChecked()) {
            volume = volumeList.get(0);
        } else if (radioButton2.isChecked()) {
            volume = volumeList.get(1);

        } else if (radioButton3.isChecked()) {
            volume = volumeList.get(2);

        } else if (radioButton4.isChecked()) {
            volume = volumeList.get(3);

        } else if (radioButton5.isChecked()) {
            volume = volumeList.get(4);

        } else if (radioButton6.isChecked()) {
            volume = volumeList.get(5);
        }


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();

        List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
        List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
        List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();
        for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
            if (foreign.getCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                priceChange = foreign.getPriceChange();
                String price = foreign.getPrice();

                double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格
                double stop_price_div = OUtil.div(OUtil.div(stopProfitList.get(index) * volume, 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul = OUtil.mul(stop_price_div, Double.valueOf(priceChange));


                //买多是加 买空是减
                if (isbuy == true) {
                    stop_price = OUtil.add(Double.parseDouble(last), stop_price_div);
                } else {
                    stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div);
                }

            }
        }
        for (OApiEntity.StockIndexCommdsBean foreign : stockIndexCommds) {
            if (foreign.getCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                priceChange = foreign.getPriceChange();
                String price = foreign.getPrice();

                double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                double stop_price_div = OUtil.div(OUtil.div(stopProfitList.get(index) * volume, 1, 1), Double.parseDouble(price), 1);
                // double stop_price_mul = OUtil.mul(stop_price_div, Double.valueOf(priceChange));


                //买多是加 买空是减
                if (isbuy == true) {
                    stop_price = OUtil.add(Double.parseDouble(last), stop_price_div);
                } else {
                    stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div);
                }

            }
        }
        for (OApiEntity.DomesticCommdsBean foreign : domesticCommds) {
            if (foreign.getCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                priceChange = foreign.getPriceChange();
                String price = foreign.getPrice();

                double mul2 = OUtil.mul(Double.valueOf(price), Double.valueOf(priceChange));//单点波动的价格

                double stop_price_div = OUtil.div(OUtil.div(stopProfitList.get(index) * volume, 1, 1), Double.parseDouble(price), 1);
                //double stop_price_mul = OUtil.mul(stop_price_div, Double.valueOf(priceChange));


                //买多是加 买空是减
                if (isbuy == true) {
                    stop_price = OUtil.add(Double.parseDouble(last), stop_price_div);
                } else {
                    stop_price = OUtil.sub(Double.parseDouble(last), stop_price_div);
                }

            }
        }


        text_stop_price.setText(stop_price + "");

        double div = OUtil.div(stopProfitList.get(index) * volume, multiple, 1);
        //  int i1 = stopProfitList.get(index) * volume
        text_stopprofit.setText(OUtil.doublePoint(div) + "");

        if (depositList.size() != 0) {
            Integer integer = depositList.get(index) * volume;

            double div1 = OUtil.div(integer, multiple, 1);
            text_lvyue.setText(OUtil.doublePoint(div1) + "");
            double add = OUtil.add(Double.parseDouble(text_unit.getText().toString()), div1);
            //int i6 = Integer.parseInt(text_unit.getText().toString()) + integer;
            text_input.setText(OUtil.doublePoint(add) + "元");
        } else {
            String[] split = profit.getText().toString().split("\n");
            String s = split[0].replaceAll("-", "");
            //String s = profit.getText().toString().replaceAll("元", "").replaceAll("-", "");
            double div1 = OUtil.div(Double.parseDouble(s), multiple, 1);

            text_lvyue.setText(OUtil.doublePoint(div1) + "");
            double div2 = OUtil.div(Double.parseDouble(s) * volume, multiple, 1);
            //Integer integer = Integer.parseInt(s) * volume;
            //  int i6 = Integer.parseInt(text_unit.getText().toString()) + integer;
            double add = OUtil.add(Double.parseDouble(text_unit.getText().toString()), div2);

            text_input.setText(OUtil.doublePoint(add) + "元");

        }
    }

    private int money_much_flag = 0;
    private int money_much_type = 0;
    private int img_much_flag = 0;

    // TODO: 2019/8/6  买多
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showBuyManyPopWindow() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_buy_long, null);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


        TextView text_balacne = view.findViewById(R.id.text_balance);
        TextView text_chongzhi = view.findViewById(R.id.text_chongzhi);

        TextView text_rate = view.findViewById(R.id.text_rate);
        RelativeLayout layout_rate = view.findViewById(R.id.layout_rate);
        View view_rate = view.findViewById(R.id.view_rate);

        if (rateDetail.equals("")) {
            layout_rate.setVisibility(View.GONE);
            view_rate.setVisibility(View.GONE);
        } else {
            text_rate.setText(rateDetail);
        }

        LinearLayout layout_shipan = view.findViewById(R.id.layout_shipan);
        ImageView img_money_type = view.findViewById(R.id.img_money_type);


        if (isTrade.equals("2")) {
            oTradeListEntity = QuoteProxy.getInstance().getoTradeListMoniEntity();
            layout_shipan.setVisibility(View.GONE);
            img_money_type.setVisibility(View.GONE);
        } else if (isTrade.equals("1")) {
            oTradeListEntity = QuoteProxy.getInstance().getoTradeListEntity();
            layout_shipan.setVisibility(View.VISIBLE);
            //同买多 模拟盘没有角模式
            if (moneyTypeList != null) {
                if (moneyTypeList.size() > 1) {
                    img_money_type.setVisibility(View.VISIBLE);
                } else {
                    img_money_type.setVisibility(View.GONE);

                }
            }


        }


        if (oTradeListEntity == null) {
            return;
        }
        text_balacne.setText(balance);
        if (isTrade.equals("2")) {
            text_chongzhi.setText("添加模拟金");
        } else if (isTrade.equals("1")) {
            text_chongzhi.setText("充值");

        }


        text_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTrade.equals("2")) {
                    getAddScore();
                } else if (isTrade.equals("1")) {
                    ORechargeEntity oRechargeEntity = QuoteProxy.getInstance().getoRechargeEntity();
                    if (oRechargeEntity == null) {
                        Toast.makeText(getActivity(), "请求中...", Toast.LENGTH_SHORT).show();
                        postRecharge();
                    } else {

                        popupWindow.dismiss();
                        int payFirst = oRechargeEntity.getPayFirst();
                        if (payFirst == 1) {
                            showItemPopWindow();
                        } else {
                            OUserActivity.enter(getActivity(), OConstant.O_RECHARGE);
                        }

                    }
                }
            }
        });


        List<OTradeListEntity.TradeListBean> tradeList = oTradeListEntity.getTradeList();


        for (OTradeListEntity.TradeListBean tradeBean : tradeList) {
            if (tradeBean.getCommCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                volumeList = tradeBean.getVolumeList();
                stopLossList = tradeBean.getStopLossList();
                stopProfitList = tradeBean.getStopProfitList();
                chargeUnit1 = tradeBean.getChargeUnit();
                depositList = tradeBean.getDepositList();
                commCode = tradeBean.getCommCode();
                contName = tradeBean.getContName();
                moneyTypeList = tradeBean.getMoneyTypeList();


            }
        }

        RadioButton profit1 = view.findViewById(R.id.profit1);
        RadioButton profit2 = view.findViewById(R.id.profit2);
        RadioButton profit3 = view.findViewById(R.id.profit3);
        RadioButton profit4 = view.findViewById(R.id.profit4);
        RadioButton profit5 = view.findViewById(R.id.profit5);

        TextView text_lvyue = view.findViewById(R.id.text_lvyue);
        TextView text_input = view.findViewById(R.id.text_input);

        RadioButton radioButton_volum1 = view.findViewById(R.id.radio_1);
        radioButton_volum1.setChecked(true);
        RadioButton radioButton_volum2 = view.findViewById(R.id.radio_2);
        RadioButton radioButton_volum3 = view.findViewById(R.id.radio_3);
        RadioButton radioButton_volum4 = view.findViewById(R.id.radio_4);
        RadioButton radioButton_volum5 = view.findViewById(R.id.radio_5);
        RadioButton radioButton_volum6 = view.findViewById(R.id.radio_6);

        TextView text_name = view.findViewById(R.id.text_name);
        text_lastprice1 = view.findViewById(R.id.text_lastprice);
        TextView text_time = view.findViewById(R.id.text_time);


        if (volumeList.size() != 0) {
            radioButton_volum1.setText(volumeList.get(0) + "手");
            radioButton_volum2.setText(volumeList.get(1) + "手");
            radioButton_volum3.setText(volumeList.get(2) + "手");
            radioButton_volum4.setText(volumeList.get(3) + "手");
            radioButton_volum5.setText(volumeList.get(4) + "手");
            radioButton_volum6.setText(volumeList.get(5) + "手");
        }


        TextView text_stopprofit = view.findViewById(R.id.text_stopprofit);
        text_stopprofit.setText(stopProfitList.get(0) + "");

        TextView text_stop_price = view.findViewById(R.id.text_stop_price);

        TextView text_unit = view.findViewById(R.id.text_coin_unit);

        if (money_much_flag == 1) {
            img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_jiaomoshi));

        } else if (money_much_flag == 0) {
            img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_yuanmoshi));
        }
        img_money_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money_much_flag == 0) {
                    img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_jiaomoshi));
                    money_much_flag = 1;
                    if (moneyTypeList.size() > 1) {
                        money_much_type = moneyTypeList.get(1);
                    }
                    if (money_much_type == 0) {
                        multiple = 1;
                    } else if (money_much_type == 1) {
                        multiple = 10;
                    }
                    if (radioButton_volum1.isChecked()) {

                        setData(true, text_stop_price, multiple, volumeList.get(0), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                    } else if (radioButton_volum2.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(1), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum3.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(2), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum4.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(3), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum5.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(4), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum6.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(5), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    }


                } else if (money_much_flag == 1) {
                    img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_yuanmoshi));
                    money_much_flag = 0;
                    money_much_type = moneyTypeList.get(0);
                    if (money_much_type == 0) {
                        multiple = 1;
                    } else if (money_much_type == 1) {
                        multiple = 10;
                    }

                    if (radioButton_volum1.isChecked()) {

                        setData(true, text_stop_price, multiple, volumeList.get(0), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                    } else if (radioButton_volum2.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(1), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum3.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(2), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum4.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(3), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum5.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(4), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum6.isChecked()) {
                        setData(true, text_stop_price, multiple, volumeList.get(5), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    }
                }
            }
        });


        radioButton_volum1.setOnClickListener(new View.OnClickListener() {

            private String lvyue;
            private Integer integer;

            @Override
            public void onClick(View v) {

                radioButton_volum1.setChecked(true);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }

                setData(true, text_stop_price, multiple, volumeList.get(0), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

            }
        });
        radioButton_volum2.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(true);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(true, text_stop_price, multiple, volumeList.get(1), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);


            }
        });
        radioButton_volum3.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(true);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(true, text_stop_price, multiple, volumeList.get(2), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                //setData(5, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });
        radioButton_volum4.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(true);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);

                if (volumeList.size() == 0) {
                    return;
                }
                setData(true, text_stop_price, multiple, volumeList.get(3), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                //  setData(10, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });
        radioButton_volum5.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(true);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(true, text_stop_price, multiple, volumeList.get(4), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                // setData(15, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });
        radioButton_volum6.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(true);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(true, text_stop_price, multiple, volumeList.get(5), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                // setData(20, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });
        RadioGroup radioGroup_profit = view.findViewById(R.id.radioGroup_profit);
        radioGroup_profit.getChildAt(0).performClick();

        text_lvyue.setText(profit1.getText().toString().replaceAll("元", ""));

        radioGroup_profit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.profit1:
                       /* int i = stopProfitList.get(0) * volume;
                        text_stopprofit.setText(i + "");

                        if (depositList.size()!=0){
                            Integer integer = depositList.get(0);
                            text_lvyue.setText(integer+"");
                            int i5 = Integer.parseInt(text_unit.getText().toString()) + integer;
                            text_input.setText(i5 + "元");
                        }*/


                        setDepositAndInput(true, text_stop_price, 0, profit1, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);


                        break;
                    case R.id.profit2:
                /*        int i1 = stopProfitList.get(1) * volume;
                        text_stopprofit.setText(i1 + "");

                        if (depositList.size()!=0){
                            Integer integer = depositList.get(1);
                            text_lvyue.setText(integer+"");
                            int i6 = Integer.parseInt(text_unit.getText().toString()) + integer;
                            text_input.setText(i6 + "元");
                        }
*/
                        //text_lvyue.setText(profit2.getText().toString().replaceAll("元", "").replaceAll("-", ""));

                        setDepositAndInput(true, text_stop_price, 1, profit2, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);


                        break;
                    case R.id.profit3:
                       /* int i2 = stopProfitList.get(2) * volume;
                        text_stopprofit.setText(i2 + "");

                        if (depositList.size()!=0){
                            Integer integer = depositList.get(2);
                            text_lvyue.setText(integer+"");

                        }

                        //text_lvyue.setText(profit3.getText().toString().replaceAll("元", "").replaceAll("-", ""));

                        int i7 = Integer.parseInt(text_unit.getText().toString()) + Integer.parseInt(profit3.getText().toString().replaceAll("元", "").replaceAll("-", ""));
                        text_input.setText(i7 + "元");*/
                        setDepositAndInput(true, text_stop_price, 2, profit3, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;
                    case R.id.profit4:
                     /*   int i3 = stopProfitList.get(3) * volume;
                        text_stopprofit.setText(i3 + "");
                        if (depositList.size()!=0){
                            Integer integer = depositList.get(3);
                            text_lvyue.setText(integer+"");

                        }
                       // text_lvyue.setText(profit4.getText().toString().replaceAll("元", "").replaceAll("-", ""));

                        int i8 = Integer.parseInt(text_unit.getText().toString()) + Integer.parseInt(profit4.getText().toString().replaceAll("元", "").replaceAll("-", ""));
                        text_input.setText(i8 + "元");*/

                        setDepositAndInput(true, text_stop_price, 3, profit4, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;
                    case R.id.profit5:
                      /*  int i4 = stopProfitList.get(4) * volume;
                        text_stopprofit.setText(i4 + "");
                        if (depositList.size()!=0){
                            Integer integer = depositList.get(4);
                            text_lvyue.setText(integer+"");

                        }

                       // text_lvyue.setText(profit5.getText().toString().replaceAll("元", "").replaceAll("-", ""));
                        int i9 = Integer.parseInt(text_unit.getText().toString()) + Integer.parseInt(profit5.getText().toString().replaceAll("元", "").replaceAll("-", ""));
                        text_input.setText(i9 + "元");*/


                        setDepositAndInput(true, text_stop_price, 4, profit5, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);
                        break;

                }
            }
        });


        TextView text_more = view.findViewById(R.id.text_more);
        LinearLayout layout_more = view.findViewById(R.id.layout_more);
        text_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreFlag == 1) {
                    layout_more.setVisibility(View.VISIBLE);
                    moreFlag = 0;
                } else if (moreFlag == 0) {
                    layout_more.setVisibility(View.GONE);
                    moreFlag = 1;

                }
            }
        });


        text_time.setText("持仓至" + clearTime + "自动平仓");

        LinearLayout radioGroup_volum = view.findViewById(R.id.radioGroup);
        radioGroup_volum.getChildAt(0).performClick();


        text_name.setText(marketName);


        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        text_jifen_pop = view.findViewById(R.id.text_jifen);

        if (eagle_result != 0 & text_jifen_pop != null) {
            text_jifen_pop.setText("(总红包余额" + OUtil.doublePoint(eagle_result)+ ",可抵扣" +  OUtil.doublePoint(OUtil.div(eagle_result, 10,1)) + "元)");
        }


        ImageView img_switch = view.findViewById(R.id.img_switch);


        String s1 = text_unit.getText().toString();
        if (s1 != null) {
            double sub = OUtil.sub(eagle_result, Double.parseDouble(s1));
            if (sub > 0) {
                img_switch.setEnabled(true);

            } else {
                img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                img_switch.setEnabled(false);
                img_much_flag = 0;
            }
        }


        TextView text_switch = view.findViewById(R.id.text_switch);
        img_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit_result = text_unit.getText().toString();
                if (img_much_flag == 0) {
                    service = unit_result;
                    eagle = "0";
                } else if (img_much_flag == 1) {
                    service = "0";
                    double mul = OUtil.mul(Double.parseDouble(unit_result), 10);
                    eagle = mul + "";
                }
                if (img_much_flag == 0) {
                    img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_open_btn));
                    text_switch.setText("本次已抵" + service + "元,扣除" + OUtil.doublePoint(OUtil.mul(Double.parseDouble(service), 10)) + "红包");
                    Toast.makeText(getActivity(), getString(R.string.o_text_hongbao), Toast.LENGTH_SHORT).show();

                    img_much_flag = 1;
                    text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    text_unit.getPaint().setAntiAlias(true);
                    text_unit.setText(unit_result + "");
                    text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                } else if (img_much_flag == 1) {
                    text_switch.setText("已关闭红包抵用,如需使用请打开");
                    img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));

                    img_much_flag = 0;
                    text_unit.setText(unit_result + "");
                    text_unit.getPaint().setFlags(0);
                    text_unit.getPaint().setAntiAlias(true);
                    text_unit.setTextColor(getResources().getColor(R.color.greencolor));

                }
            }
        });


        text_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String s1 = text_unit.getText().toString();
                    double sub = OUtil.sub(eagle_result, Double.parseDouble(s1));
                    if (sub > 0) {
                        img_switch.setEnabled(true);
                        if (img_much_flag == 1) {
                            text_switch.setText("本次已抵" + s1 + "元,扣除" + OUtil.doublePoint(OUtil.mul(Double.parseDouble(s1), 10)) + "红包");
                            text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                        } else if (img_much_flag == 0) {
                            text_switch.setText("已关闭红包抵用,如需使用请打开");
                            text_unit.getPaint().setFlags(0);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.greencolor));

                        }

                    } else {

                        img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                        img_switch.setEnabled(false);

                        img_much_flag = 0;
                        if (img_much_flag == 1) {
                            text_switch.setText("本次已抵" + s1 + "元,扣除" + OUtil.doublePoint(OUtil.mul(Double.parseDouble(s1), 10)) + "红包");
                            text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                        } else if (img_much_flag == 0) {
                            text_switch.setText("已关闭红包抵用,如需使用请打开");
                            text_unit.getPaint().setFlags(0);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.greencolor));

                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        view.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stoploss = null;
                if (profit1.isChecked()) {

                    String[] split = profit1.getText().toString().split("\n");


                    stoploss = split[0];
                }
                if (profit2.isChecked()) {
                    // stoploss = profit2.getText().toString().replaceAll("元", "");
                    String[] split = profit2.getText().toString().split("\n");


                    stoploss = split[0];
                }
                if (profit3.isChecked()) {
                    //stoploss = profit3.getText().toString().replaceAll("元", "");
                    String[] split = profit3.getText().toString().split("\n");


                    stoploss = split[0];
                }
                if (profit4.isChecked()) {
                    // stoploss = profit4.getText().toString().replaceAll("元", "");
                    String[] split = profit4.getText().toString().split("\n");


                    stoploss = split[0];
                }
                if (profit5.isChecked()) {
                    //stoploss = profit5.getText().toString().replaceAll("元", "");
                    String[] split = profit5.getText().toString().split("\n");


                    stoploss = split[0];
                }

                // String bond = text_lvyue.getText().toString();


                String unit_result = text_unit.getText().toString();

                if (img_much_flag == 0) {
                    service = unit_result;
                    eagle = "0";
                } else if (img_much_flag == 1) {
                    service = "0";
                    double mul = OUtil.mul(Double.parseDouble(unit_result), 10);
                    eagle = mul + "";
                }

                Log.d("print", "onClick:4278:  " + service);

                postOpenTrade(commCode, contName, true, "0", text_stopprofit.getText().toString(), stoploss, service, volume, String.valueOf(money_much_type), popupWindow, eagle);


            }
        });
    }


    private int money_less_flag = 0;
    private int money_less_type = 0;
    private int img_less_flag = 0;

    // TODO: 2019/8/6 买空
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showBuyLessPopWindow() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_buy_short, null);
        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        TextView text_balacne = view.findViewById(R.id.text_balance);
        TextView text_chongzhi = view.findViewById(R.id.text_chongzhi);

        TextView text_rate = view.findViewById(R.id.text_rate);
        RelativeLayout layout_rate = view.findViewById(R.id.layout_rate);
        View view_rate = view.findViewById(R.id.view_rate);

        if (rateDetail.equals("")) {
            layout_rate.setVisibility(View.GONE);
            view_rate.setVisibility(View.GONE);
        } else {
            text_rate.setText(rateDetail);
        }

        LinearLayout layout_shipan = view.findViewById(R.id.layout_shipan);

        ImageView img_money_type = view.findViewById(R.id.img_money_type);

        if (isTrade.equals("2")) {
            oTradeListEntity = QuoteProxy.getInstance().getoTradeListMoniEntity();
            layout_shipan.setVisibility(View.GONE);
            img_money_type.setVisibility(View.GONE);
        } else if (isTrade.equals("1")) {
            oTradeListEntity = QuoteProxy.getInstance().getoTradeListEntity();
            layout_shipan.setVisibility(View.VISIBLE);

            //这里是判断是否实盘模拟 暂时模拟盘没有角模式
            if (moneyTypeList != null) {
                if (moneyTypeList.size() > 1) {
                    img_money_type.setVisibility(View.VISIBLE);
                } else {
                    img_money_type.setVisibility(View.GONE);

                }
            }

        }


        if (oTradeListEntity == null) {
            return;
        }
        text_balacne.setText(balance);
        if (isTrade.equals("2")) {
            text_chongzhi.setText("添加模拟金");
        } else if (isTrade.equals("1")) {
            text_chongzhi.setText("充值");

        }

        text_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTrade.equals("2")) {
                    getAddScore();
                } else if (isTrade.equals("1")) {
                    ORechargeEntity oRechargeEntity = QuoteProxy.getInstance().getoRechargeEntity();
                    if (oRechargeEntity == null) {
                        Toast.makeText(getActivity(), "请求中...", Toast.LENGTH_SHORT).show();
                        postRecharge();
                    } else {
                        popupWindow.dismiss();
                        int payFirst = oRechargeEntity.getPayFirst();
                        if (payFirst == 1) {

                            showItemPopWindow();
                        } else {

                            OUserActivity.enter(getActivity(), OConstant.O_RECHARGE);
                        }
                    }
                }
            }
        });

        List<OTradeListEntity.TradeListBean> tradeList = oTradeListEntity.getTradeList();


        for (OTradeListEntity.TradeListBean tradeBean : tradeList) {
            if (tradeBean.getCommCode().equals(marketCode.replaceAll("[^a-z^A-Z]", ""))) {
                volumeList = tradeBean.getVolumeList();
                stopLossList = tradeBean.getStopLossList();
                stopProfitList = tradeBean.getStopProfitList();
                chargeUnit1 = tradeBean.getChargeUnit();
                depositList = tradeBean.getDepositList();
                commCode = tradeBean.getCommCode();
                contName = tradeBean.getContName();
                moneyTypeList = tradeBean.getMoneyTypeList();

            }
        }


        RadioButton profit1 = view.findViewById(R.id.profit1);
        RadioButton profit2 = view.findViewById(R.id.profit2);
        RadioButton profit3 = view.findViewById(R.id.profit3);
        RadioButton profit4 = view.findViewById(R.id.profit4);
        RadioButton profit5 = view.findViewById(R.id.profit5);

        TextView text_lvyue = view.findViewById(R.id.text_lvyue);
        TextView text_input = view.findViewById(R.id.text_input);

        RadioButton radioButton_volum1 = view.findViewById(R.id.radio_1);
        radioButton_volum1.setChecked(true);
        RadioButton radioButton_volum2 = view.findViewById(R.id.radio_2);
        RadioButton radioButton_volum3 = view.findViewById(R.id.radio_3);
        RadioButton radioButton_volum4 = view.findViewById(R.id.radio_4);
        RadioButton radioButton_volum5 = view.findViewById(R.id.radio_5);
        RadioButton radioButton_volum6 = view.findViewById(R.id.radio_6);

        TextView text_name = view.findViewById(R.id.text_name);
        text_lastprice1 = view.findViewById(R.id.text_lastprice);
        TextView text_time = view.findViewById(R.id.text_time);


        if (volumeList.size() != 0) {
            radioButton_volum1.setText(volumeList.get(0) + "手");
            radioButton_volum2.setText(volumeList.get(1) + "手");
            radioButton_volum3.setText(volumeList.get(2) + "手");
            radioButton_volum4.setText(volumeList.get(3) + "手");
            radioButton_volum5.setText(volumeList.get(4) + "手");
            radioButton_volum6.setText(volumeList.get(5) + "手");
        }


        TextView text_stopprofit = view.findViewById(R.id.text_stopprofit);
        text_stopprofit.setText(stopProfitList.get(0) + "");

        TextView text_stop_price = view.findViewById(R.id.text_stop_price);

        TextView text_unit = view.findViewById(R.id.text_coin_unit);

        if (money_less_flag == 1) {
            img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_jiaomoshi));

        } else if (money_less_flag == 0) {
            img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_yuanmoshi));
        }
        img_money_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money_less_flag == 0) {
                    img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_jiaomoshi));
                    money_less_flag = 1;
                    if (moneyTypeList.size() > 1) {
                        money_less_type = moneyTypeList.get(1);
                    } else {

                    }

                    if (money_less_type == 0) {
                        multiple = 1;
                    } else if (money_less_type == 1) {
                        multiple = 10;
                    }
                    if (radioButton_volum1.isChecked()) {

                        setData(false, text_stop_price, multiple, volumeList.get(0), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                    } else if (radioButton_volum2.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(1), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum3.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(2), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum4.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(3), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum5.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(4), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum6.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(5), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    }

                } else if (money_less_flag == 1) {
                    img_money_type.setImageDrawable(getResources().getDrawable(R.mipmap.o_yuanmoshi));
                    money_less_flag = 0;

                    money_less_type = moneyTypeList.get(0);

                    if (money_less_type == 0) {
                        multiple = 1;
                    } else if (money_less_type == 1) {
                        multiple = 10;
                    }
                    if (radioButton_volum1.isChecked()) {

                        setData(false, text_stop_price, multiple, volumeList.get(0), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                    } else if (radioButton_volum2.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(1), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum3.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(2), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum4.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(3), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum5.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(4), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    } else if (radioButton_volum6.isChecked()) {
                        setData(false, text_stop_price, multiple, volumeList.get(5), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);

                    }
                }
            }
        });


        radioButton_volum1.setOnClickListener(new View.OnClickListener() {

            private String lvyue;
            private Integer integer;

            @Override
            public void onClick(View v) {

                radioButton_volum1.setChecked(true);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);

                if (volumeList.size() == 0) {
                    return;
                }
                setData(false, text_stop_price, multiple, volumeList.get(0), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                //   setData(1, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);

            }
        });

        radioButton_volum2.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(true);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(false, text_stop_price, multiple, volumeList.get(1), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                // setData(2, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });

        radioButton_volum3.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(true);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(false, text_stop_price, multiple, volumeList.get(2), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                // setData(5, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });
        radioButton_volum4.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(true);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(false, text_stop_price, multiple, volumeList.get(3), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                //  setData(10, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });
        radioButton_volum5.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(true);
                radioButton_volum6.setChecked(false);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(false, text_stop_price, multiple, volumeList.get(4), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                // setData(15, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });

        radioButton_volum6.setOnClickListener(new View.OnClickListener() {
            private Integer integer;
            private String lvyue;

            @Override
            public void onClick(View v) {
                radioButton_volum1.setChecked(false);
                radioButton_volum2.setChecked(false);
                radioButton_volum3.setChecked(false);
                radioButton_volum4.setChecked(false);
                radioButton_volum5.setChecked(false);
                radioButton_volum6.setChecked(true);


                if (volumeList.size() == 0) {
                    return;
                }
                setData(false, text_stop_price, multiple, volumeList.get(5), profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input, depositList);
                //setData(20, profit1, profit2, profit3, profit4, profit5, text_stopprofit, text_unit, text_lvyue, text_input,depositList);


            }
        });

        RadioGroup radioGroup_profit = view.findViewById(R.id.radioGroup_profit);
        radioGroup_profit.getChildAt(0).performClick();

        text_lvyue.setText(profit1.getText().toString().replaceAll("元", ""));

        radioGroup_profit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.profit1:

                        setDepositAndInput(false, text_stop_price, 0, profit1, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;
                    case R.id.profit2:

                        setDepositAndInput(false, text_stop_price, 1, profit2, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;
                    case R.id.profit3:

                        setDepositAndInput(false, text_stop_price, 2, profit3, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;
                    case R.id.profit4:

                        setDepositAndInput(false, text_stop_price, 3, profit4, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;
                    case R.id.profit5:
                       /* int i4 = stopProfitList.get(4) * volume;
                        text_stopprofit.setText(i4 + "");
                        if (depositList.size()!=0){
                            Integer integer = depositList.get(4);
                            text_lvyue.setText(integer+"");

                        }
                      //  text_lvyue.setText(profit5.getText().toString().replaceAll("元", "").replaceAll("-", ""));
                        int i9 = Integer.parseInt(text_unit.getText().toString()) + Integer.parseInt(profit5.getText().toString().replaceAll("元", "").replaceAll("-", ""));
                        text_input.setText(i9 + "元");*/

                        setDepositAndInput(false, text_stop_price, 4, profit5, radioButton_volum1, radioButton_volum2, radioButton_volum3, radioButton_volum4, radioButton_volum5, radioButton_volum6, depositList, stopProfitList, text_stopprofit, text_lvyue, text_unit, text_input);

                        break;

                }
            }
        });


        TextView text_more = view.findViewById(R.id.text_more);
        LinearLayout layout_more = view.findViewById(R.id.layout_more);
        text_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreFlag == 1) {
                    layout_more.setVisibility(View.VISIBLE);
                    moreFlag = 0;
                } else if (moreFlag == 0) {
                    layout_more.setVisibility(View.GONE);
                    moreFlag = 1;

                }
            }
        });


        text_time.setText("持仓至" + clearTime + "自动平仓");

        LinearLayout radioGroup_volum = view.findViewById(R.id.radioGroup);
        radioGroup_volum.getChildAt(0).performClick();


        text_name.setText(marketName);


        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        text_jifen_pop = view.findViewById(R.id.text_jifen);

        if (eagle_result != 0 & text_jifen_pop != null) {
            text_jifen_pop.setText("(总红包余额" + OUtil.doublePoint(eagle_result)+ ",可抵扣" + OUtil.doublePoint(OUtil.div(eagle_result, 10,1)) + "元)");
        }


        ImageView img_switch = view.findViewById(R.id.img_switch);


        String s1 = text_unit.getText().toString();
        if (s1 != null) {
            double sub = OUtil.sub(eagle_result, Double.parseDouble(s1));
            if (sub > 0) {
                img_switch.setEnabled(true);

            } else {
                img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                img_switch.setEnabled(false);
                img_less_flag = 0;
            }
        }
        TextView text_switch = view.findViewById(R.id.text_switch);
        img_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit_result = text_unit.getText().toString();

                if (img_less_flag == 0) {
                    service = unit_result;
                    eagle = "0";
                } else if (img_less_flag == 1) {
                    service = "0";
                    double mul = OUtil.mul(Double.parseDouble(unit_result), 10);
                    eagle = mul + "";
                }

                if (img_less_flag == 0) {
                    img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_open_btn));
                    text_switch.setText("本次已抵" + service + "元,扣除" + OUtil.doublePoint(OUtil.mul(Double.parseDouble(service), 10)) + "红包");
                    Toast.makeText(getActivity(), getString(R.string.o_text_hongbao), Toast.LENGTH_SHORT).show();
                    img_less_flag = 1;

                    text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                    text_unit.getPaint().setAntiAlias(true);
                    text_unit.setText(unit_result + "");
                    text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));


                } else if (img_less_flag == 1) {
                    text_switch.setText("已关闭红包抵用,如需使用请打开");
                    img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                    img_less_flag = 0;

                    text_unit.setText(unit_result + "");
                    text_unit.getPaint().setFlags(0);
                    text_unit.getPaint().setAntiAlias(true);
                    text_unit.setTextColor(getResources().getColor(R.color.greencolor));

                }


            }
        });

        text_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String s1 = text_unit.getText().toString();
                    double sub = OUtil.sub(eagle_result, Double.parseDouble(s1));
                    if (sub > 0) {
                        img_switch.setEnabled(true);
                        if (img_less_flag == 1) {
                            text_switch.setText("本次已抵" + s1 + "元,扣除" + OUtil.doublePoint(OUtil.mul(Double.parseDouble(s1), 10)) + "红包");
                            text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                        } else if (img_less_flag == 0) {
                            text_switch.setText("已关闭红包抵用,如需使用请打开");
                            text_unit.getPaint().setFlags(0);
                            text_unit.getPaint().setAntiAlias(true);

                            text_unit.setTextColor(getResources().getColor(R.color.greencolor));

                        }
                    } else {
                        img_switch.setImageDrawable(getResources().getDrawable(R.mipmap.o_close_btn));
                        img_switch.setEnabled(false);
                        img_less_flag = 0;

                        if (img_less_flag == 1) {
                            text_switch.setText("本次已抵" + s1 + "元,扣除" + OUtil.doublePoint(OUtil.mul(Double.parseDouble(s1), 10)) + "红包");
                            text_unit.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            text_unit.getPaint().setAntiAlias(true);
                            text_unit.setTextColor(getResources().getColor(R.color.o_text_hint_color));

                        } else if (img_less_flag == 0) {
                            text_switch.setText("已关闭红包抵用,如需使用请打开");
                            text_unit.getPaint().setFlags(0);
                            text_unit.getPaint().setAntiAlias(true);

                            text_unit.setTextColor(getResources().getColor(R.color.greencolor));


                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        view.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stoploss = null;
                if (profit1.isChecked()) {
                    String[] split = profit1.getText().toString().split("\n");
                    stoploss = split[0];
                }
                if (profit2.isChecked()) {
                    // stoploss = profit2.getText().toString().replaceAll("元", "");
                    String[] split = profit2.getText().toString().split("\n");
                    stoploss = split[0];
                }
                if (profit3.isChecked()) {
                    //stoploss = profit3.getText().toString().replaceAll("元", "");

                    String[] split = profit3.getText().toString().split("\n");
                    stoploss = split[0];
                }
                if (profit4.isChecked()) {
                    // stoploss = profit4.getText().toString().replaceAll("元", "");
                    String[] split = profit4.getText().toString().split("\n");
                    stoploss = split[0];
                }
                if (profit5.isChecked()) {
                    //stoploss = profit5.getText().toString().replaceAll("元", "");
                    String[] split = profit5.getText().toString().split("\n");
                    stoploss = split[0];
                }
                String unit_result = text_unit.getText().toString();

                if (img_less_flag == 0) {
                    service = unit_result;
                    eagle = "0";
                } else if (img_less_flag == 1) {
                    service = "0";
                    double mul = OUtil.mul(Double.parseDouble(unit_result), 10);
                    eagle = mul + "";
                }

                // String bond = text_lvyue.getText().toString();

                postOpenTrade(commCode, contName, false, "0", text_stopprofit.getText().toString(), stoploss, service, volume, String.valueOf(money_less_type), popupWindow, eagle);

            }
        });
    }


    private List<OBondEntity> oBondEntityList;

    private void postOpenTrade(String commodcode, String contract, boolean isbuy, String price, String stopProfit, String stopLoss, String servicechange, int volume, String moneyType, PopupWindow popupWindow, String eagleDeduction) {
        String SEED = "0Aa1Bb2Cc3Dd4Ee5Ff6Gg7Hh8Ii9Jj0Kk1Ll2Mm3Nn4Oo5Pp6Qq7Rr8Ss9Tt0Uu1Vv2Ww3Xx4Yy5Zz6789";

        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int i1 = random.nextInt(SEED.length());
            stringBuffer.append(SEED.charAt(i1));
        }

        String POST_URL = OConstant.URL_OPEN + "?" + OConstant.PARAM_IDENTITY + "=" + stringBuffer.toString() + "&" +
                OConstant.PARAM_TRADETYPE + "=" + isTrade + "&" +
                OConstant.PARAM_SOURCE + "=" + "下单" + "&" +
                OConstant.PARAM_COMMODITY + "=" + commodcode + "&" +
                OConstant.PARAM_CONTRACT + "=" + contract + "&" +
                OConstant.PARAM_ISBUY + "=" + isbuy + "&" +
                OConstant.PARAM_PRICE + "=" + price + "&" +
                OConstant.PARAM_STOPPROFIT + "=" + stopProfit + "&" +
                OConstant.PARAM_STOPLOSS + "=" + stopLoss + "&" +
                OConstant.PARAM_SERVICE_CHANGE + "=" + servicechange + "&" +
                OConstant.PARAM_EAGLE_DEDUCTION + "=" + eagleDeduction + "&" +
                OConstant.STAY_VOLUME + "=" + volume + "&" +
                OConstant.PARAM_MONEY_TYPE + "=" + moneyType + "&" +
                OConstant.PARAM_PLATFORM + "=" + OConstant.STAY_ANDROID;
        Log.d("print", "postOpenTrade:下单地址:   " + POST_URL);
        OkGo.<String>post(POST_URL)
                /* .params(OConstant.PARAM_IDENTITY, stringBuffer.toString())
                 .params(OConstant.PARAM_TRADETYPE, isTrade)
                 .params(OConstant.PARAM_SOURCE, "下单")
                 .params(OConstant.PARAM_COMMODITY, commodcode)
                 .params(OConstant.PARAM_CONTRACT, contract)
                 .params(OConstant.PARAM_ISBUY, isbuy)
                 .params(OConstant.PARAM_PRICE, price)
                 .params(OConstant.PARAM_STOPPROFIT, stopProfit)
                 .params(OConstant.PARAM_STOPLOSS, stopLoss)
                 .params(OConstant.PARAM_SERVICE_CHANGE, servicechange)
                 .params(OConstant.PARAM_EAGLE_DEDUCTION, eagleDeduction)
                 .params(OConstant.STAY_VOLUME, volume)
                 .params(OConstant.PARAM_MONEY_TYPE, moneyType)
                 .params(OConstant.PARAM_PLATFORM, OConstant.STAY_ANDROID)*/
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (isAdded()) {
                            dismissProgressDialog();
                            if (!TextUtils.isEmpty(response.body())) {
                                OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                                Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();

                                if (oCodeMsgEntity.isSuccess() == true) {
                                    if (popupWindow == null) {
                                    } else {
                                        popupWindow.dismiss();
                                        marketCode_bus = commodcode;
                                    }


                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (isAdded()) {
                            dismissProgressDialog();
                            Toast.makeText(getContext(), "网络不好,请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /*行情选择的*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopWindow() {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_market, null);


        oforeignAdapter = new OHomeMarketAdapter(getActivity());
        ostockAdapter = new OHomeMarketAdapter(getActivity());
        odomesAdapter = new OHomeMarketAdapter(getActivity());

        postForeignQuote();
        postStockQuote();
        postDomesQuote();

        RecyclerView recyclerView_foreign = view.findViewById(R.id.recyclerview_foreign);
        deal(recyclerView_foreign);
        recyclerView_foreign.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView_foreign.setAdapter(oforeignAdapter);

        RecyclerView recyclerView_stockindex = view.findViewById(R.id.recyclerview_stockindex);
        deal(recyclerView_stockindex);
        recyclerView_stockindex.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView_stockindex.setAdapter(ostockAdapter);

        RecyclerView recyclerView_domes = view.findViewById(R.id.recyclerview_domes);
        deal(recyclerView_domes);
        recyclerView_domes.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView_domes.setAdapter(odomesAdapter);

        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

   /*     int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;*/

        oforeignAdapter.setOnItemClick(new OHomeMarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {
                getItemData(popupWindow, code);


            }
        });

        ostockAdapter.setOnItemClick(new OHomeMarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {
                getItemData(popupWindow, code);

            }
        });

        odomesAdapter.setOnItemClick(new OHomeMarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {
                getItemData(popupWindow, code);

            }
        });


        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    private int xinshouFlag = 1;
    private int upFlag = 1;
    private int downFlag = 1;
    private int zhiyinFlag = 1;
    private int zhisunFlag = 1;
    private int chicangFlag = 1;
    private int dazhangFlag = 1;
    private int jiaoyiFlag = 1;
    private int lvyueFlag = 1;
    private int yinliFlag = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showRulePopWindow() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_rule, null);
        TextView text_name = view.findViewById(R.id.text_name);
        text_name.setText(marketName);


        TextView text_unit = view.findViewById(R.id.text_coin_unit);
        if (currency!=null){
            if (currency.equals("USD")) {
                text_unit.setText("货币单位: 美元");
            } else if (currency.equals("CNY")) {
                text_unit.setText("货币单位: 人民币");
            }
        }

        TextView text_trade_unit = view.findViewById(R.id.text_trade_unit);
        text_trade_unit.setText("交易单位: " + unit);

        TextView text_min_change = view.findViewById(R.id.text_min_change);
        text_min_change.setText("最小波动: " + volatility);

        TextView text_change_money = view.findViewById(R.id.text_change_money);
        text_change_money.setText(volatilityIncome);

        TextView text_time = view.findViewById(R.id.text_buy_sell_time);
        text_time.setText("[买入时间]" + buyTimeAM + "\n" + "[卖出时间]" + sellTimeAM);

        TextView text_cleantime = view.findViewById(R.id.text_clean_time);
        text_cleantime.setText(clearTime);

        TextView text_chargeunit = view.findViewById(R.id.text_all_money);
        text_chargeunit.setText(chargeUnit + "元");

        TextView text_rate = view.findViewById(R.id.text_exchange_rate);
        text_rate.setText("汇率: " + rate);

        TextView text_introduce = view.findViewById(R.id.text_introduce);
        text_introduce.setText(introduce);

        TextView text_chicang = view.findViewById(R.id.text_chicangtime);
        text_chicang.setText(name + "最后持仓时间:" + clearTime + "\n" + "当持仓时间到点后，持仓中的交易会被强制平仓，不保证成交价格，请务必在到期前自己选择卖出。");

        TextView text_jiaoyizonghe = view.findViewById(R.id.text_jiaoyizonghefei);
        text_jiaoyizonghe.setText(name + "期货每手交易综合费: " + chargeUnit + "元/手(买进卖出只收取一次)");

        TextView text_dazhang = view.findViewById(R.id.text_dazhang);
        text_dazhang.setText("交易品种涨幅≥" + highLimit + "时禁止买跌,跌幅≥" + lowLimit + "时禁止买涨.交易品种涨幅≥" + highClose + "时持仓中买跌的交易全部强制平台,跌幅≥" + lowClose + "时持仓中买涨的交易强制平仓.");

        RelativeLayout layout_xinsou = view.findViewById(R.id.layout_xinshou);
        LinearLayout laout_stay_xinshou = view.findViewById(R.id.stay_xinshou);
        layout_xinsou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xinshouFlag == 0) {
                    laout_stay_xinshou.setVisibility(View.GONE);
                    xinshouFlag = 1;
                } else if (xinshouFlag == 1) {
                    laout_stay_xinshou.setVisibility(View.VISIBLE);
                    xinshouFlag = 0;
                }
            }
        });

        RelativeLayout layout_up = view.findViewById(R.id.layout_up);
        LinearLayout stay_up = view.findViewById(R.id.stay_up);
        layout_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upFlag == 0) {
                    stay_up.setVisibility(View.GONE);
                    upFlag = 1;
                } else if (upFlag == 1) {
                    stay_up.setVisibility(View.VISIBLE);
                    upFlag = 0;
                }
            }
        });

        RelativeLayout layout_down = view.findViewById(R.id.layout_down);
        LinearLayout stay_down = view.findViewById(R.id.stay_down);
        layout_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downFlag == 0) {
                    stay_down.setVisibility(View.GONE);
                    downFlag = 1;
                } else if (downFlag == 1) {
                    stay_down.setVisibility(View.VISIBLE);
                    downFlag = 0;
                }
            }
        });

        RelativeLayout layout_zhiyin = view.findViewById(R.id.layout_zhiyin);
        LinearLayout stay_zhiyin = view.findViewById(R.id.stay_zhiyin);
        layout_zhiyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhiyinFlag == 0) {
                    stay_zhiyin.setVisibility(View.GONE);
                    zhiyinFlag = 1;
                } else if (zhiyinFlag == 1) {
                    stay_zhiyin.setVisibility(View.VISIBLE);
                    zhiyinFlag = 0;
                }
            }
        });

        RelativeLayout layout_zhisun = view.findViewById(R.id.layout_zhisun);
        LinearLayout stay_zhisun = view.findViewById(R.id.stay_zhisun);
        layout_zhisun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zhisunFlag == 0) {
                    stay_zhisun.setVisibility(View.GONE);
                    zhisunFlag = 1;
                } else if (zhisunFlag == 1) {
                    stay_zhisun.setVisibility(View.VISIBLE);
                    zhisunFlag = 0;
                }
            }
        });

        RelativeLayout layout_chicang = view.findViewById(R.id.layout_chicang);
        LinearLayout stay_chicang = view.findViewById(R.id.stay_chicang);
        layout_chicang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chicangFlag == 0) {
                    stay_chicang.setVisibility(View.GONE);
                    chicangFlag = 1;
                } else if (chicangFlag == 1) {
                    stay_chicang.setVisibility(View.VISIBLE);
                    chicangFlag = 0;
                }
            }
        });

        RelativeLayout layout_dazhang = view.findViewById(R.id.layout_dazahng);
        LinearLayout stay_dazhang = view.findViewById(R.id.stay_dazhang);
        layout_dazhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dazhangFlag == 0) {
                    stay_dazhang.setVisibility(View.GONE);
                    dazhangFlag = 1;
                } else if (dazhangFlag == 1) {
                    stay_dazhang.setVisibility(View.VISIBLE);
                    dazhangFlag = 0;
                }
            }
        });

        RelativeLayout layout_jiaoyi = view.findViewById(R.id.layout_jiaoyizonghefei);
        LinearLayout stay_jiaoyi = view.findViewById(R.id.stay_jiaoyizonghefei);
        layout_jiaoyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jiaoyiFlag == 0) {
                    stay_jiaoyi.setVisibility(View.GONE);
                    jiaoyiFlag = 1;
                } else if (jiaoyiFlag == 1) {
                    stay_jiaoyi.setVisibility(View.VISIBLE);
                    jiaoyiFlag = 0;
                }
            }
        });

        RelativeLayout layout_lvyue = view.findViewById(R.id.layout_lvyuebaozhangjin);
        LinearLayout stay_lvyue = view.findViewById(R.id.stay_lvyuebaozhenjin);
        layout_lvyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvyueFlag == 0) {
                    stay_lvyue.setVisibility(View.GONE);
                    lvyueFlag = 1;
                } else if (lvyueFlag == 1) {
                    stay_lvyue.setVisibility(View.VISIBLE);
                    lvyueFlag = 0;
                }
            }
        });

        RelativeLayout layout_yinliruhe = view.findViewById(R.id.layout_yinliruhe);
        LinearLayout stay_yinliruhe = view.findViewById(R.id.stay_yinliruhe);
        layout_yinliruhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yinliFlag == 0) {
                    stay_yinliruhe.setVisibility(View.GONE);
                    yinliFlag = 1;
                } else if (yinliFlag == 1) {
                    stay_yinliruhe.setVisibility(View.VISIBLE);
                    yinliFlag = 0;
                }
            }
        });


        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        TextView text_moni = view.findViewById(R.id.text_moni);

        text_moni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

                // TODO: 2019/6/19  这里需要更换数据

                isTrade = "2";
                EventBus.getDefault().post(new OEventData(OUserConfig.O_TRADE, "2"));
                layout_ismoni.setVisibility(View.VISIBLE);
                layout_istrade.setVisibility(View.GONE);

                layout_light_one.setVisibility(View.GONE);
                layout_light_two.setVisibility(View.GONE);
                layout_light_three.setVisibility(View.GONE);
                stay_line.setVisibility(View.GONE);

                light_flag = 0;
                img_light.setImageDrawable(getResources().getDrawable(R.mipmap.o_shandian_hui_icon));


                setLight();


            }
        });


        view.findViewById(R.id.text_todo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OImmersiveActivity.enter(getActivity(), OConstant.O_RAIDERS);
                popupWindow.dismiss();
                getActivity().finish();
            }
        });


        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }


    private void getItemData(PopupWindow popupWindow, String code) {
        String[] split = code.split(",");
        marketCode = split[0];

        getName();
        postQuote(marketCode);
        getIsAdd();
        getTrendView(webView);
        popupWindow.dismiss();
        money_type_type_light = 0;
        money_type_flag_light = 0;
        btn_flag = 0;
        setLight();
        postItemQuote(marketCode);


    }

    private void postItemQuote(String marketCode) {
        String quoteUrl = QuoteProxy.getInstance().getQuoteUrl();
        if (quoteUrl != null) {
            OkGo.<String>post(quoteUrl)
                    .tag(this)
                    .params(PARAM_CALLBACK, "?")
                    .params(PARAM_CODE, marketCode)
                    .params("_", Calendar.getInstance().getTimeInMillis())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (!TextUtils.isEmpty(response.body())) {
                                String responese = Util.jsonReplace(response.body());
                                OMarketEntity oMarketEntity = new Gson().fromJson(responese, OMarketEntity.class);
                                String data = oMarketEntity.getData();
                                String[] split = data.split(",");
                                text_oprice.setText(split[6]);
                                text_before_price.setText(split[5]);
                                text_high.setText(split[7]);
                                text_low.setText(split[8]);
                                // Log.d("print", "onSuccess:4866:买多:" + split[4] + "-----买空 " + split[2]);
                                // TODO: 2019/7/17
                                if (text_zhang != null) {
                                    text_zhang.setText(split[4] + "手");
                                    text_die.setText(split[2] + "手");

                                    double buyVolumeViewWidth = calculateAmountViewWidth(Double.parseDouble(split[4]),
                                            OUtil.add(Double.parseDouble(split[2]), Double.parseDouble(split[4])));
                                    double saleVolumeViewWidth = calculateAmountViewWidth(Double.parseDouble(split[2]),
                                            OUtil.add(Double.parseDouble(split[2]), Double.parseDouble(split[4])));
                                    ViewGroup.LayoutParams params = view_buy_many.getLayoutParams();
                                    int height = layout_bar.getMeasuredWidth();
                                    int i = Util.dip2px(getContext(), height);
                                    params.width = (int) buyVolumeViewWidth;
                                    view_buy_many.setLayoutParams(params);
                                    params = view_buy_less.getLayoutParams();
                                    params.width = (int) saleVolumeViewWidth;
                                    view_buy_less.setLayoutParams(params);
                                }
                            }
                        }
                    });
        }
    }

    private double calculateAmountViewWidth(double volume, double totalVolume) {
        if (totalVolume == 0) return 0;
        int width = layout_bar.getMeasuredWidth();
        // double MAX_VIEW_HEIGHT = Util.dip2px(getContext(), height);
        double div = OUtil.div(volume, totalVolume, 1);
        //  Log.d("print", "calculateAmountViewWidth: 4938:   " + div)
        double widthPx = OUtil.mul(div, width);
        return widthPx;
    }

    private void postForeignQuote() {

        List<String> dataList = QuoteProxy.getInstance().getForeigndataList();
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();

        if (dataList != null) {

            if (oforeignAdapter != null) {

                oforeignAdapter.setDatas(null, dataList);
                oforeignAdapter.setForeignDatas(OUserConfig.P_FOREIGN, oApiEntity.getForeignCommds());
            }
        }

    }

    private void postStockQuote() {

        List<String> dataList = QuoteProxy.getInstance().getStockindexdataList();
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();

        if (dataList != null) {
            if (ostockAdapter != null) {

                ostockAdapter.setDatas(null, dataList);
                ostockAdapter.setStockDatas(OUserConfig.P_STOCKINDEX, oApiEntity.getStockIndexCommds());
            }

        }

    }

    private void postDomesQuote() {
        List<String> dataList = QuoteProxy.getInstance().getDomesdataList();
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (dataList != null) {

            if (odomesAdapter != null) {
                odomesAdapter.setDatas(null, dataList);
                odomesAdapter.setDomesDatas(OUserConfig.P_DOMESTIC, oApiEntity.getDomesticCommds());
            }

        }

    }

    private void deal(RecyclerView recyclerView) {
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);

        //防止嵌套出现轻微卡顿的问题
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;

            }
        });
    }


}
