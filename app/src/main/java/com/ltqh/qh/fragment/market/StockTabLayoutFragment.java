package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.GoldlistAdapter;
import com.ltqh.qh.adapter.StockTabAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.GoldlistEntity;
import com.ltqh.qh.entity.StockEntity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.view.EnhanceTabLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import butterknife.BindView;

public class StockTabLayoutFragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;



    @BindView(R.id.radio_0)
    RadioButton radioButton0;
    @BindView(R.id.radio_1)
    RadioButton radioButton1;
    @BindView(R.id.radio_2)
    RadioButton radioButton2;
    @BindView(R.id.radio_3)
    RadioButton radioButton3;
    int page = 1;
    private int lastVisibleItem;
    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";
     private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private StockTabAdapter stockTabAdapter;


    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;


    @BindView(R.id.recyclerview_gold)
    RecyclerView recyclerView_gold;
    private GoldlistAdapter goldlistAdapter;

    private String Titles[] = new String[]{"涨跌幅", "涨跌额","成交量","成交额"};

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_stock_layout;
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        goldlistAdapter = new GoldlistAdapter(getActivity());

        recyclerView_gold.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView_gold.setAdapter(goldlistAdapter);

        stockTabAdapter = new StockTabAdapter(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(stockTabAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getStockData(REFRESHTYPE, page, SORT);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == stockTabAdapter.getItemCount() - 1) {
                    //加载更多功能的代码
                    if (page<=5){

                        page = page + 1;
                        getStockData(LOADTYPE, page, SORT);
                    }else {
                        Toast.makeText(getActivity(),"到底了",Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });


        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getStockData(REFRESHTYPE, page, Constant.STAY_CHANGEPERCENT);

                        SORT = Constant.STAY_CHANGEPERCENT;
                        break;
                    case 1:
                        getStockData(REFRESHTYPE, page, Constant.STAY_PRICECHANGE);
                        SORT = Constant.STAY_PRICECHANGE;
                        break;
                    case 2:
                        getStockData(REFRESHTYPE, page, Constant.STAY_VOLUME);
                        SORT = Constant.STAY_VOLUME;
                        break;
                    case 3:
                        getStockData(REFRESHTYPE, page, Constant.STAY_AMOUNT);
                        SORT = Constant.STAY_AMOUNT;
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



        view.findViewById(R.id.radio_0).setOnClickListener(this);
        view.findViewById(R.id.radio_1).setOnClickListener(this);
        view.findViewById(R.id.radio_2).setOnClickListener(this);
        view.findViewById(R.id.radio_3).setOnClickListener(this);
        radioButton0.setBackground(getResources().getDrawable(R.mipmap.market_bg));


    }


    private void getHomeGold() {
        OkGo.<String>get(Constant.URL_HOME_GOLD_URL)
                .tag(this)
                .params(Constant.PARAM_PAGE, 1)
                .params(Constant.PARAM_NUMBER, 10)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_SORT, Constant.STAY_SORT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            GoldlistEntity goldlistEntity = new Gson().fromJson(response.body(), GoldlistEntity.class);
                           // Log.d("print", "onSuccess:134: "+goldlistEntity);
                            if (goldlistEntity.getCode() == 1) {
                                goldlistAdapter.setDatas(goldlistEntity.getData());
                            }
                        }
                    }
                });

    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }



    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {


        getStockData(REFRESHTYPE, 1, Constant.STAY_CHANGEPERCENT);

        getHomeGold();
    }

    private void getStockData(String type, int page, String sort) {
        OkGo.<String>get(Constant.URL_STOCK)
                .params(Constant.PARAM_PAGE, page)
                .params(Constant.PARAM_NUM, 20)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_NODE, Constant.STAY_SH_A)
                .params(Constant.PARAM_SORT, sort)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (type.equals(REFRESHTYPE)) {
                            swipeRefreshLayout.setRefreshing(true);

                        } else if (type.equals(LOADTYPE)) {
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (swipeRefreshLayout != null) {

                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (!TextUtils.isEmpty(response.body())) {

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);

                            if (codeMsgEntity.getCode() ==1) {
                                StockEntity stockEntity = new Gson().fromJson(response.body(), StockEntity.class);
                                List<StockEntity.DataBean> data = stockEntity.getData();
                                if (data!=null){

                                    if (type.equals(REFRESHTYPE)) {
                                        stockTabAdapter.setDatas(data);
                                    } else if (type.equals(LOADTYPE)) {
                                        stockTabAdapter.addDatas(data);
                                    }
                                }
                            } else {
                                stockTabAdapter.stopLoad();
                            }

                        } else {
                            stockTabAdapter.stopLoad();
                            Toast.makeText(getActivity(), "到底了", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "当前暂无数据", Toast.LENGTH_SHORT).show();


                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.radio_0:
                getStockData(REFRESHTYPE, page, Constant.STAY_CHANGEPERCENT);

                SORT = Constant.STAY_CHANGEPERCENT;
                radioButton0.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton1.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton2.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton3.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton0.setTextColor(getResources().getColor(R.color.white));
                radioButton1.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton2.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton3.setTextColor(getResources().getColor(R.color.text_maincolor));

                break;
            case R.id.radio_1:
                getStockData(REFRESHTYPE, page, Constant.STAY_PRICECHANGE);
                SORT = Constant.STAY_PRICECHANGE;
                radioButton0.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton1.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton2.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton3.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton0.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton1.setTextColor(getResources().getColor(R.color.white));
                radioButton2.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton3.setTextColor(getResources().getColor(R.color.text_maincolor));
                break;

            case R.id.radio_2:
                getStockData(REFRESHTYPE, page, Constant.STAY_VOLUME);
                SORT = Constant.STAY_VOLUME;
                radioButton0.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton1.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton2.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton3.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton0.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton1.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton2.setTextColor(getResources().getColor(R.color.white));
                radioButton3.setTextColor(getResources().getColor(R.color.text_maincolor));
                break;

            case R.id.radio_3:
                getStockData(REFRESHTYPE, page, Constant.STAY_AMOUNT);
                SORT = Constant.STAY_AMOUNT;
                radioButton0.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton1.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton2.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton3.setBackground(getResources().getDrawable(R.mipmap.market_bg));
                radioButton0.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton1.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton2.setTextColor(getResources().getColor(R.color.text_maincolor));
                radioButton3.setTextColor(getResources().getColor(R.color.white));
                break;

        }
    }



}
