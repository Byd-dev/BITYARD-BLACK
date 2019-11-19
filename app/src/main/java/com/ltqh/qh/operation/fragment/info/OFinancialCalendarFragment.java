package com.ltqh.qh.operation.fragment.info;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.operation.adapter.OFinanceCalendarAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OFinanceCalendarEnitiy;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;
import com.necer.ncalendar.calendar.NWeekCalendar;
import com.necer.ncalendar.listener.OnClickWeekCalendarListener;
import com.necer.ncalendar.listener.OnWeekCalendarPageChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OFinancialCalendarFragment extends OBaseFragment {


    private RecyclerView recyclerView;

    private OFinanceCalendarAdapter financeCalendarAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private int lastVisibleItem;

    private GridLayoutManager linearLayoutManager;

    private NWeekCalendar nWeekCalendar;


    private String thatDate;

    private int count = 10;

    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";

    private boolean isNowDate = true;


    @Override
    protected void intPresenter() {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_financialcalen_dar;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(OEventData oEventData) {
        String key = oEventData.getKey();
        if (key.equals(OUserConfig.P_NIGHT)){
            String value = (String) oEventData.getObject();
            if (value.equals("night")){
                nWeekCalendar.setBackgroundColor(getResources().getColor(R.color.o_bar_white_night));

            }else if (value.equals("day")){
                nWeekCalendar.setBackgroundColor(getResources().getColor(R.color.o_bar_white));

            }
        }

    }

    @Override
    protected void onLazyLoad() {

        //  getFinancial(dateToStamp(), REFRESHTYPE, 10);

        OFinanceCalendarEnitiy data = SPUtils.getData(OUserConfig.CACHE_FINANCIAL, OFinanceCalendarEnitiy.class);
        if (data != null) {
            financeCalendarAdapter.setDatas(data.getNews().getNewsData());
        } else {

            getFinancial(dateToStamp(), REFRESHTYPE, 10);
        }

    }

    protected void initView(View view) {

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        nWeekCalendar = (NWeekCalendar) view.findViewById(R.id.calendar);

        String s = dateToStamp();
        String string = SPUtils.getString(OUserConfig.P_NIGHT);

        if (string.equals("")) {

        } else {
            if (string.equals("night")) {
                nWeekCalendar.setBackgroundColor(getResources().getColor(R.color.o_bar_white_night));


            } else if (string.equals("day")) {
                nWeekCalendar.setBackgroundColor(getResources().getColor(R.color.o_bar_white));


            }
        }


        nWeekCalendar.setOnClickWeekCalendarListener(new OnClickWeekCalendarListener() {
            @Override
            public void onClickWeekCalendar(DateTime dateTime) {

                thatDate = dateTime.toString().substring(0, 10).replaceAll("-", "");

                getFinancial(thatDate, REFRESHTYPE, 10);
                isNowDate = false;

            }
        });

        nWeekCalendar.setOnWeekCalendarPageChangeListener(new OnWeekCalendarPageChangeListener() {
            @Override
            public void onWeekCalendarPageSelected(DateTime dateTime) {
               // Log.d("print", "onWeekCalendarPageSelected: " + dateTime);
                // text_month.setText(dateTime.toString().substring(5, 7) );

            }
        });


        linearLayoutManager = new GridLayoutManager(getContext(), 1);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        financeCalendarAdapter = new OFinanceCalendarAdapter(getActivity());
        recyclerView.setAdapter(financeCalendarAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFinancial(dateToStamp(), REFRESHTYPE, 10);
                DateTime dateTime = DateTime.parse(dateToNowStamp().substring(0, 10));
                if (nWeekCalendar != null) {

                    nWeekCalendar.setDateTime(dateTime);

                }

            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            /*    if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == financeCalendarAdapter.getItemCount() - 1) {
                    financeCalendarAdapter.startLoad();
                    count = count + 10;
                    if (isNowDate) {
                        initData(dateToStamp(), LOADTYPE, count);
                    } else {
                        initData(thatDate, LOADTYPE, count);
                    }

                }*/

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        financeCalendarAdapter.setOnItemClick(new OFinanceCalendarAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OFinanceCalendarEnitiy.NewsBean.NewsDataBean dataBean) {
              /*  String url = dataBean.getUrl();
                WebActivity.openCaijin(getContext(), url);*/
            }
        });


    }


    @Override
    protected void initData() {


    }

    private void getFinancial(String date, final String type, int count) {


        OkGo.<String>get(OConstant.URL_FINANCE_CALENDAR)
                .tag(this)
                .params(OConstant.PARAM_DATE, date)
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
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        swipeRefreshLayout.setRefreshing(false);

                        if (!TextUtils.isEmpty(response.body())) {

                            OFinanceCalendarEnitiy oFinanceCalendarEnitiy = new Gson().fromJson(response.body(), OFinanceCalendarEnitiy.class);

                            SPUtils.putData(OUserConfig.CACHE_FINANCIAL, oFinanceCalendarEnitiy);

                            financeCalendarAdapter.setDatas(oFinanceCalendarEnitiy.getNews().getNewsData());


                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        swipeRefreshLayout.setRefreshing(false);
                        financeCalendarAdapter.stopLoad();

                    }
                });
    }


    public String dateToStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    public String dateToNowStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
