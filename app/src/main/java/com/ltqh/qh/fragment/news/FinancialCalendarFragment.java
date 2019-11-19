package com.ltqh.qh.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.FinanceCalendarAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.FinanceCalendarEnitiy;
import com.ltqh.qh.entity.FinanceEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;
import com.necer.ncalendar.calendar.NWeekCalendar;
import com.necer.ncalendar.listener.OnClickWeekCalendarListener;
import com.necer.ncalendar.listener.OnWeekCalendarPageChangeListener;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FinancialCalendarFragment extends BaseFragment {


    private RecyclerView recyclerView;

    private FinanceCalendarAdapter financeCalendarAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private int lastVisibleItem;

    private GridLayoutManager linearLayoutManager;

    private NWeekCalendar nWeekCalendar;

    private TextView text_month;

    private String thatDate;

    private int count = 10;

    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";

    private boolean isNowDate = true;




    @Override
    protected void intPresenter() {

    }

    protected void initView(View view) {

        initData(dateToStamp(), REFRESHTYPE, 10);

        recyclerView = (RecyclerView) view.findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        nWeekCalendar = (NWeekCalendar) view.findViewById(R.id.calendar);
        text_month = (TextView) view.findViewById(R.id.text_month);

        String s = dateToStamp();


        text_month.setText(s.substring(5, 7));

       // nWeekCalendar.setBackgroundColor(getResources().getColor(R.color.stock_bg_color));
        nWeekCalendar.setBackground(getResources().getDrawable(R.drawable.bg_shape_background));


        nWeekCalendar.setOnClickWeekCalendarListener(new OnClickWeekCalendarListener() {
            @Override
            public void onClickWeekCalendar(DateTime dateTime) {

                thatDate = dateTime.toString();
             //   Log.d("print", "onWeekCalendarPageSelected: 91:"+thatDate);

                initData(dateTime.toString(), REFRESHTYPE, 10);
                isNowDate = false;

            }
        });

        nWeekCalendar.setOnWeekCalendarPageChangeListener(new OnWeekCalendarPageChangeListener() {
            @Override
            public void onWeekCalendarPageSelected(DateTime dateTime) {
                //Log.d("print", "onWeekCalendarPageSelected: "+dateTime);
                text_month.setText(dateTime.toString().substring(5, 7) );

            }
        });


        linearLayoutManager = new GridLayoutManager(getContext(),1);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        financeCalendarAdapter = new FinanceCalendarAdapter(getActivity());
        recyclerView.setAdapter(financeCalendarAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(dateToStamp(), REFRESHTYPE, 10);
                DateTime dateTime = DateTime.parse(dateToStamp().substring(0, 10));
                //Log.d("print", "initData:178 "+dateTime);

                nWeekCalendar.setDateTime(dateTime);

            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == financeCalendarAdapter.getItemCount() - 1) {
                    financeCalendarAdapter.startLoad();
                    count = count + 10;
                    if (isNowDate) {
                        initData(dateToStamp(), LOADTYPE, count);
                    } else {
                        initData(thatDate, LOADTYPE, count);
                    }

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        financeCalendarAdapter.setOnItemClick(new FinanceCalendarAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(FinanceCalendarEnitiy.DataBean dataBean) {
              /*  String url = dataBean.getUrl();
                WebActivity.openCaijin(getContext(), url);*/
            }
        });


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_financialcalen_dar;
    }


    @Override
    protected void initData() {

    }

    private void initData(String date, final String type, int count) {

        Log.d("print", "initData:173: "+date);

        String[] split = date.split("-");

        Log.d("print", "initData:180 "+split[1]+split[2]);




        OkGo.<String>get(Constant.URL_FINANCE_CALENDAR)
                .tag(this)
                .params(Constant.PARAM_YEAR, date.substring(0,4))
                .params(Constant.PARAM_MONTHDAY, split[1]+split[2].substring(0,2))
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
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            if (codeMsgEntity.getCode()==1){

                                FinanceCalendarEnitiy financeEntity = new Gson().fromJson(response.body(), FinanceCalendarEnitiy.class);
                                if (financeEntity != null) {
                                    financeCalendarAdapter.setDatas(financeEntity.getData());
                                } else {
                                    showToast("当日无数据");
                                }
                            }



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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }


}
