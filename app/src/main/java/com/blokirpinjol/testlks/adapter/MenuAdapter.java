package com.blokirpinjol.testlks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blokirpinjol.testlks.model.Menu;

import java.util.List;
import com.blokirpinjol.testlks.R;

public class MenuAdapter  extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private List<Menu> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MenuAdapter(Context context, List<Menu> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.content_menu_detail, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Menu menu = mData.get(position);
        holder.lblNama.setText(menu.getName());
        holder.lblDesc.setText(menu.getDescription());
        holder.lblHarga.setText(String.valueOf(menu.getPrice()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblNama,lblDesc, lblHarga;

        ViewHolder(View itemView) {
            super(itemView);
            lblNama = itemView.findViewById(R.id.lblNama);
            lblDesc = itemView.findViewById(R.id.lblDesc);
            lblHarga = itemView.findViewById(R.id.lblHarga);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id).getName();
    }

    // allows clicks events to be caught
   public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
