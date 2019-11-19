package com.ltqh.qh.operation.fragment.info;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.operation.adapter.OHoursAdapter;
import com.ltqh.qh.operation.adapter.OMessageAdapter;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OHoursEntity;
import com.ltqh.qh.operation.entity.OMarketEntity;
import com.ltqh.qh.operation.entity.OMessageEntity;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OMessageFragment extends BaseFragment {

    @BindView(R.id.layout_add)
    LinearLayout layout_add;
    private RecyclerView recyclerView;

    private OMessageAdapter oMessageAdapter;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;

    private int count = 0;
    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";


    @Override
    protected void intPresenter() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    protected void initView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.listview);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        oMessageAdapter = new OMessageAdapter(getActivity());
        recyclerView.setAdapter(oMessageAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLiveData(REFRESHTYPE);
            }
        });


      /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == oMessageAdapter.getItemCount() - 1) {
                    oMessageAdapter.startLoad();
                    count = count + 1;
                    getLiveData(LOADTYPE);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });*/


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_message;
    }


    @Override
    protected void initData() {

        getLiveData(FIRSTLOAD);



    }


    private void getLiveData(final String type) {


        OkGo.<String>get(OConstant.O_URL_MESSAGE)
                .params(OConstant.PARAM_ACTION,OConstant.STAY_MESSAGE)
                .execute(new StringCallback() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (type.equals(REFRESHTYPE)) {
                            swipeRefreshLayout.setRefreshing(true);
                        } else if (type.equals(FIRSTLOAD)) {
                            showProgressDialog();
                        } else if (type.equals(LOADTYPE)) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (isAdded()){

                            swipeRefreshLayout.setRefreshing(false);
                        }

                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {

                            OMessageEntity oMessageEntity = new Gson().fromJson(response.body(), OMessageEntity.class);

                            String resultMsg = oMessageEntity.getResultMsg();

                            if (resultMsg.equals("")){
                                return;
                            }
                            //保存上一次的
                           // SPUtils.putData(OUserConfig.CACHE_LIVE, oHoursEntity);
                            List<OMessageEntity.DataBean> data = oMessageEntity.getData();

                            if (data.size()!= 0) {
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                                layout_add.setVisibility(View.GONE);
                                oMessageAdapter.setDatas(data);

                            } else {
                                swipeRefreshLayout.setVisibility(View.GONE);
                                layout_add.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        swipeRefreshLayout.setRefreshing(false);
                        dismissProgressDialog();
                        oMessageAdapter.stopLoad();

                    }
                });
    }


    public String dateToStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }


}
