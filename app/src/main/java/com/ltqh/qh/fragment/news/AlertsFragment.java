package com.ltqh.qh.fragment.news;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.NewsDetailActivity;
import com.ltqh.qh.adapter.AlertsAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.AlertsEntity;
import com.ltqh.qh.entity.EastMoneyEntity;
import com.ltqh.qh.operation.activity.ONewsDetailActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.utils.ListUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class AlertsFragment extends BaseFragment {

    private final static int PERIOD = 5 * 1000; // 5s

    private RecyclerView listView;

    private AlertsAdapter alertsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> newSdata;

    private TextSwitcher mTextSwitcherNews;
    private int mNewsIndex;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_alerts;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNews();
        startScheduleJob(mHandler, PERIOD, PERIOD);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            updateNews();


        }
    };

    @Override
    protected void intPresenter() {

    }

    protected void initView(View view) {
        listView = (RecyclerView) view.findViewById(R.id.listview);
        mTextSwitcherNews = (TextSwitcher) view.findViewById(R.id.ts_news);
        mTextSwitcherNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setMaxLines(2);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_maincolor));
                textView.setTextSize(15);
                return textView;
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        listView.setLayoutManager(new GridLayoutManager(getContext(),1));
        alertsAdapter = new AlertsAdapter(getActivity());
        listView.setAdapter(alertsAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        alertsAdapter.setOnItemClick(new AlertsAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OHotEntity.NewsListBean newsListBean) {
                ONewsDetailActivity.enter(getActivity(), "ALERTS", newsListBean);

            }
        });

        initData();
    }


    private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                if (ListUtil.isNotEmpty(newSdata)) {
                    mTextSwitcherNews.setText(newSdata.get(mNewsIndex).substring(23, newSdata.get(mNewsIndex).length() - 29));
                }
            }
        }
    }

    private void getNews() {
        OkGo.<String>get(Constant.URL_HOUR)
                .tag(this)
                .params(Constant.PARAM_LASTTIME, dateToStamp())
                .params(Constant.PARAM_PAGESIZE, 50)
                .cacheKey(Constant.URL_JINTOUWANG)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        swipeRefreshLayout.setRefreshing(true);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            swipeRefreshLayout.setRefreshing(false);

                            EastMoneyEntity eastMoneyEntity = new Gson().fromJson(response.body(), EastMoneyEntity.class);
                            newSdata = eastMoneyEntity.getData();
                            mTextSwitcherNews.setText(newSdata.get(0).substring(23, newSdata.get(0).length() - 29));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


    }

    @Override
    protected void initData() {

        OkGo.<String>get(Constant.URL_NEWS_HOT)
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
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("print", "onSuccess:201: " + response);
                        if (!TextUtils.isEmpty(response.body())) {
                            OHotEntity oHotEntity = new Gson().fromJson(response.body(), OHotEntity.class);

                            List<OHotEntity.NewsListBean> newsList = oHotEntity.getNewsList();
                            alertsAdapter.setDatas(newsList);
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
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
