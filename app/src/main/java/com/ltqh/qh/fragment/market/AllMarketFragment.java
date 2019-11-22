package com.ltqh.qh.fragment.market;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.PublishActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.forum.ChatFragment;
import com.ltqh.qh.fragment.forum.GubaListFragment;
import com.ltqh.qh.operation.base.OBaseFragment;

import butterknife.BindView;

public class AllMarketFragment extends OBaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.layout_group)
    RelativeLayout layout_group;

    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    @BindView(R.id.text_change)
    TextView text_change;

    @BindView(R.id.layout_send)
    LinearLayout layout_send;

    @BindView(R.id.bar)
    LinearLayout layout_bar;

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight );

        layout_bar.setLayoutParams(params);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);

        layout_send.setOnClickListener(this);

        view.findViewById(R.id.img_back).setOnClickListener(this);
        text_change.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_allmarket;
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
                showFragment(R.id.layout_fragment_containter, new StockTabLayoutFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
               /* radio_0.setTextSize(20);
                radio_1.setTextSize(17);*/

                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new StockForeignFragment(), null, null);
                //layout_send.setVisibility(View.GONE);
               /* radio_0.setTextSize(17);
                radio_1.setTextSize(20);*/
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_send:
                if (isLogin()) {
                    PublishActivity.enter(getActivity());

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;

            case R.id.img_back:
                getActivity().finish();
                break;

            case R.id.text_change:

                break;
        }
    }
}
