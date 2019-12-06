package com.ltqh.qh.fragment.shop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.BooktypeEntity;
import com.ltqh.qh.view.EnhanceTabLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class BookFragment extends BaseFragment implements View.OnClickListener{


    private final static int PERIOD = 5 * 1000; // 5s






    @BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private BooktypeEntity booktypeEntity;


    @Override
    public void onResume() {
        super.onResume();



    }




    protected void initView(View view) {
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


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(home_tab.getTabLayout()));
        home_tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);




    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_book;
    }
    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
     /*   myPagerAdapter.addFragment(new LiveNewFragment());
        myPagerAdapter.addFragment(new FinancialCalendarFragment());
        myPagerAdapter.addFragment(new AlertsFragment());
        myPagerAdapter.addFragment(new ForumRadioFragment());*/
        for (int i = 0; i <booktypeEntity.getResult().size() ; i++) {
            myPagerAdapter.addFragment(new BookItemFragment(booktypeEntity.getResult().get(i)));
        }

        viewPager.setAdapter(myPagerAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startScheduleJob(mHandler, PERIOD, PERIOD);


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

        OkGo.<String>get(Constant.URL_BOOKTYPE)
                .tag(this)
                .params(Constant.DTYPE,"")
                .params(Constant.KEY,Constant.KEY_VALUE)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())){
                            booktypeEntity = new Gson().fromJson(response.body(), BooktypeEntity.class);
                            Log.d("print", "onSuccess:168 "+booktypeEntity);
                            List<BooktypeEntity.ResultBean> result = booktypeEntity.getResult();
                            Toast.makeText(getContext(),booktypeEntity.getReason(),Toast.LENGTH_SHORT).show();
                            if (result!=null){

                                for (int i = 0; i < result.size(); i++) {
                                    home_tab.addTab(result.get(i).getCatalog());
                                }

                                initViewPager(viewPager);
                            }

                        }
                    }
                });

    }


}
