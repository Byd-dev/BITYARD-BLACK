package com.ltqh.qh.fragment.forum;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.PublishActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.news.LiveFragment;
import com.ltqh.qh.operation.base.OBaseFragment;

import butterknife.BindView;

public class ForumFragment extends OBaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.layout_group)
    RelativeLayout layout_group;

    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    @Override
    protected void onLazyLoad() {

    }

    /*@BindView(R.id.layout_send)
        LinearLayout layout_send;*/
  /*  @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;*/
    @Override
    protected void initView(View view) {


        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);

        //layout_send.setOnClickListener(this);

        view.findViewById(R.id.img_back).setOnClickListener(this);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new ChatFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
                radio_0.setTextSize(20);
                radio_1.setTextSize(17);

                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new LiveFragment(), null, null);
               // layout_send.setVisibility(View.GONE);
                radio_0.setTextSize(17);
                radio_1.setTextSize(20);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.layout_send:
                if (isLogin()) {
                  PublishActivity.enter(getActivity());

                } else {
                    OUserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;*/

            case R.id.img_back:
                getActivity().finish();
                break;




        }
    }
}
