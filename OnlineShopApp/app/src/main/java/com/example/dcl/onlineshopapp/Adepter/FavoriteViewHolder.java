package com.example.dcl.onlineshopapp.Adepter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dcl.onlineshopapp.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    ImageView favorite_IMAGE;
    TextView favorite_title,
            favorite_price,
            favorite_size,favorite_quantity;

    public RelativeLayout view_background;
    public  LinearLayout view_foreground;

    public FavoriteViewHolder(View itemView) {
        super(itemView);

        favorite_IMAGE=itemView.findViewById(R.id.favorite_image);
        favorite_title = itemView.findViewById(R.id.favorite_title);
        favorite_price=itemView.findViewById(R.id.favorite_price);
        //favorite_size=itemView.findViewById(R.id.favorite_t_sirt_size);
       // favorite_quantity=itemView.findViewById(R.id.favorite_cotton_quality);

        view_background=itemView.findViewById(R.id.view_background);

        view_foreground=itemView.findViewById(R.id.view_foreground);

    }
}
