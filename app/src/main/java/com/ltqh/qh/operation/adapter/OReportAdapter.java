package com.ltqh.qh.operation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltqh.qh.R;
import com.ltqh.qh.operation.activity.SecondActivity;
import com.ltqh.qh.operation.config.OUserConfig;
import com.ltqh.qh.operation.entity.OReportEntity;
import com.ltqh.qh.utils.DelTagsUtil;
import com.ltqh.qh.utils.SPUtils;
import com.ltqh.qh.view.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<OReportEntity.NoticesBean> datas;


    private Context context;

    public OReportAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void setDatas(List<OReportEntity.NoticesBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<OReportEntity.NoticesBean> datas) {
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

            View view = LayoutInflater.from(context).inflate(R.layout.o_item_report, parent, false);
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
            if (position == datas.size() - 1) {
                ((MyViewHolder) holder).viewLine.setVisibility(View.GONE);
            }

            ((MyViewHolder) holder).text_title.setText(datas.get(position).getTitle());
            ((MyViewHolder) holder).text_content.setText(datas.get(position).getContent());
            ((MyViewHolder) holder).text_title.setLineSpacing(0, 1.4f);
            String date = new SimpleDateFormat("MM-dd").format(datas.get(position).getTime());
            ((MyViewHolder) holder).text_publictime.setText(date);


            String string = SPUtils.getString(OUserConfig.P_NIGHT);

            if (string.equals("")) {

            } else {
                if (string.equals("night")) {
                    ((MyViewHolder) holder).webView.setBackgroundColor(context.getResources().getColor(R.color.o_bar_white_night));
                    String CSS_STYLE ="<style>* {font-size:16px;line-height:20px;}p {color:#B2B6C1;}</style>";
                    ((MyViewHolder) holder).webView.loadDataWithBaseURL(null, CSS_STYLE+datas.get(position).getContent(), "text/html", "utf-8", null);

                } else if (string.equals("day")) {
                    ((MyViewHolder) holder).webView.setBackgroundColor(context.getResources().getColor(R.color.o_bar_white));
                    ((MyViewHolder) holder).webView.loadDataWithBaseURL(null, datas.get(position).getContent(), "text/html", "utf-8", null);

                }
            }
            ((MyViewHolder) holder).webView.setVerticalScrollBarEnabled(false); //垂直不显示

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
        TextView text_title, text_publictime, text_content;
        WebView webView;
        View viewLine;


        public MyViewHolder(View itemView) {
            super(itemView);
            text_title = (TextView) itemView.findViewById(R.id.text_title);
            text_publictime = (TextView) itemView.findViewById(R.id.text_time);
            text_content = itemView.findViewById(R.id.text_content);
            webView = itemView.findViewById(R.id.webView);
            viewLine = itemView.findViewById(R.id.view_line);

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
        void onSuccessListener(OReportEntity.NoticesBean content);

    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
