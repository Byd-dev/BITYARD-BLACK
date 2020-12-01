package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.BtcMarketAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BtcMarketF2ragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int count = 0;


    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;

    private String sort = "";


    private int lastVisibleItem;
    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private BtcMarketAdapter btcMarketAdapter;

    @Override
    protected void onLazyLoad() {


    }

    @Override
    protected void initView(View view) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initTabView();


            }
        }, 100);

        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        btcMarketAdapter = new BtcMarketAdapter(getActivity());
        recyclerView.setAdapter(btcMarketAdapter);


        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 1;

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == btcMarketAdapter.getItemCount() - 1) {
                    btcMarketAdapter.startLoad();
                    count = count + 1;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


    }

    private List<String> titleList = new ArrayList<>();

    private void initTabView() {


        String language = SPUtils.getString(Constant.LANGUAGE);
        Log.d("print", "initTabView:72:  " + language);
        if (language.equals("en_US")) {
            titleList.add("Circulation");
            titleList.add("Price");
            titleList.add("Available");
            titleList.add("Trade24h");
            titleList.add("Percent24h");
        } else {
            titleList.add("流通市值");
            titleList.add("价格");
            titleList.add("流通数量");
            titleList.add("24小时交易量");
            titleList.add("24小时涨跌幅");

        }

        for (int i = 0; i < titleList.size(); i++) {
            home_tab.addTab(titleList.get(i));
        }

        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        sort = "circulation_usd";


                        break;
                    case 1:
                        sort = "price_usd";


                        break;
                    case 2:
                        sort = "available_supply";

                        break;
                    case 3:
                        sort = "trademoney24h_usd";


                        break;
                    case 4:
                        sort = "percent_24h";

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
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_btcmarket2;
    }

    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;


        }
    }


}
