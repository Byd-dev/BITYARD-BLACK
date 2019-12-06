package com.ltqh.qh.fragment.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.activity.WebActivity;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.base.OBaseFragment;

public class FindFragment extends OBaseFragment implements View.OnClickListener {


    @Override
    public void onResume() {
        super.onResume();


    }


    protected void initView(View view) {

        view.findViewById(R.id.img_service).setOnClickListener(this);




    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_findtab;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    protected void onLazyLoad() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:

                getActivity().finish();
                break;

            case R.id.img_service:
                if (isLogin()) {
                    WebActivity.openZhiChiService(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;





        }
    }





    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }
}
