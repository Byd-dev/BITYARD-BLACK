package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.MarketAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.adapter.OMarketAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.ODateUtil;

import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.MARKET_PERIOD;
import static com.ltqh.qh.operation.base.OConstant.PERIOD;

public class DomesMarketFragment extends OBaseFragment implements View.OnClickListener {

    @BindView(R.id.layout_up_down)
    LinearLayout layout_up_down;

    @BindView(R.id.img_up_down)
    ImageView img_up_down;
    @BindView(R.id.text_up_down)
    TextView text_up_down;
    private int flag=0;
    private String isupdown="up";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    /*@BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;*/


    int page = 1;
    private int lastVisibleItem;
    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String NULLTYPE = "nulltype";
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private MarketAdapter oMarketAdapter;

    private List<String> dataList;


    @Override
    protected void onLazyLoad() {
        getQuote();

       /* if (!ODateUtil.isWeekend()){
            startScheduleJob(mHandler, MARKET_PERIOD, MARKET_PERIOD);
        }*/
    }

    @Override
    protected void initView(View view) {


        oMarketAdapter = new MarketAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(oMarketAdapter);

      /*  swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postQuote(REFRESHTYPE);
            }
        });*/

        oMarketAdapter.setOnItemClick(new MarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {
                Log.d("print", "onSuccessListener:90: " + code);

                OMarketActivity.enter(getActivity(), OConstant.OQUETO,"1", code);


            }
        });
        layout_up_down.setOnClickListener(this);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            getQuote();
           // postQuote(NULLTYPE);


        }
    };
    private void getQuote() {

        List<String> dataList = QuoteProxy.getInstance().getDomesdataList();
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();

        if (dataList!=null) {
            oMarketAdapter.setIsUpDown(isupdown);
            oMarketAdapter.setDatas(OUserConfig.P_DOMESTIC, dataList);
            oMarketAdapter.setDomesDatas(OUserConfig.P_DOMESTIC, oApiEntity.getDomesticCommds());


            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    oMarketAdapter.notifyItem(dataList);

                }
            }, PERIOD);

        }
    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_allmarket;
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

            case R.id.layout_up_down:
                if (flag==0){
                    img_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.o_market_down));
                    isupdown="down";
                    text_up_down.setText("涨跌点");
                    getQuote();
                    flag=1;
                }else if (flag==1){
                    img_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.o_market_up));
                    isupdown="up";
                    text_up_down.setText("涨跌幅");
getQuote();
                    flag=0;

                }

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
