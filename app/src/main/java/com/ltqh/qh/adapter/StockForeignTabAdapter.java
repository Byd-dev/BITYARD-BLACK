package com.ltqh.qh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.entity.GoldlistEntity;
import com.ltqh.qh.entity.StockEntity;
import com.ltqh.qh.entity.StockForeignEntity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockForeignTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<StockForeignEntity.DataBeanX.DataBean> datas;


    private Context context;

    public StockForeignTabAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<StockForeignEntity.DataBeanX.DataBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<StockForeignEntity.DataBeanX.DataBean> datas) {
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_stocktab_layout, parent, false);
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
            ((MyViewHolder) holder).text_name.setText(datas.get(position).getSymbol());

            String last = datas.get(position).getPrice();
            String high = datas.get(position).getHigh();
            String low = datas.get(position).getLow();
            double v = Double.parseDouble(last);
            double v2 = Double.parseDouble(high);
            double v3 = Double.parseDouble(low);

            String numberFormat21 = getNumberFormat2(last);
            ((MyViewHolder) holder).text_last.setText(numberFormat21);
            ((MyViewHolder) holder).text_low.setText(getNumberFormat2(low));
            ((MyViewHolder) holder).text_high.setText(datas.get(position).getAmplitude());





            String bid = datas.get(position).getOpen();




            double v1 = Double.parseDouble(bid);

            if (v > v1) {
                ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.redcolor));
                ((MyViewHolder) holder).img_zhangdie.setBackgroundResource(R.mipmap.zhang);
             //   ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.gradient_red);
                ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.redcolor));
            } else if (v == v1) {
                ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.greencolor));
                ((MyViewHolder) holder).img_zhangdie.setBackgroundResource(R.mipmap.die);
              //  ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.gradient_green);
                ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.text_secondcolor));


            } else if (v < v1) {
                ((MyViewHolder) holder).text_last.setTextColor(context.getResources().getColor(R.color.greencolor));
                ((MyViewHolder) holder).img_zhangdie.setBackgroundResource(R.mipmap.die);
               // ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.gradient_green);
                ((MyViewHolder) holder).text_high.setTextColor(context.getResources().getColor(R.color.greencolor));

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
        TextView text_name, text_last, text_low, text_high;
        ImageView img_zhangdie;
        LinearLayout layout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_last = (TextView) itemView.findViewById(R.id.text_last);
            text_low = itemView.findViewById(R.id.text_low);
            text_high = itemView.findViewById(R.id.text_high);
            img_zhangdie = itemView.findViewById(R.id.img_zhangdie);
            layout_bg = itemView.findViewById(R.id.layout_bg);

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
        void onSuccessListener(GoldlistEntity.DataBean content);

    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
