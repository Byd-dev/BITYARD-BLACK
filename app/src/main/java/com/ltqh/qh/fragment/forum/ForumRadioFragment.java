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
import com.ltqh.qh.fragment.news.LiveNewFragment;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.fragment.info.OHotFragment;
import com.ltqh.qh.operation.fragment.info.OLiveFragment;
import com.ltqh.qh.operation.fragment.info.OReportFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.ltqh.qh.view.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ForumRadioFragment extends OBaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {




    @BindView(R.id.bar)
    LinearLayout layout_bar;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;






    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {



        ViewUtils.setLayoutParams(getContext(),layout_bar);




        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initTabView();

            }
        }, 100);

    }

    private void initTabView() {
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_forumradio;
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

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new ChatFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
               /* radio_0.setTextSize(17);
                radio_1.setTextSize(15);
                radio_2.setTextSize(15);
                radio_0.setTextColor(getResources().getColor(R.color.text_maincolor));
                radio_1.setTextColor(getResources().getColor(R.color.text_9999));
                radio_2.setTextColor(getResources().getColor(R.color.text_9999));*/


                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new LiveNewFragment(), null, null);
                // layout_send.setVisibility(View.GONE);
               /* radio_0.setTextSize(15);
                radio_1.setTextSize(17);
                radio_2.setTextSize(15);

                radio_0.setTextColor(getResources().getColor(R.color.text_9999));
                radio_1.setTextColor(getResources().getColor(R.color.text_maincolor));
                radio_2.setTextColor(getResources().getColor(R.color.text_9999));*/

                break;


        }
    }
}
