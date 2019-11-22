package com.ltqh.qh.fragment.forum;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.adapter.GubalistAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.GubaEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;

public class GubaListFragment extends OBaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private GubalistAdapter gubalistAdapter;
    private LoginEntity loginEntity;


    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        gubalistAdapter = new GubalistAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(gubalistAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
            }
        });

        gubalistAdapter.setOnItemClick(new GubalistAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(GubaEntity.DataBeanX.DataBean content) {
                int shareit = content.getShareit();
                if (shareit == 0) {
                    postAddGuba(String.valueOf(content.getId()));
                } else {
                    postDelGuba(String.valueOf(content.getId()));
                }
            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_gubalist;
    }


    @Override
    public void onResume() {
        super.onResume();
        loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity != null) {
            String token = loginEntity.getData().getToken();
            getLoginGuba(token);
        } else {
            getUnLoginGuba();
        }
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }

    private void getLoginGuba(String token) {


        OkGo.<String>get(Constant.URL_GUBA_LOGIN_URL)
                .tag(this)
                .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_PAGE, 1)
                .params(Constant.PARAM_PAGE_SIZE, 20)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            if (codeMsgEntity.getCode()==1){

                                GubaEntity gubaEntity = new Gson().fromJson(response.body(), GubaEntity.class);
                                gubalistAdapter.setDatas(gubaEntity.getData().getData());
                            }

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        swipeRefreshLayout.setRefreshing(false);
                        showToast("获取失败,请检查网络");

                    }
                });

    }

    private void getUnLoginGuba() {


        OkGo.<String>get(Constant.URL_GUBA_UNLOGIN_URL)
                .tag(this)
                .params(Constant.PARAM_PAGE, 1)
                .params(Constant.PARAM_PAGE_SIZE, 20)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response.body())) {
                            GubaEntity gubaEntity = new Gson().fromJson(response.body(), GubaEntity.class);
                            gubalistAdapter.setDatas(gubaEntity.getData().getData());

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        swipeRefreshLayout.setRefreshing(false);
                        showToast("获取失败,请检查网络");
                    }
                });

    }


    private void postAddGuba(String id) {
        loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
            UserActivity.enter(getActivity(), Constant.LOGIN);
        } else {

            OkGo.<String>post(Constant.URL_ADDGUBA_URL)
                    .tag(this)
                    .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_SHARE_ID, id)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            dismissProgressDialog();
                            Log.d("print", "onSuccess:189 " + response.body());
                            if (!TextUtils.isEmpty(response.body())) {
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                showToast(codeMsgEntity.getMsg());
                                if (codeMsgEntity.getCode() == 1) {
                                    onResume();
                                }

                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });

        }
    }

    private void postDelGuba(String id) {
        loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
            UserActivity.enter(getActivity(), Constant.LOGIN);
        } else {

            OkGo.<String>post(Constant.URL_DELGUBA_URL)
                    .tag(this)
                    .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_SHARE_ID, id)
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
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                showToast(codeMsgEntity.getMsg());
                                if (codeMsgEntity.getCode() == 1) {
                                    onResume();
                                }

                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });

        }
    }
}
