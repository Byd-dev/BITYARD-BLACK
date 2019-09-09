package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.StockForeignTabAdapter;
import com.ltqh.qh.adapter.StockTabAdapter;
import com.ltqh.qh.adapter.StockforeignslideAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.StockEntity;
import com.ltqh.qh.entity.StockForeignEntity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import butterknife.BindView;

public class StockForeignFragment extends OBaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    int page = 1;
    private int lastVisibleItem;
    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";
     private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private StockforeignslideAdapter stockForeignTabAdapter;

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {
        stockForeignTabAdapter = new StockforeignslideAdapter(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(stockForeignTabAdapter);

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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == stockForeignTabAdapter.getItemCount() - 1) {
                    //加载更多功能的代码
                    page = page + 1;
                    getStockData(LOADTYPE, page, SORT);
                  /*  if (page<=6){

                    }else {
                        Toast.makeText(getActivity(),"到底了",Toast.LENGTH_SHORT).show();
                    }*/

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });


        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(0).performClick();
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
    protected int setLayoutResourceID() {
        return R.layout.fragment_stockforeigntab;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {


        getStockData(REFRESHTYPE, 1, "N");
    }

    private void getStockData(String type, int page, String sort) {
        OkGo.<String>get(Constant.URL_STOCK_USA)
                .params(Constant.PARAM_PAGE, page)
                .params(Constant.PARAM_NUM, 1)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_MARKET, sort)
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
                            Log.d("print", "onSuccess:154 " + response.body());

                            if (response.body() != null) {
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                if (codeMsgEntity.getCode() == 1) {
                                    StockForeignEntity stockForeignEntity = new Gson().fromJson(response.body(), StockForeignEntity.class);

                                    if (stockForeignEntity.getData()!=null){

                                        if (type.equals(REFRESHTYPE)) {
                                            stockForeignTabAdapter.setDatas(stockForeignEntity.getData().getData());
                                        } else if (type.equals(LOADTYPE)) {
                                            stockForeignTabAdapter.addDatas(stockForeignEntity.getData().getData());
                                        }
                                    }

                                }

                            } else {
                                stockForeignTabAdapter.stopLoad();
                            }

                        } else {
                            stockForeignTabAdapter.stopLoad();
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


        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                getStockData(REFRESHTYPE, page, "N");
                SORT = "N";
                break;
            case R.id.radio_1:
                getStockData(REFRESHTYPE, page, "O");
                SORT = "O";

                break;

            case R.id.radio_2:
                getStockData(REFRESHTYPE, page, "A");
                SORT = "A";

                break;


        }
    }
}
