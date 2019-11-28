package com.ltqh.qh.activity;

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
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.news.BtcNewsFragment;
import com.ltqh.qh.fragment.news.EosNewsFragment;
import com.ltqh.qh.fragment.news.EthNesFragment;
import com.ltqh.qh.fragment.news.LtcNewsFragment;
import com.ltqh.qh.operation.activity.SecondActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.fragment.info.OActivityFragment;
import com.ltqh.qh.operation.fragment.user.OLotteryFragment;
import com.ltqh.qh.operation.fragment.user.ORaidersFragment;
import com.ltqh.qh.operation.fragment.user.ORecommendFragment;
import com.ltqh.qh.view.StatusBarUtil;

import butterknife.BindView;

public class ImmersiveActivity extends BaseActivity implements View.OnClickListener {
    private static final String TYPE = "USER_TYPE";
    private int type;
    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.text_title)
    TextView text_title;

    private FragmentTransaction ft;


    private BtcNewsFragment btcNewsFragment;
    private EthNesFragment ethNesFragment;
    private EosNewsFragment eosNewsFragment;
    private LtcNewsFragment ltcNewsFragment;





    @Override
    protected int setContentLayout() {
        return R.layout.o_activity_immersive;
    }

    public static void enter(Context context, int type) {
        Intent intent = new Intent(context, ImmersiveActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setStatusBarDarkTheme(ImmersiveActivity.this, false);


        type = getIntent().getIntExtra(TYPE, 0);

        if (type == Constant.BTC_NEWS) {
            layout_bar.setVisibility(View.GONE);
            addBtcNewsFragment();
        } else if (type == Constant.ETH_NEWS) {
            layout_bar.setVisibility(View.GONE);
            addEthNewsFragment();
        }else if (type == Constant.EOS_NEWS) {
            layout_bar.setVisibility(View.GONE);
            addEosNewsFragment();
        }else if (type == Constant.LTC_NEWS) {
            layout_bar.setVisibility(View.GONE);
            addLtcNewsFragment();
        }

    }

    private void addLtcNewsFragment() {
        String name = LtcNewsFragment.class.getSimpleName();
        ltcNewsFragment = new LtcNewsFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, ltcNewsFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addEosNewsFragment() {
        String name = EosNewsFragment.class.getSimpleName();
        eosNewsFragment = new EosNewsFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, eosNewsFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addEthNewsFragment() {
        String name = EthNesFragment.class.getSimpleName();
        ethNesFragment = new EthNesFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, ethNesFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addBtcNewsFragment() {
        String name = BtcNewsFragment.class.getSimpleName();
        btcNewsFragment = new BtcNewsFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, btcNewsFragment, name);
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
