package com.ltqh.qh.operation.fragment.user;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.activity.SecondActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.SmsTimeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;

public class OForgetFragment extends BaseFragment implements View.OnClickListener {

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

    @BindView(R.id.img_code)
    ImageView img_code;

    private int isHide = 0;
    @BindView(R.id.edit_imgcode)
    EditText edit_imgcode;

    @Override
    protected void initView(View view) {

        btn_register.setOnClickListener(this);
        text_getcode.setOnClickListener(this);
        text_hide.setOnClickListener(this);
        img_code.setOnClickListener(this);


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_forget;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
        getImgcode();
    }

    private void getImgcode() {

        String displayName = TimeZone.getDefault().getDisplayName();
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
        String format = dateFormat.format(date);
        String url = OConstant.URL_REGISTER_IMG_CODE + "?_=" + format + "(" + displayName + ")";

        OkGo.<Bitmap>get(url)
                .tag(url)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        Bitmap bitmap = response.body();
                        img_code.post(new Runnable() {
                            @Override
                            public void run() {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] bytes = baos.toByteArray();
                                Glide.with(getActivity()).load(bytes)
                                        .asBitmap()
                                        .error(R.mipmap.o_try)
                                        .centerCrop().into(img_code);
                            }
                        });

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_code:
                getImgcode();
                break;


            case R.id.text_getcode:

                if (!TextUtils.isEmpty(edit_number.getText().toString()) && !TextUtils.isEmpty(edit_imgcode.getText().toString())) {

                    getCode(edit_number.getText().toString(), edit_imgcode.getText().toString());


                } else {
                    Toast.makeText(getActivity(), "请输入手机号码和图片验证码", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_register:
                String number = edit_number.getText().toString();
                String password = edit_password.getText().toString();
                String code = edit_code.getText().toString();


                if (!TextUtils.isEmpty(edit_number.getText().toString())
                        && !TextUtils.isEmpty(edit_imgcode.getText().toString())
                        && !TextUtils.isEmpty(edit_code.getText().toString())
                        && !TextUtils.isEmpty(edit_password.getText().toString())) {
                    postPhone(code, number, password);

                } else {
                    Toast.makeText(getActivity(), "手机号码、密码和验证码不能为空", Toast.LENGTH_SHORT).show();

                }


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
        }
    }

    //验证手机号
    private void postPhone(String code, String number, String pass) {
        OkGo.<String>post(OConstant.URL_FINDBACK)
                .tag(this)
                .params(OConstant.PARAM_ACTION, OConstant.STAY_VERIFYCODE_ACTION)
                .params(OConstant.PARAM_VERIFYCODE, code)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);

                            if (oCodeMsgEntity.getErrorCode() == 200) {
                                postAuth();
                                postReset(number, pass);
                            } else {
                                Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                dismissProgressDialog();

                            }
                        }
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                    }
                });
    }
    //验证身份信息
    private void postAuth() {
    }

    //确认重置
    private void postReset(final String num, final String pass) {
        OkGo.<String>post(OConstant.URL_FINDBACK)
                .tag(this)
                .params(OConstant.PARAM_ACTION, OConstant.STAY_PASSWD_ACTION)
                .params(OConstant.PARAM_NEWPASS, pass)
                .params(OConstant.PARAM_NEWPASS_CFM, pass)
                .params(OConstant.PARAM_TYPE, "1")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {

                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            if (oCodeMsgEntity.getErrorCode() == 200) {
                                getActivity().finish();
                                SecondActivity.enter(getActivity(), SecondActivity.TAB_TYPE.TAB_HOME);

                            } else if (oCodeMsgEntity.getErrorCode() == 500) {
                                getImgcode();
                                edit_imgcode.setText("");
                                edit_code.setText("");
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


    //获取验证码
    private void getCode(String username, String imgcode) {
        OkGo.<String>post(OConstant.URL_FINDBACK)
                .tag(this)
                .params(OConstant.PARAM_ACTION, OConstant.STAY_ACTION)
                .params(OConstant.PARAM_MOBILE, username)
                .params(OConstant.PARAM_IMGCODE, imgcode)
                .params(OConstant.PARAM_TYPE, "1")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        Log.d("print", "onSuccess:229: " + response.body());
                        if (!TextUtils.isEmpty(response.body())) {
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            if (oCodeMsgEntity.getErrorCode() == 200) {
                                mHandler.sendEmptyMessage(0);
                                Message msg = new Message();
                                mHandler.sendMessage(msg);
                                Toast.makeText(getContext(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                getImgcode();
                                edit_imgcode.setText("");
                                if (oCodeMsgEntity.getErrorCode() == 500) {
                                    edit_number.setText("");

                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                    }
                });
    }

    //验证短信验证码
    private void postCode(String username, String password, String code) {
        OkGo.<String>post(OConstant.URL_REGISTER)
                .tag(this)
                .params(OConstant.PARAM_ACTION, OConstant.STAY_VERIFYCODE_ACTION)
                .params(OConstant.PARAM_VERIFYCODE, code)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("print", "onSuccess:265: " + response.body());
                        if (!TextUtils.isDigitsOnly(response.body())) {
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            if (oCodeMsgEntity.getErrorCode() == 200) {
                                // postRegister(username, password);
                            } else {
                                dismissProgressDialog();
                                Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
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


}
