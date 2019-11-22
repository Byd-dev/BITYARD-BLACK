package com.ltqh.qh.fragment.forum;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.ltqh.qh.R;
import com.ltqh.qh.activity.IntentActivity;
import com.ltqh.qh.activity.PublishActivity;
import com.ltqh.qh.activity.UserActivity;
import com.ltqh.qh.adapter.GuLiaolistAdapter;
import com.ltqh.qh.base.BaseFragment;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.config.UserConfig;
import com.ltqh.qh.entity.CodeMsgEntity;
import com.ltqh.qh.entity.EastMoneyEntity;
import com.ltqh.qh.entity.GuliaoEntity;
import com.ltqh.qh.entity.LoginEntity;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.utils.ListUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.utils.ViewUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class ChatFragment extends OBaseFragment implements View.OnClickListener {
    private final static int PERIOD = 5 * 1000; // 5s

    @BindView(R.id.layout_view)
    LinearLayout layout_view;

    @BindView(R.id.ts_news)
    TextSwitcher mTextSwitcherNews;
    private List<String> newSdata;
    private int mNewsIndex;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private int lastVisibleItem;
    private boolean visible = true;
    @BindView(R.id.img_bg)
    TextView img_bg;

    @BindView(R.id.img_add)
    ImageView img_add;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private GuLiaolistAdapter guLiaolistAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private int page = 1;
    private String REFRESHTYPE = "refresh";
    private String LOADTYPE = "load";
    private String ITEMREFRESHTYPE = "itemrefresh";
    private GuliaoEntity guliaoEntity;
    private int id;
    private int distance;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().register(this);
        }

        guLiaolistAdapter = new GuLiaolistAdapter(getActivity());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(guLiaolistAdapter);

        img_bg.setOnClickListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.maincolor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
                if (loginEntity == null) {
                    showToast("请登录");
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    page = 1;
                    getGuliao(REFRESHTYPE, loginEntity.getData().getToken(), page, 0);
                }
            }
        });

        startScheduleJob(mHandler, PERIOD, PERIOD);


        mTextSwitcherNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setLineSpacing(1.1f, 1.1f);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_maincolor));
                textView.setTextSize(15);
                //   textView.setSingleLine();
                return textView;
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int[] lastVisiblePositions = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                    int lastVisiblePos = getMaxElem(lastVisiblePositions);
                    int totalItemCount = staggeredGridLayoutManager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisiblePos == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        page = page + 1;
                        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
                        if (loginEntity != null) {
                            getGuliao(LOADTYPE, loginEntity.getData().getToken(), page, 0);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (distance < -ViewConfiguration.getTouchSlop() && !visible) {
                    //显示fab
                    //iv_go_uploading.setVisibility(View.VISIBLE);
                    showFABAnimation(img_add);
                    distance = 0;
                    visible = true;
                } else if (distance > ViewConfiguration.getTouchSlop() && visible) {
                    //隐藏
                    //iv_go_uploading.setVisibility(View.GONE);
                    hideFABAnimation(img_add);
                    distance = 0;
                    visible = false;
                }
                if ((dy > 0 && visible) || (dy < 0 && !visible))//向下滑并且可见  或者  向上滑并且不可见
                    distance += dy;

            }
        });


        guLiaolistAdapter.setOnItemClick(new GuLiaolistAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position) {
                if (content.getId() != 68) {
                    UserActivity.enter(getActivity(), Constant.FORUM_PUBLISH, content.getId());
                }
            }
        });
        //点赞
        guLiaolistAdapter.setOnDianZanItemClick(new GuLiaolistAdapter.OnDianZanItemClick() {
            @Override
            public void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position) {
                LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
                if (loginEntity == null) {
                    showToast("请登录");
                } else {
                    int post_like = content.getPost_status();
                    if (post_like == 1) {
                        postDelZan(loginEntity.getData().getToken(), String.valueOf(content.getId()), position);

                    } else {
                        postZan(loginEntity.getData().getToken(), String.valueOf(content.getId()), position);
                    }
                }
            }
        });

        guLiaolistAdapter.setOnItemDetailClick(new GuLiaolistAdapter.OnItemDetailClick() {
            @Override
            public void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position) {
                if (content.getId() != 68) {
                    IntentActivity.enter(getActivity(), Constant.FORUM_PUBLISH, content.getId());
                }
            }
        });

        guLiaolistAdapter.setOnJuBaoItemClick(new GuLiaolistAdapter.OnJuBaoItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position) {
                id = content.getId();
                showItemPopWindow();
            }
        });


        view.findViewById(R.id.img_add).setOnClickListener(this);


    }


    private void updateNews() {
        if (newSdata != null) {
            mNewsIndex++;
            if (newSdata.size() > 0) {
                if (mNewsIndex >= newSdata.size()) mNewsIndex = 0;
                if (ListUtil.isNotEmpty(newSdata)) {
                    mTextSwitcherNews.setText(newSdata.get(mNewsIndex).substring(23, newSdata.get(mNewsIndex).length() - 29));
                }
            }
        }
    }

    private void getNews() {
        OkGo.<String>get(Constant.URL_HOUR)
                .tag(this)
                .params(Constant.PARAM_LASTTIME, dateToStamp())
                .params(Constant.PARAM_PAGESIZE, 50)
                .cacheKey(Constant.URL_JINTOUWANG)
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body())) {

                            EastMoneyEntity eastMoneyEntity = new Gson().fromJson(response.body(), EastMoneyEntity.class);
                            newSdata = eastMoneyEntity.getData();
                            mTextSwitcherNews.setText(newSdata.get(0).substring(23, newSdata.get(0).length() - 29));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        // showToast("获取失败,请检查网络");
                    }
                });


    }

    public String dateToStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * by moos on 2017.8.21
     * func:显示fab动画
     */
    public void showFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    /**
     * by moos on 2017.8.21
     * func:隐藏fab的动画
     */

    public void hideFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }


    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }


    @Override
    protected void intPresenter() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(Integer integer) {
        if (integer == Constant.PUBLISH_PERSON) {
            LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
            if (loginEntity == null) {
                showToast("请登录");
            } else {
                page = 1;
                getGuliao(REFRESHTYPE, loginEntity.getData().getToken(), page, 0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isLogin()) {
            img_bg.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            img_bg.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            //getHomeStock(0, getSort());
            //  updateNews();


        }
    };

    @Override
    protected void initData() {
        LoginEntity loginEntity = SPUtils.getData(UserConfig.LOGIN_USER, LoginEntity.class);
        if (loginEntity == null) {
            showToast("请登录");
        } else {
            page = 1;
            getGuliao(REFRESHTYPE, loginEntity.getData().getToken(), page, 0);
        }

        //getNews();

    }


    private HashMap<Integer,Boolean> map=new HashMap<>();

    private void getGuliao(final String type, String token, int page, final int position) {


        OkGo.<String>get(Constant.URL_GULIAOLIST_URL)
                .tag(this)
                .headers(Constant.PARAM_XX_TOKEN, token)
                .headers(Constant.PARAM_XX_DEVICE_TYPE, Constant.PARAM_DEVICE_NAME)
                .params(Constant.PARAM_PAGE, page)
                .params(Constant.PARAM_PAGE_SIZE, 10)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (type.equals(REFRESHTYPE)) {
                            swipeRefreshLayout.setRefreshing(true);
                        } else if (type.equals(LOADTYPE) || type.equals(ITEMREFRESHTYPE)) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (!TextUtils.isEmpty(response.body())) {
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            if (codeMsgEntity.getCode() == 0) {
                                guliaoEntity = new Gson().fromJson(response.body(), GuliaoEntity.class);
                                // Log.d("print", "onSuccess: " + guliaoEntity);
                                if (type.equals(REFRESHTYPE)) {
                                    List<GuliaoEntity.DataBeanX.DataBean> data = guliaoEntity.getData().getData();
                                    guLiaolistAdapter.setDatas(data);

                                } else if (type.equals(LOADTYPE)) {
                                    guLiaolistAdapter.addDatas(guliaoEntity.getData().getData());
                                } else if (type.equals(ITEMREFRESHTYPE)) {
                                    guLiaolistAdapter.setItemDatas(guliaoEntity.getData().getData(), position);
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        swipeRefreshLayout.setRefreshing(false);
                        guLiaolistAdapter.stopLoad();
                        showToast("获取失败,请检查网络");
                    }
                });
    }


    private void postZan(final String token, String objectId, final int position) {
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
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            //  showToast(codeMsgEntity.getMsg());
                            if (codeMsgEntity.getCode() == 1) {
                                getGuliao(ITEMREFRESHTYPE, token, page, position);
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

    private void postDelZan(final String token, String objectId, final int position) {
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
                            CodeMsgEntity codeMsgEntity = new Gson().fromJson(response.body(), CodeMsgEntity.class);
                            // showToast(codeMsgEntity.getMsg());
                            if (codeMsgEntity.getCode() == 1) {
                                getGuliao(ITEMREFRESHTYPE, token, page, position);

                               /* RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                                if (viewHolder!=null&&viewHolder instanceof GuLiaolistAdapter.MyViewHolder){
                                    ((GuLiaolistAdapter.MyViewHolder) viewHolder).text_dianzan.setTextColor(getResources().getColor(R.color.text_secondcolor));

                                }*/
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_bg:
                UserActivity.enter(getActivity(), Constant.LOGIN);

                break;


            case R.id.img_add:
                showPopWindow(v);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopWindow(View view1) {


        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_forummenu_pop, null);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        view.findViewById(R.id.img_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin()) {
                    PublishActivity.enter(getActivity());
                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }
                popupWindow.dismiss();

            }
        });


        view.findViewById(R.id.img_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (isLogin()) {
                    UserActivity.enter(getActivity(), Constant.USER_MYMEAAAGE);

                } else {
                    UserActivity.enter(getActivity(), Constant.LOGIN);
                }

            }
        });
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = view.getMeasuredHeight();
        int popupWidth = view.getMeasuredWidth();

        int[] location = new int[2];
        img_add.getLocationOnScreen(location);

        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAtLocation(img_add, Gravity.NO_GRAVITY, (location[0] + img_add.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);


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
                text_laji.setTextColor(getResources().getColor(R.color.white));
                text_bushi.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_ruma.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_weifa.setTextColor(getResources().getColor(R.color.text_maincolor));
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
                text_laji.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_bushi.setTextColor(getResources().getColor(R.color.white));
                text_ruma.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_weifa.setTextColor(getResources().getColor(R.color.text_maincolor));
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
                text_laji.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_bushi.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_ruma.setTextColor(getResources().getColor(R.color.white));
                text_weifa.setTextColor(getResources().getColor(R.color.text_maincolor));
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
                text_laji.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_bushi.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_ruma.setTextColor(getResources().getColor(R.color.text_maincolor));
                text_weifa.setTextColor(getResources().getColor(R.color.white));
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
