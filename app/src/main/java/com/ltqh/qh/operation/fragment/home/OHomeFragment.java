package com.ltqh.qh.operation.fragment.home;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.activity.OImmersiveActivity;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.activity.ONewsDetailActivity;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.adapter.OHomeMarketAdapter;
import com.ltqh.qh.operation.adapter.OHotAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.entity.OBannerEntity;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OCheckHisoryEntity;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.operation.entity.OMarketEntity;
import com.ltqh.qh.operation.entity.OReportEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.utils.ListUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.Util;
import com.ltqh.qh.view.AlphaChangeListener;
import com.ltqh.qh.view.Direction;
import com.ltqh.qh.view.GuideView;
import com.ltqh.qh.view.MyScrollView;
import com.ltqh.qh.view.NumberRunningTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.PARAM_CALLBACK;
import static com.ltqh.qh.operation.base.OConstant.PARAM_CODE;
import static com.ltqh.qh.operation.base.OConstant.PARAM_SIMPLE;

public class OHomeFragment extends OBaseFragment implements View.OnClickListener {

    private final static int PERIOD = 5 * 1000; // 5s

    @BindView(R.id.layout_view)
    RelativeLayout layout_view;

    @BindView(R.id.layout_moni)
    LinearLayout layout_moni;

    @BindView(R.id.layout_shipan)
    LinearLayout layout_shipan;


    @BindView(R.id.banner)
    XBanner banner;

