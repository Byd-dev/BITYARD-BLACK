package com.ltqh.qh.fragment.user;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.entity.UserTipEntity;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.SmsTimeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.edit_number)
    EditText edit_number;

    @BindView(R.id.edit_password)
    EditText edit_password;

    @BindView(R.id.edit_code)
    EditText edit_code;

    @BindView(R.id.btn_register)
    Button btn_register;

    @BindView(R.id.text_getcode)
    TextView text_getcode;

    @BindView(R.id.text_hide)
    TextView text_hide;

    private int isHide = 0;


    @Override
    protected void initView(View view) {

        btn_register.setOnClickListener(this);
        text_getcode.setOnClickListener(this);
        text_hide.setOnClickListener(this);

        view.findViewById(R.id.img_back).setOnClickListener(this);

        edit_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    edit_code.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_register;
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


            case R.id.text_getcode:
                if (!TextUtils.isEmpty(edit_number.getText().toString())) {
                    showProgressDialog();
                    getCode(edit_number.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_register:
                String number = edit_number.getText().toString();
                String password = edit_password.getText().toString();
                String code = edit_code.getText().toString();
                //postRegister(number, password, code);

                new NetManger().register(number, password, "888888", new OnNetResult() {
                    @Override
                    public void onNetResult(String state, Object response) {
                        if (state.equals(NetManger.BUSY)) {
                            showProgressDialog();
                        } else if (state.equals(NetManger.SUCCESS)) {
                            dismissProgressDialog();
                            new NetManger().login(number, password, new OnNetResult() {
                                @Override
                                public void onNetResult(String state, Object response) {
                                    if (state.equals(NetManger.BUSY)) {
                                        showProgressDialog();
                                    } else if (state.equals(NetManger.SUCCESS)) {
                                        dismissProgressDialog();
                                        LoginEntity loginEntity = new Gson().fromJson(response.toString(), LoginEntity.class);
                                        SPUtils.putData(UserConfig.LOGIN_USER, loginEntity);
                                        EventBus.getDefault().post(Constant.PUBLISH_PERSON);
                                        SPUtils.putString(UserConfig.USER_ACCOUNT, loginEntity.getData().getUser().getMobile());
                                        getActivity().finish();
                                    } else if (state.equals(NetManger.FAILURE)) {
                                        dismissProgressDialog();
                                        CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.toString(), CodeMsgEntity.class);
                                        Toast.makeText(getActivity(), codeMsgEntity.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else if (state.equals(NetManger.FAILURE)) {
                            dismissProgressDialog();

                        }
                    }
                });

                break;

            case R.id.text_hide:
                if (isHide == 0) {
                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHide = 1;
                    text_hide.setText(getResources().getString(R.string.text_hide));
                } else if (isHide == 1) {
                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHide = 0;
                    text_hide.setText(getResources().getString(R.string.text_show));

                }
                break;

            case R.id.img_back:
                getActivity().finish();
                break;

        }
    }

    private void postRegister(final String num, final String pass, String code) {
        OkGo.<String>post(Constant.REGISTER_URL)
                .params(Constant.PARAM_USERNAME, num)
                .params(Constant.PARAM_PASSWORD, pass)
                .params(Constant.PARAM_VERIFICATION_CODE, code)
                .tag(this)
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
                            UserTipEntity tipEntity = new Gson().fromJson(response.body(), UserTipEntity.class);
                            Toast.makeText(getActivity(), tipEntity.getMsg(), Toast.LENGTH_SHORT).show();
                            if (tipEntity.getCode() == 1) {
                                postLogin(num, pass);
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


    private void getCode(String username) {
     /*   HashMap map = new HashMap<>();
        map.put(Constant.PARAM_USERNAME, username);
        map.put(Constant.PARAM_SIGNNAME, Constant.STAY_SIGNNAME);
        HttpParams httpParams = new HttpParams();
        httpParams.put(map);*/

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add(Constant.PARAM_USERNAME, username)
                .add(Constant.PARAM_SIGNNAME, Constant.STAY_SIGNNAME)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(Constant.REGISTER_GETCODE_URL)
                .get()
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), "当前网络不好，请检查网络", Toast.LENGTH_SHORT).show();

                    }
                }).start();
            }


            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String str = "";
                try {
                    str = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String finalStr = str;
                final TipEntity tipEntity = new Gson().fromJson(finalStr, TipEntity.class);
                dismissProgressDialog();
                if (tipEntity.getCode() == 1) {
                    mHandler.sendEmptyMessage(0);
                    Message msg = new Message();
                    mHandler.sendMessage(msg);
                }
                Looper.prepare();
                Toast.makeText(getActivity(), tipEntity.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();

            }
        });


    }


    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SmsTimeUtils.check(SmsTimeUtils.SETTING_FINANCE_ACCOUNT_TIME, false);
                    SmsTimeUtils.startCountdown(text_getcode);


                    break;
                default:
                    break;
            }
        }

    };


    private void postLogin(String username, String password) {


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


                        if (!TextUtils.isEmpty(response.body()) && !response.body().equals("{")) {

                            LoginEntity loginEntity = new Gson().fromJson(response.body(), LoginEntity.class);
                            Toast.makeText(getActivity(), loginEntity.getMsg(), Toast.LENGTH_SHORT).show();

                            if (loginEntity.getCode() == 1) {
                                // LoginEntity loginEntity = new Gson().fromJson(response.body(), LoginEntity.class);
                                SPUtils.putData(UserConfig.LOGIN_USER, loginEntity);

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

  /*OkGo.<String>get(Constant.REGISTER_GETCODE_URL)
                .tag(this)
                .params(Constant.PARAM_USERNAME, username)
                .params(Constant.PARAM_SIGNNAME, Constant.STAY_SIGNNAME)
                .params(Constant.PARAM_ACCESSKEYID, "")
                .params(Constant.PARAM_ACCESSKEYSECRET, "")
                .params(Constant.PARAM_TEMPLATE_ID, "")
                .params(Constant.PARAM_OUT_ID, "")
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
                            TipEntity registerTipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            Log.d("print", "onSuccess:132 " + registerTipEntity);
                            showToast(registerTipEntity.getMsg());

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        showToast("获取失败,请检查网络");


                        Log.d("print", "onError:142 " + response.message());
                    }
                });*/
}
