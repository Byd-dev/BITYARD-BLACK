package com.ltqh.qh.fragment.news;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.adapter.CelebrityAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CelebrityEntiy;
import com.ltqh.qh.operation.base.OBaseFragment;

import butterknife.BindView;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;

public class CelebrityFragment extends OBaseFragment {


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.listview)
    RecyclerView recyclerView;

    private CelebrityAdapter celebrityAdapter;

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        celebrityAdapter=new CelebrityAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(celebrityAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        celebrityAdapter.setOnItemClick(new CelebrityAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(CelebrityEntiy.DataBean content) {
                String url = content.getUrl();
                IntentActivity.enter(getContext(), Constant.CELEBRITY_DETAIL,url);





            }
        });

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_celebrity;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

        /*NetManger.getInstance().celebrityList(new OnNetResult() {
            @Override
            public void onNetResult(String state, Object response) {
                if (state.equals(BUSY)) {
                    swipeRefreshLayout.setRefreshing(true);
                } else if (state.equals(SUCCESS)) {
                    swipeRefreshLayout.setRefreshing(false);
                    CelebrityEntiy celebrityEntiy = new Gson().fromJson(response.toString(), CelebrityEntiy.class);
                    celebrityAdapter.setDatas(celebrityEntiy.getData());
                } else if (state.equals(FAILURE)) {
                    swipeRefreshLayout.setRefreshing(false);

                }
            }
        });*/

    }
}
