package com.ltqh.qh.fragment.market;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.fragment.forum.ChatFragment;
import com.ltqh.qh.fragment.forum.GubaListFragment;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OReportEntity;
import com.ltqh.qh.operation.fragment.market.OAllMarketFragment;
import com.ltqh.qh.utils.ListUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.ltqh.qh.view.SkinInfoEnhanceTabLayout;
import com.ltqh.qh.view.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import skin.support.SkinCompatManager;

import static com.ltqh.qh.Api.NetManger.SUCCESS;
import static com.ltqh.qh.operation.base.OConstant.PERIOD;

public class BlockMarket2Fragment extends OBaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.home_tab)
    SkinInfoEnhanceTabLayout home_tab;

    @BindView(R.id.text_edit)
    TextView text_edit;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.layout_report)
    LinearLayout layout_report;

    @BindView(R.id.img_delete)
    ImageView img_delete;

    @BindView(R.id.img_night)
    ImageView img_night;

    @BindView(R.id.ts_news)
    TextSwitcher mTextSwitcherNews;

    @BindView(R.id.bar)
    LinearLayout layout_bar;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    @BindView(R.id.img_search)
    ImageView img_search;


    private List<OReportEntity.NoticesBean> newSdata;
    private int mNewsIndex;
    private int flag = 0;

    private List<String> titleList = new ArrayList<>();


    private TextView textView;

    /*    @Override
        public void onHiddenChanged(boolean hidden) {
            super.onHiddenChanged(hidden);
            Log.d("print", "onHiddenChanged: "+hidden);
            initTabView();

        }*/
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_block_market2;
    }

    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new MineMarketFragment());
        myPagerAdapter.addFragment(new DigitalMarket2Fragment());
       // myPagerAdapter.addFragment(new OAllMarketFragment());
/*        myPagerAdapter.addFragment(new StockMarketFragment());
        myPagerAdapter.addFragment(new DomesMarketFragment());*/
        viewPager.setAdapter(myPagerAdapter);
    }

    private void initTabView() {
        //  Log.d("print", "onStart:40:    "+"initTabView");

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);

        img_search.setOnClickListener(this);


        String language = SPUtils.getString(Constant.LANGUAGE);
        if (language.equals("en_US")) {
            titleList.add("Mine");
            titleList.add("Main");
           // titleList.add("All");
        } else {
            titleList.add("自选");
            titleList.add("币种");
            //titleList.add("全部");
        }


        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               /* if (tab.getPosition() == 0) {
                    text_edit.setVisibility(View.VISIBLE);

                } else {
                    text_edit.setVisibility(View.GONE);

                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < titleList.size(); i++) {
            home_tab.addTab(titleList.get(i));
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(home_tab.getTabLayout()));
        home_tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        text_edit.setOnClickListener(this);
        initViewPager(viewPager);

        dismissProgressDialog();

    }


    @Override
    public void onStart() {
        super.onStart();
        //   Log.d("print", "onStart:79:    "+"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //  Log.d("print", "onStart:85:    "+"onResume");

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


           // updateNews();


        }
    };

    private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                if (ListUtil.isNotEmpty(newSdata)) {
                    String title = newSdata.get(mNewsIndex).getTitle();
                    mTextSwitcherNews.setText(title);

                }
            }
        }
    }

    @Override
    protected void onLazyLoad() {
        // initTabView();
        // Log.d("print", "onStart:92:    "+"onLazyLoad");

        getReport();

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_report.setVisibility(View.GONE);
                cancelTimer();
            }
        });





    }

    @Override
    protected void initView(View view) {
        //   Log.d("print", "onStart:92:    "+"initView");

        ViewUtils.setLayoutParams(getContext(), layout_bar);

        showProgressDialog();
       // startScheduleJob(mHandler, PERIOD, PERIOD);


        mTextSwitcherNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(getContext());
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.o_text_5c5e76));
                textView.setTextSize(15);
                //   textView.setSingleLine();
                return textView;
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initTabView();

            }
        }, 100);

        img_night.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }


    private void getReport() {


        OkGo.<String>get(OConstant.URL_NEWS_REPORT)
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            OReportEntity oReportEntity = new Gson().fromJson(response.body(), OReportEntity.class);
                            SPUtils.putData(OUserConfig.CACHE_REPORT, oReportEntity);
                            newSdata = oReportEntity.getNotices();


                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");


                    }
                });
    }


    public String dateToStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }


    @Override
    protected void intPresenter() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_edit:
                OIntentActivity.enter(getActivity(), OConstant.OEDITMARKET);


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
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.o_text_8289));

                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    flag = 1;
                    StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
                    EventBus.getDefault().post(new OEventData(OUserConfig.P_NIGHT, "night"));


                } else if (flag == 1) {

                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.o_text_2d2ef));

                    SPUtils.putString(OUserConfig.P_NIGHT, "day");
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    flag = 0;
                    StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
                    EventBus.getDefault().post(new OEventData(OUserConfig.P_NIGHT, "day"));


                }
                break;

            case R.id.img_search:
                IntentActivity.enter(getActivity(), Constant.SEARCH);



                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new DigitalMarket2Fragment(), null, null);
                // layout_send.setVisibility(View.GONE);
                radio_0.setTextSize(17);
                radio_1.setTextSize(15);
                radio_1.setTextColor(getResources().getColor(R.color.text_9999));
                radio_0.setTextColor(getResources().getColor(R.color.text_maincolor));

                text_edit.setVisibility(View.GONE);
                img_search.setVisibility(View.VISIBLE);

                break;

            case R.id.radio_1:


                showFragment(R.id.layout_fragment_containter, new MineMarketFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
                radio_0.setTextSize(15);
                radio_1.setTextSize(17);
                radio_1.setTextColor(getResources().getColor(R.color.text_maincolor));
                radio_0.setTextColor(getResources().getColor(R.color.text_9999));

                text_edit.setVisibility(View.VISIBLE);
                img_search.setVisibility(View.GONE);
                break;

        }
    }
}
