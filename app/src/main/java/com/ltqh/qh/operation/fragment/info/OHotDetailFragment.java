package com.ltqh.qh.operation.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.AlertsDetailEntity;
import com.ltqh.qh.entity.AlertsEntity;
import com.ltqh.qh.operation.adapter.OReportAdapter;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

public class OHotDetailFragment extends BaseFragment {

    private TextView text_title, text_time;
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OHotEntity.NewsListBean alerts;
    private ImageView img_banner;
    private String imgResize;


    public static OHotDetailFragment newInstance(OHotEntity.NewsListBean alerts) {
        OHotDetailFragment fragment = new OHotDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.KEY_ARTICLE, alerts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alerts = (OHotEntity.NewsListBean) getArguments().getSerializable(Constant.KEY_ARTICLE);
            initData(String.valueOf(alerts.getId()));

        }
    }

    private void initData(String id) {
        OkGo.<String>get(OConstant.URL_NEWS_HOT_DETAIL).tag(this)
                .params(Constant.PARAM_ID, id)
                .cacheKey(Constant.URL_ALERTS)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        swipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response.body())) {
                            AlertsDetailEntity alertsDetailEntity = new Gson().fromJson(response.body(), AlertsDetailEntity.class);
                            AlertsDetailEntity.NewsBean news = alertsDetailEntity.getNews();
                            if (news != null) {

                                text_title.setText(news.getTitle());
                                text_time.setText(news.getDate());
                               /* if (news.getTitleBg().equals("")) {
                                    img_banner.setVisibility(View.GONE);
                                } else {
                                    Glide.with(getActivity()).load(news.getTitleBg()).centerCrop().into(img_banner);
                                }*/
                                if (isAdded()) {
                                    String string = SPUtils.getString(OUserConfig.P_NIGHT);
                                    if (string.equals("")) {
                                        webView.setBackgroundColor(getResources().getColor(R.color.o_bar_white));
                                        webView.loadDataWithBaseURL(null, news.getContent(), "text/html", "utf-8", null);
                                    } else {
                                        if (string.equals("night")) {
                                            webView.setBackgroundColor(getResources().getColor(R.color.o_bar_white_night));
                                            String CSS_STYLE = "<style>* {font-size:16px;line-height:20px;}p {color:#B2B6C1;}</style>";
                                            webView.loadDataWithBaseURL(null, CSS_STYLE + news.getContent(), "text/html", "utf-8", null);

                                        } else if (string.equals("day")) {

                                            webView.setBackgroundColor(getResources().getColor(R.color.o_bar_white));
                                            webView.loadDataWithBaseURL(null, news.getContent(), "text/html", "utf-8", null);
                                        }
                                    }
                                }
                            }
                        }
                    }


                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");

                        swipeRefreshLayout.setRefreshing(false);
                    }


                });
    }


    @Override
    protected void intPresenter() {

    }

    protected void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);
        text_title = (TextView) view.findViewById(R.id.text_title);
        text_time = (TextView) view.findViewById(R.id.text_date);
        webView = (WebView) view.findViewById(R.id.webView);
        img_banner = (ImageView) view.findViewById(R.id.img_banner);

        imgResize = "\tfor (var i = 0;i <document.images.length;i++){\n" +
                "\t\tmImg = document.images[i];\n" +
                "\t\tvar screenWidth = " + ViewUtils.getViewWidth(view) + ";\n" +
                "\t\tif (mImg != screenWidth) {\n" +
                "\t\t\tmImg.width = screenWidth;\n" +
                "\t\t};\n" +
                "\t}";


     /*   View headView = LayoutInflater.from(getContext()).inflate(R.layout.item_head_webview, null);
        text_title = (TextView) headView.findViewById(R.id.text_title);
        text_time = (TextView) headView.findViewById(R.id.text_data);

        webView.addView(headView);*/

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(String.valueOf(alerts.getId()));

            }
        });

        swipeRefreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
                return webView.getScrollY() > 0;
            }
        });


        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_news_detail;
    }


    @Override
    protected void initData() {

    }


}
