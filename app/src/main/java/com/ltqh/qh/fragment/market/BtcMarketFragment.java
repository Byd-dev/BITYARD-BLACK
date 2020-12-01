package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ltqh.qh.Api.QuoteListManger;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.adapter.BtcMarketAdapter;
import com.ltqh.qh.adapter.QuoteAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.ltqh.qh.view.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class BtcMarketFragment extends OBaseFragment implements View.OnClickListener, Observer {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int count = 0;
    @BindView(R.id.bar)
    LinearLayout layout_bar;

    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;

    private String sort = "0";
    private QuoteAdapter quoteAdapter_market;

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
        ViewUtils.setLayoutParams(getContext(), layout_bar);
        QuoteListManger.getInstance().addObserver(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initTabView();


            }
        }, 100);

        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager = new LinearLayoutManager(getActivity());




        /*行情 分割线-----------------------------------------------------------------------------*/
        quoteAdapter_market = new QuoteAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(quoteAdapter_market);

        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 1;
                // getBtcMarket(REFRESHTYPE, sort, count);

            }
        });




        view.findViewById(R.id.img_search).setOnClickListener(this);


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
                        sort = "0";


                        break;
                    case 1:
                        sort = "1";


                        break;
                    case 2:
                        sort = "2";

                        break;
                    case 3:
                        sort = "3";


                        break;
                    case 4:
                        sort = "4";


                        break;
                }
                //getBtcMarket(REFRESHTYPE, sort, 1);
                List<String> quoteList = arrayMap.get(sort);
                quoteAdapter_market.setDatas(quoteList);
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
        return R.layout.fragment_btcmarket;
    }

    @Override
    protected void intPresenter() {

    }

    /*private void getBtcMarket(String type, String sort, int page) {
        NetManger.getInstance().btcPrice(page, sort, new OnNetResult() {
            @Override
            public void onNetResult(String state, Object response) {
                if (state.equals(BUSY)) {
                    showProgressDialog();
                } else if (state.equals(SUCCESS)) {
                    dismissProgressDialog();
                    BtcPriceEntity btcPriceEntity = new Gson().fromJson(response.toString(), BtcPriceEntity.class);
                    if (type.equals(LOADTYPE)) {
                        btcMarketAdapter.addDatas(btcPriceEntity.getData());

                    } else if (type.equals(REFRESHTYPE)) {

                        btcMarketAdapter.setDatas(btcPriceEntity.getData());
                    }
                    swipeRefreshLayout.setRefreshing(false);

                } else if (state.equals(FAILURE)) {
                    dismissProgressDialog();
                }


            }
        });
    }*/

    @Override
    protected void initData() {
        //getBtcMarket(REFRESHTYPE, null, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.img_search:
                IntentActivity.enter(getActivity(), Constant.SEARCH);
                break;

        }
    }

    private ArrayMap<String, List<String>> arrayMap;

    @Override
    public void update(Observable o, Object arg) {
        if (o == QuoteListManger.getInstance()) {
            arrayMap = (ArrayMap<String, List<String>>) arg;
            List<String> quoteList = arrayMap.get(sort);
            runOnUiThread(() -> {
                if (quoteList.size() >= 3) {
                    quoteAdapter_market.setDatas(quoteList);

                }
            });

        }
    }

}
