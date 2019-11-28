package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.HomeBtcAdapter;
import com.ltqh.qh.adapter.MarketAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;

import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.PERIOD;

public class DigitalMarket2Fragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.layout_up_down)
    LinearLayout layout_up_down;

    @BindView(R.id.img_up_down)
    ImageView img_up_down;

    private int flag = 0;
    private String isupdown = "up";

    @BindView(R.id.text_up_down)
    TextView text_up_down;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.recyclerview_top)
    RecyclerView recyclerView_top;


   /* @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
*/

    int page = 1;
    private int lastVisibleItem;
    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String NULLTYPE = "nulltype";
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private MarketAdapter oMarketAdapter;

    private HomeBtcAdapter homeBtcAdapter;


    private List<String> dataList;


    @Override
    protected void onLazyLoad() {
        getQuote();
       /* if (!ODateUtil.isWeekend()) {
            startScheduleJob(mHandler, MARKET_PERIOD, MARKET_PERIOD);
        }*/
    }

    @Override
    protected void initView(View view) {


        oMarketAdapter = new MarketAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(oMarketAdapter);


        //btc
        homeBtcAdapter = new HomeBtcAdapter(getActivity());
        recyclerView_top.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView_top.setAdapter(homeBtcAdapter);



      /*  swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // postQuote(REFRESHTYPE);
            }
        });*/
        oMarketAdapter.setOnItemClick(new MarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {

                //  OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", code);


            }
        });
        layout_up_down.setOnClickListener(this);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            getQuote();
            //  postQuote(NULLTYPE);


        }
    };

    private void getQuote() {

        List<String> dataList = QuoteProxy.getInstance().getDigitalDataList();


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (dataList != null) {

            oMarketAdapter.setIsUpDown(isupdown);
            oMarketAdapter.setDatas(OUserConfig.P_DIGITAL, dataList);
            oMarketAdapter.setDigitalDatas(OUserConfig.P_DIGITAL, oApiEntity.getDigitalCommds());

            homeBtcAdapter.setIsUpDown(isupdown);
            homeBtcAdapter.setDatas(OUserConfig.P_DIGITAL, dataList.subList(6, 9));
            homeBtcAdapter.setDigitalDatas(OUserConfig.P_DIGITAL, oApiEntity.getDigitalCommds());



            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    oMarketAdapter.notifyItem(dataList);
                    homeBtcAdapter.notifyItem(dataList);

                }
            }, PERIOD);

        } else {

        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_digital2;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {


        // postQuote(NULLTYPE);

        //  getQuote();


    }


   /* private void postQuote(String type) {
        String string = SPUtils.getString(OUserConfig.FOREIGN);
        String code = string.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");

        OkGo.<String>post(OConstant.URL_MARKET)
                .tag(this)
                .params(PARAM_CALLBACK, "?")
                .params(PARAM_CODE, code)
                .params("_", Calendar.getInstance().getTimeInMillis())
                .params(PARAM_SIMPLE, true)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (type.equals(FIRSTLOAD)){
                            showProgressDialog();
                        } else if (type.equals(REFRESHTYPE)){
                            swipeRefreshLayout.setRefreshing(true);
                        }else if (type.equals(NULLTYPE)){

                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {


                        if (swipeRefreshLayout!=null){

                            swipeRefreshLayout.setRefreshing(false);
                        }
                        dismissProgressDialog();

                        if (!TextUtils.isEmpty(response.body())) {
                            OApiEntity oApiEntity = SPUtils.getData(OUserConfig.API, OApiEntity.class);

                            String responese = Util.jsonReplace(response.body());
                            OMarketEntity oMarketEntity = new Gson().fromJson(responese, OMarketEntity.class);

                            String data = oMarketEntity.getData();
                            if (data!=null){

                                String[] split = data.split(";");
                                dataList = new ArrayList<>();
                                for (String a : split) {
                                    dataList.add(a);
                                }

                                oMarketAdapter.setDatas(OUserConfig.P_FOREIGN, dataList);
                                oMarketAdapter.setForeignDatas(OUserConfig.P_FOREIGN,oApiEntity.getForeignCommds());
                                Handler handler=new Handler();

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //SPUtils.putString(OUserConfig.P_FOREIGN, data);

                                            //MMKV.defaultMMKV().encode(OUserConfig.P_FOREIGN,data);

                                            oMarketAdapter.notifyItem(dataList);

                                        }
                                    }, PERIOD);
                            }
                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        swipeRefreshLayout.setRefreshing(false);
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), "当前暂无数据", Toast.LENGTH_SHORT).show();


                    }
                });


    }*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.layout_up_down:
                if (flag == 0) {
                    img_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.o_market_down));
                    isupdown = "down";
                    text_up_down.setText(getResources().getString(R.string.text_quote_point));

                    getQuote();
                    flag = 1;
                } else if (flag == 1) {
                    img_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.o_market_up));
                    isupdown = "up";
                    text_up_down.setText(getResources().getString(R.string.text_quote_change));

                    getQuote();
                    flag = 0;

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
