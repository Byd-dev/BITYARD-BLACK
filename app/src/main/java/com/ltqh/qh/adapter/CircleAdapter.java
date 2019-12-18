package com.ltqh.qh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ltqh.qh.R;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<String> datas;


    private Context context;

    public CircleAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<String> datas) {
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_circle_layout, parent, false);
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
            ((MyViewHolder) holder).text_menu.setText(datas.get(position));
            switch (position){
                case 0:
                    ((MyViewHolder) holder).text_menu.setBackground(context.getResources().getDrawable(R.mipmap.bg1));
                    //((MyViewHolder) holder).text_menu.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                    break;

                case 1:
                    ((MyViewHolder) holder).text_menu.setBackground(context.getResources().getDrawable(R.mipmap.bg2));
                  //  ((MyViewHolder) holder).text_menu.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                    break;

                case 2:
                    ((MyViewHolder) holder).text_menu.setBackground(context.getResources().getDrawable(R.mipmap.bg3));

                    break;

                case 3:
                    ((MyViewHolder) holder).text_menu.setBackground(context.getResources().getDrawable(R.mipmap.bg4));
                   // ((MyViewHolder) holder).text_menu.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

                    break;
            }

        }
    }

    @Override
    public int getItemCount() {
        if (isLoadMore) {
            return datas.size() + 1;
        }
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_menu;
        ImageView img_star;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_menu = (TextView) itemView.findViewById(R.id.text_menu);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onSuccessListener(datas.get(getPosition()));
                    }
                }
            });

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
