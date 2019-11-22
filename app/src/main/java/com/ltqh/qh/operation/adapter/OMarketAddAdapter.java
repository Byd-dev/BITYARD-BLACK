package com.ltqh.qh.operation.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.base.AppContext;
import com.ltqh.qh.entity.GoldlistEntity;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.utils.SPUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OMarketAddAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<String> datas;
    private List<String> oldDatas;


    private Set<String> setList;
    private OApiEntity oApiEntity;
    private List<OApiEntity.ForeignCommdsBean> foreignCommds;
    private List<OApiEntity.DomesticCommdsBean> domesticCommds;
    private List<OApiEntity.StockIndexCommdsBean> stockIndexCommds;
    private List<OApiEntity.DigitalCommdsBean> digitalCommdsBeans;

    private Context context;
    private String key;
    private String[] code;
    private String codeadd;


    private Map<Integer, String> map = new HashMap<>();
    private int addFlag = 0;

    public OMarketAddAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        oldDatas = new ArrayList<>();
        oApiEntity = new OApiEntity();
        foreignCommds = new ArrayList<>();
        domesticCommds = new ArrayList<>();
        stockIndexCommds = new ArrayList<>();
        setList = new HashSet<>();
    }

    public void setAllDatas(String key, OApiEntity oApiEntity) {
        this.oApiEntity = oApiEntity;
        this.notifyDataSetChanged();
    }

    public void setForeignDatas(String key, List<OApiEntity.ForeignCommdsBean> foreignCommds) {
        this.key = key;
        this.foreignCommds = foreignCommds;
        this.notifyDataSetChanged();
    }

    public void setDomesDatas(String key, List<OApiEntity.DomesticCommdsBean> domesticCommds) {
        this.key = key;
        this.domesticCommds = domesticCommds;
        this.notifyDataSetChanged();
    }

    public void setStockDatas(String key, List<OApiEntity.StockIndexCommdsBean> stockIndexCommds) {
        this.key = key;
        this.stockIndexCommds = stockIndexCommds;
        this.notifyDataSetChanged();
    }
    public void setDigitalDatas(String key, List<OApiEntity.DigitalCommdsBean> digitalCommdsBeans) {
        this.key = key;
        this.digitalCommdsBeans = digitalCommdsBeans;
        this.notifyDataSetChanged();
    }
    public void setDatas(String key, List<String> datas) {
        this.key = key;
        this.datas = datas;
        this.notifyDataSetChanged();
    }


    public void setDatas(Set<String> addList) {
        this.setList = addList;
        this.notifyDataSetChanged();
    }


    public void addDatas(List<String> datas) {
        this.datas.addAll(datas);
        isLoadMore = false;
        this.notifyDataSetChanged();
    }

    public void startLoad() {
        isLoadMore = true;
        this.notifyDataSetChanged();
    }

    public void stopLoad() {
        isLoadMore = false;
        this.notifyDataSetChanged();
    }


    public void notifyItem(List<String> datas) {
        this.oldDatas = datas;
        this.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(context).inflate(R.layout.o_item_add_market_layout, parent, false);
            holder = new MyViewHolder(view);
            return holder;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.item_foot_layout, parent, false);
        holder = new ProgressViewHoler(view);


        return holder;


    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && isLoadMore) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            String s = datas.get(position);
            String[] split = s.split(",");

            String marketName = null;

            String marketTime = null;

            if (key.equals(OUserConfig.P_FOREIGN)) {
                for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                    if (split[0].startsWith(foreign.getCode())) {
                        marketName = foreign.getName() + split[0];
                        String niteWarningTime = foreign.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = foreign.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = foreign.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }

                    }
                }

            } else if (key.equals(OUserConfig.P_STOCKINDEX)) {
                for (OApiEntity.StockIndexCommdsBean stockIndex : stockIndexCommds) {
                    if (split[0].startsWith(stockIndex.getCode())) {
                        marketName = stockIndex.getName() + split[0];
                        String niteWarningTime = stockIndex.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = stockIndex.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = stockIndex.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }

                    }
                }

            } else if (key.equals(OUserConfig.P_DOMESTIC)) {
                for (OApiEntity.DomesticCommdsBean domestic : domesticCommds) {
                    if (split[0].startsWith(domestic.getCode())) {
                        marketName = domestic.getName() + split[0];
                        String niteWarningTime = domestic.getNiteWarningTime();

                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = domestic.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = domestic.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

            }
            else if (key.equals(OUserConfig.P_DIGITAL)) {
                for (OApiEntity.DigitalCommdsBean digitalCommdsBean : digitalCommdsBeans) {
                    if (split[0].startsWith(digitalCommdsBean.getCode())) {
                        marketName = digitalCommdsBean.getName() + split[0];
                        String niteWarningTime = digitalCommdsBean.getNiteWarningTime();

                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = digitalCommdsBean.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = digitalCommdsBean.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

            }
            else if (key.equals(OUserConfig.P_ALLDEX)) {
                List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
                for (OApiEntity.ForeignCommdsBean data : foreignCommds) {
                    if (split[0].startsWith(data.getCode())) {
                        marketName = data.getName() + split[0];

                        String niteWarningTime = data.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

                List<OApiEntity.StockIndexCommdsBean> stockIndexCommds = oApiEntity.getStockIndexCommds();
                for (OApiEntity.StockIndexCommdsBean data : stockIndexCommds) {
                    if (split[0].startsWith(data.getCode())) {
                        marketName = data.getName() + split[0];

                        String niteWarningTime = data.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }
                List<OApiEntity.DomesticCommdsBean> domesticCommds = oApiEntity.getDomesticCommds();
                for (OApiEntity.DomesticCommdsBean data : domesticCommds) {
                    if (split[0].startsWith(data.getCode())) {
                        marketName = data.getName() + split[0];

                        String niteWarningTime = data.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

                List<OApiEntity.DigitalCommdsBean> digitalCommds = oApiEntity.getDigitalCommds();
                for (OApiEntity.DigitalCommdsBean data : digitalCommds) {
                    if (split[0].startsWith(data.getCode())) {
                        marketName = data.getName() + split[0];

                        String niteWarningTime = data.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = data.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

            }

            ((MyViewHolder) holder).text_name.setText(marketName);

            ((MyViewHolder) holder).text_time.setText(marketTime);


            String change = split[1];

            String last = split[2];


            String newprice = split[3];


            Integer flag = Integer.valueOf(change);


            double v = Double.valueOf(last);
            double v1 = Double.valueOf(newprice);


            String a = String.valueOf((v - v1) / v * 100);

            String numberFormat2 = getNumberFormat2(a);


            String percent = null;
            if (flag == 1) {

                percent = "+" + numberFormat2 + "%";

                //((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.o_market_red);
                ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.redcolor));

            } else if (flag == -1) {

                percent = numberFormat2 + "%";

                // ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.o_market_green);
                ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.greencolor));

            } else if (flag == 0) {

                percent = numberFormat2 + "%";

                // ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.o_market_gray);
                ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.o_text_3433));

            }
            ((MyViewHolder) holder).text_high.setText(percent);

            ((MyViewHolder) holder).text_last.setText(last);




            String string = SPUtils.getString(OUserConfig.P_NIGHT);

            if (string.equals("")) {
                reFresh(oldDatas, split, last, holder, context.getResources().getColor(R.color.o_f8f3), context.getResources().getColor(R.color.o_bar_white), context.getResources().getColor(R.color.o_f0f6), 1f);
            } else {
                if (string.equals("night")) {
                    reFresh(oldDatas, split, last, holder, context.getResources().getColor(R.color.o_f8f3_night), context.getResources().getColor(R.color.o_bar_white_night), context.getResources().getColor(R.color.o_f0f6_night), 1f);

                } else if (string.equals("day")) {
                    reFresh(oldDatas, split, last, holder, context.getResources().getColor(R.color.o_f8f3), context.getResources().getColor(R.color.o_bar_white), context.getResources().getColor(R.color.o_f0f6), 1f);

                }
            }



           /* if (oldDatas.size() != 0) {
                for (int i = 0; i < oldDatas.size(); i++) {
                    String s1 = oldDatas.get(i);
                    String[] split1 = s1.split(",");
                    if (split[0].equals(split1[0])) {
                        //  Log.d("print", "onBindViewHolder:老：" + split1[2] + "-------新 " + last);
                        int compare = Double.compare(Double.valueOf(last), Double.valueOf(split1[2]));
                        switch (compare) {
                            case 1:

                                ((MyViewHolder) holder).layout_bar.setBackgroundResource(R.drawable.o_bg_zhang);

                                break;
                            case 0:
                                ((MyViewHolder) holder).layout_bar.setBackgroundColor(Color.WHITE);

                                break;
                            case -1:
                                ((MyViewHolder) holder).layout_bar.setBackgroundResource(R.drawable.o_bg_die);

                                break;
                        }


                    }


                    ObjectAnimator animator = ObjectAnimator.ofFloat(((MyViewHolder) holder).layout_bar, "alpha", 1f, 0f);
                    animator.setDuration(3000);
                    animator.start();

                }
            }
*/
            ((MyViewHolder) holder).img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("print", "onClick:360: "+setList);

                    if (setList!=null&setList.contains(split[0])) {
                        Log.d("print", "onClick:362: "+setList);
                        ((MyViewHolder) holder).img_add.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_tianjia));
                        setList.remove(split[0]);

                    } else {
                        Log.d("print", "onClick:366: "+setList);

                        ((MyViewHolder) holder).img_add.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_xuanze));
                        setList.add(split[0]);
                    }

                    if (onItemClick != null) {
                        // onItemClick.onSuccessListener(datas.get(position));
                        SPUtils.putString(OUserConfig.MINEDEX, setList.toString());

                        //AppContext.getInstance().setMinelist(setList);

                    }
                }

            });

            if (setList.contains(split[0])) {
                ((MyViewHolder) holder).img_add.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_xuanze));

            } else {
                ((MyViewHolder) holder).img_add.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_tianjia));

            }





        }


    }
    private void reFresh(List<String> oldDatas, String[] split, String last, RecyclerView.ViewHolder holder, int redColor, int bgcolor, int greenColor, float fl) {

        if (((MyViewHolder) holder).layout_bar==null){
            return;
        }
        if (oldDatas.size() != 0) {
            for (int i = 0; i < oldDatas.size(); i++) {
                String s1 = oldDatas.get(i);
                String[] split1 = s1.split(",");
                if (split[0].equals(split1[0])) {
                    //Log.d("print", "onBindViewHolder:老：" + split1[2] + "-------新 " + last);
                    int compare = Double.compare(Double.valueOf(last), Double.valueOf(split1[2]));
                    switch (compare) {
                        case 1:

                            ((MyViewHolder) holder).layout_bar.setBackgroundColor(redColor);

                            break;
                        case 0:
                            ((MyViewHolder) holder).layout_bar.setBackgroundColor(bgcolor);

                            break;
                        case -1:
                            ((MyViewHolder) holder).layout_bar.setBackgroundColor(greenColor);

                            break;
                    }
                }
                ObjectAnimator animator = ObjectAnimator.ofFloat(((MyViewHolder) holder).layout_bar, "alpha", fl, 0f);
                animator.setDuration(3000);
                animator.start();


            }
        }


    }
    public static String getNumberFormat2(String value) {
        double v = Double.parseDouble(value);
        DecimalFormat mFormat = new DecimalFormat("#0.00");
        return mFormat.format(v);
    }

    public String timesOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }


    @Override
    public int getItemCount() {
        if (isLoadMore) {
            return datas.size() + 1;
        }
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_name, text_last, text_time, text_high;
        RelativeLayout layout_bg, layout_bar;
        ImageView img_add;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_last = (TextView) itemView.findViewById(R.id.text_last);
            text_high = itemView.findViewById(R.id.text_high);
            layout_bg = itemView.findViewById(R.id.layout_bg);
            layout_bar = itemView.findViewById(R.id.layout_bar);
            text_time = itemView.findViewById(R.id.text_time);
            img_add = itemView.findViewById(R.id.img_add);


        }


    }

    public static class ProgressViewHoler extends RecyclerView.ViewHolder {
        public ProgressBar bar;

        public ProgressViewHoler(View itemView) {
            super(itemView);
            bar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }


    public interface OnItemClick {
        void onSuccessListener(String content);

    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
