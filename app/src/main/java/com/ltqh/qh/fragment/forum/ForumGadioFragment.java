package com.ltqh.qh.fragment.forum;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.PublishActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.adapter.GubalistAdapter;
import com.ltqh.qh.adapter.GubalistchatAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.GubaEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;

public class ForumGadioFragment extends OBaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.layout_group)
    RelativeLayout layout_group;

    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    private LoginEntity loginEntity;

    private GubalistchatAdapter gubalistchatAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        gubalistchatAdapter=new GubalistchatAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(gubalistchatAdapter);

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);


        view.findViewById(R.id.img_back).setOnClickListener(this);

        gubalistchatAdapter.setOnItemClick(new GubalistchatAdapter.OnItemClick() {
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
        return R.layout.fragment_forumradio;
    }

    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new ChatFragment(), null, null);
                radio_0.setTextSize(20);
                radio_1.setTextSize(15);
                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new GubaListFragment(), null, null);
                radio_0.setTextSize(15);
                radio_1.setTextSize(20);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.img_back:
                getActivity().finish();
                break;
        }
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
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            if (codeMsgEntity.getCode()==1){

                                GubaEntity gubaEntity = new Gson().fromJson(response.body(), GubaEntity.class);
                                gubalistchatAdapter.setDatas(gubaEntity.getData().getData());
                            }

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
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
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            GubaEntity gubaEntity = new Gson().fromJson(response.body(), GubaEntity.class);
                            gubalistchatAdapter.setDatas(gubaEntity.getData().getData());

                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
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
