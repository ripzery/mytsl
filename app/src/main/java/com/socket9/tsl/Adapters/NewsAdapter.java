package com.socket9.tsl.Adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socket9.tsl.ModelEntities.NewsEventEntity;
import com.socket9.tsl.R;

import java.util.List;

/**
 * Created by visit on 10/5/15 AD.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsEventEntity> newsList;
    private OnCardClickListener listener;

    public NewsAdapter(List<NewsEventEntity> newsEntities) {
        newsList = newsEntities;
    }

    public void setOnCardClickListener(OnCardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_news_event, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onCardClick(newsList.get(parent.indexOfChild(itemView)));
            }
        });
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        boolean isBlue = newsList.get(position).getType().equalsIgnoreCase("service");
        holder.tvTag.setBackgroundColor(ContextCompat.getColor(holder.tvTag.getContext(), isBlue ? R.color.colorPrimary : R.color.colorTextSecondary));
        holder.tvTitle.setText(newsList.get(position).getTitleEn());
        holder.tvTag.setText("Auto " + newsList.get(position).getType());
        Glide.with(holder.ivPhoto.getContext()).load(newsList.get(position).getPic()).centerCrop().into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTag;
        TextView tvTitle;
        ImageView ivPhoto;

        public NewsViewHolder(View itemView) {
            super(itemView);
            this.tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        }
    }

    public interface OnCardClickListener {
        void onCardClick(NewsEventEntity viewHolder);
    }
}
