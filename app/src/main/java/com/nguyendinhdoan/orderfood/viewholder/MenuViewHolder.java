package com.nguyendinhdoan.orderfood.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nguyendinhdoan.orderfood.R;
import com.nguyendinhdoan.orderfood.listener.OnItemClickListener;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView menuImage;
    public TextView menuName;
    public ProgressBar progressBar;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        menuImage = itemView.findViewById(R.id.menu_image);
        menuName = itemView.findViewById(R.id.menu_name);
        progressBar = itemView.findViewById(R.id.progress_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClickListener(v, getAdapterPosition());
    }
}

