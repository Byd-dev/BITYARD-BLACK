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
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.operation.entity.ORechargeEntity;
import com.ltqh.qh.operation.fragment.info.OHotDetailFragment;
import com.ltqh.qh.operation.fragment.info.OMessageFragment;
import com.ltqh.qh.operation.fragment.user.OAccountSettingFragment;
import com.ltqh.qh.operation.fragment.user.OBankListFragment;
import com.ltqh.qh.operation.fragment.user.OEditBankFragment;
import com.ltqh.qh.operation.fragment.user.OFindBackWithDrawFragment;
import com.ltqh.qh.operation.fragment.user.OForgetFragment;
import com.ltqh.qh.operation.fragment.user.OLoginRegisterFragment;
import com.ltqh.qh.operation.fragment.user.OMoneyDetailFragment;
import com.ltqh.qh.operation.fragment.user.OMyfriendFragment;
import com.ltqh.qh.operation.fragment.user.ORSetWithdrawPassFragment;
import com.ltqh.qh.operation.fragment.user.ORealNameFragment;
import com.ltqh.qh.operation.fragment.user.ORechargeFragment;
import com.ltqh.qh.operation.fragment.user.ORechargeStepFragment;
import com.ltqh.qh.operation.fragment.user.ORechargehistoryFragment;
import com.ltqh.qh.operation.fragment.user.ORecommendFragment;
import com.ltqh.qh.operation.fragment.user.OResetFragment;
import com.ltqh.qh.operation.fragment.user.OResetPhoneFragment;
import com.ltqh.qh.operation.fragment.user.OResetPhoneNextFragment;
import com.ltqh.qh.operation.fragment.user.OSetPassFragment;
import com.ltqh.qh.operation.fragment.user.OWithRealNameFragment;
import com.ltqh.qh.operation.fragment.user.OWithdrawForgetFragment;
import com.ltqh.qh.operation.fragment.user.OWithdrawFragment;
import com.ltqh.qh.operation.fragment.user.OWithdrawhistoryFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.StatusBarUtil;

import java.io.Serializable;

import butterknife.BindView;

public class OUserActivity extends OBaseActivity implements View.OnClickListener {
    private static final String TYPE = "USER_TYPE";
    private static final String DATA = "USER_DATA";

