package com.ltqh.qh.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.activity.MainActivity;
import com.ltqh.qh.operation.base.OBaseActivity;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OGuideEntity;
import com.ltqh.qh.utils.SPUtils;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideVestActivity extends OBaseActivity implements View.OnClickListener {
    @BindView(R.id.banner)
    XBanner banner;

    @BindView(R.id.btn_sure)
    Button btn_sure;


    private List<OGuideEntity> data;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_guide_vest;
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, GuideVestActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View view) {

        String string = SPUtils.getString(OUserConfig.O_FIRST_OPEN);
        Log.d("print ", "initView:67:  "+string);
        if (!string.equals("")) {
            MainActivity.enter(GuideVestActivity.this, MainActivity.TAB_TYPE.TAB_HOME);
            GuideVestActivity.this.finish();


        } else {
            SPUtils.putString(OUserConfig.O_FIRST_OPEN, "first");
            data = new ArrayList<>();
            data.add(new OGuideEntity("全球交易平台", "全球", "更多选择 更多币种", getResources().getDrawable(R.mipmap.o_guide_1)));
            data.add(new OGuideEntity("账户安全模式", "安全", "安全有保障", getResources().getDrawable(R.mipmap.o_guide_2)));
            data.add(new OGuideEntity("专业行情机制", "实时", "及时把握行情趋势", getResources().getDrawable(R.mipmap.o_guide_3)));

            banner.setBannerData(R.layout.o_guide_banner_layout, data);
            banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    ImageView imageView = view.findViewById(R.id.img_banner);

                    imageView.setImageDrawable(data.get(position).getDrawable());

                    TextView text_left = view.findViewById(R.id.text_left);
                    TextView text_right = view.findViewById(R.id.text_right);
                    TextView text_bottom = view.findViewById(R.id.text_bottom);

                    text_left.setText(data.get(position).getTextLeft());
                    text_right.setText(data.get(position).getTextRight());
                    text_bottom.setText(data.get(position).getTextBottom());
                }
            });

            banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    if (i == 2) {
                        btn_sure.setVisibility(View.VISIBLE);
                    } else {
                        btn_sure.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            findViewById(R.id.text_jump).setOnClickListener(this);
            btn_sure.setOnClickListener(this);

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_jump:
            case R.id.btn_sure:
                MainActivity.enter(GuideVestActivity.this, MainActivity.TAB_TYPE.TAB_HOME);

                GuideVestActivity.this.finish();
                break;
        }
    }


}
