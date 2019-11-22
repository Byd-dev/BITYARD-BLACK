package com.ltqh.qh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ltqh.qh.R;
import com.ltqh.qh.entity.GubaEntity;
import com.ltqh.qh.entity.GuliaoEntity;
import com.ltqh.qh.view.CircleImageView;
import com.ltqh.qh.view.XCRoundRectImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuLiaolistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public boolean isLoadMore = false;


    private List<GuliaoEntity.DataBeanX.DataBean> datas;

    private HashMap<Integer,Boolean> map;

    private Context context;

    public GuLiaolistAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        map=new HashMap<>();
    }

    public void setDatas(List<GuliaoEntity.DataBeanX.DataBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void setSelect(HashMap<Integer,Boolean> map){
        this.map=map;
        this.notifyDataSetChanged();;
    }

    public void setItemDatas(List<GuliaoEntity.DataBeanX.DataBean> datas, int position) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(List<GuliaoEntity.DataBeanX.DataBean> datas) {
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

            View view = LayoutInflater.from(context).inflate(R.layout.item_guliaolist_layout, parent, false);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            //  Log.d("print", "onBindViewHolder:94: "+datas.get(position).getUser().getUser_nickname());
            GuliaoEntity.DataBeanX.DataBean.UserBean user = datas.get(position).getUser();
            if (user != null) {
                ((MyViewHolder) holder).text_username.setText(user.getUser_nickname());
                Glide.with(context.getApplicationContext())
                        .load(user.getAvatar())
                        .asBitmap()
                        .error(R.mipmap.user_icon)
                        .centerCrop()
                        .into(((MyViewHolder) holder).img_head);

            }

            ((MyViewHolder) holder).text_title.setText(datas.get(position).getPost_title());
            ((MyViewHolder) holder).text_content.setText(datas.get(position).getPost_content());
            if (((MyViewHolder) holder).text_content.getMaxLines() > 15) {

            }


            ((MyViewHolder) holder).text_content.setLineSpacing(0, 1.4f);
           // ((MyViewHolder) holder).text_comment.setText("评论" + "(" + datas.get(position).getComment_count() + ")");
            int comment_count = datas.get(position).getComment_count();
            if (comment_count>0){
                ((MyViewHolder) holder).img_zan.setImageResource(R.mipmap.chat_zan);
            }else {
                ((MyViewHolder) holder).img_zan.setImageResource(R.mipmap.chat_zan_normal);

            }

            ((MyViewHolder) holder).text_comment.setText( comment_count +"");

            int post_like = datas.get(position).getPost_status();
            if (post_like == 1) {
                ((MyViewHolder) holder).text_dianzan.setTextColor(context.getResources().getColor(R.color.maincolor));
            } else {
                ((MyViewHolder) holder).text_dianzan.setTextColor(context.getResources().getColor(R.color.text_secondcolor));
            }

            ((MyViewHolder) holder).text_dianzan.setText("赞");
            int report_count = datas.get(position).getReport_count();

            if (report_count>0){
                ((MyViewHolder) holder).img_cai.setImageResource(R.mipmap.chat_cai);
            }else {
                ((MyViewHolder) holder).img_cai.setImageResource(R.mipmap.chat_cai_normal);

            }

            //  ((MyViewHolder) holder).text_readcount.setText("阅读" + "(" + datas.get(position).getReport_count() + ")");
            ((MyViewHolder) holder).text_readcount.setText(report_count +"");

            ((MyViewHolder) holder).text_publish_time.setText(datas.get(position).getPublished_time());


        }
    }

    @Override
    public int getItemCount() {
        if (isLoadMore) {
            return datas.size() + 1;
        }
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_title, text_content, text_username, text_comment, text_dianzan, text_readcount, text_publish_time,img_jubao;
        public CircleImageView img_head;
        private ImageView img_zan,img_cai;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            text_content = itemView.findViewById(R.id.text_content);
            text_username = itemView.findViewById(R.id.text_username);
            img_head = itemView.findViewById(R.id.img_head);

            text_comment = itemView.findViewById(R.id.text_comment);
            text_dianzan = itemView.findViewById(R.id.text_favorite);
            text_readcount = itemView.findViewById(R.id.text_readcount);
            text_publish_time = itemView.findViewById(R.id.text_publish_time);
            img_jubao=itemView.findViewById(R.id.img_jubao);
            img_zan=itemView.findViewById(R.id.img_zan);
            img_cai=itemView.findViewById(R.id.img_cai);




            text_dianzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDianZanItemClick != null) {

                        onDianZanItemClick.onSuccessListener(datas.get(getPosition()), getAdapterPosition());
                    }
                }
            });

            text_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onSuccessListener(datas.get(getPosition()), getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemDetailClick.onSuccessListener(datas.get(getPosition()), getAdapterPosition());
                }
            });

            img_jubao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("print", "onClick:是否关注:  "+datas.get(getPosition()).isFocus());
                  /*  if (datas.get(getPosition()).isFocus()==false){

                        img_jubao.setText(context.getResources().getString(R.string.text_attention));
                        datas.get(getPosition()).setFocus(true);
                    }else if (datas.get(getPosition()).isFocus()==true){
                        img_jubao.setText(context.getResources().getString(R.string.text_focus));
                        datas.get(getPosition()).setFocus(false);
                    }*/

                    //这是举报
                    if (onJuBaoItemClick!=null){
                        onJuBaoItemClick.onSuccessListener(datas.get(getPosition()),getAdapterPosition());
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
        void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position);

    }

    public interface OnDianZanItemClick {
        void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position);

    }

    public interface OnItemDetailClick {
        void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position);

    }
    public interface OnJuBaoItemClick{
        void onSuccessListener(GuliaoEntity.DataBeanX.DataBean content, int position);

    }
    private OnItemClick onItemClick;
    private OnDianZanItemClick onDianZanItemClick;
    private OnItemDetailClick onItemDetailClick;
    private OnJuBaoItemClick onJuBaoItemClick;

    public void setOnJuBaoItemClick(OnJuBaoItemClick onJuBaoItemClick) {
        this.onJuBaoItemClick = onJuBaoItemClick;
    }

    public void setOnItemDetailClick(OnItemDetailClick onItemDetailClick) {
        this.onItemDetailClick = onItemDetailClick;
    }

    public void setOnDianZanItemClick(OnDianZanItemClick onDianZanItemClick) {
        this.onDianZanItemClick = onDianZanItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
