package com.ltqh.qh.fragment.market;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ltqh.qh.R;
import com.ltqh.qh.adapter.MarketAdapter;
import com.ltqh.qh.base.Constant;
import com.ltqh.qh.operation.activity.OIntentActivity;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.adapter.OMarketAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.operation.utils.ODateUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.Direction;
import com.ltqh.qh.view.GuideView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

import static com.ltqh.qh.operation.base.OConstant.MARKET_PERIOD;
import static com.ltqh.qh.operation.base.OConstant.PERIOD;

public class MineMarketFragment extends OBaseFragment implements View.OnClickListener {

    @BindView(R.id.layout_up_down)
    LinearLayout layout_up_down;

    @BindView(R.id.img_up_down)
    ImageView img_up_down;

    private int flag = 0;
    private String isupdown = "up";
    @BindView(R.id.text_up_down)
    TextView text_up_down;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
   /* @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;*/

    @BindView(R.id.layout_add)
    LinearLayout layout_add;

    @BindView(R.id.layout_main)
    LinearLayout layout_main;
    private List<String> minelist;

    private Set<String> setList;

    int page = 1;
    private int lastVisibleItem;
    private String FIRSTLOAD = "firstload";
    private String REFRESHTYPE = "refresh";
    private String NULLTYPE = "nulltype";
    private LinearLayoutManager linearLayoutManager;

    private String SORT = Constant.STAY_CHANGEPERCENT;

    private MarketAdapter oMarketAdapter;

    private List<String> dataList;
    private GuideView mGVOne;

    @Override
    protected void onLazyLoad() {

        postQuote();
        if (!ODateUtil.isWeekend()) {
            startScheduleJob(mHandler, MARKET_PERIOD, MARKET_PERIOD);
        }

        String string = SPUtils.getString(OUserConfig.O_FIRST_MINE);
        if (string.equals("")) {

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showGuideViews();
                }
            }, 100);
            SPUtils.putString(OUserConfig.O_FIRST_MINE, "MINE");

        }


    }

    private void showGuideViews() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.o_step_three_layout, null);
        mGVOne = new GuideView.Builder(getActivity())
                .setTargetView(R.id.text_add)
                .setHintView(view)
                .setHintViewDirection(Direction.BOTTOM_ALIGN_RIGHT)
                .setHintViewMarginTop(-30)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGVOne.hide();
                    }
                }).create();
        mGVOne.show();


        TextView text_add = view.findViewById(R.id.text_add);

        text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> dataList = QuoteProxy.getInstance().getDataList();

                if (dataList == null) {
                    return;
                }

                String s = dataList.get(1);
                setList = new HashSet<>();
                setList.add(s);
                SPUtils.putString(OUserConfig.MINEDEX, setList.toString());
                Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                refreshView();
                mGVOne.hide();
            }
        });


    }

    @Override
    protected void initView(View view) {

        oMarketAdapter = new MarketAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(oMarketAdapter);

        layout_add.setOnClickListener(this);
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);

        //防止嵌套出现轻微卡顿的问题
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;

            }
        });

        oMarketAdapter.setOnItemClick(new MarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {

                //OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", code);


            }
        });
        view.findViewById(R.id.layout_addbutton).setOnClickListener(this);
        layout_up_down.setOnClickListener(this);


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            postQuote();


        }
    };

    @Override
    public void onResume() {
        super.onResume();
        String string = SPUtils.getString(OUserConfig.MINEDEX);

        if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {
            layout_add.setVisibility(View.GONE);
            layout_main.setVisibility(View.VISIBLE);


        } else {
            layout_add.setVisibility(View.VISIBLE);
            layout_main.setVisibility(View.GONE);
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_minemarket;
    }

    @Override
    protected void intPresenter() {

    }

    @Override
    protected void initData() {


        //  postQuote(NULLTYPE);


    }

    private Set<String> addList;

    private void postQuote() {


        String string = SPUtils.getString(OUserConfig.MINEDEX);

        // Log.d("print", "postQuote:231:  "+string);

        if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {

            String code = string.replaceAll("\\[", "").replaceAll("]", "").replace(" ", "");
            String[] splitcode = code.split(",");
            addList = new HashSet<>();


            for (String a : splitcode) {
                addList.add(a);
            }

            //Log.d("print", "postQuote:193: "+addList);


            List<String> dataList = QuoteProxy.getInstance().getDataList();
            OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();


            if (dataList != null) {

                minelist = new ArrayList<>();
                for (String quote : dataList) {
                    String[] split = quote.split(",");

                    for (String dax : addList) {
                        if (dax.equals(split[0])) {
                            minelist.add(quote);
                        }
                    }


                }

                //   Log.d("print", "postQuote:228: " +minelist);

                oMarketAdapter.setIsUpDown(isupdown);
                oMarketAdapter.setDatas(OUserConfig.P_MINEDEX, minelist);

                oMarketAdapter.setAllDatas(OUserConfig.P_MINEDEX, oApiEntity);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        oMarketAdapter.notifyItem(dataList);

                    }
                }, PERIOD);

            }


        } else {
            // Log.d("print", "postQuote: 239:" + "111111111111111111111111111111111111");
            //  cancelTimer();
        }
    }


    private void refreshView() {

        String string = SPUtils.getString(OUserConfig.MINEDEX);

        if (!string.equals("") && !string.replaceAll(" ", "").equals("[]")) {
            layout_add.setVisibility(View.GONE);
            layout_main.setVisibility(View.VISIBLE);


        } else {
            layout_add.setVisibility(View.VISIBLE);
            layout_main.setVisibility(View.GONE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.layout_add:
                OIntentActivity.enter(getActivity(), OConstant.OADDMARKET);
                break;

            case R.id.layout_addbutton:
                OIntentActivity.enter(getActivity(), OConstant.OADDMARKET);

                break;
            case R.id.layout_up_down:
                if (flag == 0) {
                    img_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.o_market_down));
                    isupdown = "down";
                    text_up_down.setText("涨跌点");
                    postQuote();
                    flag = 1;
                } else if (flag == 1) {
                    img_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.o_market_up));
                    isupdown = "up";
                    text_up_down.setText("涨跌幅");
                    postQuote();
                    flag = 0;

                }

                break;

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}
