package com.socket9.tsl.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socket9.tsl.ModelEntities.EventEntity;
import com.socket9.tsl.R;

import java.util.List;

/**
 * Created by visit on 10/5/15 AD.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private List<EventEntity> eventList;

    public EventAdapter(List<EventEntity> eventEntityList) {
        eventList = eventEntityList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_news_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.tvTitle.setText(eventList.get(position).getTitleEn());
        holder.tvTag.setText(eventList.get(position).getType());
        Glide.with(holder.ivPhoto.getContext()).load(eventList.get(position).getPic()).into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView tvTag;
        TextView tvTitle;
        ImageView ivPhoto;

        public EventViewHolder(View itemView) {
            super(itemView);
            this.tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        }
    }
}
