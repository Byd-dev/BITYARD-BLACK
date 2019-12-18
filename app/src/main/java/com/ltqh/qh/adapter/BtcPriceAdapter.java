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
import com.ltqh.qh.entity.BtcPriceEntity;
import com.ltqh.qh.entity.BtcRankEntity;

import java.util.ArrayList;
import java.util.List;

public class BtcPriceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BtcPriceEntity.DataBean> datas;
    private int type=0;

    public BtcPriceAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }
    public void setDatas(List<BtcPriceEntity.DataBean> datas){
        this.datas=datas;
        this.notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.item_btcprice_layout, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            ((MyViewHolder) holder).text_name.setText(datas.get(position).getCn_name()+"("+datas.get(position).getSymbol()+")");
            ((MyViewHolder) holder).text_price.setText(datas.get(position).getCny_price());
            String change = datas.get(position).get_$24_change();
            ((MyViewHolder) holder).text_change.setText(change);
            ((MyViewHolder) holder).text_turn.setText(datas.get(position).get_$24_turnover());

            Glide.with(context.getApplicationContext())
                    .load(datas.get(position).getImg())
                    .asBitmap()
                    .centerCrop()
                    .into(((MyViewHolder) holder).img_bg);

            if (change.contains("-")){
                ((MyViewHolder) holder).img_zhangdie.setImageResource(R.mipmap.die);
                ((MyViewHolder) holder).text_price.setTextColor(context.getResources().getColor(R.color.greencolor));

            }else {
                ((MyViewHolder) holder).img_zhangdie.setImageResource(R.mipmap.zhang);
                ((MyViewHolder) holder).text_price.setTextColor(context.getResources().getColor(R.color.redcolor));

            }


        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_name, text_change,text_price,text_turn;
        ImageView img_bg,img_zhangdie;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_name =  itemView.findViewById(R.id.text_name);
            text_change =  itemView.findViewById(R.id.text_change);
            text_price = itemView.findViewById(R.id.text_price);
            text_turn=itemView.findViewById(R.id.text_turn);
            img_zhangdie=itemView.findViewById(R.id.img_zhangdie);

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
