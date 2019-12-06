package com.ltqh.qh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.utils.DelTagsUtil;

import java.util.ArrayList;
import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<String> datas;


    private Context context;

    public HoursAdapter(Context context) {
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

            View view = LayoutInflater.from(context).inflate(R.layout.item_hours_layout, parent, false);
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
        String content;
        String time;

        if (holder instanceof MyViewHolder) {
            String s = datas.get(position);
            String[] split = s.split("#");
            if (s.startsWith("1")) {
                content = split[2];
                time = split[1];
                ((MyViewHolder) holder).text_title.setText(content);
                ((MyViewHolder) holder).layout_stay.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).layout_stay2.setVisibility(View.VISIBLE);

                ((MyViewHolder) holder).text_qianzhi.setText("前值: " + split[3]);
                ((MyViewHolder) holder).text_yuqi.setText("预期: " + split[4]);
                ((MyViewHolder) holder).text_shiji.setText("实际: " + split[5]);
                ((MyViewHolder) holder).text_yingxiang.setText(split[7]);
                String star = split[6];
                switch (star) {


                    case "1":
                        ((MyViewHolder) holder).img_star.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_yikexing_xing));

                        break;

                    case "2":
                        ((MyViewHolder) holder).img_star.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_liangkexing_xing));

                        break;
                    case "3":
                        ((MyViewHolder) holder).img_star.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_sankexing_xing));

                        break;
                    case "4":
                        ((MyViewHolder) holder).img_star.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_sikexing_xing));

                        break;
                    case "5":
                        ((MyViewHolder) holder).img_star.setImageDrawable(context.getResources().getDrawable(R.mipmap.o_wukexing_xing));

                        break;
                }


            } else {
                ((MyViewHolder) holder).layout_stay.setVisibility(View.GONE);
                ((MyViewHolder) holder).layout_stay2.setVisibility(View.GONE);

                content = split[3];
                time = s.substring(15, 20);
            }


            String textFromHtml = DelTagsUtil.getTextFromHtml(content);
            ((MyViewHolder) holder).text_title.setText(textFromHtml);
            ((MyViewHolder) holder).text_publictime.setText(time);
            ((MyViewHolder) holder).text_title.setLineSpacing(0, 1.4f);


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
        TextView text_title, text_publictime, text_qianzhi, text_yuqi, text_shiji, text_yingxiang;
        ImageView img_star;
        RelativeLayout layout_stay, layout_stay2;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.text_content);
            text_publictime = (TextView) itemView.findViewById(R.id.text_time);
            text_qianzhi = (TextView) itemView.findViewById(R.id.text_qianzhi);
            text_yuqi = (TextView) itemView.findViewById(R.id.text_yuqi);
            text_shiji = (TextView) itemView.findViewById(R.id.text_shiji);

            img_star = itemView.findViewById(R.id.img_star);
            text_yingxiang = itemView.findViewById(R.id.text_yingxiang);

            layout_stay = itemView.findViewById(R.id.layout_stay);
            layout_stay2 = itemView.findViewById(R.id.layout_stay2);

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
