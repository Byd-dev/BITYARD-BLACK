package com.ltqh.qh.operation.fragment.user;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.activity.OWebActivity;
import com.ltqh.qh.operation.adapter.ORechargeListAdapter;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.entity.ORechargeEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;

public class ORechargeFragment extends BaseFragment implements View.OnClickListener {
    private ORechargeListAdapter oRechargeListAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.edit_money)
    EditText edit_money;

    @BindView(R.id.img_delete)
    ImageView img_delete;

    @Override
    protected void initView(View view) {

        oRechargeListAdapter = new ORechargeListAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(oRechargeListAdapter);
        img_delete.setOnClickListener(this);
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

        oRechargeListAdapter.setOnItemClick(new ORechargeListAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(ORechargeEntity.PayListBean data) {

                /*String url = data.getUrl();
                String payUrl = OConstant.PANDANEWS_HOST + url;
                MainWebActivity.openUrlNotitle(getActivity(), payUrl, data.getName());*/

                OUserActivity.enter(getActivity(), OConstant.O_RECHARGE_STEP, data);
            }
        });

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_recharge;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

        postRecharge();

    }

    private void postRecharge() {
        ORechargeEntity oRechargeEntity = QuoteProxy.getInstance().getoRechargeEntity();
        if (oRechargeEntity == null) {
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
                            if (!TextUtils.isEmpty(response.body())) {

                                ORechargeEntity oRechargeEntity = new Gson().fromJson(response.body(), ORechargeEntity.class);
                                if (oRechargeEntity.getPayList() != null) {

                                    oRechargeListAdapter.setDatas(oRechargeEntity.getPayList());
                                }

                                QuoteProxy.getInstance().setoRechargeEntity(oRechargeEntity);

                            }
                        }
                    });
        } else {
            if (oRechargeEntity.getPayList() != null) {

                oRechargeListAdapter.setDatas(oRechargeEntity.getPayList());
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_delete:
                edit_money.setText("");
                break;
        }
    }
}
