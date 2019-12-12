package com.ltqh.qh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ltqh.qh.R;
import com.ltqh.qh.entity.AlertsEntity;
import com.ltqh.qh.entity.BtcRankEntity;
import com.ltqh.qh.view.XCRoundRectImageView;

import java.util.ArrayList;
import java.util.List;

public class BtcRankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BtcRankEntity.DataBean.RisingBean> datas;
    private List<BtcRankEntity.DataBean.FallingBean> fallingBeans;
    private int type=0;

    public BtcRankAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setRisData(List<BtcRankEntity.DataBean.RisingBean> datas,int type) {
        this.datas = datas;
        this.type=type;
        this.notifyDataSetChanged();
    }
    public void setFallData(List<BtcRankEntity.DataBean.FallingBean> datas,int type) {
        this.fallingBeans = datas;
        this.type=type;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.item_btcrank_layout, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            if (type==0){
                ((MyViewHolder) holder).text_name.setText(datas.get(position).getName());
                ((MyViewHolder) holder).text_price.setText(datas.get(position).getPricing());
                ((MyViewHolder) holder).text_rank.setText(datas.get(position).getRank()+"");

                Glide.with(context.getApplicationContext())
                        .load(datas.get(position).getImg())
                        .asBitmap()
                        .centerCrop()
                        .into(((MyViewHolder) holder).img_bg);
            }else if (type==1){
                ((MyViewHolder) holder).text_name.setText(fallingBeans.get(position).getName());
                ((MyViewHolder) holder).text_price.setText(fallingBeans.get(position).getPricing());
                ((MyViewHolder) holder).text_rank.setText(fallingBeans.get(position).getRank()+"");

                Glide.with(context.getApplicationContext())
                        .load(fallingBeans.get(position).getImg())
                        .asBitmap()
                        .centerCrop()
                        .into(((MyViewHolder) holder).img_bg);
            }




        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_name, text_rank,text_price;
        ImageView img_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
            text_rank = (TextView) itemView.findViewById(R.id.text_rank);
            text_price = (TextView) itemView.findViewById(R.id.text_price);

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
        void onSuccessListener(BtcRankEntity.DataBean.RisingBean newsListBean);

    }
}
