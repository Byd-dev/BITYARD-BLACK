package com.ltqh.qh.fragment.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.activity.ONewsDetailActivity;
import com.ltqh.qh.operation.adapter.OHotAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HotFragment extends OBaseFragment {


    private RecyclerView listView;

    private OHotAdapter oHotAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_hot;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void onLazyLoad() {
        OHotEntity data = SPUtils.getData(OUserConfig.CACHE_HOT, OHotEntity.class);
        if (data != null) {
            if (data.getNewsList().size()!=0){
                oHotAdapter.setDatas(data.getNewsList());
            }else {
                getHotData();

            }
        } else {
            getHotData();


        }

     //   getHotData();

    }


    @Override
    protected void intPresenter() {

    }

    protected void initView(View view) {
        listView = (RecyclerView) view.findViewById(R.id.listview);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        listView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        oHotAdapter = new OHotAdapter(getActivity());
        listView.setAdapter(oHotAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               getHotData();
            }
        });


        oHotAdapter.setOnItemClick(new OHotAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OHotEntity.NewsListBean newsListBean) {
                ONewsDetailActivity.enter(getActivity(), "ALERTS", newsListBean);
            }
        });




    }


    @Override
    protected void initData() {



    }

    private void getHotData() {
        OkGo.<String>get(OConstant.URL_NEWS_HOT)
                .tag(this)
                .params(Constant.PARAM_TYPE, "0")
                .cacheKey(Constant.URL_ALERTS)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        swipeRefreshLayout.setRefreshing(true);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response.body())) {
                            OHotEntity oHotEntity = new Gson().fromJson(response.body(), OHotEntity.class);
                            SPUtils.putData(OUserConfig.CACHE_HOT, oHotEntity);

                            if (oHotEntity.getNewsList().size() != 0) {

                                oHotAdapter.setDatas(oHotEntity.getNewsList());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        Log.d("print", "onError:212 " + response.message());
                        swipeRefreshLayout.setRefreshing(false);
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
