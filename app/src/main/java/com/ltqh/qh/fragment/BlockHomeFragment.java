package com.ltqh.qh.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.activity.WebActivity;
import com.ltqh.qh.adapter.AlertsAdapter;
import com.ltqh.qh.adapter.HomeBtcAdapter;
import com.ltqh.qh.adapter.HomeTopAdapter;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.BannerEntity;
import com.ltqh.qh.fragment.market.DigitalMarketFragment;
import com.ltqh.qh.fragment.market.MineMarketFragment;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.activity.ONewsDetailActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.operation.entity.OReportEntity;
import com.ltqh.qh.operation.fragment.market.OAllMarketFragment;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.ListUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.ltqh.qh.view.SkinInfoEnhanceTabLayout;
import com.ltqh.qh.view.StatusBarUtil;
import com.ltqh.qh.view.XCRoundRectImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import skin.support.SkinCompatManager;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;
import static com.ltqh.qh.operation.base.OConstant.PERIOD;

public class BlockHomeFragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.home_tab)
    SkinInfoEnhanceTabLayout home_tab;


    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.layout_report)
    LinearLayout layout_report;

    @BindView(R.id.img_delete)
    ImageView img_delete;

    @BindView(R.id.img_service)
    ImageView img_service;

    @BindView(R.id.ts_news)
    TextSwitcher mTextSwitcherNews;

/*    @BindView(R.id.bar)
    RelativeLayout layout_bar;*/

    @BindView(R.id.recyclerview_attention)
    RecyclerView recyclerview_attention;
    @BindView(R.id.banner_article)
    XBanner banner_article;

    @BindView(R.id.recyclerview_top)
    RecyclerView recyclerView_top;
    private HomeTopAdapter homeTopAdapter;
    private String isupdown = "up";


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
        return R.layout.fragment_block_home;
    }

    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new MineMarketFragment());
        myPagerAdapter.addFragment(new DigitalMarketFragment());
        myPagerAdapter.addFragment(new OAllMarketFragment());
