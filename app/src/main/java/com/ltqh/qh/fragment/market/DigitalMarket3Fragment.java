package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.BtcRankAdapter;
import com.ltqh.qh.adapter.HomeTopAdapter;
import com.ltqh.qh.adapter.MarketAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.BtcRankEntity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;

public class DigitalMarket3Fragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.layout_up_down)
    LinearLayout layout_up_down;


    private int flag = 0;
    private String isupdown = "up";

    @BindView(R.id.text_up_down)
    TextView text_up_down;
    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    @BindView(R.id.recyclerview_top)
    RecyclerView recyclerView_top;
    private HomeTopAdapter homeTopAdapter;

    private List<String> titleList = new ArrayList<>();


   /* @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
*/

    int page = 1;
    private int lastVisibleItem;
    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String NULLTYPE = "nulltype";
    private LinearLayoutManager linearLayoutManager;


    private BtcRankAdapter btcRankAdapter;
    private BtcRankEntity btcRankEntity;


    @Override
    protected void onLazyLoad() {
        getQuote();


        NetManger.getInstance().btcQuote(new OnNetResult() {
            @Override
            public void onNetResult(String state, Object response) {
                if (state.equals(BUSY)) {
                    showProgressDialog();
                } else if (state.equals(SUCCESS)) {
                    dismissProgressDialog();
                    btcRankEntity = new Gson().fromJson(response.toString(), BtcRankEntity.class);
                    btcRankAdapter.setRisData(btcRankEntity.getData().getRising(),0);
                } else if (state.equals(FAILURE)) {
                    dismissProgressDialog();
                }
            }
        });



       /* if (!ODateUtil.isWeekend()) {
            startScheduleJob(mHandler, MARKET_PERIOD, MARKET_PERIOD);
        }*/
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
        //涨幅榜
        btcRankAdapter = new BtcRankAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(btcRankAdapter);

        //btc
        homeTopAdapter = new HomeTopAdapter(getActivity());
        recyclerView_top.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView_top.setAdapter(homeTopAdapter);


        layout_up_down.setOnClickListener(this);


    }

    private void initTabView() {


        String language = SPUtils.getString(Constant.LANGUAGE);
        Log.d("print", "initTabView:72:  " + language);
        if (language.equals("en_US")) {
            titleList.add("Rising");
            titleList.add("Falling");

        } else {
            titleList.add("涨幅榜");
            titleList.add("跌幅榜");

        }

        for (int i = 0; i < titleList.size(); i++) {
            home_tab.addTab(titleList.get(i));
        }

        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        btcRankAdapter.setRisData(btcRankEntity.getData().getRising(),0);
                        btcRankAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        btcRankAdapter.setFallData(btcRankEntity.getData().getFalling(),1);
                        btcRankAdapter.notifyDataSetChanged();

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


    private void getQuote() {

        List<String> dataList = QuoteProxy.getInstance().getDigitalDataList();


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (dataList != null) {

            homeTopAdapter.setIsUpDown(isupdown);
            homeTopAdapter.setDatas(OUserConfig.P_DIGITAL, dataList.subList(6, 9));
            homeTopAdapter.setDigitalDatas(OUserConfig.P_DIGITAL, oApiEntity.getDigitalCommds());


        } else {

        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_digital3;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
