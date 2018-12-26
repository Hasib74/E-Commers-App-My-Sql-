package com.example.dcl.onlineshopapp.Utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.dcl.onlineshopapp.Adepter.FavoriteAdepter;
import com.example.dcl.onlineshopapp.Adepter.FavoriteViewHolder;

public class RecycularTouchHelper extends ItemTouchHelper.SimpleCallback {
    RecycularItemTouchHelperListener listener;

    public RecycularTouchHelper(int dragDirs, int swipeDirs,RecycularItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener=listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener!=null){
               listener.onSwipe(viewHolder,direction,viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        View foregroundView=((FavoriteViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().clearView(foregroundView);

      //  super.clearView(recyclerView, viewHolder);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null){
            View foregroundView=((FavoriteViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
       /// super.onSelectedChanged(viewHolder, actionState);

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView=((FavoriteViewHolder)viewHolder).view_foreground;
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView=((FavoriteViewHolder)viewHolder).view_foreground;
     //   super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }
}
