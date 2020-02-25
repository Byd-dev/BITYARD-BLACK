package com.ltqh.qh.fragment.user;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;

import butterknife.BindView;

public class AboutFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.text_version)
    TextView text_version;

    @BindView(R.id.text_one)
    TextView text_one;

    @BindView(R.id.text_two)
    TextView text_two;


    @Override
    protected void initView(View view) {

        text_version.setText("version "+ BuildConfig.VERSION_NAME);

        text_one.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_one.getPaint().setAntiAlias(true);//抗锯齿

        text_two.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_two.getPaint().setAntiAlias(true);//抗锯齿

        text_one.setOnClickListener(this);
        text_two.setOnClickListener(this);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_about;
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
            case R.id.text_one:
                IntentActivity.enter(getActivity(), Constant.RULE,1);
                break;

            case R.id.text_two:
                IntentActivity.enter(getActivity(), Constant.RULE,2);

                break;
        }
    }
}
