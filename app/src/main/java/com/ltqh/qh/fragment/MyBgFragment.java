package com.ltqh.qh.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.MainActivity;
import com.ltqh.qh.activity.PersonActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.activity.WebActivity;
import com.ltqh.qh.adapter.MyAdapter;
import com.ltqh.qh.base.AppContext;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.MyMenuEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.entity.UserInfoEntity;
import com.ltqh.qh.language.LanguageType;
import com.ltqh.qh.language.LanguageUtil;
import com.ltqh.qh.operation.activity.OUserActivity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.utils.AppUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.CircleImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyBgFragment extends OBaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_nickname)
    TextView text_login;

    @BindView(R.id.layout_delete)
    RelativeLayout layout_delete;

    @BindView(R.id.text_hc)
    TextView text_hc;

   /* @BindView(R.id.layout_logout)
    RelativeLayout layout_logout;*/


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.img_head)
    CircleImageView img_head;

    @BindView(R.id.text_sign)
    TextView text_sign;


    private MyAdapter myAdapter;
    private List<MyMenuEntity> data;
    private String language;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_my_bg;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        UserInfoEntity userInfoEntity = SPUtils.getData(UserConfig.USER, UserInfoEntity.class);

        if (userInfoEntity != null) {
            // layout_logout.setVisibility(View.VISIBLE);
         /*   text_login.setBackground(getResources().getDrawable(R.drawable.gradient_maincolor));
            text_login.setTextColor(getResources().getColor(R.color.white));*/

            if (userInfoEntity.getData() != null) {
                String user_nickname = userInfoEntity.getData().getUser_nickname();
                Glide.with(getActivity())
                        .load(Constant.USER_AVATER_URL + userInfoEntity.getData().getAvatar())
                        .asBitmap()
                        .error(R.mipmap.user_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(img_head);

                if (user_nickname.equals("")) {
                    text_login.setText("用户");
                    text_sign.setText(getResources().getString(R.string.text_happy));
             /*   text_login.setBackground(getResources().getDrawable(R.drawable.shape_bg_white));
                text_login.setTextColor(getResources().getColor(R.color.text_maincolor));*/


                } else {
                    text_login.setText(userInfoEntity.getData().getUser_nickname());
                    text_sign.setText(userInfoEntity.getData().getSignature());
             /*   text_login.setBackground(getResources().getDrawable(R.drawable.shape_bg_white));
                text_login.setTextColor(getResources().getColor(R.color.text_maincolor));
*/
                }
            }


        } else {

           /* text_login.setBackground(getResources().getDrawable(R.drawable.gradient_maincolor));
            text_login.setTextColor(getResources().getColor(R.color.white));*/

            text_login.setText(getResources().getText(R.string.text_log_in));
            text_sign.setText(getResources().getString(R.string.text_sign_more));
            img_head.setImageDrawable(getResources().getDrawable(R.mipmap.user_icon));

            //layout_logout.setVisibility(View.GONE);
            if (loginEntity != null) {
               /* text_login.setBackground(getResources().getDrawable(R.drawable.shape_bg_white));
                text_login.setTextColor(getResources().getColor(R.color.text_maincolor));
*/
                //layout_logout.setVisibility(View.VISIBLE);

                String user_nickname = loginEntity.getData().getUser().getUser_nickname();

                Glide.with(getActivity())
                        .load(Constant.USER_AVATER_URL + loginEntity.getData().getUser().getAvatar())
                        .asBitmap()
                        .error(R.mipmap.user_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(img_head);


                if (user_nickname.equals("")) {

                    text_login.setText(getResources().getText(R.string.text_user));
                    text_sign.setText(getResources().getString(R.string.text_happy));

                } else {
                    text_login.setText(loginEntity.getData().getUser().getUser_nickname());
                    text_sign.setText(loginEntity.getData().getUser().getSignature());

                }

            } else {
               /* text_login.setBackground(getResources().getDrawable(R.drawable.gradient_maincolor));
                text_login.setTextColor(getResources().getColor(R.color.white));*/

            }
        }
        text_hc.setText(getResources().getText(R.string.text_cache) + "(" + AppUtil.getAppClearSize(getContext()) + ")");

    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {

        EventBus.getDefault().register(this);
        text_hc.setText(getResources().getText(R.string.text_cache) + "(" + AppUtil.getAppClearSize(getContext()) + ")");
        layout_delete.setOnClickListener(this);

        view.findViewById(R.id.layout_service).setOnClickListener(this);
        view.findViewById(R.id.layout_about).setOnClickListener(this);
        view.findViewById(R.id.layout_mymessage).setOnClickListener(this);
        view.findViewById(R.id.layout_change).setOnClickListener(this);
        view.findViewById(R.id.layout_person).setOnClickListener(this);
        view.findViewById(R.id.img_signature).setOnClickListener(this);

        view.findViewById(R.id.img_service).setOnClickListener(this);
        view.findViewById(R.id.img_message).setOnClickListener(this);

        view.findViewById(R.id.layout_language).setOnClickListener(this);
        view.findViewById(R.id.layout_language2).setOnClickListener(this);
        view.findViewById(R.id.text_edit).setOnClickListener(this);

        //layout_logout.setOnClickListener(this);
        view.findViewById(R.id.tv_nickname).setOnClickListener(this);

        /*text_title.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        text_title.getPaint().setAntiAlias(true);//抗锯齿
        text_title.getPaint().setColor(getResources().getColor(R.color.second_color));*/


        myAdapter = new MyAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(myAdapter);

        data = new ArrayList<>();
        data.add(new MyMenuEntity("我的消息", R.mipmap.my_icon1));
        data.add(new MyMenuEntity("联系客服", R.mipmap.my_icon2));
        data.add(new MyMenuEntity("修改密码", R.mipmap.my_icon3));
        data.add(new MyMenuEntity("清除缓存" + "(" + AppUtil.getAppClearSize(getActivity()) + ")", R.mipmap.my_icon4));
        data.add(new MyMenuEntity("个人资料", R.mipmap.my_icon5));
        data.add(new MyMenuEntity("关于我们", R.mipmap.my_icon6));


        myAdapter.setDatas(data);

        myAdapter.setOnItemClick(new MyAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(MyMenuEntity content) {
                switch (content.getName()) {
                    case "我的消息":
                        if (isLogin()) {
                            UserActivity.enter(getActivity(), Constant.USER_MYMEAAAGE);
                        } else {
                            UserActivity.enter(getActivity(), Constant.LOGIN);
                        }
                        break;
                    case "联系客服":
                        if (isLogin()) {
                            WebActivity.openZhiChiService(getActivity());
                        } else {
                            UserActivity.enter(getActivity(), Constant.LOGIN);
                        }
                        break;
                    case "修改密码":
                        if (isLogin()) {
                            UserActivity.enter(getActivity(), Constant.RESET);

                        } else {
                            UserActivity.enter(getActivity(), Constant.LOGIN);
                        }
                        break;
                 /*   case "清除缓存":
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                    || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                //进入到这里代表没有权限.请求权限
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                            } else {
                                clearCache();
                            }
                        } else {
                            clearCache();
                        }
                        break;*/
                    case "个人资料":
                        if (isLogin()) {
                            PersonActivity.enter(getActivity());
                        } else {
                            UserActivity.enter(getActivity(), Constant.LOGIN);
                        }
                        break;
                    case "关于我们":
                        WebActivity.openAboutUs(getActivity());

                        break;
                }

                if (content.getName().contains("清除缓存")) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //进入到这里代表没有权限.请求权限
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                        } else {
                            clearCache();
                        }
                    } else {
                        clearCache();
                    }
                }
            }
        });


    }


    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }

    int language_type = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_delete:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //进入到这里代表没有权限.请求权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2000);
                    } else {
                        clearCache();
                    }
                } else {
                    clearCache();
                }
                break;
            case R.id.layout_service:
                IntentActivity.enter(getActivity(), Constant.FEEDBACK);

                break;

            case R.id.layout_about:
                WebActivity.openAboutUs(getActivity());

                break;

            case R.id.layout_logout:
                postLogout();
                break;

            case R.id.tv_nickname:
            case R.id.text_edit:

                if (isLogin()) {
                    PersonActivity.enter(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;

            case R.id.layout_mymessage:
                if (isLogin()) {
                    UserActivity.enter(getActivity(), Constant.USER_MYMEAAAGE);

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;

            case R.id.layout_change:
                if (isLogin()) {
                    UserActivity.enter(getActivity(), Constant.RESET);

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;
            case R.id.layout_person:
                if (isLogin()) {
                    PersonActivity.enter(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;

            case R.id.img_signature:
                if (isLogin()) {
                    UserActivity.enter(getActivity(), Constant.SIGNATURE);
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);

                }
                break;

            case R.id.img_service:
                if (isLogin()) {
                    WebActivity.openZhiChiService(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;

            case R.id.img_message:
                OUserActivity.enter(getActivity(), OConstant.O_MESSAGE);

                break;

            case R.id.layout_language:
                language = LanguageType.ENGLISH.getLanguage();
                changeLanguage(language);


                break;
            case R.id.layout_language2:
                language = LanguageType.CHINESE.getLanguage();
                changeLanguage(language);

                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void changeLanguage(String language) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtil.changeAppLanguage(AppContext.getAppContext(), language);
        }
        SPUtils.putString(Constant.LANGUAGE, language);
        MainActivity.reStart(getContext());
    }

    private void clearCache() {
        AppUtil.cleanExternalCache(getContext());
        AppUtil.cleanDatabases(getContext());
        AppUtil.cleanInternalCache(getContext());

        showToast("缓存已清理");


        text_hc.setText("清除缓存(" + "0.0MB)");

        text_hc.invalidate();
    }

    //只有修改成功了 才更新数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(Integer data) {
        if (data == Constant.ONRESUME_PERSON) {
            getUserInfo();
        }
    }

    private void getUserInfo() {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);

        String token = loginEntity.getData().getToken();
        OkGo.<String>get(Constant.USER_INFO_URL)
                .tag(this)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            Log.d("print", "onSuccess:152 " + codeMsgEntity);
                            if (codeMsgEntity.getCode() == 1) {
                                UserInfoEntity userInfoEntity = new Gson().fromJson(response.body(), UserInfoEntity.class);
                                Log.d("print", "onSuccess:用户信息 " + userInfoEntity);

                                String user_nickname = userInfoEntity.getData().getUser_nickname();
                                String mobile = userInfoEntity.getData().getMobile();

                                String signature = userInfoEntity.getData().getSignature();
                                if (signature.equals("")) {
                                    //text_sign.setText("开开心心每一天~");
                                } else {
                                    //text_sign.setText(signature);
                                }

                                SPUtils.putData(UserConfig.USER, userInfoEntity);
                            } else {
                                SPUtils.remove(UserConfig.LOGIN_USER);
                                SPUtils.remove(UserConfig.USER);
                                Toast.makeText(getActivity(), "用户登录已失效", Toast.LENGTH_SHORT).show();
                                UserActivity.enter(getActivity(), Constant.LOGIN);
                                getActivity().finish();
                            }
                        }
                    }
                });
    }


    private void postLogout() {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);

        String token = loginEntity.getData().getToken();

        Log.d("print", "postLogout:Token:" + token);
        if (loginEntity == null) {
            return;
        }

        OkGo.<String>post(Constant.LOGIN_OUT_URL)
                .tag(this)
                .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        dismissProgressDialog();
                        if (!TextUtils.isEmpty(response.body()) && !response.body().equals("{")) {
                            TipEntity tipEntity = new Gson().fromJson(response.body(), TipEntity.class);
                            Toast.makeText(getActivity(), tipEntity.getMsg(), Toast.LENGTH_SHORT).show();

                            if (tipEntity.getCode() == 1) {
                                SPUtils.remove(UserConfig.LOGIN_USER);
                                SPUtils.remove(UserConfig.USER);
                                // getActivity().finish();
                                EventBus.getDefault().postSticky(Constant.ONRESUME_LOGIN);
                                onResume();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismissProgressDialog();
                        Toast.makeText(getActivity(), "当前网络不好，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
