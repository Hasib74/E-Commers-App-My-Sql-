package com.example.dcl.onlineshopapp.Utils;

import android.support.v7.widget.RecyclerView;

public interface RecycularItemTouchHelperListener {

    void onSwipe(RecyclerView.ViewHolder viewHolder,int direction,int position);

}
