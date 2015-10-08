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

import timber.log.Timber;

/**
 * Created by visit on 10/5/15 AD.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

    private OnContactClickListener listener;
    private List<ContactEntity> contactList;
    private static final int BASE_ID = 1000;

    public ContactAdapter(List<ContactEntity> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_contact, parent, false);

        return new ContactViewHolder(itemView);
    }

    public void setContactListener(OnContactClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
//        holder.tvContent.setText(contactList.get(position).getAddress());
            holder.tvTitle.setText(contactList.get(position).getTitleEn());
        if(contactList.get(position).getSubTitle() != null && contactList.get(position).getId() > BASE_ID) {
            holder.tvContent.setText(contactList.get(position).getSubTitle());
            holder.tvContent.setVisibility(View.VISIBLE);
        }
        if(contactList.get(position).getIcon() != 0)
            holder.ivIcon.setImageDrawable(ContextCompat.getDrawable(holder.ivIcon.getContext(), contactList.get(position).getIcon()));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvContent;
        ImageView ivIcon;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Timber.d("IndexOfChild : " + getAdapterPosition());
                    listener.onClick(contactList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnContactClickListener{
        void onClick(ContactEntity contactEntity);
    }
}
