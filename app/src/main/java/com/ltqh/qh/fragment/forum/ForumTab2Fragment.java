package com.ltqh.qh.fragment.forum;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.PublishActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.market.DigitalMarket2Fragment;
import com.ltqh.qh.fragment.market.MineMarketFragment;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.fragment.info.OLiveFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.ltqh.qh.view.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ForumTab2Fragment extends OBaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;

    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @BindView(R.id.bar)
    LinearLayout layout_bar;


    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    //private String Titles[] = new String[]{"讨论", "小组"};

    private List<String> titleList = new ArrayList<>();

    @BindView(R.id.layout_send)
    LinearLayout layout_send;

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


        ViewUtils.setLayoutParams(getContext(),layout_bar);

        view.findViewById(R.id.img_add).setOnClickListener(this);


    }

    private void initTabView() {
        //  Log.d("print", "onStart:40:    "+"initTabView");

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);

        String language = SPUtils.getString(Constant.LANGUAGE);
        Log.d("print", "initTabView:72:  " + language);
        if (language.equals("en_US")) {
            titleList.add("Discuss");
            titleList.add("Circle");
        } else if (language.equals("zh_CN")) {
            titleList.add("讨论");
            titleList.add("币圈");

        }
        layout_send.setOnClickListener(this);

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

    }

    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new ChatFragment());
        myPagerAdapter.addFragment(new OLiveFragment());

        viewPager.setAdapter(myPagerAdapter);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_forum2;
    }

    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add:
                if (isLogin()) {
                  PublishActivity.enter(getActivity());

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new ChatFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
                radio_0.setTextSize(17);
                radio_1.setTextSize(15);
                radio_0.setTextColor(getResources().getColor(R.color.text_maincolor));
                radio_1.setTextColor(getResources().getColor(R.color.text_9999));



                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new OLiveFragment(), null, null);
                // layout_send.setVisibility(View.GONE);
                radio_0.setTextSize(15);
                radio_1.setTextSize(17);
                radio_0.setTextColor(getResources().getColor(R.color.text_9999));
                radio_1.setTextColor(getResources().getColor(R.color.text_maincolor));


                break;

        }
    }
}
