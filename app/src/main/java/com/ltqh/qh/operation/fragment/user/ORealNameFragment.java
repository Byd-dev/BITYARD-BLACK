package com.ltqh.qh.operation.fragment.user;

import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OMineEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class ORealNameFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.text_lianxi)
    TextView text_lianxi;

    @BindView(R.id.edit_realname)
    EditText edit_realname;

    @BindView(R.id.edit_id)
    EditText edit_id;

    @BindView(R.id.btn_sure)
    Button btn_sure;

    private int isHide = 0;


    @Override
    protected void initView(View view) {

        text_lianxi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_lianxi.getPaint().setAntiAlias(true);//抗锯齿

        view.findViewById(R.id.layout_lianxi).setOnClickListener(this);
        view.findViewById(R.id.btn_sure).setOnClickListener(this);

        OBaseMineEntity oBaseMineEntity = QuoteProxy.getInstance().getoBaseMineEntity();
        if (oBaseMineEntity != null) {
            String name = oBaseMineEntity.getInfo().getName();
            if (!name.equals("")) {
                edit_realname.setText("*" + name.substring(name.length() - 1, name.length()));
                edit_realname.setEnabled(false);
                btn_sure.setBackground(getResources().getDrawable(R.drawable.o_new_order_bg_gray));
                btn_sure.setEnabled(false);
                btn_sure.setText("已验证");
            }

            String identityNumber = oBaseMineEntity.getInfo().getIdentityNumber();

            if (!identityNumber.equals("")) {
                String replace = identityNumber.substring(0, 6) + "****" + identityNumber.substring(14, identityNumber.length());
                edit_id.setText(replace);
                edit_id.setEnabled(false);
            }


        }


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
        return R.layout.o_fragment_realname;
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
                String realname = edit_realname.getText().toString();
                String id = edit_id.getText().toString();

                if (realname.equals("")){
                    Toast.makeText(getActivity(),"请输入真实姓名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id.equals("")){
                    Toast.makeText(getActivity(),"请输入省份证号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                postProfileAuth(realname, id);
                break;
            case R.id.layout_lianxi:

                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true){

                    OBaseMineEntity oBaseMineEntity = SPUtils.getData(OUserConfig.BASEMINE, OBaseMineEntity.class);
                    int userid = oBaseMineEntity.getUser().getId();
                    String user_nickname = oBaseMineEntity.getUser().getUsername();
                    String name = oBaseMineEntity.getInfo().getName();

                    long l = System.currentTimeMillis();
                    String content=userid+name+"1"+user_nickname+"zy"+l+"henDid-corbop-6jemqa";
                    String s1 = AES.md5(content).toUpperCase();
                    String s3 = OConstant.URL_SERVICE + s1;

                    Log.d("print", "onClick: 347:  "+s3);
                    OWebActivity.openUrlNotitle(getActivity(), s3, "在线客服");

                }else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }


                break;

        }
    }


    private void postProfileAuth(String realname, String id) {


        String url = OConstant.URL_PROFILE_AUTH + "?" + OConstant.PARAM_ACTION + "=" + OConstant.STAY_AUTH_IDENTITY + "&" + OConstant.PARAM_NAME + "=" + realname + "&" + OConstant.PARAM_IDENTITYNUMBER + "=" + id;

        Log.d("print", "postProfileAuth:155:   " + url);

        OkGo.<String>post(url)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                            if (!TextUtils.isEmpty(response.body())) {
                                Log.d("print", "onSuccess:150:  " + response.body());
                                OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                                Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                if (oCodeMsgEntity.isSuccess() == true) {
                                   // getActivity().finish();
                                    //修改成功了才通知设置页面更新数据
                                    getBaseMine();
                                    EventBus.getDefault().post(new OEventData(OUserConfig.O_REALNAME, true));
                                }
                            }
                        }
                    }
                });


    }

    private void getBaseMine() {
        OkGo.<String>get(OConstant.URL_USER_BASE_MINE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if (!TextUtils.isEmpty(response.body())) {
                            OBaseMineEntity oBaseMineEntity = new Gson().fromJson(response.body(), OBaseMineEntity.class);


                            QuoteProxy.getInstance().setoBaseMineEntity(oBaseMineEntity);

                            getActivity().finish();


                        }

                    }
                });
    }
}
