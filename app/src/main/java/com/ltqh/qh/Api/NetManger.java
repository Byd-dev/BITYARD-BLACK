package com.ltqh.qh.Api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ltqh.qh.BuildConfig;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.TipEntity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class NetManger {

    public static NetManger instance;

    public static  String BUSY="busy";
    public static  String SUCCESS="success";
    public static  String FAILURE="failure";

    private String BASE_URL="https://d.wanjinig.cn";


    public static NetManger getInstance() {
        if (instance == null) {
            instance = new NetManger();
        }

        return instance;
    }


    public void login(final String username, final String password,OnNetResult onNetResult) {


        OkGo.<String>post(BASE_URL+"/user/communal/login")
                .tag(this)
                .params(Constant.PARAM_USERNAME, username)
                .params(Constant.PARAM_PASSWORD, password)
                .params(Constant.PARAM_DEVICE_TYPE,Constant.STAY_DEVICE)
                .params(Constant.PARAM_APP,"1")
                .params(Constant.PARAM_APPLY_NAME,BuildConfig.APPLICATION_ID)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY,null);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("print", "onSuccess:109 " + response.body());
                        if (!TextUtils.isEmpty(response.body()) && !response.body().equals("{")) {

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);

                            if (codeMsgEntity.getCode() == 1) {

                                onNetResult.onNetResult(SUCCESS,response.body());


                            } else {
                                onNetResult.onNetResult(FAILURE,response.body());

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE,response.body());


                    }
                });
    }



    public void register(final String num, final String pass, String code,OnNetResult onNetResult) {
        OkGo.<String>post(BASE_URL+"/user/communal/register")
                .headers(Constant.PARAM_XX_LANGUAGE,"zh-CN")
                .params(Constant.PARAM_USERNAME, num)
                .params(Constant.PARAM_PASSWORD, pass)
                .params(Constant.PARAM_VERIFICATION_CODE, code)
                .params(Constant.PARAM_APP,"1")
                .params(Constant.PARAM_APPLY_NAME, BuildConfig.APPLICATION_ID)
                .params(Constant.PARAM_IS_VALIDATE,"1")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        onNetResult.onNetResult(BUSY,null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            TipEntity tipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            if (tipEntity.getCode() == 1) {
                                onNetResult.onNetResult(SUCCESS,response.body());
                            }else {
                                onNetResult.onNetResult(FAILURE,response.body());

                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNetResult.onNetResult(FAILURE,response.body());


                    }
                });
    }






}
