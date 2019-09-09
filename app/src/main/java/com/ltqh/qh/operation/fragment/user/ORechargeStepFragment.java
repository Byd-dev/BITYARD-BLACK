package com.ltqh.qh.operation.fragment.user;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OPayUrlEntity;
import com.ltqh.qh.operation.entity.ORechargeEntity;
import com.ltqh.qh.operation.entity.OwithdrawEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import butterknife.BindView;

public class ORechargeStepFragment extends OBaseFragment implements View.OnClickListener {


    @BindView(R.id.text_balance)
    TextView text_balance;

    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.edit_money)
    EditText edit_money;

    @BindView(R.id.img_delete)
    ImageView img_delete;
    @BindView(R.id.text_lianxi)
    TextView text_lianxi;
    @BindView(R.id.text_tip1)
    TextView text_tip1;
    @BindView(R.id.text_tip2)
    TextView text_tip2;
    @BindView(R.id.text_tip3)
    TextView text_tip3;


    private ORechargeEntity.PayListBean payListBean;

    public static ORechargeStepFragment newInstance(ORechargeEntity.PayListBean data) {
        ORechargeStepFragment fragment = new ORechargeStepFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.KEY_ARTICLE, data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            payListBean = (ORechargeEntity.PayListBean) getArguments().getSerializable(Constant.KEY_ARTICLE);

        }
    }

    @Override
    protected void onLazyLoad() {
        String description = payListBean.getDescription();

        text_title.setText("充值金额(元)，" + description.replaceAll("\n", ""));
        Log.d("print", "onLazyLoad:37接收到:   " + payListBean);

        text_tip2.setText("*" + description.replaceAll("\n", ""));

        if (payListBean.getName().contains("微信支付")) {
            text_tip1.setVisibility(View.VISIBLE);
            text_tip2.setVisibility(View.VISIBLE);
            text_tip3.setVisibility(View.VISIBLE);
        }

        OwithdrawEntity owithdrawEntity = QuoteProxy.getInstance().getOwithdrawEntity();

        if (owithdrawEntity != null) {
            if (text_balance != null) {
                text_balance.setText(owithdrawEntity.getAsset().getMoney() + "元");
            }
        }
    }

    @Override
    protected void initView(View view) {

        img_delete.setOnClickListener(this);
        text_lianxi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_lianxi.getPaint().setAntiAlias(true);//抗锯齿


        view.findViewById(R.id.layout_lianxi).setOnClickListener(this);
        view.findViewById(R.id.btn_sure).setOnClickListener(this);
        edit_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    img_delete.setVisibility(View.GONE);

                } else {
                    img_delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_recharge_step;
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
            case R.id.img_delete:
                edit_money.setText("");
                break;

            case R.id.layout_lianxi:

                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                    OBaseMineEntity oBaseMineEntity = SPUtils.getData(OUserConfig.BASEMINE, OBaseMineEntity.class);
                    int userid = oBaseMineEntity.getUser().getId();
                    String user_nickname = oBaseMineEntity.getUser().getUsername();
                    String name = oBaseMineEntity.getInfo().getName();

                    long l = System.currentTimeMillis();
                    String content = userid + name + "1" + user_nickname + "zy" + l + "henDid-corbop-6jemqa";
                    String s1 = AES.md5(content).toUpperCase();
                    String s3 = OConstant.URL_SERVICE + s1;

                    Log.d("print", "onClick: 347:  " + s3);
                    OWebActivity.openUrlNotitle(getActivity(), s3, "在线客服");

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }


                break;

            case R.id.btn_sure:

                String money = edit_money.getText().toString();
                String url = payListBean.getUrl();
                String[] split = url.split("&");
                String[] split4 = split[0].split("=");
                String[] split1 = split[1].split("=");
                String[] split2 = split[2].split("=");
                String[] split3 = split[3].split("=");

                String type=split4[1];
                String max=split2[1];
                String min=split1[1];
                String device=split3[1];


                Log.d("print", "onClick:191:  type"+split4[1]+"   min:"+split1[1]   +"  max:"+split2[1]+"   device:"+split3[1]);
                if (money.equals("")){
                    Toast.makeText(getContext(),"请输入充值金额",Toast.LENGTH_SHORT).show();

                    return;
                }
                double money_double = Double.parseDouble(money);
                double max_double = Double.parseDouble(max);
                double min_double = Double.parseDouble(min);

                if (OUtil.sub(max_double,money_double)<0){
                    Toast.makeText(getContext(),"当前最高充值金额是"+max_double,Toast.LENGTH_SHORT).show();
                }else if (OUtil.sub(money_double,min_double)<0){
                    Toast.makeText(getContext(),"当前最低充值金额是"+min_double,Toast.LENGTH_SHORT).show();
                }else {
                    postPayUrl("ALIPAY_WAP",device,max,min,money,type);
                }

                break;
        }
    }


    private void  postPayUrl(String channel,String device,String max,String min,String money,String type){
        OkGo.<String>post(OConstant.URL_PAY)
                .params(OConstant.PARAM_CHANNEL,channel)
                .params(OConstant.PARAM_DEVICE,device)
                .params(OConstant.PARAM_MAX,max)
                .params(OConstant.PARAM_MIN,min)
                .params(OConstant.PARAM_MONEY,money)
                .params(OConstant.PARAM_TYPE,type)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())){
                            OPayUrlEntity oPayUrlEntity = new Gson().fromJson(response.body(), OPayUrlEntity.class);
                            Log.d("print", "onSuccess:225:   "+oPayUrlEntity);
                            if (oPayUrlEntity.isSuccess()==true){
                                OWebActivity.openUrlNotitle(getActivity(),oPayUrlEntity.getRedirectURL(),"充值");
                            }else {
                                Toast.makeText(getContext(),oPayUrlEntity.getErrorMsg(),Toast.LENGTH_SHORT).show();

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
}
