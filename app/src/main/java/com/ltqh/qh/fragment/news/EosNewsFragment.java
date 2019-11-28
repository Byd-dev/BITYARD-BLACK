package com.ltqh.qh.fragment.news;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.ViewUtils;

import butterknife.BindView;

public class EosNewsFragment extends OBaseFragment {
    @BindView(R.id.bar)
    RelativeLayout layout_bar;

    @BindView(R.id.text_content)
    TextView text_content;



    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {
        ViewUtils.setRelativeLayoutParams(getActivity(),layout_bar,48);

        view.findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        text_content.setLineSpacing(0f,1.6f);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_eos_news;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }
}