    private int type;
    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.text_title)
    TextView text_title;

    @BindView(R.id.text_add)
    TextView text_add;

    @BindView(R.id.view_line)
    View view_line;


    private FragmentTransaction ft;
    private OLoginRegisterFragment oLoginFragment;

    private OForgetFragment oForgetFragment;
    private OResetFragment oResetFragment;

    private OAccountSettingFragment oAccountSettingFragment;
    private ORealNameFragment oRealNameFragment;
    private OBankListFragment oBankListFragment;
    private OEditBankFragment oEditBankFragment;
    private OResetPhoneFragment oResetPhoneFragment;
    private OResetPhoneNextFragment oResetPhoneNextFragment;
    private OSetPassFragment oSetPassFragment;
    private ORSetWithdrawPassFragment orSetWithdrawPassFragment;
    private OWithdrawForgetFragment oWithdrawForgetFragment;
    private OWithRealNameFragment oWithRealNameFragment;
    private OFindBackWithDrawFragment oFindBackWithDrawFragment;
    //提款页面
    private OWithdrawFragment owithdrawFragment;
    //
    private OWithdrawhistoryFragment oWithdrawhistoryFragment;

    private ORechargehistoryFragment oRechargehistoryFragment;
    private ORechargeFragment oRechargeFragment;
    private OMoneyDetailFragment oMoneyDetailFragment;
    private OMessageFragment oMessageFragment;
    private OMyfriendFragment oMyfriendFragment;
    private ORechargeStepFragment oRechargeStepFragment;


    @Override
    protected int setContentLayout() {
        return R.layout.o_activity_user;
    }

    public static void enter(Context context, int type) {
        Intent intent = new Intent(context, OUserActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    public static void enter(Context context, int type, ORechargeEntity.PayListBean data) {
        Intent intent = new Intent(context, OUserActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(DATA, data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String string = SPUtils.getString(OUserConfig.P_NIGHT);

        if (string.equals("")) {

        } else {
            if (string.equals("night")) {
                StatusBarUtil.setStatusBarDarkTheme(OUserActivity.this, false);

            } else if (string.equals("day")) {
                StatusBarUtil.setStatusBarDarkTheme(OUserActivity.this, true);
            }
        }

        StatusBarUtil.setRootViewFitsSystemWindows(this, true);


        type = getIntent().getIntExtra(TYPE, 0);

        if (type == OConstant.LOGIN) {
            StatusBarUtil.setStatusBarDarkTheme(OUserActivity.this, false);
            StatusBarUtil.setRootViewFitsSystemWindows(this, false);

            layout_bar.setVisibility(View.GONE);
            addLoginFragment();
        } else if (type == OConstant.FORGET) {
            text_title.setText("忘记密码");
            addForgetFragment();
        } else if (type == OConstant.OACCOUNTSETTING) {
            text_title.setText("账户设置");
            addAccountSettingFragment();
        } else if (type == OConstant.OREALNAME) {
            text_title.setText("实名认证");
            addRealNameFragment();
        } else if (type == OConstant.BANK) {
            text_title.setText("银行卡管理");
            addBankFragment();
            text_add.setVisibility(View.VISIBLE);
        } else if (type == OConstant.O_EDIT_BANK) {
            text_title.setText("绑定银行卡");
            text_add.setVisibility(View.GONE);
            addEditBankFragment();
        } else if (type == OConstant.RESET) {
            text_title.setText("修改密码");
            text_add.setVisibility(View.GONE);
            addResetFragment();
        } else if (type == OConstant.RESETPHONE) {
            text_title.setText("验证手机号码");
            text_add.setVisibility(View.GONE);
            addResetPhoneFragment();
        } else if (type == OConstant.RESETPHONENEXT) {
            text_title.setText("绑定手机号码");
            text_add.setVisibility(View.GONE);
            addResetPhoneNextFragment();
        } else if (type == OConstant.SETWITHDRAWPASS) {
            text_title.setText("设置提款密码");
            text_add.setVisibility(View.GONE);
            addSetTiKuanPassFragment();
        } else if (type == OConstant.RESETWITHDRAWPASS) {
            text_title.setText("修改提款密码");
            text_add.setVisibility(View.GONE);
            addResetWithPassFragment();

        } else if (type == OConstant.WITHDRAWFORGETPASS) {
            text_title.setText("找回提款密码");
            text_add.setVisibility(View.GONE);
            addForgetWithPassFragment();

        } else if (type == OConstant.CHECKREALNAME_ID) {
            text_title.setText("核对信息");
            text_add.setVisibility(View.GONE);
            addWithdrawRealnameFragment();
        } else if (type == OConstant.FINDBACKWITHDRAW) {
            text_title.setText("输入新密码");
            text_add.setVisibility(View.GONE);
            addFindBackWithdrawFragment();
        } else if (type == OConstant.WITHDRAW) {
            text_title.setText("提现");
            text_add.setText("提现记录");
            text_add.setVisibility(View.VISIBLE);
            addWithdrawFragment();
        } else if (type == OConstant.O_WITHDRAW_HISTORY) {
            text_title.setText("提现记录");
            text_add.setVisibility(View.GONE);
            addWithdrawHistoryFragment();
        } else if (type == OConstant.O_RECHARGE) {
            text_title.setText("充值");
            text_add.setText("充值记录");
            text_add.setVisibility(View.VISIBLE);
            addRechargeFragment();
        } else if (type == OConstant.O_RECHARGE_HISTORY) {
            text_title.setText("充值记录");
            text_add.setVisibility(View.GONE);
            addRechargeHistoryFragment();
        } else if (type == OConstant.O_MONEY_DETAIL) {
            text_title.setText("资金明细");
            text_add.setVisibility(View.GONE);
            addMoneyDetailFragment();
            view_line.setVisibility(View.VISIBLE);
        } else if (type == OConstant.O_MESSAGE) {
            text_title.setText(getResources().getString(R.string.text_message));
            text_add.setVisibility(View.GONE);
            addMessageFragment();
        } else if (type == OConstant.O_MYFRIEND) {
            text_title.setText("我的用户");
            text_add.setVisibility(View.GONE);
            addMyFriendFragment();
        } else if (type == OConstant.O_RECHARGE_STEP) {
            ORechargeEntity.PayListBean payListBean = (ORechargeEntity.PayListBean) getIntent().getSerializableExtra(DATA);
            String name = payListBean.getName();
            text_title.setText(name);
            addRechargeStepFragment(payListBean);
        }
    }

    private void addRechargeStepFragment(ORechargeEntity.PayListBean payListBean) {
        ORechargeStepFragment oRechargeStepFragment = ORechargeStepFragment.newInstance(payListBean);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_fragment_containter, oRechargeStepFragment).commit();
    }


    private void addMyFriendFragment() {
        String name = OMyfriendFragment.class.getSimpleName();
        oMyfriendFragment = new OMyfriendFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oMyfriendFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }


    private void addMessageFragment() {
        String name = OMessageFragment.class.getSimpleName();
        oMessageFragment = new OMessageFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oMessageFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addMoneyDetailFragment() {
        String name = OMoneyDetailFragment.class.getSimpleName();
        oMoneyDetailFragment = new OMoneyDetailFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oMoneyDetailFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addRechargeHistoryFragment() {
        String name = ORechargehistoryFragment.class.getSimpleName();
        oRechargehistoryFragment = new ORechargehistoryFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oRechargehistoryFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();

    }

    private void addRechargeFragment() {
        String name = ORechargeFragment.class.getSimpleName();
        oRechargeFragment = new ORechargeFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oRechargeFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();

    }

    private void addWithdrawHistoryFragment() {
        String name = OWithdrawhistoryFragment.class.getSimpleName();
        oWithdrawhistoryFragment = new OWithdrawhistoryFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oWithdrawhistoryFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    //提现
    private void addWithdrawFragment() {
        String name = OWithdrawFragment.class.getSimpleName();
        owithdrawFragment = new OWithdrawFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, owithdrawFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addFindBackWithdrawFragment() {
        String name = OFindBackWithDrawFragment.class.getSimpleName();
        oFindBackWithDrawFragment = new OFindBackWithDrawFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oFindBackWithDrawFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addWithdrawRealnameFragment() {
        String name = OWithRealNameFragment.class.getSimpleName();
        oWithRealNameFragment = new OWithRealNameFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oWithRealNameFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addForgetWithPassFragment() {
        String name = OWithdrawForgetFragment.class.getSimpleName();
        oWithdrawForgetFragment = new OWithdrawForgetFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oWithdrawForgetFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addResetWithPassFragment() {
        String name = ORSetWithdrawPassFragment.class.getSimpleName();
        orSetWithdrawPassFragment = new ORSetWithdrawPassFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, orSetWithdrawPassFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addSetTiKuanPassFragment() {
        String name = OSetPassFragment.class.getSimpleName();
        oSetPassFragment = new OSetPassFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oSetPassFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addResetPhoneNextFragment() {
        String name = OResetPhoneNextFragment.class.getSimpleName();
        oResetPhoneNextFragment = new OResetPhoneNextFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oResetPhoneNextFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();

    }

    private void addResetPhoneFragment() {
        String name = OResetPhoneFragment.class.getSimpleName();
        oResetPhoneFragment = new OResetPhoneFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oResetPhoneFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }


    private void addLoginFragment() {
        String name = OLoginRegisterFragment.class.getSimpleName();
        oLoginFragment = new OLoginRegisterFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oLoginFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addForgetFragment() {
        String name = OForgetFragment.class.getSimpleName();
        oForgetFragment = new OForgetFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oForgetFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addAccountSettingFragment() {
        String name = OAccountSettingFragment.class.getSimpleName();
        oAccountSettingFragment = new OAccountSettingFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oAccountSettingFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addRealNameFragment() {
        String name = ORealNameFragment.class.getSimpleName();
        oRealNameFragment = new ORealNameFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oRealNameFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addBankFragment() {
        String name = OBankListFragment.class.getSimpleName();
        oBankListFragment = new OBankListFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oBankListFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addEditBankFragment() {
        String name = OEditBankFragment.class.getSimpleName();
        oEditBankFragment = new OEditBankFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oEditBankFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addResetFragment() {
        String name = OResetFragment.class.getSimpleName();
        oResetFragment = new OResetFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, oResetFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_add.setOnClickListener(this);
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
        String title = text_add.getText().toString();
        switch (v.getId()) {
            case R.id.text_add:

                if (title.equals("添加")) {

                    OUserActivity.enter(this, OConstant.O_EDIT_BANK);
                } else if (title.equals("提现记录")) {
                    OUserActivity.enter(this, OConstant.O_WITHDRAW_HISTORY);
                } else if (title.equals("充值记录")) {
                    OUserActivity.enter(this, OConstant.O_RECHARGE_HISTORY);
                }


                break;
        }
    }
}
