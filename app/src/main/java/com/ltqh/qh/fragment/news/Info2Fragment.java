package com.ltqh.qh.fragment.news;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.activity.WebActivity;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.AppContext;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.market.DigitalMarket2Fragment;
import com.ltqh.qh.fragment.market.MineMarketFragment;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.fragment.info.OHotFragment;
import com.ltqh.qh.operation.fragment.info.OLiveFragment;
import com.ltqh.qh.operation.fragment.info.OReportFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.InfoEnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Info2Fragment extends OBaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private final static int PERIOD = 5 * 1000; // 5s

    @BindView(R.id.layout_view)
    LinearLayout layout_view;

    @BindView(R.id.home_tab)
    InfoEnhanceTabLayout home_tab;

    /*@BindView(R.id.home_tab)
    TabLayout tabLayout;*/

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    @BindView(R.id.radio_2)
    RadioButton radio_2;

    @BindView(R.id.img_search)
    ImageView img_search;


    private List<String> titleList = new ArrayList<>();


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_info2;
    }

    @Override

    public void onResume() {
        super.onResume();

    }

    private void initTabView() {
       /* titleList.add("7×24");
        titleList.add("每日热点");
        titleList.add("日历");
        titleList.add("公告");*/

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);
        radio_2.setChecked(false);

        img_search.setOnClickListener(this);


        String language = SPUtils.getString(Constant.LANGUAGE);
        Log.d("print", "initTabView:72:  " + language);
        if (language.equals("en_US")) {
            titleList.add("7×24");
            titleList.add("Hot");
           // titleList.add("Calendar");
            titleList.add("Board");
        } else  {
            titleList.add("7×24");
            titleList.add("每日热点");
           // titleList.add("日历");
            titleList.add("公告");
        }


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

        for (int i = 0; i < titleList.size(); i++) {
            home_tab.addTab(titleList.get(i));
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(home_tab.getTabLayout()));
        home_tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        initViewPager(viewPager);
        dismissProgressDialog();



        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        dismissProgressDialog();*/

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


        view.findViewById(R.id.text_video).setOnClickListener(this);
        view.findViewById(R.id.text_school).setOnClickListener(this);
     //   view.findViewById(R.id.img_service).setOnClickListener(this);


    }


    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new OLiveFragment(), AppContext.getInstance().getResources().getString(R.string.text_short));
        myPagerAdapter.addFragment(new OHotFragment(), AppContext.getInstance().getResources().getString(R.string.text_hot));
       // myPagerAdapter.addFragment(new OFinancialCalendarFragment(), AppContext.getInstance().getResources().getString(R.string.text_calendar));
        myPagerAdapter.addFragment(new OReportFragment(), AppContext.getInstance().getResources().getString(R.string.text_board));

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
            case R.id.text_video:
                IntentActivity.enter(getActivity(),Constant.VIDEO);

                break;
            case R.id.text_school:
                // OIntentActivity.enter(getActivity(), OConstant.O_CONVERSION);
                IntentActivity.enter(getActivity(),Constant.LEARNCLASS);

                break;

           /* case R.id.img_service:

                if (isLogin()) {
                    WebActivity.openZhiChiService(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;*/


        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new LiveNewFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
               /* radio_0.setTextSize(17);
                radio_1.setTextSize(15);
                radio_2.setTextSize(15);
                radio_0.setTextColor(getResources().getColor(R.color.text_maincolor));
                radio_1.setTextColor(getResources().getColor(R.color.text_9999));
                radio_2.setTextColor(getResources().getColor(R.color.text_9999));*/


                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new OHotFragment(), null, null);
                // layout_send.setVisibility(View.GONE);
               /* radio_0.setTextSize(15);
                radio_1.setTextSize(17);
                radio_2.setTextSize(15);

                radio_0.setTextColor(getResources().getColor(R.color.text_9999));
                radio_1.setTextColor(getResources().getColor(R.color.text_maincolor));
                radio_2.setTextColor(getResources().getColor(R.color.text_9999));*/

                break;
            case R.id.radio_2:
                showFragment(R.id.layout_fragment_containter, new OReportFragment(), null, null);
                // layout_send.setVisibility(View.GONE);
                radio_0.setTextSize(15);
                radio_1.setTextSize(15);
                radio_2.setTextSize(17);

                radio_0.setTextColor(getResources().getColor(R.color.text_9999));
                radio_1.setTextColor(getResources().getColor(R.color.text_9999));
                radio_2.setTextColor(getResources().getColor(R.color.text_maincolor));

                break;

        }
    }
}
