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
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OMineEntity;
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

public class OResetFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.edit_password_old)
    EditText edit_password_old;

    @BindView(R.id.edit_password)
    EditText edit_password;

    @BindView(R.id.edit_password_confirm)
    EditText edit_password_confirm;

    @BindView(R.id.btn_sure)
    Button btn_sure;


    @BindView(R.id.text_hide)
    TextView text_hide;


    private int isHide = 0;


    @Override
    protected void initView(View view) {

        btn_sure.setOnClickListener(this);
        text_hide.setOnClickListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        OMineEntity oMineEntity = SPUtils.getData(OUserConfig.USER, OMineEntity.class);
        if (oMineEntity != null) {

        } else {


        }

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_reset;
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


            case R.id.btn_sure:
                String passwordOld = edit_password_old.getText().toString();
                String password = edit_password.getText().toString();
                String passwordConfirm = edit_password_confirm.getText().toString();


                if (!TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(passwordOld)
                        && !TextUtils.isEmpty(passwordConfirm)) {
                    postReset(passwordOld, password, passwordConfirm);
                } else {
                    Toast.makeText(getActivity(), "新旧密码不能为空", Toast.LENGTH_SHORT).show();

                }


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
        }
    }


    //确认重置
    private void postReset(final String passOld, String pass, String passConfirm) {
        OkGo.<String>post(OConstant.URL_RESETPASS)
                .tag(this)
                .params(OConstant.PARAM_OLDPASS, passOld)
                .params(OConstant.PARAM_NEWPASS, pass)
                .params(OConstant.PARAM_NEWPASS_CFM, passConfirm)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (isAdded()) {
                            dismissProgressDialog();
                            if (!TextUtils.isEmpty(response.body())) {

                                OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);


                                Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                if (oCodeMsgEntity.getErrorCode() == 200) {
                                    getActivity().finish();
                                    SecondActivity.enter(getActivity(), SecondActivity.TAB_TYPE.TAB_HOME);

                                } else if (oCodeMsgEntity.getErrorCode() == 500) {

                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (isAdded()){

                            dismissProgressDialog();
                            Toast.makeText(getActivity(), "当前网络不好，请检查网络", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
