package com.ltqh.qh.operation.fragment.user;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.adapter.OBankListSelectAdapter;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OBankListEntity;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.entity.OwithdrawEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class OWithdrawFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.layout_view)
    RelativeLayout layout_view;


    @BindView(R.id.text_bank_card)
    TextView text_bank_card;

    @BindView(R.id.img_bank)
    ImageView img_card;

    @BindView(R.id.text_balance)
    TextView text_balance;


    @BindView(R.id.btn_sure)
    Button btn_sure;

    @BindView(R.id.edit_balance)
    EditText edit_balance;

    @BindView(R.id.edit_password)
    EditText edit_password;

    @BindView(R.id.text_lianxi)
    TextView text_lianxi;


    private OBankListSelectAdapter oBankListSelectAdapter;
    private OwithdrawEntity owithdrawEntity;
    private String bankcard_id;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_withdraw;
    }

    @Override
    protected void initView(View view) {


        view.findViewById(R.id.btn_sure).setOnClickListener(this);
        view.findViewById(R.id.layout_bank).setOnClickListener(this);

        text_lianxi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_lianxi.getPaint().setAntiAlias(true);//抗锯齿
        view.findViewById(R.id.layout_lianxi).setOnClickListener(this);


        edit_balance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    String s1 = edit_balance.getText().toString();
                    String s2 = text_balance.getText().toString();
                    double v = Double.parseDouble(s1);
                    double v1 = Double.parseDouble(s2);
                    if (OUtil.sub(v, v1) > 0) {
                        edit_balance.setText(s2);
                    }


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        getWithdraw();


    }

    private void getWithdraw() {

        OkGo.<String>get(OConstant.URL_WITHDRAW)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            owithdrawEntity = new Gson().fromJson(response.body(), OwithdrawEntity.class);

                            QuoteProxy.getInstance().setOwithdrawEntity(owithdrawEntity);

                            List<OwithdrawEntity.BankCardsBean> bankCards = owithdrawEntity.getBankCards();
                            OwithdrawEntity.BankCardsBean bankCardsBean = bankCards.get(0);
                            String bank = bankCardsBean.getBank();
                            String cardNumber = bankCardsBean.getCard();
                            if (text_bank_card == null) {
                                return;
                            }

                            text_bank_card.setText(bank + "  (尾号" + cardNumber.substring(cardNumber.length() - 4, cardNumber.length()) + ")");
                            setImg(bank);

                            text_balance.setText(String.valueOf(owithdrawEntity.getAsset().getMoney()));


                        }
                    }
                });

    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
        // getWithdraw();

        owithdrawEntity = QuoteProxy.getInstance().getOwithdrawEntity();

        if (owithdrawEntity != null) {
            List<OwithdrawEntity.BankCardsBean> bankCards = owithdrawEntity.getBankCards();
            OwithdrawEntity.BankCardsBean bankCardsBean = bankCards.get(0);
            String bank = bankCardsBean.getBank();
            String cardNumber = bankCardsBean.getCard();

            text_bank_card.setText(bank + "  (尾号" + cardNumber.substring(cardNumber.length() - 4, cardNumber.length()) + ")");
            setImg(bank);
            text_balance.setText(String.valueOf(owithdrawEntity.getAsset().getMoney()));
            bankcard_id = bankCardsBean.getId();
        }


        // getBankList();

    /*    OBankListEntity oBankListEntity = QuoteProxy.getInstance().getoBankListEntity();
        if (oBankListEntity == null) {
            return;
        }

        OBankListEntity.BankCardsBean bankCardsBean = oBankListEntity.getBankCards().get(0);
        String bank = bankCardsBean.getBank();
        String cardNumber = bankCardsBean.getCardNumber();

        text_bank_card.setText(bank + "  (尾号" + cardNumber.substring(cardNumber.length() - 4, cardNumber.length()) + ")");

        setImg(bank);*/


    }


    private void setImg(String bank) {
        if (bank.contains("工商")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_gongshang));

        } else if (bank.contains("建设")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_jianhang));

        } else if (bank.contains("农业")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_nongye));

        } else if (bank.contains("招商")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_zhaoshang));

        } else if (bank.contains("中国")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_china));

        } else if (bank.contains("交通")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_jiaotong));

        } else if (bank.contains("邮政")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_youzheng));

        } else if (bank.contains("民生")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_minsheng));

        } else if (bank.contains("浦发")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_pufa));

        } else if (bank.contains("兴业")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_xingye));

        } else if (bank.contains("华夏")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_huaxia));

        } else if (bank.contains("光大")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_guangda));

        } else if (bank.contains("广发")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_guangfa));

        } else if (bank.contains("中信")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_zhongxin));

        } else if (bank.contains("平安")) {
            img_card.setImageDrawable(getResources().getDrawable(R.mipmap.bank_pingan));

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_bank:

                showBankPopWindow();

                break;


            case R.id.btn_sure:


                String balance = edit_balance.getText().toString();
                String password = edit_password.getText().toString();


                if (balance.equals("")) {
                    Toast.makeText(getActivity(), "请输入提款金额", Toast.LENGTH_SHORT).show();
                    return;
                } else if (OUtil.sub(Double.parseDouble(balance), 100.0) < 0) {
                    Toast.makeText(getActivity(), "最低提款金额为100", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(getActivity(), "请输入提款密码", Toast.LENGTH_SHORT).show();
                    return;
                }


                postWithDraw(balance, password);
                break;

            case R.id.layout_lianxi:

                if (QuoteProxy.getInstance().isLogin() == true & isMineLogin() == true){

                    OBaseMineEntity oBaseMineEntity = SPUtils.getData(OUserConfig.BASEMINE, OBaseMineEntity.class);
                    int id = oBaseMineEntity.getUser().getId();
                    String user_nickname = oBaseMineEntity.getUser().getUsername();
                    String name = oBaseMineEntity.getInfo().getName();

                    long l = System.currentTimeMillis();
                    String content=id+name+"1"+user_nickname+"zy"+l+"henDid-corbop-6jemqa";
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


    private void postWithDraw(String balance, String password) {
        OkGo.<String>post(OConstant.URL_WITHDRAW)
                .params(OConstant.PARAM_TYPE, "0")
                .params(OConstant.PARAM_ACTION, OConstant.STAY_APPLY)
                .params(OConstant.PARAM_MONEY, balance)
                .params(OConstant.PARAM_BANK_CARD, bankcard_id)
                .params(OConstant.PARAM_PASSWORD, password)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body())) {
                            OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);

                            showTipPopWindow(oCodeMsgEntity);


                        }
                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showTipPopWindow(OCodeMsgEntity oCodeMsgEntity) {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.o_item_withdraw_tip, null);
        TextView text_title = view.findViewById(R.id.text_title);
        text_title.setLineSpacing(0, 1.4f);
        String title = oCodeMsgEntity.getErrorMsg().replaceAll("<br/>", "");
        text_title.setText(title);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);


        Button btn_sure = view.findViewById(R.id.btn_submit);
        if (oCodeMsgEntity.getErrorMsg().contains("成功")) {
            btn_sure.setText("确定");
            btn_cancel.setText("取消");
        } else if (oCodeMsgEntity.getErrorMsg().contains("密码错误")) {
            btn_sure.setText("去找回");
            btn_cancel.setText("再试试");

        } else if (oCodeMsgEntity.getErrorMsg().contains("提现")) {
            btn_sure.setText("去交易");
            btn_cancel.setText("再想想");

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
                popupWindow.dismiss();
                getActivity().finish();


                if (oCodeMsgEntity.getErrorMsg().contains("成功")) {

                } else if (oCodeMsgEntity.getErrorMsg().contains("密码错误")) {
                    OUserActivity.enter(getActivity(), OConstant.WITHDRAWFORGETPASS);
                } else if (oCodeMsgEntity.getErrorMsg().contains("提现")) {
                    List<String> dataList = QuoteProxy.getInstance().getDataList();

                    if (dataList == null) {
                        return;
                    }

                    String s = dataList.get(0);
                    OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", s);
                }

            }
        });

        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }


    public void backgroundAlpha(float bgalpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgalpha;
        getActivity().getWindow().setAttributes(lp);


    }


    private void getBankList() {
        OkGo.<String>get(OConstant.URL_BANKLIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            OBankListEntity oBankListEntity = new Gson().fromJson(response.body(), OBankListEntity.class);
                            QuoteProxy.getInstance().setoBankListEntity(oBankListEntity);

                            EventBus.getDefault().post(new OEventData(OUserConfig.O_BANKLIST, true));


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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showBankPopWindow() {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_bank_select, null);

        oBankListSelectAdapter = new OBankListSelectAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(oBankListSelectAdapter);
        if (owithdrawEntity != null) {

            List<OwithdrawEntity.BankCardsBean> bankCards = owithdrawEntity.getBankCards();
            oBankListSelectAdapter.setDatas(bankCards);
        }


        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        view.findViewById(R.id.text_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OUserActivity.enter(getActivity(), OConstant.O_EDIT_BANK);
                popupWindow.dismiss();
            }
        });

        oBankListSelectAdapter.setOnItemClick(new OBankListSelectAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OwithdrawEntity.BankCardsBean data) {
                String bank = data.getBank();
                String cardNumber = data.getCard();
                text_bank_card.setText(bank + "  (尾号" + cardNumber.substring(cardNumber.length() - 4, cardNumber.length()) + ")");

                bankcard_id = data.getId();
                setImg(bank);
                popupWindow.dismiss();


            }
        });
        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

}
