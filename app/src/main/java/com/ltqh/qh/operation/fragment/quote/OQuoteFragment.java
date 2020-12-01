package com.ltqh.qh.operation.fragment.quote;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.AppContext;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OPositionEntity;
import com.ltqh.qh.operation.entity.OProfitEntity;
import com.ltqh.qh.operation.fragment.home.OHomeFragment;
import com.ltqh.qh.operation.fragment.market.OAllMarketFragment;
import com.ltqh.qh.operation.fragment.market.ODomesMarketFragment;
import com.ltqh.qh.operation.fragment.market.OForeignMarketFragment;
import com.ltqh.qh.operation.fragment.market.OMarketFragment;
import com.ltqh.qh.operation.fragment.market.OMineMarketFragment;
import com.ltqh.qh.operation.fragment.market.OStockMarketFragment;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import skin.support.SkinCompatManager;

public class OQuoteFragment extends OBaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.radio_0)
    RadioButton radioButton_0;
    @BindView(R.id.radio_1)
    RadioButton radioButton_1;
    @BindView(R.id.img_night)
    ImageView img_night;
    private int flag = 0;

    private String Titles[] = new String[]{"实盘交易", "实盘持仓"};
    private String code;
    private String istrade;


    public static OQuoteFragment newInstance(String data, String istrade) {
        OQuoteFragment fragment = new OQuoteFragment();
        Bundle args = new Bundle();
        args.putString("code", data);
        args.putString("istrade", istrade);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_quote;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getString("code");
            istrade = getArguments().getString("istrade");

        }
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {
       // showProgressDialog();

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }


        if (istrade.equals("1")) {
            radioButton_0.setText("实盘交易");
            radioButton_1.setText("实盘持仓");

        } else if (istrade.equals("2")) {
            radioButton_0.setText("模拟交易");
            radioButton_1.setText("模拟持仓");
        }

        view.findViewById(R.id.img_back).setOnClickListener(this);
        img_night.setOnClickListener(this);


        radioGroup.setOnCheckedChangeListener(this);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                radioGroup.getChildAt(0).performClick();
            }
        },50);

       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
            }
        },1000);*/


    }

    @Override
    protected void initData() {

    }


    @Override
    protected void intPresenter() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(OEventData oEventData){
        String key = oEventData.getKey();
        if (key.equals(OUserConfig.O_TRADE)){
            istrade= (String) oEventData.getObject();
            if (istrade.equals("1")) {
                radioButton_0.setText("实盘交易");
                radioButton_1.setText("实盘持仓");
            } else if (istrade.equals("2")) {
                radioButton_0.setText("模拟交易");
                radioButton_1.setText("模拟持仓");
            }
        }else if (key.equals(OUserConfig.O_POSITION)){
            radioGroup.getChildAt(0).performClick();
        }else if (key.equals(OUserConfig.O_POSITION_DETAIL)){
            radioGroup.getChildAt(0).performClick();

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.img_night:
                String string = SPUtils.getString(OUserConfig.P_NIGHT);

                if (string.equals("")) {

                } else {
                    if (string.equals("night")) {
                        flag = 1;

                    } else if (string.equals("day")) {
                        flag = 0;

                    }
                }
                if (flag == 0) {

                    SPUtils.putString(OUserConfig.P_NIGHT, "night");

                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    flag = 1;
                    StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
                    EventBus.getDefault().post(new OEventData(OUserConfig.P_NIGHT,"night"));


                } else if (flag == 1) {


                    SPUtils.putString(OUserConfig.P_NIGHT, "day");
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    flag = 0;
                    StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
                    EventBus.getDefault().post(new OEventData(OUserConfig.P_NIGHT,"day"));


                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new OQuoteDetailFragment(), "code", code +","+ istrade);

                break;
            case R.id.radio_1:
                if (QuoteProxy.getInstance().isLogin()==true&isMineLogin()==true){
                    showFragment(R.id.layout_fragment_containter, new OPositionFragment(), "code", code +","+ istrade);
                }else {
                    radioButton_1.setChecked(false);
                    radioButton_0.setChecked(true);
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }

                break;


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
        EventBus.getDefault().unregister(this);
    }
}
