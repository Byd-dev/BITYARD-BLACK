package com.ltqh.qh.operation.fragment.user;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.operation.activity.OImmersiveActivity;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.activity.OPersonActivity;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OBankListEntity;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OMineEntity;
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

import java.util.List;

import butterknife.BindView;

public class OMyFragment extends OBaseFragment implements View.OnClickListener {

    @BindView(R.id.layout_view)
    RelativeLayout layout_view;

    @BindView(R.id.tv_nickname)
    TextView text_login;

    @BindView(R.id.text_balance)
    TextView text_balance;

    @BindView(R.id.img_check)
    ImageView img_check;

    @BindView(R.id.text_balance2)
    TextView text_balance2;

    @BindView(R.id.text_jifen)
    TextView text_jifen;

    @BindView(R.id.view_line)
    View view_line;
    @BindView(R.id.layout_logout)
    RelativeLayout layout_logout;


    private int flag = 1;
    private OMineEntity oMineEntity;
    private PopupWindow popupWindow;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_my;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                getMine();
                getWithdraw();
                getBaseMine();
                postRecharge();
            } else {
                SPUtils.remove(OUserConfig.LOGIN_USER);
            }


        }
    };

    @Override
    public void onResume() {
        super.onResume();


        if (getUserVisibleHint()) {
            oMineEntity = SPUtils.getData(OUserConfig.USER, OMineEntity.class);
            Log.d("print", "onResume:141:   " + QuoteProxy.getInstance().isLogin() + "----" + isMineLogin());
            if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                postRecharge();
                getWithdraw();
                getBaseMine();
                getMine();
                view_line.setVisibility(View.GONE);
                layout_logout.setVisibility(View.GONE);


            } else {
                text_login.setText("登录");
                text_balance.setText("----");
                text_jifen.setText("红包余额: --");
                SPUtils.remove(OUserConfig.USER);

                view_line.setVisibility(View.GONE);
                layout_logout.setVisibility(View.GONE);
            }
        }


    }


    private void getBankList() {
        OkGo.<String>get(OConstant.URL_BANKLIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OBankListEntity oBankListEntity = new Gson().fromJson(response.body(), OBankListEntity.class);
                            QuoteProxy.getInstance().setoBankListEntity(oBankListEntity);
                        }
                    }
                });

    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        startScheduleJob(mHandler, 5000, 5000);

        view.findViewById(R.id.tv_nickname).setOnClickListener(this);
        //设置
        view.findViewById(R.id.img_setting).setOnClickListener(this);
        //选择
        img_check.setOnClickListener(this);
        view.findViewById(R.id.layout_recharge).setOnClickListener(this);
        view.findViewById(R.id.layout_withdraw).setOnClickListener(this);

        view.findViewById(R.id.text_moni).setOnClickListener(this);
        view.findViewById(R.id.text_shipan).setOnClickListener(this);
        view.findViewById(R.id.text_detail).setOnClickListener(this);
        view.findViewById(R.id.layout_accountsetting).setOnClickListener(this);
        view.findViewById(R.id.layout_service).setOnClickListener(this);
        view.findViewById(R.id.img_liuyan).setOnClickListener(this);
        view.findViewById(R.id.layout_about).setOnClickListener(this);
        view.findViewById(R.id.layout_guide).setOnClickListener(this);
        view.findViewById(R.id.layout_recommend).setOnClickListener(this);
        view.findViewById(R.id.layout_myfriend).setOnClickListener(this);
        layout_logout.setOnClickListener(this);


    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
        //获取充值列表

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        List<String> dataList = QuoteProxy.getInstance().getDataList();

        switch (v.getId()) {
            case R.id.tv_nickname:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }
                break;

            case R.id.img_setting:

                if (isMineLogin()) {
                    OPersonActivity.enter(getActivity());
                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }

                break;
            case R.id.img_check:
                if (flag == 0) {
                    img_check.setImageDrawable(getResources().getDrawable(R.mipmap.o_my_eyeopen));
                    text_balance2.setVisibility(View.GONE);
                    text_balance.setVisibility(View.VISIBLE);
                    flag = 1;
                } else if (flag == 1) {
                    img_check.setImageDrawable(getResources().getDrawable(R.mipmap.o_my_eyeclose));
                    text_balance2.setVisibility(View.VISIBLE);
                    text_balance.setVisibility(View.GONE);
                    flag = 0;
                }
                break;

            case R.id.layout_recharge:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    ORechargeEntity oRechargeEntity = QuoteProxy.getInstance().getoRechargeEntity();
                    if (oRechargeEntity == null) {
                        postRecharge();
                    } else {
                        int payFirst = oRechargeEntity.getPayFirst();
                        if (payFirst == 1) {
                            showItemPopWindow();
                        } else {
                            OUserActivity.enter(getActivity(), OConstant.O_RECHARGE);
                        }
                    }
                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }

                break;
            case R.id.layout_withdraw:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                    OBaseMineEntity oBaseMineEntity = QuoteProxy.getInstance().getoBaseMineEntity();
                    if (oBaseMineEntity == null) {
                        getBaseMine();
                    } else {
                        String name = oBaseMineEntity.getInfo().getName();
                        if (name.equals("")) {
                            showTipPopWindow(0);
                        } else {
                            int bankCardCount = oBaseMineEntity.getBankCardCount();
                            if (bankCardCount == 0) {
                                showTipPopWindow(1);
                            } else {
                                String withdrawPw = oBaseMineEntity.getUser().getWithdrawPw();
                                if (withdrawPw.equals("")) {
                                    showTipPopWindow(3);

                                } else {
                                    OUserActivity.enter(getActivity(), OConstant.WITHDRAW);
                                }
                            }
                        }


                    }

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }

                break;

            case R.id.text_moni:

                if (dataList == null) {
                    return;
                }

                String s = dataList.get(0);
                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "2", s);
                break;
            case R.id.text_shipan:

                if (dataList == null) {
                    return;
                }

                String s2 = dataList.get(0);
                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", s2);
                break;
            //资金明细
            case R.id.text_detail:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OUserActivity.enter(getActivity(), OConstant.O_MONEY_DETAIL);

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }

                break;

            case R.id.layout_accountsetting:

                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OUserActivity.enter(getActivity(), OConstant.OACCOUNTSETTING);

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }
                break;

            case R.id.layout_service:


                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                    OBaseMineEntity oBaseMineEntity = SPUtils.getData(OUserConfig.BASEMINE, OBaseMineEntity.class);
                    int id = oBaseMineEntity.getUser().getId();
                    String user_nickname = oBaseMineEntity.getUser().getUsername();
                    String name = oBaseMineEntity.getInfo().getName();

                    long l = System.currentTimeMillis();
                    String content = id + name + "1" + user_nickname + "zy" + l + "henDid-corbop-6jemqa";
                    String s1 = AES.md5(content).toUpperCase();
                    String s3 = OConstant.URL_SERVICE + s1;

                    Log.d("print", "onClick: 347:  " + s3);
                    OWebActivity.openUrlNotitle(getActivity(), s3, "在线客服");

                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }


                break;

            case R.id.img_liuyan:

                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OUserActivity.enter(getActivity(), OConstant.O_MESSAGE);


                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }

                break;

            case R.id.layout_about:
                OIntentActivity.enter(getActivity(), OConstant.O_ABOUT);


                break;

            case R.id.layout_guide:
                OIntentActivity.enter(getActivity(), OConstant.O_GUIDEBOOK);
                break;

            case R.id.layout_recommend:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OImmersiveActivity.enter(getActivity(), OConstant.O_RECOMMEND);


                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }
                break;

            case R.id.layout_myfriend:
                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                    OUserActivity.enter(getActivity(), OConstant.O_MYFRIEND);


                } else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);

                }


                break;

            case R.id.layout_logout:

                getLogout();

                break;

        }
    }

    private void getLogout() {
        OkGo.<String>get(OConstant.URL_LOGOUT)
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
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            if (oCodeMsgEntity.isSuccess() == true) {
                                OMineEntity data = SPUtils.getData(OUserConfig.USER, OMineEntity.class);
                                SPUtils.putString(OUserConfig.USER_ACCOUNT, data.getUser().getLoginMobile());
                                SPUtils.remove(OUserConfig.USER);

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showTipPopWindow(int flag) {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.o_item_tip, null);
        TextView text_title = view.findViewById(R.id.text_title);
        text_title.setLineSpacing(0, 1.4f);
        if (flag == 0) {
            text_title.setText("您当前还未实名认证,为保障您的账户安全,请先实名认证");
        } else if (flag == 1) {
            text_title.setText("您当前还未绑定银行卡,为保障您的正常交易,请先绑定银行卡");

        } else if (flag == 3) {
            text_title.setText("您当前还未设置提款密码,请设置提款密码");

        }


        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);


        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.6f;
        getActivity().getWindow().setAttributes(params);

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });

        //关键
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    OUserActivity.enter(getActivity(), OConstant.OREALNAME);

                } else if (flag == 1) {
                    OUserActivity.enter(getActivity(), OConstant.O_EDIT_BANK);

                } else if (flag == 3) {
                    OUserActivity.enter(getActivity(), OConstant.SETWITHDRAWPASS);

                }
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showItemPopWindow() {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.o_item_check_pop, null);

        EditText edit_name = view.findViewById(R.id.edit_name);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);


        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.6f;
        getActivity().getWindow().setAttributes(params);

        TextView text_back = view.findViewById(R.id.text_back);
        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        //关键
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString();
                postCheckName(name);
            }
        });

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }

    //核实用户验证
    private void postCheckName(String name) {

        String url = OConstant.URL_RECHARGE + "?" + OConstant.PARAM_ACTION + "=" + OConstant.STAY_CHECKNAME + "&" + OConstant.PARAM_NAME + "=" + name;
        Log.d("print", "postCheckName:309:   " + url);
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
                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:315:   " + response.body());
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                            Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            if (oCodeMsgEntity.isSuccess() == true) {
                                postRecharge();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), getString(R.string.o_text_err), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void backgroundAlpha(float bgalpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgalpha;
        getActivity().getWindow().setAttributes(lp);


    }


    private void getMine() {
        OMineEntity oMineEntity = QuoteProxy.getInstance().getoMineEntity();
        if (oMineEntity != null) {


            double eagle = oMineEntity.getAsset().getEagle();

            double eagleRatio = oMineEntity.getEagleRatio();

           // double div_eagle = OUtil.div(eagle, eagleRatio, 1);
            if (text_jifen != null) {

                text_jifen.setText("红包余额: " + OUtil.doublePoint(eagle));
            }


        } else {

            OkGo.<String>get(OConstant.URL_USER_MINE)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            if (!TextUtils.isEmpty(response.body())) {
                                OMineEntity oMineEntity = new Gson().fromJson(response.body(), OMineEntity.class);
                                SPUtils.putData(OUserConfig.USER, oMineEntity);
                                if (oMineEntity == null) {
                                    return;
                                }                                //登录成功需要更新下

                                double eagle = oMineEntity.getAsset().getEagle();

                                double eagleRatio = oMineEntity.getEagleRatio();

                               // double div_eagle = OUtil.div(eagle, eagleRatio, 1);
                                if (text_jifen != null) {

                                    text_jifen.setText("红包余额: " + eagle);
                                }


                                QuoteProxy.getInstance().setEagle(eagle);
                                QuoteProxy.getInstance().setoMineEntity(oMineEntity);


                            }
                        }
                    });

        }

    }

    private void getWithdraw() {

        OwithdrawEntity owithdrawEntity = QuoteProxy.getInstance().getOwithdrawEntity();

        if (owithdrawEntity != null) {
            if (text_login != null) {
                text_login.setText(owithdrawEntity.getUser().getUsername());
                text_balance.setText(owithdrawEntity.getAsset().getMoney() + "");
            }
        } else {

            OkGo.<String>get(OConstant.URL_WITHDRAW)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {

                                if (!TextUtils.isEmpty(response.body())) {
                                    OwithdrawEntity owithdrawEntity = new Gson().fromJson(response.body(), OwithdrawEntity.class);

                                    Log.d("print", "onSuccess:144:   " + owithdrawEntity);


                                    QuoteProxy.getInstance().setOwithdrawEntity(owithdrawEntity);
                                    if (text_login != null) {

                                        text_login.setText(owithdrawEntity.getUser().getUsername());
                                        text_balance.setText(owithdrawEntity.getAsset().getMoney() + "");
                                    }


                                }
                            }
                        }
                    });
        }

    }

    private void getBaseMine() {

        OBaseMineEntity oBaseMineEntity = QuoteProxy.getInstance().getoBaseMineEntity();


        if (oBaseMineEntity != null) {

        } else {


            OkGo.<String>get(OConstant.URL_USER_BASE_MINE)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            if (!TextUtils.isEmpty(response.body())) {
                                OBaseMineEntity oBaseMineEntity = new Gson().fromJson(response.body(), OBaseMineEntity.class);

                                QuoteProxy.getInstance().setoBaseMineEntity(oBaseMineEntity);


                            }

                        }
                    });

        }
    }

    private void postRecharge() {
        ORechargeEntity oRechargeEntity = QuoteProxy.getInstance().getoRechargeEntity();

        if (oRechargeEntity != null) {

        } else {


            OkGo.<String>post(OConstant.URL_RECHARGE)
                    .params(OConstant.PARAM_ACTION, OConstant.STAY_GET_PAY_LIST)
                    .params(OConstant.PARAM_SWITCH_TYPE, "1")
                    .params(OConstant.PARAM_PLATFORM, OConstant.STAY_ANDROID)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);

                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true) {
                                if (!TextUtils.isEmpty(response.body())) {

                                    ORechargeEntity oRechargeEntity = new Gson().fromJson(response.body(), ORechargeEntity.class);

                                    QuoteProxy.getInstance().setoRechargeEntity(oRechargeEntity);


                                }
                            } else {

                            }
                        }
                    });

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
