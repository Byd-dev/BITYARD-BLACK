package com.ltqh.qh.operation.fragment.info;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.fragment.news.StockNewsFragment;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.view.InfoEnhanceTabLayout;

import butterknife.BindView;

public class OInfoFragment extends OBaseFragment implements View.OnClickListener {
    private final static int PERIOD = 5 * 1000; // 5s

    @BindView(R.id.layout_view)
    LinearLayout layout_view;

    @BindView(R.id.home_tab)
    InfoEnhanceTabLayout home_tab;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private String Titles[] = new String[]{"7×24", "每日热点", "日历", "公告"};


    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_info;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initTabView() {
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
        dismissProgressDialog();

    }

    @Override
    protected void onLazyLoad() {


    }


    @Override
    protected void initView(View view) {
        showProgressDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initTabView();

            }
        }, 100);


        view.findViewById(R.id.layout_one).setOnClickListener(this);
        view.findViewById(R.id.layout_two).setOnClickListener(this);
        view.findViewById(R.id.layout_three).setOnClickListener(this);


    }


    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new OLiveFragment());
        myPagerAdapter.addFragment(new OHotFragment());
        myPagerAdapter.addFragment(new StockNewsFragment());
        myPagerAdapter.addFragment(new OReportFragment());

        viewPager.setAdapter(myPagerAdapter);
    }


    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {

        //getNews();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_one:
            case R.id.layout_two:
            case R.id.layout_three:
                Toast.makeText(getActivity(), "敬请期待!", Toast.LENGTH_SHORT).show();
               // OIntentActivity.enter(getActivity(), OConstant.O_CONVERSION);
                break;
        }
    }


}
