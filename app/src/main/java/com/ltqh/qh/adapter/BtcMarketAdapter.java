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

import com.bumptech.glide.Glide;
import com.ltqh.qh.R;
import com.ltqh.qh.entity.BtcPriceEntity;

import java.util.ArrayList;
import java.util.List;

public class BtcMarketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BtcPriceEntity.DataBean> datas;
    private int type=0;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;
    public BtcMarketAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }
    public void setDatas(List<BtcPriceEntity.DataBean> datas){
        this.datas=datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<BtcPriceEntity.DataBean> datas) {
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

            View view = LayoutInflater.from(context).inflate(R.layout.item_btc_market_layout, parent, false);
            holder = new MyViewHolder(view);
            return holder;
        }


        View view = LayoutInflater.from(context).inflate(R.layout.item_foot_layout, parent, false);
        holder = new HoursAdapter.ProgressViewHoler(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).text_symbol.setText(datas.get(position).getSymbol());
            ((MyViewHolder) holder).text_name.setText(datas.get(position).getCn_name());
            ((MyViewHolder) holder).text_price.setText(datas.get(position).getCny_price());
            String change = datas.get(position).get_$24_change();
            ((MyViewHolder) holder).text_change.setText(change);
            ((MyViewHolder) holder).text_market_price.setText(datas.get(position).getMarket_price());

            Glide.with(context.getApplicationContext())
                    .load(datas.get(position).getImg())
                    .asBitmap()
                    .centerCrop()
                    .into(((MyViewHolder) holder).img_bg);


            if (change.contains("-")){
                ((MyViewHolder) holder).img_zhangdie.setImageResource(R.mipmap.die);
                ((MyViewHolder) holder).text_price.setTextColor(context.getResources().getColor(R.color.greencolor));
                ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.o_market_green);


            }else {
                ((MyViewHolder) holder).img_zhangdie.setImageResource(R.mipmap.zhang);
                ((MyViewHolder) holder).text_price.setTextColor(context.getResources().getColor(R.color.redcolor));
                ((MyViewHolder) holder).layout_bg.setBackgroundResource(R.drawable.o_market_red);

            }





        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && isLoadMore) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;

    }
    @Override
    public int getItemCount() {
        if (isLoadMore) {
            return datas.size() + 1;
        }
        return datas.size();
    }
    public static class ProgressViewHoler extends RecyclerView.ViewHolder {
        public ProgressBar bar;

        public ProgressViewHoler(View itemView) {
            super(itemView);
            bar = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_name,text_symbol, text_change,text_price,text_market_price;
        ImageView img_bg,img_zhangdie;
        LinearLayout layout_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name =  itemView.findViewById(R.id.text_name);
            text_symbol=itemView.findViewById(R.id.text_symbol);
            text_change =  itemView.findViewById(R.id.text_change);
            text_price = itemView.findViewById(R.id.text_price);
            text_market_price=itemView.findViewById(R.id.text_market_price);

            img_zhangdie=itemView.findViewById(R.id.img_zhangdie);
            layout_bg=itemView.findViewById(R.id.layout_bg);
            img_bg = itemView.findViewById(R.id.img_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick!=null){
                        onItemClick.onSuccessListener(datas.get(getPosition()));
                    }
                }
            });
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public  interface OnItemClick{
        void onSuccessListener(BtcPriceEntity.DataBean newsListBean);

    }
}