/*        myPagerAdapter.addFragment(new StockMarketFragment());
        myPagerAdapter.addFragment(new DomesMarketFragment());*/
        viewPager.setAdapter(myPagerAdapter);
    }

    private void initTabView() {
        //  Log.d("print", "onStart:40:    "+"initTabView");

        String language = SPUtils.getString(Constant.LANGUAGE);
        if (language.equals("en_US")) {
            titleList.add("Mine");
            titleList.add("Main");
            titleList.add("All");
        } else {
            titleList.add("自选");
            titleList.add("主区");
            titleList.add("全部");
        }


        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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
        initViewPager(viewPager);


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
        if (banner_article != null) {
            banner_article.startAutoPlay();
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //暂时屏蔽消息
            //updateNews();

            getQuote();


        }
    };

    private void getBanner() {
        OkGo.<String>get(Constant.URL_BANNER)
                .tag(this)
                .params(Constant.PARAM_SLIDE_ID, 1)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            BannerEntity bannerEntity = new Gson().fromJson(response.body(), BannerEntity.class);
                            List<BannerEntity.DataBean> data = bannerEntity.getData();
                            upArticleBanner(data);
                        }
                    }
                });
    }


    private void upArticleBanner(List<BannerEntity.DataBean> data) {
        Log.d("print", "upBanner:461:   " + data);
        if (data == null) {
            return;
        }

        if (banner_article != null) {
            banner_article.setBannerData(R.layout.item_articlebanner_layout, data);
            banner_article.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    XCRoundRectImageView imageView = view.findViewById(R.id.img_banner);
                    Log.d("print", "loadBanner: 643:  " + data.get(position).getXBannerUrl());
                    Glide.with(getActivity()).load(data.get(position).getXBannerUrl()).asBitmap().into(imageView);
                    TextView textView = view.findViewById(R.id.text_title);
                    textView.setText(data.get(position).getTitle());
                    TextView text_content = view.findViewById(R.id.text_content);
                    text_content.setText(data.get(position).getContent());
                }
            });
        }

       /* banner.setPages(data, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });*/

    }

    private AlertsAdapter alertsAdapter;

    @Override
    protected void onLazyLoad() {

        getNewsData();
        // initTabView();
        // Log.d("print", "onStart:92:    "+"onLazyLoad");
        getBanner();

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

        //  ViewUtils.setLinearLayoutParams(getContext(), layout_bar,48);

        showProgressDialog();
        startScheduleJob(mHandler, PERIOD, PERIOD);


        //btc
        homeTopAdapter = new HomeTopAdapter(getActivity());
        recyclerView_top.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView_top.setAdapter(homeTopAdapter);

        //新闻
        alertsAdapter = new AlertsAdapter(getActivity());
        recyclerview_attention.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_attention.setAdapter(alertsAdapter);


        alertsAdapter.setOnItemClick(new AlertsAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OHotEntity.NewsListBean newsListBean) {
                ONewsDetailActivity.enter(getActivity(), "ALERTS", newsListBean);

            }
        });


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

        img_service.setOnClickListener(this);
        view.findViewById(R.id.layout_info).setOnClickListener(this);
        view.findViewById(R.id.layout_tool).setOnClickListener(this);
        view.findViewById(R.id.layout_feedback).setOnClickListener(this);
        view.findViewById(R.id.layout_question).setOnClickListener(this);
        view.findViewById(R.id.layout_search).setOnClickListener(this);
        view.findViewById(R.id.img_message).setOnClickListener(this);
        view.findViewById(R.id.img_service).setOnClickListener(this);


    }


    private void getQuote() {

        List<String> dataList = QuoteProxy.getInstance().getDigitalDataList();

        Log.d("print", "getQuote:283:  " + dataList);
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (dataList != null) {


            homeTopAdapter.setIsUpDown(isupdown);
            homeTopAdapter.setDatas(OUserConfig.P_DIGITAL, dataList.subList(6, 9));
            homeTopAdapter.setDigitalDatas(OUserConfig.P_DIGITAL, oApiEntity.getDigitalCommds());


           /* Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    homeTopAdapter.notifyItem(dataList);

                }
            }, PERIOD);*/
            cancelTimer();
            dismissProgressDialog();


        } else {
            NetManger.getInstance().api(new OnNetResult() {
                @Override
                public void onNetResult(String state, Object response) {
                    if (state.equals(SUCCESS)) {
                        //  dismissProgressDialog();
                        NetManger.getInstance().postQuote();
                    } else if (state.equals(BUSY)) {
                        // showProgressDialog();
                    } else if (state.equals(FAILURE)) {
                        // dismissProgressDialog();
                        Toast.makeText(getActivity(), getResources().getString(R.string.o_text_err), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void initData() {
        getQuote();
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

            case R.id.layout_info:
                IntentActivity.enter(getActivity(), Constant.INFO);

                break;

            case R.id.layout_tool:
                IntentActivity.enter(getActivity(), Constant.SKILLALL);

                break;

            case R.id.layout_feedback:
                IntentActivity.enter(getActivity(), Constant.FEEDBACK);

                break;
            case R.id.layout_question:
                IntentActivity.enter(getActivity(), Constant.LEARNCLASS);

                break;
            case R.id.layout_search:
                IntentActivity.enter(getActivity(), Constant.SEARCH);
                break;

            case R.id.img_message:
                if (isLogin()) {
                    UserActivity.enter(getActivity(), Constant.USER_MYMEAAAGE);

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;
            case R.id.img_service:
                if (isLogin()) {
                    WebActivity.openZhiChiService(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner_article != null) {
            banner_article.stopAutoPlay();
        }
        cancelTimer();
    }

    private void getNewsData() {
        OkGo.<String>get(Constant.URL_NEWS_HOT)
                .tag(this)
                .params(Constant.PARAM_TYPE, "0")
                .cacheKey(Constant.URL_ALERTS)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        //showProgressDialog();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        // Log.d("print", "onSuccess:1188:    " + response.body());

                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            OHotEntity oHotEntity = new Gson().fromJson(response.body(), OHotEntity.class);

                            List<OHotEntity.NewsListBean> newsList = oHotEntity.getNewsList();
                            // Log.d("print", "onSuccess:1198:  " + newsList);
                            alertsAdapter.setDatas(newsList);
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        showToast("获取失败,请检查网络");
                    }
                });
    }
}
