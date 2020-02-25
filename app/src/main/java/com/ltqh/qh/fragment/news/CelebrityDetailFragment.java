package com.ltqh.qh.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CelebrityDetailEntity;
import com.ltqh.qh.operation.base.OBaseFragment;

import butterknife.BindView;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;

public class CelebrityDetailFragment extends OBaseFragment {


    @BindView(R.id.text_content)
    TextView text_content;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private String content;

    public static CelebrityDetailFragment newInstance(String content) {
        CelebrityDetailFragment fragment = new CelebrityDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.KEY_ARTICLE, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(Constant.KEY_ARTICLE);

        }
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        text_content.setLineSpacing(0,1.4f);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_celebritydetail;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

        NetManger.getInstance().celebrityDetail(content, new OnNetResult() {
            @Override
            public void onNetResult(String state, Object response) {
                if (state.equals(BUSY)) {
                    swipeRefreshLayout.setRefreshing(true);
                }else if (state.equals(SUCCESS)){
                    swipeRefreshLayout.setRefreshing(false);
                    CelebrityDetailEntity celebrityDetailEntity = new Gson().fromJson(response.toString(), CelebrityDetailEntity.class);
                    text_content.setText(celebrityDetailEntity.getData().getJianjie());
                }else if (state.equals(FAILURE)){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }
}
