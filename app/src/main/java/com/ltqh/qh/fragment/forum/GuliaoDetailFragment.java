package com.ltqh.qh.fragment.forum;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.adapter.CommentAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.CommentDetailEntity;
import com.ltqh.qh.entity.GuliaoDetailEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.entity.TipEntity;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.CircleImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

@SuppressLint("ValidFragment")
public class GuliaoDetailFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.img_head)
    CircleImageView img_head;

    @BindView(R.id.text_username)
    TextView text_username;

    @BindView(R.id.text_publish_time)
    TextView text_publishtime;

    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_content)
    TextView text_content;
    @BindView(R.id.text_comment)
    TextView text_comment;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.edit_comment)
    EditText edit_comment;

    @BindView(R.id.layout_view)
    LinearLayout layout_view;

    @BindView(R.id.text_comment_count)
    TextView text_comment_count;

    @BindView(R.id.text_pinglun)
    TextView text_pinglun;

    @BindView(R.id.text_favorite)
    TextView text_favotite;

    @BindView(R.id.img_more)
    ImageView img_more;


    private CommentAdapter commentAdapter;

    private int id;
    private GuliaoDetailEntity.DataBean data;

    @SuppressLint("ValidFragment")
    public GuliaoDetailFragment(int id) {
        this.id = id;
    }


    @Override
    protected void initView(View view) {

        commentAdapter = new CommentAdapter(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(commentAdapter);
        text_pinglun.setEnabled(false);

        getDetail(id);
        getCommentList(id);

        edit_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    text_pinglun.setTextColor(getResources().getColor(R.color.maincolor));
                    text_pinglun.setEnabled(true);
                } else {
                    text_pinglun.setTextColor(getResources().getColor(R.color.text_secondcolor));
                    text_pinglun.setEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        text_pinglun.setOnClickListener(this);
        text_favotite.setOnClickListener(this);
        view.findViewById(R.id.img_back).setOnClickListener(this);
        img_more.setOnClickListener(this);


    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_guliaodetail;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {

    }

    private void getDetail(int id) {

        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
        } else {

            OkGo.<String>get(Constant.URL_DETAIL_URL)
                    .tag(this)
                    .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_ID, id)
                    .execute(new StringCallback() {
                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            // showProgressDialog();
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            //dismissProgressDialog();
                            if (!TextUtils.isEmpty(response.body())) {
                                String s = response.body().replaceAll(" ", "");
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(s, CodeMsgEntity.class);

                                if (codeMsgEntity.getCode() == 0) {
                                    GuliaoDetailEntity guliaoDetailEntity = new Gson().fromJson(s, GuliaoDetailEntity.class);
                                    data = guliaoDetailEntity.getData();
                                    GuliaoDetailEntity.DataBean.UserBean user = data.getUser();
                                    //   Log.d("print", "onSuccess:77 " + guliaoDetailEntity);
                                    if (user != null) {
                                        text_username.setText(user.getUser_nickname());
                                        Glide.with(getActivity().getApplicationContext()).load(user.getAvatar())
                                                .asBitmap()
                                                .error(R.mipmap.user_icon)
                                                .centerCrop().into(img_head);
                                    }
                                    text_title.setText(data.getPost_title());
                                    text_publishtime.setText(data.getPublished_time());
                                    text_content.setText(data.getPost_content());
                                    text_content.setLineSpacing(0, 1.4f);
                                    text_comment.setText(getResources().getString(R.string.text_comment) + "(" + data.getComment_count() + ")");
                                    int post_like = data.getPost_like();
                                    if (post_like == 1) {
                                        text_favotite.setTextColor(getResources().getColor(R.color.maincolor));
                                    } else {
                                        text_favotite.setTextColor(getResources().getColor(R.color.text_secondcolor));
                                    }

                                    text_favotite.setText(getResources().getString(R.string.text_favorite));
                                    text_comment_count.setText(data.getComment_count() + getResources().getString(R.string.text_comment));


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

    }


    private void getCommentList(int id) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
        } else {
            OkGo.<String>get(Constant.URL_COMMENT_LIST_URL)
                    .tag(this)
                    .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .params(Constant.PARAM_OBJECT_ID, id)
                    .execute(new StringCallback() {

                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);

                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            if (!TextUtils.isEmpty(response.body())) {
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                if (codeMsgEntity.getCode() == 1) {
                                    CommentDetailEntity commentDetailEntity = new Gson().fromJson(response.body(), CommentDetailEntity.class);
                                    List<List<CommentDetailEntity.DataBean>> data = commentDetailEntity.getData();
                                    Log.d("print", "onSuccess:213 " + data.get(0));
                                    commentAdapter.setDatas(data.get(0));

                                }
                                //   Log.d("print", "onSuccess:162 " + response.body());
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            dismissProgressDialog();

                        }
                    });


        }
    }

    private void postComment(final int id, String content) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
        } else {
            OkGo.<String>post(Constant.URL_COMMENT_POST_URL)
                    .tag(this)
                    .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_OBJECT_ID, id)
                    .params(Constant.PARAM_CONTENT, content)
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
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                showToast(codeMsgEntity.getMsg());
                                if (codeMsgEntity.getCode() == 1) {
                                    getCommentList(id);
                                    getDetail(id);
                                    edit_comment.setText("");

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
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_pinglun:
                String s = edit_comment.getText().toString();
                postComment(id, s);
                break;

            case R.id.text_favorite:
                LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
                if (loginEntity == null) {
                    showToast("请登录");
                } else {
                    int post_like = data.getPost_like();
                    if (post_like == 1) {
                        postDelZan(loginEntity.getData().getToken(), String.valueOf(data.getId()));

                    } else {
                        postZan(loginEntity.getData().getToken(), String.valueOf(data.getId()));
                    }
                }
                break;

            case R.id.img_back:
                getActivity().finish();
                break;

            case R.id.img_more:
                showPopWindow();
                // edit_comment.clearFocus();
                break;

        }
    }

    private void postZan(final String token, final String objectId) {
        OkGo.<String>post(Constant.URL_POSTZAN_URL)
                .tag(this)
                .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_TABLE_NAME, Constant.STAY_SHARE_POST)
                .params(Constant.PARAM_OBJECT_ID, objectId)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:382 " + response.body());
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            //  showToast(codeMsgEntity.getMsg());
                            if (codeMsgEntity.getCode() == 1) {
                                getDetail(Integer.parseInt(objectId));


                              /*  RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                                if (viewHolder!=null&&viewHolder instanceof GuLiaolistAdapter.MyViewHolder){
                                    ((GuLiaolistAdapter.MyViewHolder) viewHolder).text_dianzan.setTextColor(getResources().getColor(R.color.maincolor));
                                    Log.d("print", "onSuccess:228 "+position);
                                }*/
                            }
                        }
                    }
                });
    }

    private void postDelZan(final String token, final String objectId) {
        OkGo.<String>post(Constant.URL_POSTDELZAN_URL)
                .tag(this)
                .headers(Constant.PARAM_CONTENT_TYPE, Constant.PARAM_APPLICATION)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_ID, objectId)
                .params(Constant.PARAM_TABLE_NAME, Constant.STAY_SHARE_POST)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {
                            Log.d("print", "onSuccess:417 " + response.body());

                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            // showToast(codeMsgEntity.getMsg());
                            if (codeMsgEntity.getCode() == 1) {
                                getDetail(Integer.parseInt(objectId));

                               /* RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                                if (viewHolder!=null&&viewHolder instanceof GuLiaolistAdapter.MyViewHolder){
                                    ((GuLiaolistAdapter.MyViewHolder) viewHolder).text_dianzan.setTextColor(getResources().getColor(R.color.text_secondcolor));

                                }*/
                            }
                        }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopWindow() {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_more_pop, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        view.findViewById(R.id.layout_neipan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();

            }
        });


        view.findViewById(R.id.layout_waipan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showItemPopWindow();
                popupWindow.dismiss();


            }
        });


        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(img_more);

    }

    private String type = "1";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showItemPopWindow() {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_report_pop, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        TextView text_laji = view.findViewById(R.id.text_laji);
        TextView text_bushi = view.findViewById(R.id.text_bushi);
        TextView text_ruma = view.findViewById(R.id.text_ruma);
        TextView text_weifa = view.findViewById(R.id.text_weifa);
        EditText edit_content = view.findViewById(R.id.edit_content);
        TextView text_count = view.findViewById(R.id.text_count);

        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_count.setText(s.length() + "/200字已输入");
                if (s.length() == 200) {
                    Toast.makeText(getContext(), "只能输入这么多", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        text_laji.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));

                type = "1";
                return false;
            }
        });

        text_bushi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                type = "2";
                return false;
            }
        });
        text_ruma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                type = "4";
                return false;
            }
        });
        text_weifa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                text_laji.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_bushi.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_ruma.setBackground(getResources().getDrawable(R.drawable.shape_bg_kuang_mainclor));
                text_weifa.setBackground(getResources().getDrawable(R.drawable.new_chat_bg_mainclor));
                type = "5";
                return false;
            }
        });
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edit_content.getText().toString();
                if (!s.equals("")) {
                    postReport(id, type, s);
                    closePopupWindow(popupWindow);
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(getActivity(), "请输入反馈内容", Toast.LENGTH_SHORT).show();
                }


            }
        });


        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopupWindow(popupWindow);

                popupWindow.dismiss();


            }
        });
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);


        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(layout_view, Gravity.CENTER, 0, 0);

    }

    private void closePopupWindow(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
            WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
            params.alpha = 1f;
            getActivity().getWindow().setAttributes(params);
        }
    }


    private void postReport(int id, String type, String content) {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
        } else {

            OkGo.<String>post(Constant.URL_REPORT)
                    .headers(Constant.PARAM_XX_TOKEN, loginEntity.getData().getToken())
                    .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                    .params(Constant.PARAM_SHARE_POST_ID, id)
                    .params(Constant.PARAM_TYPE, type)
                    .params(Constant.PARAM_NOTE, content)
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
                                CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                                if (codeMsgEntity.getCode() == 1) {
                                    Toast.makeText(getActivity(), "反馈成功,我们将尽快告知您", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

        }
    }

}
