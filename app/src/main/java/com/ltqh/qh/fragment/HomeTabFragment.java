package com.ltqh.qh.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.NewsDetailActivity;
import com.ltqh.qh.activity.PersonActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.adapter.GoldlistAdapter;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.BannerEntity;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.EastMoneyEntity;
import com.ltqh.qh.entity.GoldShowEntity;
import com.ltqh.qh.entity.GoldlistEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.UserInfoEntity;
import com.ltqh.qh.fragment.news.AlertsFragment;
import com.ltqh.qh.fragment.news.DubiFragment;
import com.ltqh.qh.fragment.news.LiveFragment;
import com.ltqh.qh.utils.ListUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.CircleImageView;
import com.ltqh.qh.view.XCRoundRectImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.utils.Util.dateToStamp;

public class HomeTabFragment extends BaseFragment implements View.OnClickListener {

    private final static int PERIOD = 5 * 1000; // 5s

    @BindView(R.id.text_login)
    TextView text_login;

    @BindView(R.id.banner)
    MZBannerView banner;

    /*@BindView(R.id.home_tab)
    EnhanceTabLayout home_tab;*/

    @BindView(R.id.home_tab)
    TabLayout home_tab;


    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private GoldlistAdapter goldlistAdapter;
    private int mNewsIndex;

    @BindView(R.id.ts_notice)
    TextSwitcher mTextSwitcherNews;
   // private List<GoldShowEntity.DataBean> newSdata;
    private List<String> newSdata;

    @BindView(R.id.img_head)
    CircleImageView img_head;

    @Override
    public void onResume() {
        super.onResume();
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        UserInfoEntity userInfoEntity = SPUtils.getData(UserConfig.USER, UserInfoEntity.class);

        if (userInfoEntity != null) {

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
                text_login.setText("用户");
            } else {
                text_login.setText(userInfoEntity.getData().getUser_nickname());
            }

        } else {
            text_login.setText("登录");
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

                String user_nickname = loginEntity.getData().getUser().getUser_nickname();
                if (user_nickname.equals("")) {
                    text_login.setText("用户");
                } else {
                    text_login.setText(loginEntity.getData().getUser().getUser_nickname());

                }
            }
        }


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_hometab;
    }

    private String Titles[] = new String[]{"快讯", "热点", "要闻"};
    private int[] pics = new int[]{R.mipmap.tab_img1, R.mipmap.tab_img2, R.mipmap.tab_img3};

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
        text_login.setOnClickListener(this);
        goldlistAdapter = new GoldlistAdapter(getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(goldlistAdapter);
        view.findViewById(R.id.text_more).setOnClickListener(this);
        view.findViewById(R.id.text_ketang).setOnClickListener(this);
        view.findViewById(R.id.text_quanzi).setOnClickListener(this);
        view.findViewById(R.id.text_gongju).setOnClickListener(this);
        view.findViewById(R.id.text_shipin).setOnClickListener(this);
        view.findViewById(R.id.img_head).setOnClickListener(this);

        mTextSwitcherNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                textView.setTextSize(15);
                return textView;
            }
        });


        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

      /*  for (int i = 0; i < Titles.length; i++) {
            home_tab.addTab(home_tab.newTab());
            home_tab.getTabAt(i).setText(Titles[i]);
        }*/


        initViewPager(viewPager);
        //  viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(home_tab.getTabLayout()));
        viewPager.setOffscreenPageLimit(3);
        home_tab.setupWithViewPager(viewPager);


        startScheduleJob(mHandler, PERIOD, PERIOD);

    }

    private View makeTabView(int position) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.item_tab_layout, null);
        TextView textView = tabView.findViewById(R.id.text_title);
        ImageView imageView = tabView.findViewById(R.id.img_icon);
        textView.setText(Titles[position]);
        imageView.setImageResource(pics[position]);

        return tabView;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

          //  updateNews();


        }
    };

    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        // myPagerAdapter.addFragment(new ForumRadioFragment());
        myPagerAdapter.addFragment(new LiveFragment(), "快讯");
        myPagerAdapter.addFragment(new DubiFragment(), "热点");
        myPagerAdapter.addFragment(new AlertsFragment(), "要闻");
        viewPager.setAdapter(myPagerAdapter);
    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
        getBanner();

        getHomeGold();

        getNews();
       // getGold();
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
                            //  mTextSwitcherNews.setText(newSdata.get(0).getName() + "当前价格:" + newSdata.get(0).getTrade() + "最高价格:" + newSdata.get(0).getHigh());


                        }
                    }
                });
    }*/

   /* private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                Log.d("print", "updateNews:241 " + newSdata);
                if (ListUtil.isNotEmpty(newSdata)) {
                    if (mTextSwitcherNews != null) {

                        mTextSwitcherNews.setText(newSdata.get(mNewsIndex).getName() + "当前价格:" + newSdata.get(mNewsIndex).getTrade() + "最高价格:" + newSdata.get(mNewsIndex).getHigh());
                    }
                }
            }
        }
    }*/
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
        OkGo.<String>get(Constant.URL_HOUR)
                .tag(this)
                .params(Constant.PARAM_LASTTIME, dateToStamp())
                .params(Constant.PARAM_PAGESIZE, 50)
                .cacheKey(Constant.URL_JINTOUWANG)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {

                            EastMoneyEntity eastMoneyEntity = new Gson().fromJson(response.body(), EastMoneyEntity.class);
                            newSdata = eastMoneyEntity.getData();
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

    private void getHomeGold() {
        OkGo.<String>get(Constant.URL_HOME_GOLD_URL)
                .tag(this)
                .params(Constant.PARAM_PAGE, 1)
                .params(Constant.PARAM_NUMBER, 10)
                .params(Constant.PARAM_ASC, 0)
                .params(Constant.PARAM_SORT, Constant.STAY_SORT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            GoldlistEntity goldlistEntity = new Gson().fromJson(response.body(), GoldlistEntity.class);
                            if (goldlistEntity.getCode() == 1) {

                                goldlistAdapter.setDatas(goldlistEntity.getData());
                            }
                        }
                    }
                });

    }

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

            case R.id.text_more:

                IntentActivity.enter(getActivity(), Constant.STOCKMARKET);


                break;

            case R.id.text_ketang:
                IntentActivity.enter(getActivity(), Constant.LEARNCLASS);

                break;
            case R.id.text_quanzi:
                IntentActivity.enter(getActivity(), Constant.FORUM);

                break;
            case R.id.text_gongju:
                IntentActivity.enter(getActivity(), Constant.SKILLALL);

                break;
            case R.id.text_shipin:
                IntentActivity.enter(getActivity(), Constant.VIDEO);

                break;
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
                            upBanner(data);
                        }
                    }
                });
    }


    private void upBanner(List<BannerEntity.DataBean> data) {
        banner.setPages(data, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

    }

    public static class BannerViewHolder implements MZViewHolder<BannerEntity.DataBean> {
        private XCRoundRectImageView mImageView;
        private TextView text_title;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layout, null);
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
                    .error(R.mipmap.home_banner)
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
        }else if (integer==Constant.ONRESUME_PERSON){
            getUserInfo();
        }
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
    @Override
    public void onPause() {
        super.onPause();
        banner.pause();//暂停轮播
        EventBus.getDefault().unregister(this);
        cancelTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
