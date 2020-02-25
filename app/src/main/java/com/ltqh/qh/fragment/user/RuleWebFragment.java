package com.ltqh.qh.fragment.user;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.ltqh.qh.R;
import com.ltqh.qh.base.BaseFragment;

import butterknife.BindView;

public class RuleWebFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;
    private int id;

    public static RuleWebFragment newInstance(int id) {
        RuleWebFragment fragment = new RuleWebFragment();
        Bundle args = new Bundle();
        args.putSerializable("ID", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = (int) getArguments().getSerializable("ID");

        }
    }

    @Override
    protected void initView(View view) {

        if (id==1){
            webView.loadUrl("file:///android_asset/agreement.html");
        }else {
            webView.loadUrl("file:///android_asset/privacy.html");

        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_ruleweb;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }
}
