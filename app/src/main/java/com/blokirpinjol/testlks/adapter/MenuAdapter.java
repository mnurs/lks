package com.blokirpinjol.testlks.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blokirpinjol.testlks.model.Menu;

import java.util.List;
import com.blokirpinjol.testlks.R;
import com.blokirpinjol.testlks.service.App;
import com.blokirpinjol.testlks.ui.MenuAksiActivity;
import com.blokirpinjol.testlks.ui.MenuDetailActivity;

public class MenuAdapter  extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    private List<Menu> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MenuAdapter(Context context, List<Menu> data) {
        Log.d("MenuAdapter",String.valueOf(data.size()));
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
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final Menu menu = mData.get(position);
        final MenuDetailActivity menuDetailActivity = new MenuDetailActivity();
        holder.lblNama.setText(menu.getName());
        holder.lblDesc.setText(menu.getDescription());
        holder.lblHarga.setText(String.valueOf(menu.getPrice()));
        // Set onclicklistener pada view tvTitle (TextView)
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(menu.getId()));
                bundle.putString("name", String.valueOf(menu.getName()));
                bundle.putString("desc", String.valueOf(menu.getDescription()));
                bundle.putString("price", String.valueOf(menu.getPrice()));
                Intent intent = new Intent(App.getmContext(), MenuAksiActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDetailActivity.deleteMenu(String.valueOf(menu.getId()));
                mData.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblNama,lblDesc, lblHarga;
        ImageButton btnEdit,btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            lblNama = itemView.findViewById(R.id.lblNama);
            lblDesc = itemView.findViewById(R.id.lblDesc);
            lblHarga = itemView.findViewById(R.id.lblHarga);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("MenuAdapter","MASUK3");
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public int getItem(int id) {
        Log.d("MenuAdapter","MASUK5");
        return mData.get(id).getId();
    }

    // allows clicks events to be caught
   public void setClickListener(ItemClickListener itemClickListener) {
       Log.d("MenuAdapter","MASUK4");
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
