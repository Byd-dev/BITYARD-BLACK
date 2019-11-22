package com.ltqh.qh.fragment.market;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.fragment.market.ODigitalMarketFragment;
import com.ltqh.qh.operation.fragment.market.OForeignMarketFragment;
import com.ltqh.qh.operation.fragment.market.OMarketFragment;
import com.ltqh.qh.operation.fragment.market.OStockMarketFragment;
import com.ltqh.qh.view.EnhanceTabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class AllMarketTabFragment extends OBaseFragment implements View.OnClickListener{


    private final static int PERIOD = 5 * 1000; // 5s






    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.bar)
    LinearLayout layout_bar;
    private String Titles[] = new String[]{"国内", "国外"};



    @Override
    public void onResume() {
        super.onResume();



    }




    protected void initView(View view) {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight );

        layout_bar.setLayoutParams(params);

        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < Titles.length; i++) {
            home_tab.addTab(Titles[i]);
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(home_tab.getTabLayout()));
        home_tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        initViewPager(viewPager);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_allmarket_tab;
    }
    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new OMarketFragment());
        myPagerAdapter.addFragment(new ODigitalMarketFragment());

        viewPager.setAdapter(myPagerAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startScheduleJob(mHandler, PERIOD, PERIOD);


    }

    @Override
    protected void onLazyLoad() {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };





    @Override
    public void onClick(View view) {
        switch (view.getId()) {



        }
    }




    public String dateToStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }



    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }
}