    @BindView(R.id.scrollView)
    MyScrollView myScrollView;

    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.layout_bar2)
    RelativeLayout layout_bar2;

    @BindView(R.id.img_check)
    ImageView img_qiandao;


    @BindView(R.id.ts_news)
    TextSwitcher mTextSwitcherNews;
    private List<OReportEntity.NoticesBean> newSdata;
    private int mNewsIndex;


    private OHotAdapter oHotAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.layout_islogin)
    RelativeLayout layoutLogin;

    @BindView(R.id.recyclerview_market)
    RecyclerView recyclerView_market;

    private OHomeMarketAdapter oHomeMarketAdapter;

    private List<String> dataList;
    private GuideView mGVOne, mGVTwo, mGVThree;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
            layoutLogin.setVisibility(View.GONE);
        } else {
            layoutLogin.setVisibility(View.VISIBLE);
        }


    }

    private void showGuideViews() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_step_layout, null);
        View view_check = LayoutInflater.from(getActivity()).inflate(R.layout.o_step_check_layout, null);


        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.o_step_two_layout, null);
        //引导页第一步
        mGVOne = new GuideView.Builder(getActivity())
                .setTargetView(R.id.img_check2)
                .setHintView(view_check)
                .setHintViewDirection(Direction.BOTTOM_ALIGN_LEFT)
                .setHintViewMarginTop(-30)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVOne.hide();
                        mGVTwo.show();
                    }
                }).create();
        mGVOne.show();

        //引导页第一步
        mGVTwo = new GuideView.Builder(getActivity())
                .setTargetView(R.id.layout_moni)
                .setHintView(view)
                .setHintViewDirection(Direction.BOTTOM_ALIGN_LEFT)
                .setHintViewMarginTop(-30)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVTwo.hide();
                        mGVThree.show();
                    }
                }).create();
        mGVOne.show();


        mGVThree = new GuideView.Builder(getActivity())
                .setTargetView(R.id.layout_shipan)
                .setHintView(view2)
                .setHintViewDirection(Direction.BOTTOM_ALIGN_RIGHT)
                .setHintViewMarginTop(-30)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVThree.hide();
                    }
                }).create();


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateNews();
            getQuote();


        }
    };

    private void getQuote() {

        List<String> dataList = QuoteProxy.getInstance().getForeigndataList();
        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (dataList == null) {

            postQuote();

        } else {

            oHomeMarketAdapter.setDatas(null, dataList.subList(0, 3));
            oHomeMarketAdapter.setForeignDatas(OUserConfig.P_FOREIGN, oApiEntity.getForeignCommds());
        }
    }

    private List<String> foreigndataList;
    int count = 0;

    //行情的请求
    public void postQuote() {


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        if (oApiEntity == null) {
            getApi();
        } else {

            String quoteDomain = oApiEntity.getQuoteDomain();


            try {
                String urlList = AES.HexDecrypt(quoteDomain.getBytes(), "1111111122222222");
                String[] split = urlList.split(";");

                int length = split.length;
                if (count < length) {
                    String indexUrl = split[count] + "/quote.jsp";
                    getQuote(indexUrl, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void getQuote(String indexUrl, int length) {
        String string = SPUtils.getString(OUserConfig.ALLDEX);

        if (string != null) {
            String code = string.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");
            OkGo.<String>post(indexUrl)
                    .tag(this)
                    .params(PARAM_CALLBACK, "?")
                    .params(PARAM_CODE, code)
                    .params("_", Calendar.getInstance().getTimeInMillis())
                    .params(PARAM_SIMPLE, true)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            //showProgressDialog();

                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            //dismissProgressDialog();
                            if (!TextUtils.isEmpty(response.body())) {
                                String responese = Util.jsonReplace(response.body());
                                OMarketEntity oMarketEntity = new Gson().fromJson(responese, OMarketEntity.class);
                                String data = oMarketEntity.getData();
                                if (data == null) {
                                    return;
                                }
                                if (data != null) {
                                    String[] split = data.split(";");
                                    dataList = new ArrayList<>();
                                    for (String a : split) {
                                        dataList.add(a);
                                    }
                                }
                                foreigndataList = new ArrayList<>();
                                List<String> foreignList = QuoteProxy.getInstance().getForeignList();
                                if (foreignList != null) {
                                    for (String quote : dataList) {
                                        String[] split = quote.split(",");
                                        if (foreignList.toString().contains(split[0].replaceAll("[^a-z^A-Z]", ""))) {
                                            foreigndataList.add(quote);
                                        }
                                    }
                                    QuoteProxy.getInstance().setForeigndataList(foreigndataList);
                                }
                                OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();

                                if (oHomeMarketAdapter != null & oApiEntity != null & dataList != null) {

                                    oHomeMarketAdapter.setDatas(null, dataList.subList(0, 3));
                                    oHomeMarketAdapter.setForeignDatas(OUserConfig.P_FOREIGN, oApiEntity.getForeignCommds());

                                }

                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            dismissProgressDialog();
                            Log.d("print", "onError:433:   " + count + "-----" + length);
                            if (count >= length - 1) {
                                getApi();
                                count = 0;
                            }
                            count++;

                        }
                    });

        }
    }

    private void getApi() {
        OkGo.<String>get(OConstant.URL_API)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            OApiEntity oApiEntity = new Gson().fromJson(response.body(), OApiEntity.class);
                            QuoteProxy.getInstance().setoApiEntity(oApiEntity);
                            Log.d("print", "onSuccess:458:   " + oApiEntity);

                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });

    }

    @Override
    protected void onLazyLoad() {
        String string = SPUtils.getString(OUserConfig.O_FIRST_HOME);
        if (string.equals("")) {

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showGuideViews();
                }
            }, 100);
            SPUtils.putString(OUserConfig.O_FIRST_HOME, "HOME");
        }


    }

    @Override
    protected void initView(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.findViewById(R.id.img_liuyan).setOnClickListener(this);
        view.findViewById(R.id.img_liuyan2).setOnClickListener(this);
        view.findViewById(R.id.img_kefu).setOnClickListener(this);
        view.findViewById(R.id.img_kefu2).setOnClickListener(this);
        view.findViewById(R.id.text_finance).setOnClickListener(this);
        view.findViewById(R.id.text_guide).setOnClickListener(this);
        view.findViewById(R.id.text_activity).setOnClickListener(this);

        view.findViewById(R.id.img_check).setOnClickListener(this);
        view.findViewById(R.id.img_check2).setOnClickListener(this);

        view.findViewById(R.id.layout_report).setOnClickListener(this);
        view.findViewById(R.id.layout_hot).setOnClickListener(this);

        view.findViewById(R.id.layout_search_one).setOnClickListener(this);
        view.findViewById(R.id.layout_search_two).setOnClickListener(this);


        mTextSwitcherNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.o_text_5c5e76));
                textView.setTextSize(15);
                //   textView.setSingleLine();
                return textView;
            }
        });


        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        int i = Util.dip2px(getContext(), 48);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight + i);

        layout_bar.setLayoutParams(params);
        layout_bar2.setLayoutParams(params);

        img_qiandao.setVisibility(View.VISIBLE);

        myScrollView.setAlphaChangeListener(new AlphaChangeListener() {
            @Override
            public void alphaChanging(float alpha) {
                layout_bar.setVisibility(View.VISIBLE);
                layout_bar.setAlpha(alpha);
                layout_bar2.setAlpha(1 - alpha);


            }
        });
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);

        //防止嵌套出现轻微卡顿的问题
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;

            }
        });
        oHotAdapter = new OHotAdapter(getActivity());
        recyclerView.setAdapter(oHotAdapter);

        //解决数据加载不完的问题
        recyclerView_market.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView_market.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView_market.setFocusable(false);


        //防止嵌套出现轻微卡顿的问题
        recyclerView_market.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;

            }
        });


        oHomeMarketAdapter = new OHomeMarketAdapter(getActivity());
        recyclerView_market.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView_market.setAdapter(oHomeMarketAdapter);


        oHomeMarketAdapter.setOnItemClick(new OHomeMarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {

                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", code);

            }
        });

        oHotAdapter.setOnItemClick(new OHotAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OHotEntity.NewsListBean newsListBean) {
                ONewsDetailActivity.enter(getActivity(), "ALERTS", newsListBean);
            }
        });


        view.findViewById(R.id.text_login).setOnClickListener(this);
        view.findViewById(R.id.layout_shipan).setOnClickListener(this);

        view.findViewById(R.id.layout_moni).setOnClickListener(this);
        startScheduleJob(mHandler, PERIOD, PERIOD);

    }


    /*private void getHours() {

        OkGo.<String>get(OConstant.URL_NEWS_HOURS)
                .tag(this)
                .params(OConstant.PARAM_MAXID, "0")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {

                            OHoursEntity oHoursEntity = new Gson().fromJson(response.body(), OHoursEntity.class);
                            newSdata = oHoursEntity.getNewsList();
                            if (newSdata.get(0).startsWith("0")) {
                                String[] split = newSdata.get(0).split("#");
                                mTextSwitcherNews.setText(DelTagsUtil.getTextFromHtml(split[3]));

                            }


                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");


                    }
                });
    }*/

    private void getReport() {


        OkGo.<String>get(OConstant.URL_NEWS_REPORT)
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
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
                            OReportEntity oReportEntity = new Gson().fromJson(response.body(), OReportEntity.class);
                            SPUtils.putData(OUserConfig.CACHE_REPORT, oReportEntity);
                            newSdata = oReportEntity.getNotices();


                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        dismissProgressDialog();

                    }
                });
    }

    private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                if (ListUtil.isNotEmpty(newSdata)) {
                    String title = newSdata.get(mNewsIndex).getTitle();
                    if (mTextSwitcherNews != null) {

                        mTextSwitcherNews.setText(title);
                    }

                }
            }
        }
    }

    private void getBanner() {

        OkGo.<String>get(OConstant.URL_BANNER)
                .tag(this)
                .params(OConstant.PARAM_ACTION, "carousel")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OBannerEntity oBannerEntity = new Gson().fromJson(response.body(), OBannerEntity.class);
                            upBanner(oBannerEntity.getCarousels());
                        }
                    }
                });
    }

    private void upBanner(List<OBannerEntity.CarouselsBean> data) {
        if (data == null) {
            return;
        }

        if (banner != null) {

            banner.setBannerData(R.layout.o_item_banner_layout, data);
            banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {

                    ImageView imageView = view.findViewById(R.id.img_banner);
                    Glide.with(getActivity()).load(data.get(position).getXBannerUrl()).asBitmap().into(imageView);
                }
            });

            banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    String key = data.get(position).getKey();
                    if (key.startsWith("#")) {
                        Toast.makeText(getActivity(), data.get(position).getMcname(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (key.contains("activity")) {
                            OWebActivity.openUrlNotitle(getActivity(), OConstant.PANDANEWS_HOST + key, data.get(position).getMcname());
                        } else if (key.contains("trade")) {
                            List<String> dataList = QuoteProxy.getInstance().getDataList();

                            if (dataList == null) {
                                return;
                            }

                            String s = dataList.get(1);

                            OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", s);


                        }
                    }
                }
            });
        }
    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

        postQuote();

        getBanner();

        //getHours();
        getReport();
        OHotEntity data = SPUtils.getData(OUserConfig.CACHE_HOT, OHotEntity.class);
        if (data != null) {
            if (data.getNewsList().size() != 0) {
                oHotAdapter.setDatas(data.getNewsList().subList(0, 4));
            } else {
                getHotNews();

            }
        } else {
            getHotNews();
        }

    }

    private void getHotNews() {
        OkGo.<String>get(OConstant.URL_NEWS_HOT)
                .tag(this)
                .params(Constant.PARAM_TYPE, "0")
                .cacheKey(Constant.URL_ALERTS)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OHotEntity oHotEntity = new Gson().fromJson(response.body(), OHotEntity.class);
                            SPUtils.putData(OUserConfig.CACHE_HOT, oHotEntity);
                            if (oHotEntity.getNewsList().size() != 0) {
                                oHotAdapter.setDatas(oHotEntity.getNewsList().subList(0, 4));
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        showToast("获取失败,请检查网络");
                    }
                });

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.text_login:
                OUserActivity.enter(getActivity(), OConstant.LOGIN);
                break;

            case R.id.layout_moni:

                List<String> dataList = QuoteProxy.getInstance().getDataList();

                if (dataList == null) {
                    return;
                }

                String s = dataList.get(0);

                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "2", s);

                break;

            case R.id.layout_shipan:
                List<String> dataList2 = QuoteProxy.getInstance().getDataList();

                if (dataList2 == null) {
                    return;
                }

                String s2 = dataList2.get(0);

                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", s2);


                break;

            case R.id.img_liuyan:
            case R.id.img_liuyan2:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OUserActivity.enter(getActivity(), OConstant.O_MESSAGE);


                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }
                break;

            case R.id.img_kefu:
            case R.id.img_kefu2:


                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                    OBaseMineEntity oBaseMineEntity = SPUtils.getData(OUserConfig.BASEMINE, OBaseMineEntity.class);
                    int id = oBaseMineEntity.getUser().getId();
                    String user_nickname = oBaseMineEntity.getUser().getUsername();
                    String name = oBaseMineEntity.getInfo().getName();

                    long l = System.currentTimeMillis();
                    String content = id + name + "1" + user_nickname + "zy" + l + "henDid-corbop-6jemqa";
                    String s1 = AES.md5(content).toUpperCase();
                    String s3 = OConstant.URL_SERVICE + s1;

                    OWebActivity.openUrlNotitle(getActivity(), s3, "在线客服");

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }


                break;

            case R.id.text_finance:
                OIntentActivity.enter(getActivity(), OConstant.O_FINANCE);
                break;

            case R.id.text_guide:
                OIntentActivity.enter(getActivity(), OConstant.O_GUIDEBOOK);

                break;
            case R.id.text_activity:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OImmersiveActivity.enter(getActivity(), OConstant.O_ACTIVITY);


                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }

                break;
            case R.id.img_check:
            case R.id.img_check2:


                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    getTask();
                    getCheck();
                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }

                break;

            case R.id.layout_report:
                OIntentActivity.enter(getActivity(), OConstant.O_REPORT);
                break;
            case R.id.layout_hot:
                OIntentActivity.enter(getActivity(), OConstant.O_HOT);
                break;

            case R.id.layout_search_one:
            case R.id.layout_search_two:
                OIntentActivity.enter(getActivity(), OConstant.O_SEARCH);
                break;

        }
    }

    private void getTask() {

        OkGo.<String>get(OConstant.URL_TASK_CHECK_HISTORY)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OCheckHisoryEntity oCheckHisoryEntity = new Gson().fromJson(response.body(), OCheckHisoryEntity.class);

                            if (oCheckHisoryEntity.isSuccess() == true & isAdded() == true) {

                                OCheckHisoryEntity.DataBean data = oCheckHisoryEntity.getData();
                                getBond(data);
                                Log.d("print", "onSuccess:759:   " + data);
                            }
                        }
                    }
                });
    }


    private void getBond(OCheckHisoryEntity.DataBean data) {
        OkGo.<String>get(OConstant.URL_TASK_CHECK_IN_HISTORY)
                .execute(new StringCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:702:   " + response.body());
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            if (oCodeMsgEntity.isSuccess() == true) {
                                showCheckPopWindow(data);
                            } else {
                                // Toast.makeText(getActivity(),oCodeMsgEntity.getErrorMsg(),Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });
    }


    private void getCheck() {


        OkGo.<String>get(OConstant.URL_TASK_CHECK)
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
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            if (oCodeMsgEntity.isSuccess() == true) {
                            } else {

                                Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showCheckPopWindow(OCheckHisoryEntity.DataBean data) {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.o_item_check, null);

        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);


        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.6f;
        getActivity().getWindow().setAttributes(params);

        NumberRunningTextView numberRunningTextView = view.findViewById(R.id.text_eagle);
        TextView text_content = view.findViewById(R.id.text_content);

        String pointsArray = data.getPointsArray();
        String pointsStatus = data.getPointsStatus();

        String[] split = pointsArray.split(",");
        String[] split1 = pointsStatus.split(",");
        String today_eagle = null;
        for (int i = 0; i < split1.length; i++) {
            if (split1[i].equals("0")) {
                text_content.setText("今日签到+" + split[i] + "红包");
                today_eagle = split[i];
            }
        }


        double eagle = QuoteProxy.getInstance().getEagle();
        // double div = OUtil.div(Double.parseDouble(today_eagle), 10, 1);

        double add = OUtil.add(eagle, Double.parseDouble(today_eagle));
        numberRunningTextView.setContent(OUtil.doublePoint(add) + "");


        //关键
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> dataList2 = QuoteProxy.getInstance().getDataList();

                if (dataList2 == null) {
                    return;
                }

                String s2 = dataList2.get(1);

                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", s2);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }

    public void backgroundAlpha(float bgalpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgalpha;
        getActivity().getWindow().setAttributes(lp);


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
        if (banner != null) {
            banner.stopAutoPlay();
        }

    }


}
