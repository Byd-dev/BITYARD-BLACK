package com.ltqh.qh.operation.fragment.user;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.adapter.OAreaSelectAdapter;
import com.ltqh.qh.operation.adapter.OAreaSelectChildAdapter;
import com.ltqh.qh.operation.adapter.OBankSelectAdapter;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OAddressEntity;
import com.ltqh.qh.operation.entity.OBankEntity;
import com.ltqh.qh.operation.entity.OBankListEntity;
import com.ltqh.qh.operation.entity.OBaseMineEntity;
import com.ltqh.qh.operation.entity.OCodeMsgEntity;
import com.ltqh.qh.operation.entity.OEventData;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.pro.switchlibrary.AES;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;

public class OEditBankFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.layout_view)
    RelativeLayout layout_view;

    @BindView(R.id.text_area)
    TextView text_area;

    @BindView(R.id.text_bank)
    TextView text_bank;

    @BindView(R.id.text_realname)
    TextView text_realname;

    @BindView(R.id.text_tip)
    TextView text_tip;

    @BindView(R.id.edit_zhihang)
    EditText edit_zhihang;

    @BindView(R.id.edit_cardnumber)
    EditText edit_cardnumber;


    private OAreaSelectAdapter oAreaSelectAdapter;
    private OAreaSelectChildAdapter oAreaSelectChildAdapter;
    private OBankSelectAdapter oBankSelectAdapter;

    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.text_lianxi)
    TextView text_lianxi;

    private int isHide = 0;
    private OAddressEntity oAddressEntity;
    private OBankEntity oBankEntity;
    private String provice;
    private String city;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_editbank;
    }

    @Override
    protected void initView(View view) {

        text_lianxi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_lianxi.getPaint().setAntiAlias(true);//抗锯齿
        String s = readTextFromSDcard("address.json");
        oAddressEntity = new Gson().fromJson(s, OAddressEntity.class);
        Log.d("print", "initView:53: " + oAddressEntity.getData().get(0));


        String s1 = readTextFromSDcard("bank.json");
        oBankEntity = new Gson().fromJson(s1, OBankEntity.class);


        view.findViewById(R.id.layout_area).setOnClickListener(this);
        view.findViewById(R.id.layout_bank).setOnClickListener(this);
        view.findViewById(R.id.layout_lianxi).setOnClickListener(this);

        OBaseMineEntity oBaseMineEntity = QuoteProxy.getInstance().getoBaseMineEntity();

        text_realname.setText("开户姓名" + "\"" + oBaseMineEntity.getInfo().getName() + "\"");
        text_tip.setText("为了确保安全,请绑定" + "\"" + oBaseMineEntity.getInfo().getName() + "\"" + "的银行卡");

        view.findViewById(R.id.btn_sure).setOnClickListener(this);

    }

    private String readTextFromSDcard(String name) {
        InputStreamReader inputStreamReader;
        String resultString = null;
        try {
            inputStreamReader = new InputStreamReader(getActivity().getAssets().open(name), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            resultString = stringBuilder.toString();
            Log.i("TAG", stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultString;
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_area:

                showPopWindow();

                break;
            case R.id.layout_bank:
                showBankPopWindow();
                break;

            case R.id.btn_sure:


                String text_bankname = text_bank.getText().toString();
                String text_areaname = text_area.getText().toString();
                if (text_areaname.contains("、")) {
                    String[] split = text_areaname.split("、");
                    provice = split[0];
                    city = split[1];
                }


                String text_zhihang = edit_zhihang.getText().toString();
                String text_cardnumber = edit_cardnumber.getText().toString();


                // Log.d("print", "onClick: 195: 开户银行:"+text_bankname+"地区:"+text_areaname+"支行:"+text_zhihang+"卡号:"+text_cardnumber);

                if (text_bankname.equals("")) {
                    Toast.makeText(getActivity(), "请选择开户银行", Toast.LENGTH_SHORT).show();
                    return;

                } else if (text_areaname.equals("")) {
                    Toast.makeText(getActivity(), "请选择省份城市", Toast.LENGTH_SHORT).show();
                    return;

                } else if (text_zhihang.equals("")) {
                    Toast.makeText(getActivity(), "请输入开户支行", Toast.LENGTH_SHORT).show();
                    return;

                }

                String url = OConstant.URL_BANK_ADD + "?" + OConstant.PARAM_ACTION + "=add&bank=" + text_bankname + "&province=" + provice + "&city=" + city + "&subbranch=" + text_zhihang + "&cardNumber=" + text_cardnumber + "&cardNumberCfm=" + text_cardnumber;
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
                                    OCodeMsgEntity oCodeMsgEntity = new Gson().fromJson(response.body(), OCodeMsgEntity.class);
                                    Toast.makeText(getActivity(), oCodeMsgEntity.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                    if (oCodeMsgEntity.isSuccess() == true) {

                                        getBankList();
                                        getBaseMine();
                                    }
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                dismissProgressDialog();
                            }
                        });


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

                    OWebActivity.openUrlNotitle(getActivity(), s3, "在线客服");

                }else {
                    OUserActivity.enter(getActivity(), OConstant.LOGIN);
                }


                break;


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
    private void showPopWindow() {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_area, null);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        oAreaSelectAdapter = new OAreaSelectAdapter(getActivity());
        recyclerView.setAdapter(oAreaSelectAdapter);


        oAreaSelectAdapter.setDatas(oAddressEntity.getData());


        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        oAreaSelectAdapter.setOnItemClick(new OAreaSelectAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OAddressEntity.DataBean data) {
                Log.d("print", "onSuccessListener:158:  " + data);
                showChildPopWindow(popupWindow, data);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showChildPopWindow(PopupWindow popupWindowparent, OAddressEntity.DataBean dataBean) {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_child_area, null);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        oAreaSelectChildAdapter = new OAreaSelectChildAdapter(getActivity());
        recyclerView.setAdapter(oAreaSelectChildAdapter);


        oAreaSelectChildAdapter.setDatas(dataBean.getShi());


        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        oAreaSelectChildAdapter.setOnItemClick(new OAreaSelectChildAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String data) {
                Log.d("print", "onSuccessListener:218:  " + dataBean.getName() + data);
                text_area.setText(dataBean.getName() + "、" + data);
                popupWindow.dismiss();
                popupWindowparent.dismiss();
            }
        });

        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showBankPopWindow() {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_item_pop_bank, null);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        oBankSelectAdapter = new OBankSelectAdapter(getActivity());
        recyclerView.setAdapter(oBankSelectAdapter);


        oBankSelectAdapter.setDatas(oBankEntity.getData());


        PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        oBankSelectAdapter.setOnItemClick(new OBankSelectAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(OBankEntity.DataBean data) {
                text_bank.setText(data.getValue());
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
