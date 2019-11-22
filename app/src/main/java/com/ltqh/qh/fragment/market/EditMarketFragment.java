package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.adapter.OMarketEditAdapter;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.MARKET_PERIOD;

public class EditMarketFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
  /*  @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
*/
    private List<String> editlist;


    int page = 1;
    private int lastVisibleItem;

    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String NULLTYPE = "nulltype";
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private OMarketEditAdapter oMarketEditAdapter;

    private List<String> dataList;

    private Set<String> addList;


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void initView(View view) {

        String string = SPUtils.getString(OUserConfig.MINEDEX);
        if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {
            startScheduleJob(mHandler, MARKET_PERIOD, MARKET_PERIOD);
        }

        oMarketEditAdapter = new OMarketEditAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(oMarketEditAdapter);

      /*  swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postQuote(REFRESHTYPE);
            }
        });*/

        oMarketEditAdapter.setOnItemClick(new OMarketEditAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(int position) {
                oMarketEditAdapter.removeItemData(position);
            }
        });

        view.findViewById(R.id.img_back).setOnClickListener(this);

        view.findViewById(R.id.text_delete).setOnClickListener(this);



    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postQuote(NULLTYPE);


        }
    };

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_editmarket;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {


        postQuote(FIRSTLOAD);


    }

    private void postQuote(String type) {

        String string = SPUtils.getString(OUserConfig.MINEDEX);
        if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {

            String code = string.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");
            String[] splitcode = code.split(",");
            addList = new HashSet<>();

            for (String a : splitcode) {
                addList.add(a);
            }


            List<String> dataList = QuoteProxy.getInstance().getDataList();
            OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();


            if (dataList!=null) {

                editlist=new ArrayList<>();
                for (String quote:dataList) {
                    String[] split = quote.split(",");

                    for (String dax:addList) {
                        if (dax.equals(split[0])){
                            editlist.add(quote);
                        }
                    }

                }


                oMarketEditAdapter.setAllDatas(OUserConfig.P_ALLDEX, oApiEntity);

                oMarketEditAdapter.setDatas(OUserConfig.P_ALLDEX, editlist);

                oMarketEditAdapter.setDatas(addList);

            }



           /* OkGo.<String>post(OConstant.URL_MARKET)
                    .tag(this)
                    .params(PARAM_CALLBACK, "?")
                    .params(PARAM_CODE, code)
                    .params("_", Calendar.getInstance().getTimeInMillis())
                    .params(PARAM_SIMPLE, true)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            if (type.equals(FIRSTLOAD)) {
                                showProgressDialog();
                            } else if (type.equals(REFRESHTYPE)) {
                                swipeRefreshLayout.setRefreshing(true);
                            } else if (type.equals(NULLTYPE)) {

                            }
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            dismissProgressDialog();
                            if (swipeRefreshLayout != null) {
                                swipeRefreshLayout.setRefreshing(false);

                            }

                            if (!TextUtils.isEmpty(response.body())) {
                                OApiEntity oApiEntity = SPUtils.getData(OUserConfig.API, OApiEntity.class);

                                String responese = Util.jsonReplace(response.body());
                                OMarketEntity oMarketEntity = new Gson().fromJson(responese, OMarketEntity.class);

                                String data = oMarketEntity.getData();
                                if (data != null) {

                                    String[] split = data.split(";");

                                    dataList = new ArrayList<>();
                                    for (String a : split) {
                                        dataList.add(a);
                                    }


                                    List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
                                    List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
                                    List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();


                                    oMarketEditAdapter.setAllDatas(OUserConfig.P_ALLDEX, oApiEntity);

                                    oMarketEditAdapter.setDatas(OUserConfig.P_ALLDEX, dataList);

                                    oMarketEditAdapter.setDatas(addList);


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //SPUtils.putString(OUserConfig.P_ALLDEX, data);
                                            //MMKV.defaultMMKV().encode(OUserConfig.P_ALLDEX,data);
                                            //oMarketAdapter.notifyItem(quetoApiEntity);
                                            oMarketEditAdapter.notifyItem(dataList);


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
                    });*/

        } else {

            dataList = new ArrayList<>();
            oMarketEditAdapter.setDatas(OUserConfig.P_ALLDEX, dataList);
           // swipeRefreshLayout.setRefreshing(false);

            cancelTimer();


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;

            case R.id.text_delete:
                SPUtils.remove(OUserConfig.MINEDEX);
                postQuote(NULLTYPE);
                break;


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
