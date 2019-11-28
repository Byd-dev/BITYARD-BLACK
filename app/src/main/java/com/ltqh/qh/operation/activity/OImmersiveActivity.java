package com.ltqh.qh.operation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.fragment.info.OActivityFragment;
import com.ltqh.qh.operation.fragment.user.OLotteryFragment;
import com.ltqh.qh.operation.fragment.user.ORaidersFragment;
import com.ltqh.qh.operation.fragment.user.ORecommendFragment;
import com.ltqh.qh.view.StatusBarUtil;

import butterknife.BindView;

public class OImmersiveActivity extends BaseActivity implements View.OnClickListener {
    private static final String TYPE = "USER_TYPE";
    private int type;
    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.text_title)
    TextView text_title;

    private FragmentTransaction ft;


    private OActivityFragment oActivityFragment;
    private ORecommendFragment oRecommendFragment;
    private ORaidersFragment oRaidersFragment;
    private OLotteryFragment oLotteryFragment;




    @Override
    protected int setContentLayout() {
        return R.layout.o_activity_immersive;
    }

    public static void enter(Context context, int type) {
        Intent intent = new Intent(context, OImmersiveActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StatusBarUtil.setRootViewFitsSystemWindows(this, false);


        type = getIntent().getIntExtra(TYPE, 0);

        if (type == OConstant.O_ACTIVITY) {
            layout_bar.setVisibility(View.GONE);
            addOActivityFragment();
        } else if (type == OConstant.O_RECOMMEND) {
            layout_bar.setVisibility(View.GONE);
            addRecommendFragment();
        } else if (type == OConstant.O_RAIDERS) {
            layout_bar.setVisibility(View.GONE);
            addRaidersFragment();
        }else if (type==OConstant.O_LOTTERY){
            layout_bar.setVisibility(View.GONE);
            addLotteryFragment();
        }

    }

    private void addLotteryFragment() {
        String name = OLotteryFragment.class.getSimpleName();
        oLotteryFragment = new OLotteryFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oLotteryFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addRaidersFragment() {
        String name = ORaidersFragment.class.getSimpleName();
        oRaidersFragment = new ORaidersFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oRaidersFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addRecommendFragment() {
        String name = ORecommendFragment.class.getSimpleName();
        oRecommendFragment = new ORecommendFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oRecommendFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addOActivityFragment() {
        String name = OActivityFragment.class.getSimpleName();
        oActivityFragment = new OActivityFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oActivityFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {

        findViewById(R.id.img_back).setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }


}
