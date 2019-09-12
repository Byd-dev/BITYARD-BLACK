package com.ltqh.qh.fragment.find;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.forum.ForumFragment;
import com.ltqh.qh.fragment.forum.ForumGadioFragment;
import com.ltqh.qh.fragment.news.AlertsFragment;
import com.ltqh.qh.fragment.news.FinancialCalendarFragment;
import com.ltqh.qh.fragment.news.LiandeFragment;
import com.ltqh.qh.operation.base.OBaseFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FindFragment extends OBaseFragment implements View.OnClickListener {


    @Override
    public void onResume() {
        super.onResume();


    }


    protected void initView(View view) {




    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_findtab;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    protected void onLazyLoad() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:

                getActivity().finish();
                break;





        }
    }





    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }
}
