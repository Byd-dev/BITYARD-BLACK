package com.ltqh.qh.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.NewsDetailActivity;
import com.ltqh.qh.activity.PersonActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.activity.WebActivity;
import com.ltqh.qh.adapter.AlertsAdapter;
import com.ltqh.qh.adapter.GoldlistAdapter;
import com.ltqh.qh.adapter.HomeBannerAdapter;
import com.ltqh.qh.adapter.HomeBtcAdapter;
import com.ltqh.qh.adapter.HomeCalendarAdapter;
import com.ltqh.qh.adapter.HomeChatAdapter;
import com.ltqh.qh.adapter.HomeMenuAdapter;
import com.ltqh.qh.adapter.MarketAdapter;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.adapter.StockHomeAdapter;
import com.ltqh.qh.adapter.StockTabAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.BannerEntity;
import com.ltqh.qh.entity.BtcMarketEntity;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.GoldlistEntity;
import com.ltqh.qh.entity.GuliaoEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.StockEntity;
import com.ltqh.qh.entity.UserInfoEntity;
import com.ltqh.qh.fragment.news.LiandeFragment;
import com.ltqh.qh.fragment.news.StrategyFragment;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.activity.ONewsDetailActivity;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.operation.entity.OHoursEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.ListUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.ltqh.qh.view.AlphaChangeListener;
import com.ltqh.qh.view.CircleImageView;
import com.ltqh.qh.view.MyScrollView;
import com.ltqh.qh.view.XCRoundRectImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.stx.xhb.xbanner.XBanner;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;
import static com.ltqh.qh.operation.base.OConstant.PERIOD;

public class HomeFragment extends OBaseFragment implements View.OnClickListener {

    private final static int PERIOD = 5 * 1000; // 5s

    @BindView(R.id.text_login)
    TextView text_login;

    @BindView(R.id.img_bg)
    ImageView img_bg;

    private int id;

    @BindView(R.id.layout_view)
    RelativeLayout layout_view;

    @BindView(R.id.recyclerview_stock)
    RecyclerView recyclerView_stock;

    @BindView(R.id.recyclerview_chat)
    RecyclerView recyclerView_chat;

    @BindView(R.id.recyclerview_attention)
    RecyclerView recyclerview_attention;

    @BindView(R.id.recyclerview_menu)
    RecyclerView recyclerView_menu;

    @BindView(R.id.text_time)
    TextView text_time;



    private HomeBannerAdapter homeBannerAdapter;



