package com.ltqh.qh.fragment.find;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.NewsDetailActivity;
import com.ltqh.qh.activity.PersonActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.adapter.AlertsAdapter;
import com.ltqh.qh.adapter.GoldlistAdapter;
import com.ltqh.qh.adapter.MyPagerAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.BannerEntity;
import com.ltqh.qh.entity.GoldShowEntity;
import com.ltqh.qh.entity.GoldlistEntity;
import com.ltqh.qh.fragment.forum.ForumRadioFragment;
import com.ltqh.qh.fragment.news.AlertsFragment;
import com.ltqh.qh.fragment.news.LiandeFragment;
import com.ltqh.qh.fragment.news.LiveFragment;
import com.ltqh.qh.operation.activity.ONewsDetailActivity;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.utils.ListUtil;
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

public class RecommendFragment extends BaseFragment implements View.OnClickListener {

    private final static int PERIOD = 5 * 1000; // 5s



    @BindView(R.id.banner)
    MZBannerView banner;





    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private GoldlistAdapter goldlistAdapter;
    private int mNewsIndex;

    @BindView(R.id.ts_notice)
    TextSwitcher mTextSwitcherNews;
    private List<GoldShowEntity.DataBean> newSdata;

    @BindView(R.id.recyclerview_attention)
    RecyclerView recyclerview_attention;
    private AlertsAdapter alertsAdapter;




    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_recommend;
    }


    @Override
    protected void initView(View view) {

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        if (!EventBus.getDefault().isRegistered(this)){

            EventBus.getDefault().register(this);
        }
        goldlistAdapter = new GoldlistAdapter(getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(goldlistAdapter);

        alertsAdapter = new AlertsAdapter(getActivity());

        recyclerview_attention.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_attention.setAdapter(alertsAdapter);
        alertsAdapter.setOnItemClick(new AlertsAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OHotEntity.NewsListBean newsListBean) {
                ONewsDetailActivity.enter(getActivity(), "ALERTS", newsListBean);

            }
        });
        view.findViewById(R.id.text_more).setOnClickListener(this);
        view.findViewById(R.id.text_ketang).setOnClickListener(this);
        view.findViewById(R.id.text_quanzi).setOnClickListener(this);
        view.findViewById(R.id.text_gongju).setOnClickListener(this);
        view.findViewById(R.id.text_shipin).setOnClickListener(this);
        view.findViewById(R.id.img_banner).setOnClickListener(this);

        mTextSwitcherNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setMaxLines(2);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_maincolor));
                textView.setTextSize(15);
                return textView;
            }
        });





        startScheduleJob(mHandler, PERIOD, PERIOD);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            getHomeGold();
          //  updateNews();


        }
    };

    private void initViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(new ForumRadioFragment());
        myPagerAdapter.addFragment(new LiveFragment());
        myPagerAdapter.addFragment(new LiandeFragment());
        myPagerAdapter.addFragment(new AlertsFragment());
        viewPager.setAdapter(myPagerAdapter);
    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
        getBanner();

        getHomeGold();

        getGold();
        getNewsData();

    }

    private void getGold() {
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
    }

    private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
              //  Log.d("print", "updateNews:241 " + newSdata);
                if (ListUtil.isNotEmpty(newSdata)) {
                    if (mTextSwitcherNews != null) {

                        mTextSwitcherNews.setText(newSdata.get(mNewsIndex).getName() + "当前价格:" + newSdata.get(mNewsIndex).getTrade() + "最高价格:" + newSdata.get(mNewsIndex).getHigh());
                    }
                }
            }
        }
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

            case R.id.img_banner:
                IntentActivity.enter(getActivity(),Constant.LEARNCLASS);
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
        }
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
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Log.d("print", "onSuccess:201: " + response);
                        dismissProgressDialog();
                        if (!response.body().startsWith("{")) {
                            OHotEntity oHotEntity = new Gson().fromJson(response.body(), OHotEntity.class);

                            List<OHotEntity.NewsListBean> newsList = oHotEntity.getNewsList().subList(0, 3);
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
