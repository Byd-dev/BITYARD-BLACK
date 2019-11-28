package com.ltqh.qh.fragment.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.NewsDetailActivity;
import com.ltqh.qh.activity.WebActivity;
import com.ltqh.qh.adapter.EastMoneyAdapter;
import com.ltqh.qh.adapter.StockNewsAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.BannerEntity;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.EastMoneyEntity;
import com.ltqh.qh.entity.StocknewsEntity;
import com.ltqh.qh.fragment.HomeFragment;
import com.ltqh.qh.fragment.market.StockForeignFragment;
import com.ltqh.qh.fragment.market.StockTabLayoutFragment;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.view.XCRoundRectImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.stx.xhb.xbanner.XBanner;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class StockNewsFragment extends OBaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.banner)
    MZBannerView banner;

    private RecyclerView recyclerView;

    private StockNewsAdapter stockNewsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;

    private int count = 10;

    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;
    @BindView(R.id.radio_2)
    RadioButton radio_2;

    @BindView(R.id.radio_3)
    RadioButton radio_3;
    @BindView(R.id.radio_4)
    RadioButton radio_4;

    @BindView(R.id.radio_5)
    RadioButton radio_5;

    private String type = "5";


    @BindView(R.id.banner_article)
    XBanner banner_article;
    @Override
    protected void intPresenter() {

    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
        if (banner_article != null) {
            banner_article.startAutoPlay();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner_article != null) {
            banner_article.stopAutoPlay();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_stocknews;
    }


    @Override
    protected void onLazyLoad() {

    }
    private void getBanner2() {
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
            banner_article.setBannerData(R.layout.item_info_banner_layout, data);
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

       /* banner.setPages(data, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });*/

    }


    protected void initView(View view) {

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);


        initData(REFRESHTYPE, "5");

        recyclerView = (RecyclerView) view.findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        stockNewsAdapter = new StockNewsAdapter(getActivity());
        recyclerView.setAdapter(stockNewsAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(REFRESHTYPE, type);
            }
        });

        /*不分页*/
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == stockNewsAdapter.getItemCount() - 1) {
                    stockNewsAdapter.startLoad();
                    count = count + 10;
                    initData(LOADTYPE, count);

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });*/

        stockNewsAdapter.setOnItemClick(new StockNewsAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(StocknewsEntity.DataBean dataBean) {
                NewsDetailActivity.enter(getContext(), "STOCKNEWSDATA", dataBean);

            }
        });


    }


    @Override
    protected void initData() {
        getBanner();
        getBanner2();

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
                return new HomeFragment.BannerViewHolder();
            }
        });

    }

    private void initData(final String type, String value) {

        OkGo.<String>get(Constant.URL_STOCKNEWS)
                .tag(this)
                .params(Constant.PARAM_TYPE, value)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (type.equals(REFRESHTYPE)) {
                            swipeRefreshLayout.setRefreshing(true);
                        } else if (type.equals(LOADTYPE)) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        swipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response.body())) {

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            if (codeMsgEntity.getCode() == 1) {
                                StocknewsEntity stocknewsEntity = new Gson().fromJson(response.body(), StocknewsEntity.class);
                                List<StocknewsEntity.DataBean> data = stocknewsEntity.getData();
                                stockNewsAdapter.setDatas(data);
                            }


                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        swipeRefreshLayout.setRefreshing(false);
                        stockNewsAdapter.stopLoad();

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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                initData(REFRESHTYPE, "4");
                type = "4";

                break;

            case R.id.radio_1:
                initData(REFRESHTYPE, "1");
                type = "1";

                break;
            case R.id.radio_2:

                initData(REFRESHTYPE, "2");
                type = "2";

                break;

            case R.id.radio_3:
                initData(REFRESHTYPE, "3");
                type = "3";
                break;
            case R.id.radio_4:

                initData(REFRESHTYPE, "5");
                type = "5";
                break;

            case R.id.radio_5:
                initData(REFRESHTYPE, "6");
                type = "6";
                break;

        }
    }
}
