package com.ltqh.qh.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.IntentConfig;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.fragment.HomeFragment;
import com.ltqh.qh.fragment.MyFragment;
import com.ltqh.qh.fragment.forum.ForumTabFragment;
import com.ltqh.qh.fragment.market.BtcMarketFragment;
import com.ltqh.qh.fragment.news.Info2Fragment;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

import static com.ltqh.qh.Api.NetManger.SUCCESS;
import static com.ltqh.qh.activity.MainActivity.TAB_TYPE.TAB_HALL;
import static com.ltqh.qh.activity.MainActivity.TAB_TYPE.TAB_INFORMATION;

public class MainActivity extends OBaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.img_publish)
    ImageView img_publish;

    /**
     * 首页Tab索引
     */
    public static final class TAB_TYPE {
        public static final int COUNT = 4;
        public static final int TAB_HOME = 0;
        public static final int TAB_HALL = TAB_HOME + 1;
        public static final int TAB_POSITION = TAB_HALL + 1;
        public static final int TAB_INFORMATION = TAB_POSITION + 1;
        //   public static final int TAB_DISCOVERY = TAB_INFORMATION + 1;


        public static final int TAB_HALL_QUOTATION = 100;
        public static final int TAB_MY = 3;

    }


    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity != null) {
        } else {

        }
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);


        StatusBarUtil.setStatusBarDarkTheme(MainActivity.this, true);


        EventBus.getDefault().register(this);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(0).performClick();

        img_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    PublishActivity.enter(MainActivity.this);
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                } else {
                    UserActivity.enter(MainActivity.this, Constant.LOGIN);
                }
            }
        });
    }

    @Override
    protected void initData() {
        String string = SPUtils.getString(OUserConfig.ALLDEX);
        if (string == null) {
            NetManger.getInstance().api(new OnNetResult() {
                @Override
                public void onNetResult(String state, Object response) {
                    if (state.equals(SUCCESS)) {
                        NetManger.getInstance().postQuote();
                    }
                }
            });
        }


    }

    @Override
    protected void initEvent() {

    }

    public static void enter(Context context, int tabIndex) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(IntentConfig.Keys.POSITION, tabIndex);
        context.startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(IntentConfig.Keys.REGISTER_SUCCESS)) {
            //showCompleteInfoDialog(intent.getStringExtra(IntentConfig.Keys.REGISTER_SUCCESS));
        } else if (intent.hasExtra(IntentConfig.Keys.POSITION)) {
            int position = intent.getIntExtra(IntentConfig.Keys.POSITION, 0);
            radioGroup.getChildAt(position).performClick();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new HomeFragment(), null, null);

                break;
            case R.id.radio_1:

                showFragment(R.id.layout_fragment_containter, new Info2Fragment(), null, null);


                break;

            case R.id.radio_2:

                showFragment(R.id.layout_fragment_containter, new BtcMarketFragment(), null, null);


                break;

            case R.id.radio_3:
                showFragment(R.id.layout_fragment_containter, new ForumTabFragment(), null, null);


                break;
            case R.id.radio_4:
                showFragment(R.id.layout_fragment_containter, new MyFragment(), null, null);

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(Integer integer) {
        if (integer == TAB_INFORMATION) {
            radioGroup.getChildAt(TAB_INFORMATION).performClick();
        } else if (integer == TAB_HALL) {
            radioGroup.getChildAt(TAB_HALL).performClick();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static void reStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
