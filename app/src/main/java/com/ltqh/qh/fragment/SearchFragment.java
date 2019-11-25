package com.ltqh.qh.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ltqh.qh.R;
import com.ltqh.qh.operation.activity.OMarketActivity;
import com.ltqh.qh.operation.adapter.OMarketAdapter;
import com.ltqh.qh.operation.adapter.OMarketHistoryAdapter;
import com.ltqh.qh.operation.base.OBaseFragment;
import com.ltqh.qh.operation.base.OConstant;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.quotebase.QuoteProxy;
import com.ltqh.qh.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchFragment extends OBaseFragment implements View.OnClickListener {


    @BindView(R.id.layout_search_history)
    LinearLayout layout_history;

    private OMarketHistoryAdapter oMarketHistoryAdapter;

    @BindView(R.id.edit_search)
    EditText edit_search;

    @BindView(R.id.img_delete)
    ImageView img_delete;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerview_history)
    RecyclerView recyclerView_history;

    @BindView(R.id.text_cancel)
    TextView text_cancel;

    private OMarketAdapter oMarketAdapter;
    private String name;

    private List<String> searchData;

    private List<String> codeList;
    private List<String> histoyList;
    private List<String> newHistoryList;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.o_fragment_search;
    }

    @Override
    public void onResume() {
        super.onResume();
        //onLazyLoad();
    }

    @Override
    protected void onLazyLoad() {

        oMarketAdapter = new OMarketAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(oMarketAdapter);
        codeList = new ArrayList<>();

        /*oMarketAdapter.setOnItemClick(new OMarketAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {

                String searchList = SPUtils.getString(OUserConfig.SEARCH_LIST);

                if (searchList.equals("")) {

                } else {
                    String s = searchList.replaceAll("\\[", "").replaceAll("]", "");

                    codeList.add(s);


                }


                codeList.add(code.replaceAll(",", "-"));

                Log.d("print", "onSuccessListener:95:  " + searchList + "  ------------- " + code + "  ------------ " + codeList);

                SPUtils.putString(OUserConfig.SEARCH_LIST, codeList.toString());


                OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", code);
                onLazyLoad();


            }
        });*/

        oMarketHistoryAdapter = new OMarketHistoryAdapter(getActivity());
        recyclerView_history.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_history.setAdapter(oMarketHistoryAdapter);


        String searchList = SPUtils.getString(OUserConfig.SEARCH_LIST);
        Log.d("print", "onLazyLoad:111: 历史记录:   " + searchList);
        histoyList = new ArrayList<>();

        if (searchList.equals("")) {
            layout_history.setVisibility(View.GONE);
        } else {

            layout_history.setVisibility(View.VISIBLE);

            String[] split = searchList.replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
            for (String search : split) {
                histoyList.add(search.replaceAll("-", ","));
            }

            Log.d("print", "onLazyLoad:108:   " + histoyList + " --- " + histoyList.size());


            newHistoryList = new ArrayList<>();
            if (histoyList.size() != 0) {
                /*for (String history : histoyList) {
                    if (!newHistoryList.contains(history)) {
                        newHistoryList.add(history);
                    }
                }*/
                //去重
                for (int i = 0; i < histoyList.size(); i++) {
                    for (int j = i + 1; j < histoyList.size(); j++) {
                        String[] split1 = histoyList.get(i).split(",");
                        String[] split2 = histoyList.get(j).split(",");
                        if (split1[0].equals(split2[0])) {
                            j = ++i;
                        }
                    }
                    newHistoryList.add(histoyList.get(i));
                }

                OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
                oMarketHistoryAdapter.setDatas(OUserConfig.P_ALLDEX, newHistoryList);
                oMarketHistoryAdapter.setAllDatas(OUserConfig.P_ALLDEX, oApiEntity);
            }
        }

        oMarketHistoryAdapter.setOnItemClick(new OMarketHistoryAdapter.OnItemClick() {
            @Override
            public void onSuccessListener(String code) {

             //   OMarketActivity.enter(getActivity(), OConstant.OQUETO, "1", code);

            }
        });

    }

    @Override
    protected void initView(View view) {

        text_cancel.setOnClickListener(this);
        img_delete.setOnClickListener(this);
        view.findViewById(R.id.text_delete).setOnClickListener(this);


        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    layout_history.setVisibility(View.GONE);
                    img_delete.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    text_cancel.setText("搜索");
                    //getSearchData(edit_search.getText().toString());
                } else {
                    String searchList = SPUtils.getString(OUserConfig.SEARCH_LIST);
                    if (searchList.equals("")) {
                        layout_history.setVisibility(View.GONE);

                    } else {

                        layout_history.setVisibility(View.VISIBLE);
                    }
                    img_delete.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    text_cancel.setText("取消");
                    if (searchData!=null) {

                        searchData.clear();
                    }


                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getSearchData(String edit) {


        layout_history.setVisibility(View.GONE);
        searchData = new ArrayList<>();

        List<String> foreigndataList = QuoteProxy.getInstance().getForeigndataList();
        List<String> stockindexdataList = QuoteProxy.getInstance().getStockindexdataList();
        List<String> domesdataList = QuoteProxy.getInstance().getDomesdataList();


        OApiEntity oApiEntity = QuoteProxy.getInstance().getoApiEntity();
        List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
        List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
        List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();

        if (foreigndataList != null) {
            for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                if (edit.contains(foreign.getName())) {
                    for (String foreignData : foreigndataList) {
                        String[] split = foreignData.split(",");
                        if (split[0].startsWith(foreign.getCode())) {
                            searchData.add(foreignData);
                        }
                    }
                }

            }
            for (OApiEntity.StockIndexCommdsBean stockindex : stockIndexCommds) {
                if (edit.contains(stockindex.getName())) {
                    for (String stockData : stockindexdataList) {
                        String[] split = stockData.split(",");
                        if (split[0].startsWith(stockindex.getCode())) {
                            searchData.add(stockData);
                        }
                    }
                }
            }
            for (OApiEntity.DomesticCommdsBean domestic : domesticCommds) {
                if (edit.contains(domestic.getName())) {
                    for (String domesData : domesdataList) {
                        String[] split = domesData.split(",");
                        if (split[0].startsWith(domestic.getCode())) {
                            searchData.add(domesData);
                        }
                    }
                }
            }

            if (searchData.size() == 0) {
                Toast.makeText(getActivity(), "搜索为空", Toast.LENGTH_SHORT).show();

            } else {
                oMarketAdapter.setDatas(OUserConfig.P_ALLDEX, searchData);
                oMarketAdapter.setAllDatas(OUserConfig.P_ALLDEX, oApiEntity);
            }


        } else {

            Toast.makeText(getActivity(), "当前无数据", Toast.LENGTH_SHORT).show();
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
            case R.id.text_cancel:
                if (text_cancel.getText().toString().equals("搜索")) {

                    getSearchData(edit_search.getText().toString());
                } else if (text_cancel.getText().toString().equals("取消")) {
                    getActivity().finish();
                }
                break;

            case R.id.img_delete:
                edit_search.setText("");

                break;

            case R.id.text_delete:
                SPUtils.remove(OUserConfig.SEARCH_LIST);
                Toast.makeText(getActivity(), "已清空", Toast.LENGTH_SHORT).show();
                onLazyLoad();

                break;

        }
    }
}
