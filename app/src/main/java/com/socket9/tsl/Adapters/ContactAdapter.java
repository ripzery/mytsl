package com.socket9.tsl.Adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socket9.tsl.ModelEntities.ContactEntity;
import com.socket9.tsl.R;

import java.util.List;

/**
 * Created by visit on 10/5/15 AD.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

    private OnContactClickListener listener;
    private List<ContactEntity> contactList;

    public ContactAdapter(List<ContactEntity> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_contact, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(contactList.get(parent.indexOfChild(itemView)));
            }
        });
        return new ContactViewHolder(itemView);
    }

    public void setContactListener(OnContactClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
//        holder.tvContent.setText(contactList.get(position).getAddress());
            holder.tvTitle.setText(contactList.get(position).getTitleEn());
        if(contactList.get(position).getSubTitle() != null)
            holder.tvContent.setText(contactList.get(position).getSubTitle());
        if(contactList.get(position).getIcon() != 0)
            holder.ivIcon.setImageDrawable(ContextCompat.getDrawable(holder.ivIcon.getContext(), contactList.get(position).getIcon()));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvContent;
        ImageView ivIcon;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
        }
    }

    public interface OnContactClickListener{
        void onClick(ContactEntity contactEntity);
    }
}