    @BindView(R.id.scrollView)
    MyScrollView myScrollView;

    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.layout_bar2)
    RelativeLayout layout_bar2;
    @BindView(R.id.banner_article)
    XBanner banner_article;

    @BindView(R.id.img_head)
    CircleImageView img_head;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private GoldlistAdapter goldlistAdapter;
    private StockHomeAdapter stockAdapter;
    private StockTabAdapter stockTabAdapter;
    private HomeChatAdapter homeChatAdapter;
    private HomeCalendarAdapter homeCalendarAdapter;
    private HomeMenuAdapter homeMenuAdapter;
    private AlertsAdapter alertsAdapter;

    private String isupdown = "up";
    private HomeBtcAdapter oMarketAdapter;


    private int mNewsIndex;

    @BindView(R.id.ts_news)
    TextSwitcher mTextSwitcherNews;


    private List<String> newSdata;

    private List<String> menus = new ArrayList<>();


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }
    @Override
    public void onResume() {
        super.onResume();


        if (banner_article != null) {
            banner_article.startAutoPlay();
        }

        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        UserInfoEntity userInfoEntity = SPUtils.getData(UserConfig.USER, UserInfoEntity.class);

        if (userInfoEntity != null) {
            if (userInfoEntity.getData() == null) {
                return;
            }

            // getUserInfo();
            Glide.with(getActivity())
                    .load(Constant.USER_AVATER_URL + userInfoEntity.getData().getAvatar())
                    .asBitmap()
                    .error(R.mipmap.user_icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(img_head);
            String user_nickname = userInfoEntity.getData().getUser_nickname();
            if (user_nickname.equals("")) {
                text_login.setText(getResources().getString(R.string.text_user));
            } else {
                text_login.setText(userInfoEntity.getData().getUser_nickname());
            }

        } else {
            text_login.setText(getResources().getString(R.string.text_log_in));
            img_head.setImageDrawable(getResources().getDrawable(R.mipmap.user_icon));

            if (loginEntity != null) {
                Glide.with(getActivity())
                        .load(Constant.USER_AVATER_URL + loginEntity.getData().getUser().getAvatar())
                        .asBitmap()
                        .error(R.mipmap.user_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(img_head);
                //  getUserInfo();
                getGuliao(loginEntity.getData().getToken(), 0);

                String user_nickname = loginEntity.getData().getUser().getUser_nickname();
                if (user_nickname.equals("")) {
                    text_login.setText("用户");
                } else {
                    text_login.setText(loginEntity.getData().getUser().getUser_nickname());

                }
            }
        }

        if (isLogin()) {

            recyclerView_chat.setVisibility(View.VISIBLE);
            img_bg.setVisibility(View.GONE);
        } else {
            recyclerView_chat.setVisibility(View.GONE);
            img_bg.setVisibility(View.VISIBLE);
        }
    }


    private String Titles[] = new String[]{"直播", "策略"};
    private List<Integer> banners = new ArrayList<>();

    @Override
    protected void onLazyLoad() {



    }

    @Override
    protected void initView(View view) {
        ViewUtils.setRelativeLayoutParams(getContext(), layout_bar,48);
        ViewUtils.setRelativeLayoutParams(getContext(), layout_bar2,48);

        myScrollView.setAlphaChangeListener(new AlphaChangeListener() {
            @Override
            public void alphaChanging(float alpha) {
                layout_bar.setVisibility(View.VISIBLE);
                layout_bar.setAlpha(alpha);
                layout_bar2.setAlpha(1 - alpha);


            }
        });
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        EventBus.getDefault().register(this);

        menus.add("投资课堂");
        menus.add("工具换算");
        menus.add("视频学习");

        List<Integer> bannerList = new ArrayList<>();


        text_login.setOnClickListener(this);
       /* goldlistAdapter = new GoldlistAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(goldlistAdapter);*/

        stockAdapter = new StockHomeAdapter(getActivity());
        recyclerView_stock.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView_stock.setAdapter(stockAdapter);
        //btc
        oMarketAdapter = new HomeBtcAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(oMarketAdapter);


        homeChatAdapter = new HomeChatAdapter(getActivity());


        recyclerView_chat.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_chat.setAdapter(homeChatAdapter);

        homeMenuAdapter = new HomeMenuAdapter(getActivity());
        recyclerView_menu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_menu.setAdapter(homeMenuAdapter);
        homeMenuAdapter.setDatas(menus);


        homeCalendarAdapter = new HomeCalendarAdapter(getActivity());
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
                TextView textView = new TextView(getContext());
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_secondcolor));
                textView.setTextSize(15);
                //   textView.setSingleLine();
                return textView;
            }
        });


        startScheduleJob(mHandler, PERIOD, PERIOD);

        view.findViewById(R.id.img_kefu).setOnClickListener(this);
        view.findViewById(R.id.text_message).setOnClickListener(this);
        view.findViewById(R.id.text_ketang).setOnClickListener(this);
        view.findViewById(R.id.text_gongju).setOnClickListener(this);
        view.findViewById(R.id.text_shipin).setOnClickListener(this);
        view.findViewById(R.id.text_chat).setOnClickListener(this);
        view.findViewById(R.id.img_head).setOnClickListener(this);

        view.findViewById(R.id.layout_search).setOnClickListener(this);
        text_time.setText(dateToStamp().substring(0, 10));


        view.findViewById(R.id.img_message).setOnClickListener(this);



      /*  homeChatAdapter.setOnItemClick(new HomeChatAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position) {
                if (content.getId() != 68) {
                    OUserActivity.enter(getActivity(), Constant.FORUM_PUBLISH, content.getId());
                }
            }
        });*/

        homeMenuAdapter.setOnItemClick(new HomeMenuAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String content) {
                switch (content) {
                    case "视频学习":
                        //IntentActivity.enter(getActivity(), Constant.FORUM);
                        IntentActivity.enter(getActivity(), Constant.VIDEO);

                        break;
                    case "投资课堂":
                        IntentActivity.enter(getActivity(), Constant.LEARNCLASS);

                        break;
                    case "工具换算":
                        IntentActivity.enter(getActivity(), Constant.SKILLALL);

                        break;
                    case "新闻资讯":
                        // EventBus.getDefault().post(MainActivity.TAB_TYPE.TAB_INFORMATION);
                        IntentActivity.enter(getActivity(), Constant.INFO);

                        break;
                }
            }
        });

        homeChatAdapter.setOnJuBaoItemClick(new HomeChatAdapter.OnJuBaoItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position) {
                id = content.getId();
                Log.d("print", "onSuccessListener:278 " + id);
                showItemPopWindow();
            }
        });

    }

    String sorts[] = new String[]{Constant.STAY_PRICECHANGE, Constant.STAY_CHANGEPERCENT, Constant.STAY_VOLUME, Constant.STAY_AMOUNT, Constant.STAY_TURNOVERRATIO};

    public String getSort() {
        String wellcomTips;
        int id = (int) (Math.random() * (sorts.length - 1));//随机产生一个index索引
        wellcomTips = sorts[id];
        return wellcomTips;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //  getHomeGold();

            //getHomeStock(0, getSort());
            updateNews();
            getQuote();


        }
    };

    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new StrategyFragment());
        myPagerAdapter.addFragment(new LiandeFragment());
        viewPager.setAdapter(myPagerAdapter);
    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
        // getHomeStock(0, Constant.STAY_PRICECHANGE);
        //getHomeGold();
        getQuote();


        getBanner();

        //getGold();
        getNews();
        getNewsData();
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            //  showToast("请登录");
        } else {
            getGuliao(loginEntity.getData().getToken(), 0);
        }

        //getCalendar(dateToStamp().toString(), 3);
    }


    private void getQuote() {

        List<String> dataList = QuoteProxy.getInstance().getDigitalDataList();

        Log.d("print", "getQuote:483:  " + dataList);


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (dataList != null) {

            oMarketAdapter.setIsUpDown(isupdown);
            oMarketAdapter.setDatas(OUserConfig.P_DIGITAL, dataList.subList(6, 9));
            oMarketAdapter.setDigitalDatas(OUserConfig.P_DIGITAL, oApiEntity.getDigitalCommds());


            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    oMarketAdapter.notifyItem(dataList);

                }
            }, PERIOD);

        } else {

        }
    }


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
        //  Log.d("print", "upBanner:461:   " + data);
        if (data == null) {
            return;
        }

        if (banner_article != null) {
            banner_article.setBannerData(R.layout.item_articlebanner_layout, data);
            banner_article.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    XCRoundRectImageView imageView = view.findViewById(R.id.img_banner);
                    Glide.with(getActivity()).load(data.get(position).getXBannerUrl()).asBitmap().into(imageView);
                    TextView textView = view.findViewById(R.id.text_title);
                    textView.setText(data.get(position).getTitle());
                    TextView text_content = view.findViewById(R.id.text_content);
                    text_content.setText(data.get(position).getContent());
                }
            });

            banner_article.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    NewsDetailActivity.enter(getContext(), "BANNER", data.get(position));

                }
            });
        }

       /* banner_one.setPages(data, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });*/

    }

    /*private void getGold() {
        OkGo.<String>get(Constant.URL_HOME_GOLD_URL)
                .tag(this)
                .params(Constant.PARAM_TYPE, 2)
                .params(Constant.PARAM_PAGE, 1)
                .params(Constant.PARAM_NUMBER, 10)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_SORT, Constant.STAY_SORT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            GoldShowEntity goldShowEntity = new Gson().fromJson(response.body(), GoldShowEntity.class);
                            newSdata = goldShowEntity.getData();
                            if (newSdata != null) {
                                mTextSwitcherNews.setText(newSdata.get(0).getName() + "  当前价格:" + newSdata.get(0).getTrade() + "  最高价格:" + newSdata.get(0).getHigh());
                            }


                        }
                    }
                });
    }*/

    /*private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                //   Log.d("print", "updateNews:241 " + newSdata);
                if (ListUtil.isNotEmpty(newSdata)) {
                    if (mTextSwitcherNews != null) {

                        mTextSwitcherNews.setText(newSdata.get(mNewsIndex).getName() + "  当前价格:" + newSdata.get(mNewsIndex).getTrade() + "  最高价格:" + newSdata.get(mNewsIndex).getHigh());
                    }
                }
            }
        }
    }*/

    private void getHomeGold() {
        OkGo.<String>get(Constant.URL_HOME_GOLD_URL)
                .tag(this)
                .params(Constant.PARAM_PAGE, 1)
                .params(Constant.PARAM_NUMBER, 3)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_SORT, Constant.STAY_SORT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            GoldlistEntity goldlistEntity = new Gson().fromJson(response.body(), GoldlistEntity.class);
                            if (goldlistEntity.getCode() == 1) {
                                //goldlistAdapter.setDatas(goldlistEntity.getData().subList(1, 3));
                                goldlistAdapter.setDatas(goldlistEntity.getData());

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }


    private void getHomeStock(int page, String sort) {
        OkGo.<String>get(Constant.URL_STOCK)
                .params(Constant.PARAM_PAGE, page)
                .params(Constant.PARAM_NUM, 6)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_NODE, Constant.STAY_SH_A)
                .params(Constant.PARAM_SORT, sort)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);

                            if (codeMsgEntity.getCode() == 1) {

                                StockEntity stockEntity = new Gson().fromJson(response.body(), StockEntity.class);
                                List<StockEntity.DataBean> data = stockEntity.getData();

                                stockAdapter.setDatas(data);
                            }


                        }
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });


    }


    private void getGuliao(String token, int page) {


        OkGo.<String>get(Constant.URL_GULIAOLIST_URL)
                .tag(this)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_PAGE, page)
                .params(Constant.PARAM_PAGE_SIZE, 1)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);


                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            if (codeMsgEntity.getCode() == 0) {
                                GuliaoEntity guliaoEntity = new Gson().fromJson(response.body(), GuliaoEntity.class);
                                //  Log.d("print", "onSuccess: " + guliaoEntity);
                                homeChatAdapter.setDatas(guliaoEntity.getData().getData());

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }


    /*private void getCalendar(String date, int count) {
        OkGo.<String>get(Constant.URL_CAIJINGRILI)
                .tag(this)
                .params(Constant.PARAM_LASTTIME, date)
                .params(Constant.PARAM_PAGESIZE, count)
                .cacheKey(Constant.URL_JINTOUWANG)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            FinanceEntity financeEntity = new Gson().fromJson(response.body(), FinanceEntity.class);
                            FinanceEntity.DataBean.NewsBean news = financeEntity.getData().getNews();
                            if (news != null) {
                                List<FinanceEntity.DataBean.NewsBean.NewsDataBean> newsData = news.getNewsData();
                                homeCalendarAdapter.setDatas(newsData);
                            } else {
                                showToast("当日无数据");
                            }


                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");

                    }
                });
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_login:
            case R.id.img_head:
                if (isLogin()) {
                    PersonActivity.enter(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;





            case R.id.text_chat:
                IntentActivity.enter(getActivity(), Constant.FORUM);

                break;
            case R.id.layout_search:
            case R.id.img_kefu:
                IntentActivity.enter(getActivity(), Constant.SEARCH);

               /* if (isLogin()) {
                    WebActivity.openZhiChiService(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }*/
                break;
            case R.id.text_message:
                if (isLogin()) {
                    UserActivity.enter(getActivity(), Constant.USER_MYMEAAAGE);

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;













            case R.id.text_ketang:

                IntentActivity.enter(getActivity(), Constant.LEARNCLASS);

                break;


            case R.id.text_gongju:
                IntentActivity.enter(getActivity(), Constant.SKILLALL);

                break;

            case R.id.text_more2:
                IntentActivity.enter(getActivity(), Constant.STOCKSLIDE, Constant.STAY_CHANGEPERCENT, "涨跌幅");

                break;

            case R.id.text_shipin:
                IntentActivity.enter(getActivity(), Constant.FEEDBACK);

                break;

            case R.id.layout_service:
                if (isLogin()) {
                    WebActivity.openZhiChiService(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;





            case R.id.img_message:
                OUserActivity.enter(getActivity(), OConstant.O_MESSAGE);


                break;


        }
    }


    public static class BannerStayViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        private TextView text_title;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_livebanner_layout, null);
            mImageView = view.findViewById(R.id.img_banner);
            text_title = view.findViewById(R.id.text_title);
            return view;
        }

        @Override
        public void onBind(final Context context, int position, final Integer data) {

            mImageView.setImageResource(data);

            // 数据绑定
          /*  Glide.with(context)
                    .load(data)
                    .asBitmap()
                    .centerCrop()
                    .into(mImageView);*/


        }
    }


    public static class BannerViewHolder implements MZViewHolder<BannerEntity.DataBean> {
        private ImageView mImageView;
        private TextView text_title;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_livebanner_layout, null);
            mImageView = view.findViewById(R.id.img_banner);
            text_title = view.findViewById(R.id.text_title);
            return view;
        }

        @Override
        public void onBind(final Context context, int position, final BannerEntity.DataBean data) {


            // 数据绑定
            Glide.with(context)
                    .load(data.getImage())
                    .asBitmap()
                    .centerCrop()
                    .into(mImageView);
            text_title.setText(data.getTitle());

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsDetailActivity.enter(context, "BANNER", data);

                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(Integer integer) {
        if (integer == Constant.ONRESUME_LOGIN) {
            onResume();

        } else if (integer == Constant.ONRESUME_PERSON) {
            getUserInfo();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner_article != null) {
            banner_article.stopAutoPlay();
        }
        EventBus.getDefault().unregister(this);

        cancelTimer();
    }


    public String dateToStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                if (ListUtil.isNotEmpty(newSdata)) {
                    mTextSwitcherNews.setText(newSdata.get(mNewsIndex).substring(23, newSdata.get(mNewsIndex).length() - 29));
                }
            }
        }
    }

    private void getNews() {

        OkGo.<String>get(Constant.URL_NEWS_HOURS)
                .tag(this)
                .params(Constant.PARAM_MAXID, 0)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            // Log.d("print", "onSuccess:971:    " + response.body());
                            OHoursEntity oHoursEntity = new Gson().fromJson(response.body(), OHoursEntity.class);
                            newSdata = oHoursEntity.getNewsList();

                            mTextSwitcherNews.setText(newSdata.get(0).substring(23, newSdata.get(0).length() - 29));

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        // showToast("获取失败,请检查网络");
                    }
                });


    }

    private String type = "1";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showItemPopWindow() {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_report_pop, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        TextView text_laji = view.findViewById(R.id.text_laji);
        TextView text_bushi = view.findViewById(R.id.text_bushi);
        TextView text_ruma = view.findViewById(R.id.text_ruma);
        TextView text_weifa = view.findViewById(R.id.text_weifa);
        EditText edit_content = view.findViewById(R.id.edit_content);
        TextView text_count = view.findViewById(R.id.text_count);

        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_count.setText(s.length() + "/200字已输入");
                if (s.length() == 200) {
                    Toast.makeText(getContext(), "只能输入这么多", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        text_laji.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_laji.setTextColor(getResources().getColor(R.color.white));
                text_bushi.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_ruma.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_weifa.setTextColor(getResources().getColor(R.color.text_maincolor));
                type = "1";
                return false;
            }
        });

        text_bushi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_laji.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_bushi.setTextColor(getResources().getColor(R.color.white));
                text_ruma.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_weifa.setTextColor(getResources().getColor(R.color.text_maincolor));
                type = "2";
                return false;
            }
        });
        text_ruma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_laji.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_bushi.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_ruma.setTextColor(getResources().getColor(R.color.white));
                text_weifa.setTextColor(getResources().getColor(R.color.text_maincolor));
                type = "4";
                return false;
            }
        });
        text_weifa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_laji.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_bushi.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_ruma.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_weifa.setTextColor(getResources().getColor(R.color.white));
                type = "5";
                return false;
            }
        });
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edit_content.getText().toString();
                if (!s.equals("")) {
                    postReport(id, type, s);
                    closePopupWindow(popupWindow);
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(getActivity(), "请输入反馈内容", Toast.LENGTH_SHORT).show();
                }


            }
        });


        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopupWindow(popupWindow);

                popupWindow.dismiss();


            }
        });
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);


        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }

    private void closePopupWindow(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
            WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
            params.alpha = 1f;
            getActivity().getWindow().setAttributes(params);
        }
    }

    private void postReport(int id, String type, String content) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
        } else {

            OkGo.<String>post(Constant.URL_REPORT)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_SHARE_POST_ID, id)
                    .params(Constant.PARAM_TYPE, type)
                    .params(Constant.PARAM_NOTE, content)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            dismissProgressDialog();
                            if (!TextUtils.isEmpty(response.body())) {
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                if (codeMsgEntity.getCode() == 1) {
                                    Toast.makeText(getActivity(), "反馈成功,我们将尽快告知您", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

        }
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

                            List<OHotEntity.NewsListBean> newsList = oHotEntity.getNewsList().subList(0, 3);
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


    private void getUserInfo() {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);

        String token = loginEntity.getData().getToken();
        OkGo.<String>get(Constant.USER_INFO_URL)
                .tag(this)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            //Log.d("print", "onSuccess:152 " + codeMsgEntity);
                            if (codeMsgEntity.getCode() == 1) {
                                UserInfoEntity userInfoEntity = new Gson().fromJson(response.body(), UserInfoEntity.class);
                                //   Log.d("print", "onSuccess:用户信息 " + userInfoEntity);

                                String user_nickname = userInfoEntity.getData().getUser_nickname();
                                String mobile = userInfoEntity.getData().getMobile();

                                Glide.with(getActivity())
                                        .load(Constant.USER_AVATER_URL + userInfoEntity.getData().getAvatar())
                                        .asBitmap()
                                        .error(R.mipmap.user_icon)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop()
                                        .into(img_head);

                              /*  String signature = userInfoEntity.getData().getSignature();
                                if (signature.equals("")) {
                                    text_sign.setText("开开心心每一天~");
                                } else {
                                    text_sign.setText(signature);
                                }*/

                                SPUtils.putData(UserConfig.USER, userInfoEntity);
                            } else {
                                SPUtils.remove(UserConfig.LOGIN_USER);
                                SPUtils.remove(UserConfig.USER);
                                Toast.makeText(getActivity(), "用户登录已失效", Toast.LENGTH_SHORT).show();
                                UserActivity.enter(getActivity(), Constant.LOGIN);
                                getActivity().finish();
                            }


                        }
                    }
                });
    }


}
