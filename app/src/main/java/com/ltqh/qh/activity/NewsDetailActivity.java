package com.ltqh.qh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.ltqh.qh.R;
import com.ltqh.qh.entity.AlertsEntity;
import com.ltqh.qh.entity.BannerEntity;
import com.ltqh.qh.entity.EventEntity;
import com.ltqh.qh.entity.StocknewsEntity;
import com.ltqh.qh.entity.StrategyEntity;
import com.ltqh.qh.fragment.news.AlertsDetailFragment;
import com.ltqh.qh.fragment.news.BannerDetailFragment;
import com.ltqh.qh.fragment.news.EventListFragment;
import com.ltqh.qh.fragment.news.StockNewsDetailsFragment;
import com.ltqh.qh.fragment.news.StrategyDetailsFragment;
import com.ltqh.qh.operation.base.OBaseActivity;

public class NewsDetailActivity extends OBaseActivity {


    public static void enter(Context context, String type, AlertsEntity.NewsListBean data) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("DATA", data);
        context.startActivity(intent);
    }

    public static void enter(Context context, String type, EventEntity.DataBean data) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("EVENTDATA", data);
        context.startActivity(intent);
    }

    public static void enter(Context context, String type, BannerEntity.DataBean data) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("BANNERDATA", data);
        context.startActivity(intent);
    }

    public static void enter(Context context, String type, StrategyEntity.DataBean data) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("STRATEGYDATA", data);
        context.startActivity(intent);
    }

    public static void enter(Context context, String type, StocknewsEntity.DataBean data) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("STOCKNEWSDATA", data);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);




        String type = getIntent().getStringExtra("TYPE");
        if (type.equals("ALERTS")) {
            addAlertsFragment();
        } else if (type.equals("EVENT")) {
            addEventFragment();
        } else if (type.equals("STRATEGY")) {
            addStrategyDetailsFragment();
        } else if (type.equals("BANNER")) {
            addBannerDetailFragment();
        } else if (type.equals("STOCKNEWSDATA")) {
            addStocknewsDetailFragment();
        }


    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);


    }

    private void addAlertsFragment() {
        AlertsEntity.NewsListBean alerts = (AlertsEntity.NewsListBean) getIntent().getSerializableExtra("DATA");
        AlertsDetailFragment alertsDetailFragment = AlertsDetailFragment.newInstance(alerts);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, alertsDetailFragment).commit();
    }

    private void addEventFragment() {
        EventEntity.DataBean alerts = (EventEntity.DataBean) getIntent().getSerializableExtra("EVENTDATA");
        EventListFragment eventListFragment = EventListFragment.newInstance(alerts);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, eventListFragment).commit();
    }

    private void addStrategyDetailsFragment() {
        StrategyEntity.DataBean alerts = (StrategyEntity.DataBean) getIntent().getSerializableExtra("STRATEGYDATA");
        StrategyDetailsFragment eventListFragment = StrategyDetailsFragment.newInstance(alerts);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, eventListFragment).commit();
    }

    private void addBannerDetailFragment() {
        BannerEntity.DataBean alerts = (BannerEntity.DataBean) getIntent().getSerializableExtra("BANNERDATA");
        BannerDetailFragment bannerDetailFragment = BannerDetailFragment.newInstance(alerts);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, bannerDetailFragment).commit();
    }


    private void addStocknewsDetailFragment() {
        StocknewsEntity.DataBean alerts = (StocknewsEntity.DataBean) getIntent().getSerializableExtra("STOCKNEWSDATA");
        StockNewsDetailsFragment stockNewsDetailsFragment = StockNewsDetailsFragment.newInstance(alerts);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, stockNewsDetailsFragment).commit();
    }
}
