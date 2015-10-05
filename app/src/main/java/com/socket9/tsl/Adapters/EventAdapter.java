package com.socket9.tsl.Adapters;

import android.support.v4.content.ContextCompat;
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
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private List<NewsEventEntity> eventList;
    private OnCardClickListener listener;

    public EventAdapter(List<NewsEventEntity> NewsEventEntityList) {
        eventList = NewsEventEntityList;
    }

    public void setOnCardClickListener(OnCardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_news_event, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onCardClick(eventList.get(parent.indexOfChild(view)));
            }
        });
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        boolean isBlue = eventList.get(position).getType().equalsIgnoreCase("service");
        holder.tvTag.setBackgroundColor(ContextCompat.getColor(holder.tvTag.getContext(), isBlue ? R.color.colorPrimary : R.color.colorTextSecondary));
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

    public interface OnCardClickListener {
        void onCardClick(NewsEventEntity viewHolder);
    }
}
