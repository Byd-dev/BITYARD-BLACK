package com.ltqh.qh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseActivity;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.forum.GuliaoDetailFragment;
import com.ltqh.qh.fragment.news.LiveFragment;
import com.ltqh.qh.fragment.user.ForgetFragment;
import com.ltqh.qh.fragment.user.LoginFragment;
import com.ltqh.qh.fragment.user.MyMeaasgeFragment;
import com.ltqh.qh.fragment.user.NicknameFragment;
import com.ltqh.qh.fragment.user.PersonalCenterFragment;
import com.ltqh.qh.fragment.user.RegisterFragment;
import com.ltqh.qh.fragment.user.ResetPassFragment;
import com.ltqh.qh.fragment.user.SignFragment;
import com.ltqh.qh.operation.activity.SecondActivity;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.view.StatusBarUtil;

import butterknife.BindView;

public class UserActivity extends OBaseActivity implements View.OnClickListener {
    @BindView(R.id.text_register)
    TextView text_register;

    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.text_title)
    TextView text_title;


    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    private int isLogin = 0;

    private static final String TYPE = "LOGIN_TYPE";
    private int type;
    private RegisterFragment registerFragment;
    private LoginFragment loginFragment;
    private PersonalCenterFragment personalCenterFragment;
    private ForgetFragment forgetFragment;
    private ResetPassFragment resetPassFragment;
    private NicknameFragment nicknameFragment;
    private SignFragment signFragment;

    private GuliaoDetailFragment guliaoDetailFragment;
    private MyMeaasgeFragment myMeaasgeFragment;
    private FragmentTransaction ft;

    public static void enter(Context context, int type) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    public static void enter(Context context, int type,int id) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(Constant.PARAM_ID,id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式
       // StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //浅色标题
       // StatusBarUtil.setStatusBarDarkTheme(UserActivity.this, false);

        setStatusBar(getResources().getColor(R.color.white));



        type = getIntent().getIntExtra(TYPE, 0);
        int id = getIntent().getIntExtra(Constant.PARAM_ID,0);


        if (type == Constant.REGISTER) {
            addRegisterFragment();
        } else if (type == Constant.LOGIN) {
            addLoginFragment();
            layout_bar.setVisibility(View.GONE);

        } else if (type == Constant.PERSONUSER) {
            addPersonUserFragment();
        } else if (type == Constant.FORGET) {
            addForgetFragment();
            text_title.setText(getResources().getString(R.string.text_find_pass));
            text_register.setVisibility(View.GONE);
        } else if (type == Constant.RESET) {
            text_title.setText(getResources().getString(R.string.text_change));
            text_register.setVisibility(View.GONE);
            addResetFragment();

        } else if (type == Constant.NICKNAME) {
            text_title.setText(getResources().getString(R.string.text_change_nickname));
            text_register.setVisibility(View.GONE);
            addNicknameFragment();
        } else if (type == Constant.SIGNATURE) {
            text_title.setText(getResources().getString(R.string.text_sign));
            text_register.setVisibility(View.GONE);
            addSignFragment();
        } else if (type == Constant.FORUM_PUBLISH) {
            text_title.setText(getResources().getString(R.string.text_detail));
            text_register.setVisibility(View.GONE);
            addGuliaoDetailFragment(id);
        }else if (type == Constant.USER_MYMEAAAGE) {
            text_title.setText(getResources().getString(R.string.text_message));
            text_register.setVisibility(View.GONE);
            addMyMessageFragment();
        }


    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_login;
    }

    private void addLoginFragment() {
        String name = LoginFragment.class.getSimpleName();
        loginFragment = new LoginFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, loginFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addRegisterFragment() {
        String name = RegisterFragment.class.getSimpleName();
        registerFragment = new RegisterFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, registerFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addPersonUserFragment() {
        String name = PersonalCenterFragment.class.getSimpleName();
        personalCenterFragment = new PersonalCenterFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, personalCenterFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addForgetFragment() {
        String name = ForgetFragment.class.getSimpleName();
        forgetFragment = new ForgetFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, forgetFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addResetFragment() {
        String name = ResetPassFragment.class.getSimpleName();
        resetPassFragment = new ResetPassFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, resetPassFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addNicknameFragment() {
        String name = NicknameFragment.class.getSimpleName();
        nicknameFragment = new NicknameFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, nicknameFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addSignFragment() {
        String name = SignFragment.class.getSimpleName();
        signFragment = new SignFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, signFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addGuliaoDetailFragment(int id) {
        String name = GuliaoDetailFragment.class.getSimpleName();
        guliaoDetailFragment = new GuliaoDetailFragment(id);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, guliaoDetailFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    private void addMyMessageFragment() {
        String name = MyMeaasgeFragment.class.getSimpleName();
        myMeaasgeFragment = new MyMeaasgeFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layout_fragment_containter, myMeaasgeFragment, name);
        ft.addToBackStack(name);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        text_register.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_register:
                if (isLogin == 0) {
                    ft.hide(loginFragment);
                    addRegisterFragment();
                    text_register.setText(getResources().getString(R.string.text_already_account));
                    text_register.setTextColor(getResources().getColor(R.color.maincolor));
                    text_title.setText(getResources().getString(R.string.text_account_register));
                    isLogin = 1;
                } else if (isLogin == 1) {
                    ft.hide(registerFragment);
                    addLoginFragment();
                    text_register.setText(getResources().getString(R.string.text_create_account));
                    text_register.setTextColor(getResources().getColor(R.color.text_maincolor));
                    text_title.setText(getResources().getString(R.string.text_log_in));

                    isLogin = 0;
                }


                break;

            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
