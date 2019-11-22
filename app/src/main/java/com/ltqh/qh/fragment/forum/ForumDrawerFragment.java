package com.ltqh.qh.fragment.forum;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.PersonActivity;
import com.ltqh.qh.activity.PublishActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.UserInfoEntity;
import com.ltqh.qh.fragment.HomeFragment;
import com.ltqh.qh.fragment.find.HomeFindFragment;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.CircleImageView;

import butterknife.BindView;

public class ForumDrawerFragment extends OBaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.layout_group)
    RelativeLayout layout_group;

    @BindView(R.id.radio_0)
    RadioButton radio_0;

    @BindView(R.id.radio_1)
    RadioButton radio_1;

    @BindView(R.id.layout_send)
    LinearLayout layout_send;
   /* @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;*/


    @BindView(R.id.img_head)
    CircleImageView img_head;
    @BindView(R.id.text_login)
    TextView text_login;

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {


        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(0);
        radio_0.setChecked(true);
        radio_1.setChecked(false);

        layout_send.setOnClickListener(this);

        view.findViewById(R.id.img_back).setOnClickListener(this);
        view.findViewById(R.id.img_menu).setOnClickListener(this);

        view.findViewById(R.id.text_ketang).setOnClickListener(this);
        view.findViewById(R.id.text_quanzi).setOnClickListener(this);
        view.findViewById(R.id.text_gongju).setOnClickListener(this);
        view.findViewById(R.id.text_shipin).setOnClickListener(this);
        img_head.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_forum_drawer;
    }

    @Override
    protected void intPresenter() {

    }


    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        UserInfoEntity userInfoEntity = SPUtils.getData(UserConfig.USER, UserInfoEntity.class);
        Log.d("print", "onResume: "+userInfoEntity);
        if (userInfoEntity!=null) {


            if (userInfoEntity.getData() != null) {
                String avatar = userInfoEntity.getData().getAvatar();
                if (avatar == null) {
                    return;
                }
                // getUserInfo();
                Glide.with(getActivity())
                        .load(Constant.USER_AVATER_URL + avatar)
                        .asBitmap()
                        .error(R.mipmap.user_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(img_head);
                String user_nickname = userInfoEntity.getData().getUser_nickname();
                if (user_nickname.equals("")) {
                    text_login.setText("用户");
                } else {
                    text_login.setText(userInfoEntity.getData().getUser_nickname());
                }

            } else {
                text_login.setText("登录");
                img_head.setImageDrawable(getResources().getDrawable(R.mipmap.user_icon));

                if (loginEntity != null) {
                    Glide.with(getActivity())
                            .load(Constant.USER_AVATER_URL + loginEntity.getData().getUser().getAvatar())
                            .asBitmap()
                            .error(R.mipmap.user_icon)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(img_head);
                    //  getUserInfo();


                    String user_nickname = loginEntity.getData().getUser().getUser_nickname();
                    if (user_nickname.equals("")) {
                        text_login.setText("用户");
                    } else {
                        text_login.setText(loginEntity.getData().getUser().getUser_nickname());

                    }
                }
            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_0:
                showFragment(R.id.layout_fragment_containter, new HomeFindFragment(), null, null);
                //layout_send.setVisibility(View.VISIBLE);
                /*radio_0.setTextSize(20);
                radio_1.setTextSize(17);*/

                break;

            case R.id.radio_1:
                showFragment(R.id.layout_fragment_containter, new ChatFragment(), null, null);
               // layout_send.setVisibility(View.GONE);
               /* radio_0.setTextSize(17);
                radio_1.setTextSize(20);*/
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_login:
            case R.id.img_head:
                if (isLogin()) {
                    PersonActivity.enter(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                break;
            case R.id.layout_send:
                if (isLogin()) {
                  PublishActivity.enter(getActivity());

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

                break;

            case R.id.img_back:
                getActivity().finish();
                break;

         /*   case R.id.img_menu:
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)){

                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;*/

            case R.id.text_ketang:
                IntentActivity.enter(getActivity(), Constant.LEARNCLASS);

                break;

            case R.id.text_quanzi:

                IntentActivity.enter(getActivity(), Constant.FEEDBACK);

                break;
            case R.id.text_gongju:
                IntentActivity.enter(getActivity(), Constant.SKILLALL);

                break;

            case R.id.text_shipin:
                IntentActivity.enter(getActivity(), Constant.VIDEO);

                break;
        }
    }
}
