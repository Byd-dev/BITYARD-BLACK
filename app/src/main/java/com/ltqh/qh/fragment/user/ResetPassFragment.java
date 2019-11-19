package com.ltqh.qh.fragment.user;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.entity.UserTipEntity;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class ResetPassFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.edit_password_old)
    EditText edit_password_old;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.edit_password_confirm)
    EditText edit_password_confirm;


    @BindView(R.id.btn_reset)
    Button btn_reset;

    @BindView(R.id.text_hide_old)
    TextView text_hide_old;
    @BindView(R.id.text_hide)
    TextView text_hide;
    @BindView(R.id.text_hide_confirm)
    TextView text_hide_confirm;

    private int isHide = 0;
    private int isHideOld = 0;
    private int isHideConfirm = 0;


    @Override
    protected void initView(View view) {

        btn_reset.setOnClickListener(this);
        text_hide.setOnClickListener(this);
        text_hide_old.setOnClickListener(this);
        text_hide_confirm.setOnClickListener(this);


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_resetpass;
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

            case R.id.btn_reset:
                String password_old = edit_password_old.getText().toString();
                String password = edit_password.getText().toString();
                String password_confirm = edit_password_confirm.getText().toString();
                postChange(password_old, password, password_confirm);

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

            case R.id.text_hide_old:
                if (isHideOld == 0) {
                    edit_password_old.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHideOld = 1;
                    text_hide_old.setText(getResources().getString(R.string.text_hide));
                } else if (isHideOld == 1) {
                    edit_password_old.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHideOld = 0;
                    text_hide_old.setText(getResources().getString(R.string.text_show));

                }
                break;

            case R.id.text_hide_confirm:
                if (isHideConfirm == 0) {
                    edit_password_confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHideConfirm = 1;
                    text_hide_confirm.setText(getResources().getString(R.string.text_hide));
                } else if (isHideConfirm == 1) {
                    edit_password_confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHideConfirm = 0;
                    text_hide_confirm.setText(getResources().getString(R.string.text_show));

                }
                break;
        }
    }

    private void postChange(String oldpass, final String pass, String confirpass) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            return;
        }
        String token = loginEntity.getData().getToken();


        OkGo.<String>post(Constant.LOGIN_CHANGE_URL)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_OLD_PASSWORD, oldpass)
                .params(Constant.PARAM_PASSWORD, pass)
                .params(Constant.PARAM_CONFIRM_PASSWORD, confirpass)
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
                            TipEntity tipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            Toast.makeText(getActivity(), tipEntity.getMsg(), Toast.LENGTH_SHORT).show();
                            if (tipEntity.getCode() == 1) {
                                UserActivity.enter(getActivity(), Constant.LOGIN);
                                getActivity().finish();
                                EventBus.getDefault().post(Constant.ONRESUME_PERSON);
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
