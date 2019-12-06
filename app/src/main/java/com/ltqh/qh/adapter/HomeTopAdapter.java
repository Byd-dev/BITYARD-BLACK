package com.ltqh.qh.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OApiEntity;
import com.ltqh.qh.operation.utils.ODateUtil;
import com.ltqh.qh.operation.utils.OUtil;
import com.ltqh.qh.utils.SPUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<String> datas;
    private List<String> oldDatas;

    private String lastPrice;

    private List<String> dataList;

    private List<String> dataList2;

    private OApiEntity oApiEntity;
    private List<OApiEntity.ForeignCommdsBean> foreignCommds;
    private List<OApiEntity.DomesticCommdsBean> domesticCommds;
    private List<OApiEntity.StockIndexCommdsBean> stockIndexCommds;
    private List<OApiEntity.DigitalCommdsBean> digitalCommdsBeans;
    private Context context;
    private String key;
    private boolean openTradeTime;
    private boolean openTradeTime1;
    private boolean openTradeTime2;

    private String isUpDown="up";

    public HomeTopAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        oldDatas = new ArrayList<>();
        oApiEntity = new OApiEntity();
        foreignCommds = new ArrayList<>();
        domesticCommds = new ArrayList<>();
        stockIndexCommds = new ArrayList<>();
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

    public void setDigitalDatas(String key, List<OApiEntity.DigitalCommdsBean> digitalCommdsBeans) {
        this.key = key;
        this.digitalCommdsBeans = digitalCommdsBeans;
        this.notifyDataSetChanged();
    }

    public void setDomesDatas(String key, List<OApiEntity.DomesticCommdsBean> domesticCommds) {
        this.key = key;
        this.domesticCommds = domesticCommds;
        this.notifyDataSetChanged();
    }

    public void setIsUpDown(String isUpDown){
        this.isUpDown=isUpDown;
    }

    public void setStockDatas(String key, List<OApiEntity.StockIndexCommdsBean> stockIndexCommds) {
        this.key = key;
        this.stockIndexCommds = stockIndexCommds;
        this.notifyDataSetChanged();
    }

    public void setDatas(String key, List<String> datas) {
        this.key = key;
        this.datas = datas;
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

            View view = LayoutInflater.from(context).inflate(R.layout.item_hometop_layout, parent, false);
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

            String marketCode=null;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            String change = null;
            String last=null;
            String newprice=null;
            change = split[1];
            last = split[2];
            newprice = split[3];




            Integer flag = Integer.valueOf(change);

            double v = Double.valueOf(last);
            double v1 = Double.valueOf(newprice);
            String a = String.valueOf((v - v1) / v * 100);
            double sub = OUtil.sub(v, v1);

            String numberFormat2 = getNumberFormat2(a);


            if (key.equals(OUserConfig.P_FOREIGN)) {
                for (OApiEntity.ForeignCommdsBean foreign : foreignCommds) {
                    if (split[0].startsWith(foreign.getCode())) {
                        marketName = foreign.getName();
                        marketCode=split[0];
                        String niteWarningTime = foreign.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(foreign.getAmTradeTime()), sdf.parse(foreign.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(foreign.getPmTradeTime()), sdf.parse(foreign.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(foreign.getNiteTradeTime()), sdf.parse(foreign.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                        marketName = stockIndex.getName();
                        marketCode=split[0];
                        String niteWarningTime = stockIndex.getNiteWarningTime();
                        String am = niteWarningTime.substring(0, 2);
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(stockIndex.getAmTradeTime()), sdf.parse(stockIndex.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(stockIndex.getPmTradeTime()), sdf.parse(stockIndex.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(stockIndex.getNiteTradeTime()), sdf.parse(stockIndex.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                        marketName = domestic.getName() ;
                        marketCode= split[0];
                        String niteWarningTime = domestic.getNiteWarningTime();
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(domestic.getAmTradeTime()), sdf.parse(domestic.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(domestic.getPmTradeTime()), sdf.parse(domestic.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(domestic.getNiteTradeTime()), sdf.parse(domestic.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = domestic.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = domestic.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

            } else if (key.equals(OUserConfig.P_DIGITAL)) {
                for (OApiEntity.DigitalCommdsBean digitalCommdsBean : digitalCommdsBeans) {
                    if (split[0].startsWith(digitalCommdsBean.getCode())) {

                        marketName = digitalCommdsBean.getName() ;
                        marketCode= split[0].replaceAll("[^a-z^A-Z]", "");
                        String niteWarningTime = digitalCommdsBean.getNiteWarningTime();
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(digitalCommdsBean.getAmTradeTime()), sdf.parse(digitalCommdsBean.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(digitalCommdsBean.getPmTradeTime()), sdf.parse(digitalCommdsBean.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(digitalCommdsBean.getNiteTradeTime()), sdf.parse(digitalCommdsBean.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String am = niteWarningTime.substring(0, 2);

                        int i = Integer.valueOf(am);
                        if (i <= 5) {
                            marketTime = digitalCommdsBean.getAmOpenTime().substring(0, 5) + "~次日" + niteWarningTime.substring(0, 5);
                        } else {
                            marketTime = digitalCommdsBean.getAmOpenTime().substring(0, 5) + "~夜间" + niteWarningTime.substring(0, 5);
                        }
                    }
                }

            } else if (key.equals(OUserConfig.P_ALLDEX) || key.equals(OUserConfig.P_MINEDEX)) {
                List<OApiEntity.ForeignCommdsBean> foreignCommds = oApiEntity.getForeignCommds();
                for (OApiEntity.ForeignCommdsBean data : foreignCommds) {
                    if (split[0].startsWith(data.getCode())) {
                        marketName = data.getName() ;
                        marketCode= split[0].replaceAll("[^a-z^A-Z]", "");
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getAmTradeTime()), sdf.parse(data.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getPmTradeTime()), sdf.parse(data.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getNiteTradeTime()), sdf.parse(data.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                        marketName = data.getName() ;
                        marketCode=split[0];
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getAmTradeTime()), sdf.parse(data.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getPmTradeTime()), sdf.parse(data.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getNiteTradeTime()), sdf.parse(data.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                        marketName = data.getName() ;
                        marketCode= split[0];
                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getAmTradeTime()), sdf.parse(data.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getPmTradeTime()), sdf.parse(data.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getNiteTradeTime()), sdf.parse(data.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                        marketName = data.getName() ;
                        marketCode= split[0].replaceAll("[^a-z^A-Z]", "");

                        try {
                            openTradeTime = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getAmTradeTime()), sdf.parse(data.getAmWarningTime()));
                            openTradeTime1 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getPmTradeTime()), sdf.parse(data.getPmWarningTime()));
                            openTradeTime2 = ODateUtil.isOpenTradeTime(sdf.parse(sdf.format(new Date())), sdf.parse(data.getNiteTradeTime()), sdf.parse(data.getNiteWarningTime()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

            ((MyViewHolder) holder).text_name.setText(marketName+"/");

            ((MyViewHolder) holder).text_time.setText(marketCode);


            String percent = null;
            String sub_str=null;


            if (ODateUtil.isWeekend() == false & openTradeTime == false & openTradeTime1 == false & openTradeTime2 == false) {
                ((MyViewHolder) holder).text_high.setText("休市");
                ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.o_text_b2b6c1));


            } else {

                if (flag == 1) {
                    sub_str="+"+sub;

                    percent = "+" + numberFormat2 + "%";
                 //   ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.redcolor));

                    ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.redcolor));

                    ((MyViewHolder) holder).img_up_down.setBackgroundResource(R.mipmap.img_up);
                } else if (flag == -1) {
                    sub_str=sub+"";

                    percent = numberFormat2 + "%";
                 //   ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.greencolor));

                    ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.greencolor));
                    ((MyViewHolder) holder).img_up_down.setBackgroundResource(R.mipmap.img_down);


                } else if (flag == 0) {
                    sub_str=sub+"";

                    percent = numberFormat2 + "%";
                    ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.o_text_b2b6c1));

                    ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.o_text_b2b6c1));


                }
                if (isUpDown.equals("up")){
                    ((MyViewHolder) holder).text_high.setText(percent);
                }else if (isUpDown.equals("down")){
                    ((MyViewHolder) holder).text_high.setText(sub_str);

                }



            }


            ((MyViewHolder) holder).text_last.setText("¥ "+last);


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
            //暂时隐藏分割线
           /* if (position==2){
                ((MyViewHolder) holder).view_line.setVisibility(View.GONE);
            }*/



       /*     if (oldDatas.size() != 0) {
                for (int i = 0; i < oldDatas.size(); i++) {
                    String s1 = oldDatas.get(i);
                    String[] split1 = s1.split(",");
                    if (split[0].equals(split1[0])) {
                        //Log.d("print", "onBindViewHolder:老：" + split1[2] + "-------新 " + last);
                        int compare = Double.compare(Double.valueOf(last), Double.valueOf(split1[2]));
                        switch (compare) {
                            case 1:


                                ((MyViewHolder) holder).layout_bar.setBackgroundResource(R.drawable.o_bg_zhang);

                                break;
                            case 0:
                                ((MyViewHolder) holder).layout_bar.setBackgroundColor(context.getResources().getColor(R.color.o_bar_white));

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
            }*/


        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onSuccessListener(datas.get(position));
                }
            }
        });
    }

    private void reFresh(List<String> oldDatas, String[] split, String last, RecyclerView.ViewHolder holder, int redColor, int bgcolor, int greenColor, float fl) {

        if (((MyViewHolder) holder).layout_bar == null) {
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
        RelativeLayout  layout_bar;
        LinearLayout layout_bg;
        ImageView img_up_down;
        View view_line;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            text_last = itemView.findViewById(R.id.text_last);
            text_high = itemView.findViewById(R.id.text_high);
            layout_bar = itemView.findViewById(R.id.layout_bar);
            text_time = itemView.findViewById(R.id.text_time);
            layout_bg=itemView.findViewById(R.id.layout_bg);
            img_up_down=itemView.findViewById(R.id.img_up_down);
            view_line=itemView.findViewById(R.id.view_line);

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
        void onSuccessListener(String code);

    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
