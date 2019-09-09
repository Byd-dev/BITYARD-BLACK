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
import com.ltqh.qh.operation.entity.OHotEntity;
import com.ltqh.qh.view.XCRoundRectImageView;

import java.util.ArrayList;
import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<OHotEntity.NewsListBean> datas;

    public AlertsAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<OHotEntity.NewsListBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.item_alerts_layout, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).text_title.setText(datas.get(position).getTitle());
            ((MyViewHolder) holder).text_time.setText(datas.get(position).getDate());
            Glide.with(context.getApplicationContext())
                    .load(datas.get(position).getThumb())
                    .asBitmap()
                    .centerCrop()
                    .into(((MyViewHolder) holder).img_bg);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_title, text_time;
        XCRoundRectImageView img_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_time = (TextView) itemView.findViewById(R.id.text_time);
            img_bg = itemView.findViewById(R.id.img_bg);

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
        void onSuccessListener(OHotEntity.NewsListBean newsListBean);

    }
}
