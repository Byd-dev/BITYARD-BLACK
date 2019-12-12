package com.ltqh.qh.fragment.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.HoursAdapter;
import com.ltqh.qh.operation.adapter.OHoursAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OHoursEntity;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LiveFragment extends OBaseFragment {


    private RecyclerView recyclerView;

    private HoursAdapter oHoursAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;

    private int count = 0;
    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";


    @Override
    protected void intPresenter() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onLazyLoad() {
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

        OkGo.<String>get(uri)
                .headers("X-CMC_PRO_API_KEY","78be76f5-4a85-4885-9787-a3858b63c1d4")
                .params("start","1")
                .params("limit","10")
                .params("convert","USD")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.d("print", "onSuccess:73:   "+response.body());
                    }
                });




        OHoursEntity data = SPUtils.getData(OUserConfig.CACHE_LIVE, OHoursEntity.class);

        if (data != null) {
            oHoursAdapter.setDatas(data.getNewsList());
        } else {
            getLiveData(FIRSTLOAD, 0);

        }
      //  getLiveData(FIRSTLOAD, 0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLiveData(REFRESHTYPE, 0);
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (swipeRefreshLayout.isRefreshing()) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == oHoursAdapter.getItemCount() - 1) {
                    oHoursAdapter.startLoad();
                    count = count + 1;
                    getLiveData(LOADTYPE, count);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    protected void initView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        oHoursAdapter = new HoursAdapter(getActivity());
        recyclerView.setAdapter(oHoursAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);




    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_live;
    }


    @Override
    protected void initData() {


    }


    private void getLiveData(final String type, int count) {


        OkGo.<String>get(OConstant.URL_NEWS_HOURS)
                .tag(this)
                .params(OConstant.PARAM_MAXID, count)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {


                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (type.equals(REFRESHTYPE)) {
                            swipeRefreshLayout.setRefreshing(true);
                        } else if (type.equals(FIRSTLOAD)) {
                            //showProgressDialog();
                        } else if (type.equals(LOADTYPE)) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        swipeRefreshLayout.setRefreshing(false);
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            OHoursEntity oHoursEntity = new Gson().fromJson(response.body(), OHoursEntity.class);
                            //保存上一次的
                            SPUtils.putData(OUserConfig.CACHE_LIVE, oHoursEntity);
                            List<String> data = oHoursEntity.getNewsList();
                            if (type.equals(FIRSTLOAD) || type.equals(REFRESHTYPE)) {
                                oHoursAdapter.setDatas(data);
                            } else if (type.equals(LOADTYPE)) {
                                oHoursAdapter.addDatas(data);

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("获取失败,请检查网络");
                        swipeRefreshLayout.setRefreshing(false);
                        dismissProgressDialog();
                        oHoursAdapter.stopLoad();

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
