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

public class NicknameFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.edit_nickname)
    EditText edit_nickname;


    @BindView(R.id.btn_reset)
    Button btn_reset;





    @Override
    protected void initView(View view) {

        btn_reset.setOnClickListener(this);


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_nickname;
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

                String nickname = edit_nickname.getText().toString();
                postChange(nickname);

                break;


        }
    }

    private void postChange(String nickname) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            return;
        }
        String token = loginEntity.getData().getToken();


        OkGo.<String>post(Constant.USER_INFO_URL)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_NICKNAME, nickname)
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
                                getActivity().finish();
                                EventBus.getDefault().post(Constant.ONRESUME_PERSON);
                                EventBus.getDefault().post(Constant.PUBLISH_PERSON);
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
