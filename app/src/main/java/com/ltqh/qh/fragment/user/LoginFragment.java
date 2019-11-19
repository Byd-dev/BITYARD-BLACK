package com.ltqh.qh.fragment.user;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

import butterknife.BindView;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.edit_number)
    EditText edit_number;

    @BindView(R.id.edit_password)
    EditText edit_password;

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.text_hide)
    TextView text_hide;

    @BindView(R.id.text_forget)
    TextView text_forget;

    private int isHide = 0;

    @Override
    protected void initView(View view) {
        btn_login.setOnClickListener(this);
        text_hide.setOnClickListener(this);
        text_forget.setOnClickListener(this);


        String string = SPUtils.getString(UserConfig.USER_ACCOUNT);

        if (string != null) {
            edit_number.setText(string);
        }


        view.findViewById(R.id.img_back).setOnClickListener(this);

        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    edit_password.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                 //postLogin(edit_number.getText().toString(), edit_password.getText().toString());

                new NetManger().getInstance().login(edit_number.getText().toString(), edit_password.getText().toString(), new OnNetResult() {
                    @Override
                    public void onNetResult(String state, Object response) {
                        if (state.equals(NetManger.BUSY)) {
                            showProgressDialog();
                        } else if (state.equals(NetManger.SUCCESS)) {
                            dismissProgressDialog();
                            LoginEntity loginEntity = new Gson().fromJson(response.toString(), LoginEntity.class);
                            SPUtils.putData(UserConfig.LOGIN_USER, loginEntity);
                            EventBus.getDefault().post(Constant.PUBLISH_PERSON);
                            Log.d("print", "onNetResult:用户token: "+loginEntity.getData().getToken());
                            SPUtils.putString(UserConfig.USER_ACCOUNT, loginEntity.getData().getUser().getMobile());
                            getActivity().finish();

                        }else if (state.equals(NetManger.FAILURE)){
                            dismissProgressDialog();
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.toString(), CodeMsgEntity.class);
                            Toast.makeText(getActivity(), codeMsgEntity.getMsg(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
            case R.id.text_hide:
                if (isHide == 0) {
                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHide = 1;
                    text_hide.setText("隐藏");
                } else if (isHide == 1) {
                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHide = 0;
                    text_hide.setText("显示");

                }
                break;

            case R.id.text_forget:
                UserActivity.enter(getActivity(), Constant.FORGET);
                getActivity().finish();
                break;

            case R.id.img_back:
                getActivity().finish();
                break;
        }
    }

    private void postLogin(final String username, final String password) {


        OkGo.<String>post(Constant.LOGIN_URL)
                .tag(this)
                .params(Constant.PARAM_USERNAME, username)
                .params(Constant.PARAM_PASSWORD, password)
                .params(Constant.PARAM_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();

                        Log.d("print", "onSuccess:109 " + response.body());
                        if (!TextUtils.isEmpty(response.body()) && !response.body().equals("{")) {

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            Toast.makeText(getActivity(), codeMsgEntity.getMsg(), Toast.LENGTH_SHORT).show();

                            if (codeMsgEntity.getCode() == 1) {
                                LoginEntity loginEntity = new Gson().fromJson(response.body(), LoginEntity.class);
                                Log.d("print", "onSuccess:Token:" + loginEntity.getData().getToken());

                                SPUtils.putData(UserConfig.LOGIN_USER, loginEntity);
                                EventBus.getDefault().post(Constant.PUBLISH_PERSON);
                                SPUtils.putString(UserConfig.USER_ACCOUNT, loginEntity.getData().getUser().getMobile());

                                getActivity().finish();


                            } else {

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), "当前网络不好，请检查网络", Toast.LENGTH_SHORT).show();


                    }
                });
    }

    //根据泛型返回解析制定的类型
    public <T> T fromToJson(String json, Type listType) {
        Gson gson = new Gson();
        T t = null;
        t = gson.fromJson(json, listType);
        return t;
    }

}
