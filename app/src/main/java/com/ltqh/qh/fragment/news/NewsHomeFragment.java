package com.ltqh.qh.fragment.news;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.Api.NetManger;
import com.ltqh.qh.Api.OnNetResult;
import com.ltqh.qh.R;
import com.ltqh.qh.entity.BtcNewsEntity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.view.XCRoundRectImageView;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;

import static com.ltqh.qh.Api.NetManger.BUSY;
import static com.ltqh.qh.Api.NetManger.FAILURE;
import static com.ltqh.qh.Api.NetManger.SUCCESS;

public class NewsHomeFragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.banner_article)
    XBanner banner_article;


    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        view.findViewById(R.id.layout_up).setOnClickListener(this);
        view.findViewById(R.id.layout_down).setOnClickListener(this);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_news_home;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

        NetManger.getInstance().btcNews(new OnNetResult() {
            @Override
            public void onNetResult(String state, Object response) {
                if (state.equals(BUSY)){
                    showProgressDialog();
                }else if (state.equals(SUCCESS)){
                    dismissProgressDialog();
                    BtcNewsEntity btcNewsEntity = new Gson().fromJson(response.toString(), BtcNewsEntity.class);
                    upArticleBanner(btcNewsEntity.getData());

                }else if (state.equals(FAILURE)){
                    dismissProgressDialog();
                }
            }
        });


    }




    private void upArticleBanner(List<BtcNewsEntity.DataBean> data) {
        Log.d("print", "upBanner:461:   " + data);
        if (data == null) {
            return;
        }

        if (banner_article != null) {
            banner_article.setBannerData(R.layout.item_quiz_banner, data);
            banner_article.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    XCRoundRectImageView imageView = view.findViewById(R.id.img_banner);
                    Glide.with(getActivity()).load(data.get(position).getXBannerUrl()).asBitmap().into(imageView);
                    TextView textView = view.findViewById(R.id.text_title);
                    textView.setText(data.get(position).getTitle());
                    TextView text_content = view.findViewById(R.id.text_content);
                    text_content.setText(data.get(position).getContent());
                    text_content.setLineSpacing(0,1.4f);
                    TextView text_time=view.findViewById(R.id.text_time);
                    text_time.setText(data.get(position).getDate());
                    view.findViewById(R.id.img_share).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager tvCopy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            tvCopy.setText(data.get(position).getContent().trim());
                            openWeChat();
                        }
                    });
                }
            });
        }

    }

    public void openWeChat() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            this.getActivity().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this.getActivity(), "检测到您手机没有安装微信,请安装后使用该功能", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //  Log.d("print", "onStart:85:    "+"onResume");
        if (banner_article != null) {
            banner_article.startAutoPlay();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner_article != null) {
            banner_article.stopAutoPlay();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_up:
                Toast.makeText(getActivity(), getResources().getString(R.string.text_kanduo),Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_down:
                Toast.makeText(getActivity(), getResources().getString(R.string.text_kankong),Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
