package com.ltqh.qh.operation.fragment.user;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.entity.OUnionEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.ImageUtil;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.operation.utils.QRCodeUtil;
import com.ltqh.qh.utils.Util;
import com.ltqh.qh.view.AlphaChangeListener;
import com.ltqh.qh.view.MyScrollView;
import com.ltqh.qh.view.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import butterknife.BindView;

public class ORecommendFragment extends OBaseFragment implements View.OnClickListener {
    @BindView(R.id.text_url)
    TextView text_url;

    @BindView(R.id.img_code)
    ImageView img_code;
    private Bitmap qrcode_bitmap;//生成的二维码

    @BindView(R.id.text_commratio)
    TextView text_commratio;

    @BindView(R.id.text_user_count)
    TextView text_user_count;

    @BindView(R.id.text_trade_count)
    TextView text_trade_count;

    @BindView(R.id.layout_bar)
    RelativeLayout layout_bar;

    @BindView(R.id.layout_bar2)
    RelativeLayout layout_bar2;

    @BindView(R.id.scrollView)
    MyScrollView myScrollView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_recommend;
    }
    @Override
    protected void onLazyLoad() {
        getUnion();
    }

    private void getUnion() {
        OkGo.<String>get(OConstant.URL_UNION)
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
                            OUnionEntity oUnionEntity = new Gson().fromJson(response.body(), OUnionEntity.class);

                            Log.d("print", "onSuccess:68:   "+oUnionEntity);
                            int userId = oUnionEntity.getUnion().getUserId();
                            if (isAdded()){
                                text_url.setText(OConstant.PANDANEWS_HOST + "/?ru=" + userId);

                                generateQrcodeAndDisplay(OConstant.PANDANEWS_HOST + "/?ru=" + userId);
                                double mul = OUtil.mul(oUnionEntity.getUnion().getCommRatio(), 100);
                                text_commratio.setText(mul+"%");

                                text_user_count.setText(oUnionEntity.getUnion().getUserCount()+"");
                                text_trade_count.setText("交易"+oUnionEntity.getUnion().getUserConsume()+"人");
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

    /**
     * 生成二维码并显示
     */
    private void generateQrcodeAndDisplay(String content) {
        if (content.length() <= 0) {

            return;
        }
        qrcode_bitmap = QRCodeUtil.createQRCodeBitmap(content, 125, 125, "UTF-8",
                "H", "1", Color.BLACK, Color.WHITE, BitmapFactory.decodeResource(getResources(), R.mipmap.bitshop_logo), 0.2F, null);
        img_code.setImageBitmap(qrcode_bitmap);
    }

    @Override
    protected void initView(View view) {
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        int i = Util.dip2px(getContext(), 48);

        Log.d("print", "initView:144:    "+statusBarHeight+"---"+i);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, statusBarHeight + i);
        layout_bar.setLayoutParams(params);
        layout_bar2.setLayoutParams(params);

        myScrollView.setAlphaChangeListener(new AlphaChangeListener() {
            @Override
            public void alphaChanging(float alpha) {
                layout_bar2.setVisibility(View.VISIBLE);
                layout_bar2.setAlpha(alpha);
                layout_bar.setAlpha(1 - alpha);
              /*  if (alpha==0.0){
                    StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);

                }else {
                    StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);

                }*/


            }
        });

        view.findViewById(R.id.text_copy).setOnClickListener(this);
        view.findViewById(R.id.img_back).setOnClickListener(this);
        img_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //进入到这里代表没有权限.请求权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                    } else {
                        saveImg(qrcode_bitmap);

                    }
                } else {
                    saveImg(qrcode_bitmap);

                }

                return true;
            }
        });

        view.findViewById(R.id.text_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> dataList2 = QuoteProxy.getInstance().getDataList();

                if (dataList2 == null) {
                    return;
                }

                String s2 = dataList2.get(1);

                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", s2);

            }
        });

    }

    /**
     * 保存图片至本地
     *
     * @param bitmap
     */
    private void saveImg(Bitmap bitmap) {
        String fileName = "qr_" + System.currentTimeMillis() + ".jpg";
        boolean isSaveSuccess = ImageUtil.saveImageToGallery(getActivity(), bitmap, fileName);
        if (isSaveSuccess) {
            Toast.makeText(getActivity(), "图片已保存至本地", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.text_copy:
                ClipboardManager tvCopy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                tvCopy.setText(text_url.getText().toString().trim());
                Toast.makeText(getActivity(), "复制成功,快发送给好友吧!", Toast.LENGTH_SHORT).show();

                getWechatApi();

                break;
        }
    }

    private void getWechatApi() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "检测到您手机没有安装微信,请安装后使用该功能", Toast.LENGTH_SHORT).show();
        }
    }
}
