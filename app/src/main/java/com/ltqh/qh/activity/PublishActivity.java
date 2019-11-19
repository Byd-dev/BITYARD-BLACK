package com.ltqh.qh.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseActivity;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class PublishActivity extends OBaseActivity implements View.OnClickListener {

    @BindView(R.id.text_count)
    TextView text_count;

    @BindView(R.id.edit_title)
    EditText edit_title;

    @BindView(R.id.edit_content)
    EditText edit_content;

    @BindView(R.id.text_fabu)
    TextView text_fabu;


    public static void enter(Context context) {
        Intent intent = new Intent(context, PublishActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int setContentLayout() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        setStatusBar(getResources().getColor(R.color.maincolor));

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.bottom_silent,R.anim.bottom_out);
            }
        });
        text_fabu.setEnabled(false);
        text_fabu.setOnClickListener(this);


        edit_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    text_fabu.setTextColor(getResources().getColor(R.color.white));
                    text_fabu.setEnabled(true);
                } else {
                    text_fabu.setTextColor(getResources().getColor(R.color.text_secondcolor));
                    text_fabu.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_count.setText(s.length() + "/500字已输入");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_fabu:
                LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
                if (loginEntity == null) {
                    showToast("请登录");
                } else {
                    String token = loginEntity.getData().getToken();
                    postPublish(token, edit_title.getText().toString(), edit_content.getText().toString());
                }


                break;
        }
    }


    private void postPublish(String token, String title, String content) {

        OkGo.<String>post(Constant.URL_PUBLISH_URL)
                .tag(this)
                .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_SHARE_ID, 22)
                .params(Constant.PARAM_POST_TITLE, title)
                .params(Constant.PARAM_POST_CONTENT, content)
                .params(Constant.PARAM_MORE, "")
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
                                edit_title.setText("");
                                edit_content.setText("");
                                EventBus.getDefault().post(Constant.PUBLISH_PERSON);

                            }else {
                                SPUtils.remove(UserConfig.LOGIN_USER);
                                SPUtils.remove(UserConfig.USER);
                                Toast.makeText(PublishActivity.this,"用户登录已失效",Toast.LENGTH_SHORT).show();
                                UserActivity.enter(PublishActivity.this,Constant.LOGIN);
                                PublishActivity.this.finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        showToast("当前网络有问题");
                    }
                });
    }
}
